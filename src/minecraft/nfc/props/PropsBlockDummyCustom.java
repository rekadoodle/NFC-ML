package nfc.props;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import nfc.block.BlockMultiCustomRender;

public class PropsBlockDummyCustom extends PropsBlockCustomRenderMulti {

	public PropsBlockDummyCustom(PropsBlockTexture props) {
		super(props.NAME, 1, 1, props.getTextureFile(), props.RENDER_TYPE, props.HARDNESS, props.RESISTANCE, props.ID_DROPPED, props.DAMAGE_DROPPED, props.getTextureIndex());
	}

	@Override
	public void setBlockBounds(Block block, int metadata, int renders) {
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public int getTextureIndex(int side, int metadata) {
		return super.getTextureIndex(side);
	}

	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int side, BlockMultiCustomRender block) {
		//do nothing
	}

	@Override
	public void setBoundingBox(Block block, int metadata) {
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void setInvBlockBounds(Block block, int metadata) {
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public int getMetadataAfterWrench(int metadata) {
		return metadata;
	}

}
