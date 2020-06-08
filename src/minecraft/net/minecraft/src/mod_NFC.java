package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.nfc.*;
import net.minecraft.src.nfc.block.*;

public class mod_NFC extends BaseMod {

	public mod_NFC() {
		NFC.instance.init(this);
	}
	
	@Override
	public String Version() {
		return NFC.instance.version();
	}
	
	@Override
	public boolean OnTickInGame(Minecraft mc)
    {
		NFC.instance.onTickIngame();
        return true;
    }
	
	@Override
	public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int x, int y, int z, Block block, int renderType)
    {
		NFC.instance.renderWorldBlock(renderblocks, (BlockMultiTexture) block, iblockaccess.getBlockMetadata(x, y, z), x, y, z);
        return true;
    }
	
	@Override
	public void RenderInvBlock(RenderBlocks renderblocks, Block block, int metadata, int renderType)
    {
		NFC.instance.renderInvBlock(renderblocks, (BlockMultiTexture) block, metadata);
    }
	
	@Override
	public void GenerateSurface(World world, Random random, int x, int z)
    {
		NFC.instance.generateSurface(world, random, x, z);
    }
	
	@Override
	public void ModsLoaded()
    {
		NFC.instance.modsLoaded();
    }
	
	//Info for mine_diver's mod menu
	public String Description() {
		return NFC.instance.description();
	}
	
	public String Name() {
		return NFC.instance.name();
	}
	
	public String Icon() {
		return NFC.instance.icon();
	}
}
