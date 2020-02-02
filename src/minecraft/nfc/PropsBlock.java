package nfc;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

public class PropsBlock extends Props {
	public final float HARDNESS;
	public final float RESISTANCE;
	public int ID_DROPPED;
	public int DAMAGE_DROPPED;
	public int block_id;
	public int block_metadata;
	
	public PropsBlock(String name, float hardness, float resistance, int textureIndex) {
		this(name, hardness, resistance, 0, 0, textureIndex);
	}

	public PropsBlock(String name, float hardness, float resistance, int idDropped, int damageDropped, int textureIndex) {
		super(name, textureIndex);
		this.HARDNESS = hardness;
		this.RESISTANCE = resistance;
		this.ID_DROPPED = idDropped;
		this.DAMAGE_DROPPED = damageDropped;
		this.addLocalisation(name);
	}
	
	public int getTextureIndex(int side) {
		return super.getTextureIndex();
	}
	
	@Override
	protected String getNamePrefix() {
		return "tile.nfc.";
	}

	@Override
	public ItemStack getItemStack(int amount) {
		return new ItemStack(this.block_id, amount, this.block_metadata);
	}
}
