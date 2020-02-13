package net.minecraft.src.nfc.props;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.nfc.*;
import net.minecraft.src.nfc.block.BlockBrickOven;

public class PropsItem extends Props {
	public int item_metadata;
	public int item_id;

	public PropsItem(String name, int textureIndex) {
		super(name, textureIndex);
	}
	
	@Override
	protected String getNamePrefix() {
		return "item.nfc.";
	}
	
	@Override
	public ItemStack getItemStack(int amount) {
		return new ItemStack(this.item_id, amount, this.item_metadata);
	}
	
	public static class Food extends PropsItem {
		
		public final int HEAL_AMOUNT;

		public Food(String name, int healAmount, int textureIndex) {
			super(name, textureIndex);
			this.HEAL_AMOUNT = healAmount;
		}
		
		public void onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	    {
	        itemstack.stackSize--;
	        entityplayer.heal(HEAL_AMOUNT);
	    }
	}
	
	public static class Wrench extends PropsItem {
		
		public interface IWrenchable {
			public abstract boolean onWrenched(World world, int x, int y, int z);
		}

		public Wrench(String name, int textureIndex) {
			super(name, textureIndex);
		}
		
		public boolean onItemUseFirst(ItemStack ist, EntityPlayer player, World world, int x, int y, int z, int side) {
			Block block = Block.blocksList[world.getBlockId(x, y, z)];
			world.playSoundAtEntity(player, Core.instance.soundManager.getSound(SoundManagerNFC.wrench), 0.5F, 0.825F + (new Random().nextFloat() * 0.05f));
			if(block instanceof IWrenchable) {
				return ((IWrenchable)block).onWrenched(world, x, y, z);
			}
			else if(block instanceof BlockBrickOven) {
				world.markBlockAsNeedsUpdate(x, y, z);
				return world.setBlockMetadata(x, y, z, (world.getBlockMetadata(x, y, z) + 1) % 6);
			}
			else if(block instanceof BlockFurnace) {
				world.markBlockAsNeedsUpdate(x, y, z);
				return world.setBlockMetadata(x, y, z, (((world.getBlockMetadata(x, y, z) - 1)) % 4) + 2);
			}
			return false;
		}
	}
	
	public static class Telescope extends PropsItem {
		
		private boolean zooming;
		private Utils.EasyField<Double> cameraZoomField = new Utils.EasyField<Double>(EntityRenderer.class, "cameraZoom", "E");

		public Telescope(String name, int textureIndex) {
			super(name, textureIndex);
		}
		
		public void onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	    {
			setZoom(!zooming);
	    }
		
		public void setZoom(boolean bool) {
			zooming = bool;
			if(cameraZoomField.exists()) {
				cameraZoomField.set(Utils.mc.entityRenderer, bool ? 8.0D : 1.0D);
			}
		}
		
		public boolean isZooming() {
			return zooming;
		}
		
		public void renderOverlay() {
			Minecraft mc = Utils.mc;
			ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int i = scaledresolution.getScaledWidth();
			int j = scaledresolution.getScaledHeight();
			GL11.glDisable(2929 /* GL_DEPTH_TEST */);
			GL11.glDepthMask(false);
			GL11.glDisable(2896 /*GL_LIGHTING*/);
			GL11.glBlendFunc(770, 771);
			GL11.glDisable(3008 /* GL_ALPHA_TEST */);
			Tessellator tessellator = Tessellator.instance;
			GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, 0);
			GL11.glColor3f(0.0f, 0.0f, 0.0f);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0, j, -90D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((i/2)-(j/2), j, -90D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((i/2)-(j/2), 0.0D, -90D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(0, 0.0D, -90D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV((i/2)+(j/2), j, -90D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((i/2)+(j/2), 0.0D, -90D, 0.0D, 0.0D);
			tessellator.draw();
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glEnable(3008 /* GL_ALPHA_TEST */);
			GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, mc.renderEngine.getTexture("/terrain.png"));
			tessellator.startDrawingQuads();
			int l = Block.glass.getBlockTextureFromSide(0);
			int i1 = (l & 0xf) << 4;
		    int j1 = l & 0xf0;
		    double d = (float)i1 / 256F;
		    double d2 = ((float)i1 + 15.99F) / 256F;
		    double d4 = (float)j1 / 256F;
		    double d6 = ((float)j1 + 15.99F) / 256F;
			tessellator.addVertexWithUV((i/2)-(j/2), j, -90D, d, d6);
			tessellator.addVertexWithUV((i/2)+(j/2), j, -90D, d2, d6);
			tessellator.addVertexWithUV((i/2)+(j/2), 0.0D, -90D, d2, d4);
			tessellator.addVertexWithUV((i/2)-(j/2), 0.0D, -90D, d, d4);
			tessellator.draw();
			GL11.glDepthMask(true);
			GL11.glEnable(2929 /* GL_DEPTH_TEST */);
			GL11.glEnable(3008 /* GL_ALPHA_TEST */);
			
			//crosshair fix
			if(!mc.gameSettings.hideGUI)
	        {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/icons.png"));
		        GL11.glEnable(3042 /*GL_BLEND*/);
		        GL11.glBlendFunc(775, 769);
		        new Gui().drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
		        GL11.glDisable(3042 /*GL_BLEND*/);
	        }
			if(!mc.gameSettings.hideGUI || mc.currentScreen != null)
	        {
	            mc.ingameGUI.renderGameOverlay(Utils.renderPartialTicks(), mc.currentScreen != null, Utils.cursorX(), Utils.cursorY());
	        }
			//GL11.glEnable(2896 /*GL_LIGHTING*/);
			if(mc.currentScreen != null) {
				mc.currentScreen.drawScreen(Utils.cursorX(), Utils.cursorY(), Utils.renderPartialTicks());
			}
		}
	}
}
