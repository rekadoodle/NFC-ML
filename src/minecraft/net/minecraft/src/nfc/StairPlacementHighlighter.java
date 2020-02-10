package net.minecraft.src.nfc;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;
import net.minecraft.src.forge.IHighlightHandler;

public class StairPlacementHighlighter implements IHighlightHandler {

	@Override
	public boolean onBlockHighlight(RenderGlobal renderglobal, EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f) {
		if(true) return false;
	
		if(itemstack != null /* && itemstack.itemID == mod_NFC.slab.blockID && mod_NFC.slab.isStairs(itemstack.getItemDamage())*/) {
			renderglobal.drawBlockBreaking(entityplayer, movingobjectposition, 0, entityplayer.inventory.getCurrentItem(), f);
	        renderglobal.drawSelectionBox(entityplayer, movingobjectposition, 0, entityplayer.inventory.getCurrentItem(), f);
			if(i == 0 && movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
	        {
	            int j = renderglobal.worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
	            if(j > 0) {
	            	Block block = Block.blocksList[j];
	            	block.setBlockBoundsBasedOnState(renderglobal.worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
	            	if(block.minY < 0.5D && block.maxY > 0.5D)
		            {
			            GL11.glEnable(3042 /*GL_BLEND*/);
			            GL11.glLineWidth(5.0F);
			            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
			            GL11.glDepthMask(false);
			            float f1 = 0.03F;
		                double d = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)f;
		                double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)f;
		                double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)f;
		                drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBoxFromPool(movingobjectposition.blockX + block.minY, movingobjectposition.blockY + 0.5D, movingobjectposition.blockZ + block.minZ, movingobjectposition.blockX + block.maxX, movingobjectposition.blockY + 0.5D, movingobjectposition.blockZ + block.maxZ).expand(f1, f1, f1).getOffsetBoundingBox(-d, -d1, -d2));
			            GL11.glDepthMask(true);
			            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
			            GL11.glDisable(3042 /*GL_BLEND*/);
		            }
	            }
	        }
			return true;
		}
		return false;
	}

	private void drawOutlinedBoundingBox(AxisAlignedBB axisalignedbb) {
		Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
	}

}
