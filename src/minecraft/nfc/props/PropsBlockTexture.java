package nfc.props;

import net.minecraft.src.Block;
import net.minecraft.src.forge.ITextureProvider;


public class PropsBlockTexture extends PropsBlock {

	//Render Types
	public static final int STANDARD_BLOCK = 0;
	public static final int SLAB = 1;
	public static final int STAIRS = 10;

	public final int RENDER_TYPE;
	public final String TEXTURE_FILE;
	
	public PropsBlockTexture(String name, Block block) {
		super(name, block.getHardness(), block.getExplosionResistance(null), 0, 0, block.blockIndexInTexture);
		this.RENDER_TYPE = STANDARD_BLOCK;
		this.TEXTURE_FILE = getTextureFile(block);
	}
	
	public PropsBlockTexture(String name, String textureFile, float hardness, float resistance, int textureIndex) {
		this(name, textureFile, STANDARD_BLOCK, hardness, resistance, 0, 0, textureIndex);
	}

	public PropsBlockTexture(String name, int renderType, float hardness, float resistance, int idDropped, int damageDropped, int textureIndex) {
		this(name, "terrain.png", renderType, hardness, resistance, idDropped, damageDropped, textureIndex);
	}
	
	public PropsBlockTexture(String name, String textureFile, int renderType, float hardness, float resistance, int idDropped, int damageDropped, int textureIndex) {
		super(name, hardness, resistance, idDropped, damageDropped, textureIndex);
		this.RENDER_TYPE = renderType;
		this.TEXTURE_FILE = textureFile;
	}

	public String getTextureFile() {
		return TEXTURE_FILE;
	}
	
	public static String getTextureFile(Block block) {
		if(block instanceof ITextureProvider) {
			return ((ITextureProvider)block).getTextureFile();
		}
		return "/terrain.png";
	}
}
