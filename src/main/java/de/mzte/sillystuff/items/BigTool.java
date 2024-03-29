package de.mzte.sillystuff.items;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class BigTool extends ToolItem {
    public final int radius, depth;
    public final List<Material> additionalMaterials;
    public static final HashMap<ToolType, List<Material>> materialsForToolTypes = new HashMap<>();
    private final AtomicBoolean brokeBlocks = new AtomicBoolean(false);

    static {
        //This Jank was mojang's idea, not mine!
        materialsForToolTypes.put(ToolType.AXE, Arrays.asList(Material.WOOD, Material.NETHER_WOOD, Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD));
        materialsForToolTypes.put(ToolType.PICKAXE, Arrays.asList(Material.IRON, Material.ANVIL, Material.ROCK));
        materialsForToolTypes.put(ToolType.SHOVEL, Arrays.asList(Material.SNOW, Material.SNOW_BLOCK));
    }

    public BigTool(float attackDamageIn,
                   float attackSpeedIn,
                   IItemTier tier,
                   Properties builder,
                   int radius,
                   int depth,
                   int durabilityMultiplier,
                   Material... additionalMaterials) {
        super(attackDamageIn, attackSpeedIn, new IItemTier() {
            @Override
            public int getMaxUses() {
                return tier.getMaxUses() * durabilityMultiplier;
            }

            @Override
            public float getEfficiency() {
                return tier.getEfficiency();
            }

            @Override
            public float getAttackDamage() {
                return tier.getAttackDamage();
            }

            @Override
            public int getHarvestLevel() {
                return tier.getHarvestLevel();
            }

            @Override
            public int getEnchantability() {
                return tier.getEnchantability();
            }

            @Override
            public Ingredient getRepairMaterial() {
                return tier.getRepairMaterial();
            }
        }, ImmutableSet.of(), builder);
        this.radius = radius;
        this.depth = depth;
        this.additionalMaterials = Arrays.asList(additionalMaterials);
    }


    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        brokeBlocks.set(false);
        if(entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            RayTraceResult ray = Item.rayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);
            if(this.canHarvestBlock(state) && ray.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockRay = (BlockRayTraceResult) ray;
                Direction facing = blockRay.getFace();
                if(!player.isCrouching()) {
                    getArea(pos, facing)
                            .filter(b -> worldIn instanceof ServerWorld &&
                                    entityLiving instanceof ServerPlayerEntity &&
                                    !worldIn.isAirBlock(b) &&
                                    this.canHarvestBlock(worldIn.getBlockState(b)) &&
                                    worldIn.getBlockState(b).getBlockHardness(worldIn, b) > 0 &&
                                    worldIn.getTileEntity(b) == null)
                            .forEach(b -> {
                                brokeBlocks.set(true);
                                BlockState tempState = worldIn.getBlockState(b);
                                Block block = tempState.getBlock();

                                //Damage Item
                                stack.damageItem(1, entityLiving, this::breakAnimation);
                                //Call Destroy Function For Custom behaviour blocks may have
                                block.onPlayerDestroy(worldIn, b, tempState);
                                //Generate Drops
                                block.harvestBlock(worldIn, player, b, tempState, null, stack);
                                //Generate XP
                                block.dropXpOnBlockBreak((ServerWorld) worldIn, b, tempState.getBlock().getExpDrop(
                                        tempState,
                                        worldIn,
                                        b,
                                        EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack),
                                        EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack)));
                                //Remove Block
                                worldIn.destroyBlock(b, false);
                            });
                    if(brokeBlocks.get())
                        //Restore damage that's been done to the item by the game
                        stack.damageItem(-1, entityLiving, this::breakAnimation);
                }
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    private Stream<BlockPos> getArea(BlockPos block, Direction face) {
        BlockPos bottomLeft, topRight;

        if(face == Direction.DOWN || face == Direction.UP) {
            bottomLeft = block
                    //Move South
                    .south(this.radius)
                    //Move West
                    .west(this.radius)
                    //Move Down By Depth
                    .offset(face.getOpposite(), this.depth);

            topRight = block
                    //Move North
                    .north(this.radius)
                    //Move East
                    .east(this.radius);
        }else {
            //Move Down By Radius
            bottomLeft = block.down(this.radius)
                    //Move In By Depth
                    .offset(face.getOpposite(), this.depth)
                    //Rotate CCW and move by radius
                    .offset(face.rotateYCCW(), this.radius);

            //Move Up by radius
            topRight = block.up(this.radius)
                    //Rotate Clockwise and expand by radius
                    .offset(face.rotateY(), this.radius);
        }

        return BlockPos.getAllInBox(bottomLeft, topRight);
    }

    private int getHarvestLevel() {
        return this.getToolTypes(null).parallelStream()
                .mapToInt(t -> this.getHarvestLevel(null, t, null, null))
                .max()
                .orElse(-1);
    }

    private boolean isMaterialValid(Material material) {
        return this.additionalMaterials.contains(material) ||
                materialsForToolTypes.entrySet().stream()
                        .filter(e -> this.getToolTypes(null).contains(e.getKey()))
                        .anyMatch(e -> e.getValue().contains(material));
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        Set<ToolType> toolTypes = this.getToolTypes(null);
        return (toolTypes.contains(blockIn.getHarvestTool()) ||
                this.isMaterialValid(blockIn.getMaterial())) &&
                this.getHarvestLevel() >= blockIn.getHarvestLevel();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return this.getToolTypes(null).contains(state.getHarvestTool()) ||
                this.isMaterialValid(state.getMaterial()) &&
                        this.getHarvestLevel() >= state.getHarvestLevel() ?
                this.efficiency :
                super.getDestroySpeed(stack, state);
    }

    public void breakAnimation(LivingEntity entity) {
        entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }
}
