package net.minecraft.src.nfc;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.nfc.props.PropsBlock;

public class WorldGenConcentrated extends WorldGenerator {
	
	public WorldGenConcentrated(PropsBlock block, int rad, int vienam, int amo) {
		this(block.block_id, rad, vienam, amo, block.block_metadata);
	}
	
	public WorldGenConcentrated(int i, int rad, int vienam, int amo) {
		this(i, rad, vienam, amo, 0);
	}
	
	public WorldGenConcentrated(int i, int rad, int vienam, int amo, int metadata) {
		minableBlockId = i;
		radius = rad;
		viens = vienam;
		amount = amo;
		minableBlockMetadata = metadata;
	}
	
	public boolean generate(World world, Random random, int i, int j, int k) {
		double deg = 6.2831853/(double)viens;
		for(int p = viens;p >= 0; p--){
			double length = (double)radius;
				length  = length*random.nextFloat();
			int xx = (int) (length*Math.cos(deg*p))+radius;
			int zz = (int) (length*Math.sin(deg*p))+radius;
			(new WorldGenMinable(minableBlockId, amount)).generate(world,
					random, i+xx, j+random.nextInt(3), k+zz);
		}

		return false;
	}
	
	int minableBlockId, radius, viens, amount, minableBlockMetadata;

}

