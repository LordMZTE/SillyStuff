package de.mzte.sillystuff.tile;

import de.mzte.sillystuff.Config;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

import static de.mzte.sillystuff.SillyStuff.MODID;

public class AcceleratorTile extends TileEntity implements ITickableTileEntity {
	public boolean hasBeacon;
	public int speedLevel = 0;
	public AcceleratorTile() {
		super(ForgeRegistries.TILE_ENTITIES.getValue(new ResourceLocation(MODID, "accelerator")));
	}

	@Override
	public void tick() {
		if(!world.isRemote) {
			int range = Config.ACCELERATOR_BEACON_RANGE.get();
			for(TileEntity te : this.world.loadedTileEntityList) {
				BlockPos pos = te.getPos();
				if(te instanceof BeaconTileEntity) {
					BeaconTileEntity beacon = (BeaconTileEntity) te;
					if(Math.abs(pos.getX() - this.pos.getX()) <= range &&
							Math.abs(pos.getY() - this.pos.getY()) <= range &&
							Math.abs(pos.getZ() - this.pos.getZ()) <= range &&
							beacon.primaryEffect == Effects.SPEED &&
							beacon.levels > 0 && ! beacon.beamSegments.isEmpty()) {
						if(beacon.primaryEffect == Effects.SPEED && beacon.secondaryEffect == Effects.SPEED) {
							speedLevel = 2;
							break;
						} else if(beacon.primaryEffect == Effects.SPEED) {
							speedLevel = 1;
							break;
						} else {
							speedLevel = 0;
						}
					}
				} else {
					speedLevel = 0;
				}
			}

			world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BlockStateProperties.POWERED, speedLevel > 0));
			if(speedLevel > 0) {
				BlockPos tickAT = pos.offset(world.getBlockState(pos).get(BlockStateProperties.FACING));
				TileEntity te;
				if((te = world.getTileEntity(tickAT)) != null) {
					if((te instanceof ITickableTileEntity) && ! (te instanceof AcceleratorTile)) {
						for(int i = 0; i < Config.ACCELERATOR_ACCELERATION.get(); i++) {
							((ITickableTileEntity) te).tick();
						}
					}
				}
			}
		}
	}
}
