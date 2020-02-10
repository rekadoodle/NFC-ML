package net.minecraft.src.nfc;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

public class GuiBrickOven extends GuiContainer {

	public GuiBrickOven(InventoryPlayer inventoryplayer, TileEntityBrickOven tileentityfurnace) {
		super(new ContainerBrickOven(inventoryplayer, tileentityfurnace));
		furnaceInventory = tileentityfurnace;
		ySize = 202;
	}

	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString("Brick Oven", 60, 6, 0x404040);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	}

	protected void drawGuiContainerBackgroundLayer(float f) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(mc.renderEngine.getTexture(new StringBuilder().append(Core.resources).append("oven.png").toString()));
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		if (furnaceInventory.isBurning()) {
			int l = furnaceInventory.getBurnTimeRemainingScaled(12);
			drawTexturedModalRect(j + 56, (k + 72 + 12) - l, 176, 12 - l, 14, l + 2);
		}
		int i1 = furnaceInventory.getCookProgressScaled(24);
		drawTexturedModalRect(j + 79, k + 70, 176, 14, i1 + 1, 16);
	}

	private TileEntityBrickOven furnaceInventory;
}