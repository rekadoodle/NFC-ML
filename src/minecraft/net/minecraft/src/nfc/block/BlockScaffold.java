package net.minecraft.src.nfc.block;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.nfc.Core;

public class BlockScaffold extends Block implements ITextureProvider {

	public BlockScaffold(int id, int textureIndex, Material material) {
		super(id, textureIndex, material);
		this.setBlockName("nfc.scaffold");
		ModLoader.AddName(this, "Scaffold");
		ModLoader.RegisterBlock(this);
		this.setBlockBounds(0.4F, 0.0F, 0.4F, 0.6F, 1.0F, 0.6F);
	}

	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public String getTextureFile() 
	{
		return new StringBuilder(Core.resources).append("terrain.png").toString();
	}

	@Override
	public boolean isLadder()
	{
		return true;
	}
}
