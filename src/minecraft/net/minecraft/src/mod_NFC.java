package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.nfc.*;
import net.minecraft.src.nfc.block.*;

public class mod_NFC extends BaseMod {

	public mod_NFC() {
		Core.instance.init(this);
	}
	
	@Override
	public String Version() {
		return Core.instance.version();
	}
	
	@Override
	public boolean OnTickInGame(Minecraft mc)
    {
		Core.instance.onTickIngame();
        return true;
    }
	
	@Override
	public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int x, int y, int z, Block block, int renderType)
    {
		Core.instance.renderWorldBlock(renderblocks, (BlockMultiTexture) block, iblockaccess.getBlockMetadata(x, y, z), x, y, z);
        return true;
    }
	
	@Override
	public void RenderInvBlock(RenderBlocks renderblocks, Block block, int metadata, int renderType)
    {
		Core.instance.renderInvBlock(renderblocks, (BlockMultiTexture) block, metadata);
    }
	
	@Override
	public void GenerateSurface(World world, Random random, int x, int z)
    {
		Core.instance.generateSurface(world, random, x, z);
    }
	
	//Info for mine_diver's mod menu
	public String Description() {
		return Core.instance.description();
	}
	
	public String Name() {
		return Core.instance.name();
	}
	
	public String Icon() {
		return Core.instance.icon();
	}
}
