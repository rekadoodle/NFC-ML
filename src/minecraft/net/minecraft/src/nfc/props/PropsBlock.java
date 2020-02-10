package net.minecraft.src.nfc.props;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.MinecraftForge;

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
	

	public static class Ore extends PropsBlock {

		private final int TIER;
		
		public Ore(PropsItemToolMaterial material, float hardness, int textureIndex) {
			this(material.NAME, material.TIER - 1, hardness, 0, 0, textureIndex);
		}

		public Ore(PropsItemToolMaterial material, float hardness, int idDropped, int damageDropped, int textureIndex) {
			this(material.NAME, material.TIER - 1, hardness, idDropped, damageDropped, textureIndex);
		}
		
		public Ore(String name, float hardness, int textureIndex) {
			this(name, 0, hardness, 0, 0, textureIndex);
		}
		
		public Ore(String name, int tier, float hardness, int idDropped, int damageDropped, int textureIndex) {
			super(name, hardness, 500.0F, idDropped, damageDropped, textureIndex);
			this.TIER = tier;
			this.addLocalisation(new StringBuilder().append(name).append(' ').append("Ore").toString());
		}
		
		public Ore(String name, float hardness, int idDropped, int damageDropped, int textureIndex) {
			this(name, 0, hardness, idDropped, damageDropped, textureIndex);
		}

		public void setHarvestLevel() {
			MinecraftForge.setBlockHarvestLevel(Block.blocksList[this.block_id], this.block_metadata, "pickaxe", this.TIER);
		}
		
		@Override
		public String getNamePrefix() {
			return new StringBuilder().append(super.getNamePrefix()).append("ore.").toString();
		}
	}
}
