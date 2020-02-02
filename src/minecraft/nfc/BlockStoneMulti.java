package nfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.forge.MinecraftForge;

public class BlockStoneMulti extends BlockTE {
	

	private final List<nfc.PropsBlock> propsList = new ArrayList<nfc.PropsBlock>();
    
    private final ItemMulti item = mod_NFC.item;
	
	public final BlockProperties STONE_BLOCK = new BlockProperties("Stone Block");
	public final BlockProperties STONE_BLOCK_OFFSET_XY = new BlockProperties("Stone Block Offset:XY");
	public final BlockProperties STONE_BLOCK_OFFSET_X = new BlockProperties("Stone Block Offset:X");
	public final BlockProperties STONE_BLOCK_OFFSET_Y = new BlockProperties("Stone Block Offset:Y");
	public final BlockProperties STONE_BRICK = new BlockProperties("Stone Brick");
	public final BlockProperties STONE_BRICK_SMALL = new BlockProperties("Small Stone Brick");
	public final BlockCustomRender STONE_PLATED = new BlockCustomRender("Plated Stone", "/terrain.png", 6);
	
	public class OreProperties extends nfc.PropsBlock {
		
		public OreProperties(PropsItemToolMaterial material, float hardness) {
			this(material.NAME, material.TIER - 1, hardness, BlockStoneMulti.this.blockID, propsList.size());
		}

		public OreProperties(PropsItemToolMaterial material, float hardness, int idDropped, int damageDropped) {
			this(material.NAME, material.TIER - 1, hardness, idDropped, damageDropped);
		}
		
		public OreProperties(String name, float hardness) {
			this(name, 0, hardness, BlockStoneMulti.this.blockID, propsList.size());
		}
		
		public OreProperties(String name, int tier, float hardness, int idDropped, int damageDropped) {
			super(name, hardness, 500.0F, idDropped, damageDropped, propsList.size());
			MinecraftForge.setBlockHarvestLevel(BlockStoneMulti.this, this.block_metadata, "pickaxe", tier);
			this.addLocalisation(new StringBuilder().append(name).append(' ').append("Ore").toString());
			propsList.add(this);
		}
		
		@Override
		public String getNamePrefix() {
			return new StringBuilder().append(super.getNamePrefix()).append("ore.").toString();
		}
	}
	
	public class BlockProperties extends nfc.PropsBlock {
		
		public BlockProperties(String name) {
			this(name, 1.0F, 10.0F, propsList.size());
			this.addLocalisation(name);
		}

		public BlockProperties(String name, float hardness, float resistance, int textureIndex) {
			super(name, hardness, resistance, BlockStoneMulti.this.blockID, propsList.size(), textureIndex);
			this.addLocalisation(name);
			propsList.add(this);
		}
		
	}
	
	public class BlockCustomRender extends nfc.PropsBlockTexture {
		
		public BlockCustomRender(String name, String textureFile, int textureIndex) {
			super(name, textureFile, BlockCustomRender.STANDARD_BLOCK, 1.0F, 10.0F, BlockStoneMulti.this.blockID, propsList.size(), textureIndex);
			propsList.add(this);
		}
		
	}
	
	public BlockStoneMulti(int id) {
		super(id, Material.rock);
		this.setResistance(500.0F);
		System.out.println(this.propsList.size());
	}
	
	@Override
	protected TileEntity getBlockEntity() {
		return new TileEntityBlock();
	}
	
	@Override
	public int idDropped(int metadata, Random rand)
    {
		return propsList.get(metadata).ID_DROPPED;
    }
	
	@Override
	protected int damageDropped(int metadata)
    {
		return propsList.get(metadata).DAMAGE_DROPPED;
    }
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
    {
		return propsList.get(metadata).getTextureIndex(side);
    }
	
	@Override
    protected String getItemNameFromMetadata(int metadata) {
    	return propsList.get(metadata).getName();
    }
	
	@Override
	public float getHardness(int metadata)
	{
		return propsList.get(metadata).HARDNESS;
    }
	
	@Override
	public float getResistance(int metadata) {
		return propsList.get(metadata).RESISTANCE * 3.0F;
	}

	@Override
	public nfc.PropsBlockTexture getRenderProps(int metadata) {
		if(propsList.get(metadata) instanceof nfc.PropsBlockTexture) {
			return (nfc.PropsBlockTexture) propsList.get(metadata);
		}
		return null;
	}
}
