package de.mzte.sillystuff.blocks;

import de.mzte.sillystuff.tile.AcceleratorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class Accelerator extends DirectionalBlock {

	public Accelerator() {
		super(Block.Properties.create(Material.ROCK)
				.harvestLevel(1)
				.sound(SoundType.STONE)
				.hardnessAndResistance(5)
				.harvestTool(ToolType.PICKAXE));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}


	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING);
		builder.add(BlockStateProperties.POWERED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState()
				.with(FACING, context.getNearestLookingDirection().getOpposite())
				.with(BlockStateProperties.POWERED, false);
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new AcceleratorTile();
	}
}
