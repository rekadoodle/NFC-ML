package nfc.block;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import nfc.props.PropsBlock;

public class BlockMultiGlass extends BlockMultiTexture {

	public BlockMultiGlass(int id, int renderID, Material material, PropsBlock... blocks) {
		super(id, renderID, material, blocks);
	}

	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(iblockaccess.getBlockId(i, j, k) == blockID)
        {
            return false;
        } else
        {
            return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
        }
    }
}
