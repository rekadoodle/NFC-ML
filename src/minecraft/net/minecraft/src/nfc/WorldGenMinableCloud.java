package net.minecraft.src.nfc;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.nfc.props.PropsBlock;

public class WorldGenMinableCloud extends WorldGenerator {
	
	public WorldGenMinableCloud(PropsBlock block, int rad, int den, int amo) {
		this(block.block_id, rad, den, amo, block.block_metadata);
	}
	
	public WorldGenMinableCloud(int i, int rad, int den, int amo) {
		this(i, rad, den, amo, 0);
	}
	
	public WorldGenMinableCloud(int i, int rad, int den, int amo, int metadata) {
		minableBlockId = i;
		radius = rad;
		density = den;
		amount = amo;
		minableBlockMetadata = metadata;
	}
	
	public boolean generate(World world, Random random, int i, int j, int k) {
		int p = (int) (amount - (amount*(random.nextFloat()*0.2)));
		boolean pregen[][][] = new boolean [radius*2][radius*2][radius*2];
		double deg = 6.2831853/(double)p;
		for(int o = p-1; o >= 0; o--){
			double length = (double)radius;
			for(int w = 0; w <= density; w++){
				length  = length*random.nextFloat();
			}
			int xx = (int) (length*Math.cos(deg*o))+radius;
			int yy = (int) (length*Math.sin(deg*o))+radius;
			int zz = (int) (length*Math.sin(6.2831853*random.nextFloat()))+radius;
			if (pregen[xx][yy][zz]){
				o++;
			} else { pregen[xx][yy][zz] = true; }
			
		}
		
		for(int o = 0; o < radius*2; o++) for(int l = 0; l < radius*2; l++) for(int f = 0; f < radius*2; f++) {
			if(pregen[o][l][f] && world.getBlockId(o+i, l+j, k+f) == Block.stone.blockID){
				world.setBlockAndMetadata(o+i, l+j, k+f, minableBlockId, minableBlockMetadata);
			}
		}

		return false;
	}
	
	final int minableBlockId, radius, density, amount, minableBlockMetadata;
}
