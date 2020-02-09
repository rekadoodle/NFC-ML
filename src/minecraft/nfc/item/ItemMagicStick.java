package nfc.item;

import net.minecraft.src.*;

public class ItemMagicStick extends Item {

	public ItemMagicStick(int i) {
		super(i);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side)
    {
		TileEntity e = world.getBlockTileEntity(x, y, z);
		//if(e instanceof TileEntityBlock) {
			//world.markBlockNeedsUpdate(x, y, z);
			//((TileEntityBlock)e).interact();
			//ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(((TileEntityBlock)e).interact() + " on side " + side);
		//}
		//else 
		{
			if(e != null) {
				ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(e.getClass().getSimpleName());
			}
			else {
				ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("null FAGGIT");
				ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("metadata="+ (world.getBlockMetadata(x, y, z) + 1));
			}
		}
		return world.setBlockMetadata(x, y, z, world.getBlockMetadata(x, y, z) + 1);
    }

}
