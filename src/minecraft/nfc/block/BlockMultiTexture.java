package nfc.block;

import net.minecraft.src.Material;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.forge.ForgeHooksClient;
import nfc.props.PropsBlock;
import nfc.props.PropsBlockTexture;

public class BlockMultiTexture extends BlockMulti {
	private String textureFile = super.getTextureFile();
	private final int renderID;
	private int renderType;

	public BlockMultiTexture(int id, int renderID, Material material, PropsBlock... blocks) {
		super(id, material, blocks);
		this.renderID = renderID;
		this.resetRenderType();
	}

	@Override
	public int getRenderType()
    {
		return this.renderType;
    }
	
	public void setRenderType(int renderType) {
		this.renderType = renderType;
	}
	
	public void resetRenderType() {
		this.renderType = this.renderID;
	}
	
	@Override
	public String getTextureFile() 
	{
		return textureFile;
	}
	
	public void setTextureFile(String s) {
		this.textureFile = s;
	}
	
	public void resetTextureFile() {
		this.textureFile = super.getTextureFile();
	}
	
	public void renderBlockWorld(RenderBlocks renderblocks, int metadata, int x, int y, int z) {
		PropsBlock blockprops = this.getBlockProps(metadata);
		this.preRender(blockprops, metadata, renderblocks);
		renderblocks.renderBlockByRenderType(this, x, y, z);
		this.postRender();
	}
	
	public void preRender(PropsBlock blockprops, int metadata, RenderBlocks renderblocks) {
		if(blockprops instanceof PropsBlockTexture) {
			PropsBlockTexture blocktexture = (PropsBlockTexture)blockprops;
			this.setRenderType(blocktexture.RENDER_TYPE);
			this.setTextureFile(blocktexture.getTextureFile());
			ForgeHooksClient.beforeBlockRender(this, renderblocks);
		}
		else {
			this.setRenderType(PropsBlockTexture.STANDARD_BLOCK);
		}
	}
	
	public void postRender() {
		this.resetRenderType();
		this.resetTextureFile();
	}
	
	public void renderBlockInv(RenderBlocks renderblocks, int metadata) {
		PropsBlock blockprops = this.getBlockProps(metadata);
		this.preRender(blockprops, metadata, renderblocks);
		renderblocks.renderBlockOnInventory(this, metadata, 1.0F);
		this.postRender();
	}
}
