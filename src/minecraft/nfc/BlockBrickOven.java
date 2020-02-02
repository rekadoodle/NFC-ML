package nfc;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class BlockBrickOven extends BlockFurnace implements ITextureProvider {
	
	private final int FRONT_TEXTURE_INDEX;

	public BlockBrickOven(int id, boolean isActive, int frontTextureIndex) {
		super(id, isActive);
		this.blockIndexInTexture = 33;
		this.FRONT_TEXTURE_INDEX = frontTextureIndex;
		ModLoader.RegisterBlock(this);
	}

	@Override
	public int idDropped(int i, Random random) {
		return mod_NFC.BrickOvenIdle.blockID;
	}

	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side) {
		if (side == iblockaccess.getBlockMetadata(x, y, z)) {
			return FRONT_TEXTURE_INDEX;
		}
		return blockIndexInTexture;
	}

	@Override
	public int getBlockTextureFromSide(int side) {
		if (side == 3) {
			return FRONT_TEXTURE_INDEX;
		} 
		return blockIndexInTexture;
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if (!world.multiplayerWorld) {
			TileEntityBrickOven tileentityfurnace = (TileEntityBrickOven) world.getBlockTileEntity(i, j, k);
			ModLoader.OpenGUI(entityplayer, new GuiBrickOven(entityplayer.inventory, tileentityfurnace));
		}
		return true;
	}

	public static void updateFurnaceBlockState(boolean flag, World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		setKeepFurnaceInventory(true);
		if (flag) {
			world.setBlockWithNotify(i, j, k, mod_NFC.BrickOvenActive.blockID);
		} else {
			world.setBlockWithNotify(i, j, k, mod_NFC.BrickOvenIdle.blockID);
		}
		setKeepFurnaceInventory(false);
		world.setBlockMetadataWithNotify(i, j, k, l);
		tileentity.func_31004_j();
		world.setBlockTileEntity(i, j, k, tileentity);
	}
	
	private static void setKeepFurnaceInventory(boolean value) {
		try {
			parentKeepInventoryField.set(null, value);
		} 
		catch (Exception e) { e.printStackTrace(); } 
	}

	@Override
	protected TileEntity getBlockEntity() {
		return new TileEntityBrickOven();
	}
	
	@Override
	public String getTextureFile() 
	{
		return new StringBuilder(mod_NFC.resources).append("terrain.png").toString();
	}
	
    private static Field parentKeepInventoryField = Utils.getField(BlockFurnace.class, "keepFurnaceInventory", "c");

}
