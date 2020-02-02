package nfc;

import net.minecraft.src.*;

public class BlockTest extends Block {

	public BlockTest(int i, int j, Material material) {
		super(i, j, material);
		this.setBlockBounds(0.0F, 0F, 0F, 0.5F, 1.0F, 1.0F);
		this.setBlockName("nfc.test");
		ModLoader.RegisterBlock(this);
	}

}
