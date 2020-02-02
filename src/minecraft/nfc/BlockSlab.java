package nfc;

import java.util.ArrayList;

import net.minecraft.src.*;
import net.minecraft.src.World;
import net.minecraft.src.forge.*;

public class BlockSlab extends BlockTE {
	private final int modelID;

	private final ArrayList<BlockProperties> list = new ArrayList<BlockProperties>();
	
	public final BlockProperties PLATED_STONE_SLAB = new BlockProperties("Stone", Block.stairSingle, false, true);
	public final BlockProperties SANDSTONE_SLAB = new BlockProperties("Sandstone", Block.sandStone, false);
	public final BlockProperties PLANKS_SLAB = new BlockProperties("Wood", Block.planks, false);
	public final BlockProperties COBBLE_SLAB = new BlockProperties("Cobble", Block.cobblestone, false);
	public final BlockProperties BRICK_SLAB = new BlockProperties("Brick", Block.brick, false);
	public final BlockProperties PLANKS_STAIRS = new BlockProperties("Wood", Block.planks, true);
	public final BlockProperties BRICK_STAIRS = new BlockProperties("Brick", Block.brick, true);
	
	public class BlockProperties {
		private final String name;
		private final int id = list.size();
		private final Block block;
		private int metadata;
		private boolean rotateTexture;
		public float hardness;
		public boolean isStairs;
		
		private BlockProperties(String niceName, Block block, boolean isStair) {
			this(niceName, block, isStair, false, 0);
		}
		
		private BlockProperties(String niceName, Block block, boolean isStair, boolean rotateTexture) {
			this(niceName, block, isStair, rotateTexture, 0);
		}
		
		private BlockProperties(String niceName, Block block, boolean isStair, boolean rotateTexture, int metadata) {
			list.add(this);
			this.block = block;
			this.name = new StringBuilder(block.getBlockName()).append('@').append(metadata).toString();
			this.metadata = metadata;
			this.rotateTexture = rotateTexture;
			this.hardness = block.getHardness(metadata);
			this.isStairs = isStair;
			if(isStair) {
				ModLoader.AddLocalization(getName(id), new StringBuilder(niceName).append(' ').append("Stairs").toString());
			}
			else {
				ModLoader.AddLocalization(getName(id), new StringBuilder(niceName).append(' ').append("Slab").toString());
			}
		}
		
		int getTextureId(int side) {
			return block.getBlockTextureFromSideAndMetadata(side, metadata);
		}
		
		private String getTextureFile() {
			if(block instanceof ITextureProvider)
				return ((ITextureProvider)block).getTextureFile();
			return "/terrain.png";
		}
	}
	
	public boolean isStairs(int metadata) {
		return list.get(metadata).isStairs;
	}

	public BlockSlab(int id, int model) {
		super(id, Material.rock);
		this.modelID = model;
		this.renderType = model;
		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 0);
	}
	
	private int renderType;
	
	@Override
	public int getRenderType()
    {
        return renderType;
    }
	
	@Override
	protected int damageDropped(int i)
    {
        return i;
    }

	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public float getHardness(int metadata)
	{
		return list.get(metadata).hardness;
    }
	
	@Override
	protected String getItemNameFromMetadata(int metadata) {
		if(list.get(metadata).isStairs) {
			return new StringBuilder("nfc.stairs.").append(list.get(metadata).name).toString();
		}
		return new StringBuilder().append("nfc.slab.").append(list.get(metadata).name).toString();
	}

	@Override
	protected TileEntity getBlockEntity() {
		return new TileEntitySlab();
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
		TileEntity e = iblockaccess.getBlockTileEntity(x, y, z);
		if(e instanceof TileEntitySlab) return ((TileEntitySlab) e).shouldSideBeRendered(side);
		return true;
    }
	
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
		TileEntitySlab te = (TileEntitySlab)iblockaccess.getBlockTileEntity(x, y, z);
        if(te != null)
        return te.getTextureFromSideAndMetadata(this, side, te.getBlockMetadata());
        return 0;
    }
	
	public boolean shouldTextureRotate(int metadata) {
		return list.get(metadata).rotateTexture;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
    {
		if(metadata < list.size())
		return list.get(metadata).getTextureId(side);
		return 0;
    }

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
    {
		super.onBlockAdded(world, i, j, k);
    }
	
	@Override
	public void onBlockPlaced(World world, int x, int y, int z, int side)
    {
		TileEntitySlab e1 = (TileEntitySlab) world.getBlockTileEntity(x, y, z);
		e1.setOrientation(e1.getOppositeSide(side));
		if(side == 0)
        {
            y++;
        }
        if(side == 1)
        {
            y--;
        }
        if(side == 2)
        {
            z++;
        }
        if(side == 3)
        {
            z--;
        }
        if(side == 4)
        {
            x++;
        }
        if(side == 5)
        {
            x--;
        }
        TileEntity e = world.getBlockTileEntity(x, y, z);
        if(e instanceof TileEntitySlab) {
        	slabplacedon = (TileEntitySlab) e;
		}
    }
	
	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance)
    {
        if(world.multiplayerWorld)
        {
            return;
        }
        metadata = blockRemovedTileEntity.getBlockMetadata();
        int i1 = ((TileEntitySlab)blockRemovedTileEntity).getQuantity();
        for(int j1 = 0; j1 < i1; j1++)
        {
            if(world.rand.nextFloat() > chance)
            {
                continue;
            }
            int id = idDropped(metadata, world.rand);
            if(id > 0)
            {
                dropBlockAsItem_do(world, x, y, z, new ItemStack(id, 1, damageDropped(metadata)));
            }
        }
    }
	
	private TileEntitySlab slabplacedon;
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
    {
		int metadata = (((EntityPlayer)entityliving).getCurrentEquippedItem().getItemDamage());
		if(list.get(metadata).isStairs) {
			System.out.println("PING");
			super.onBlockPlacedBy(world, i, j, k, entityliving);
			int l = 6 + (MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3);
			TileEntitySlab slab = this.getTileEntity(world, i, j, k);
			MovingObjectPosition mop = Utils.mc.objectMouseOver;
			if(mop != null && mop.typeOfHit == EnumMovingObjectType.TILE) {
				double yPlacementCoordOnBlock = mop.hitVec.yCoord - (double)j;
				if(yPlacementCoordOnBlock > 0.5)
					l += 4;
			}
			slab.setOrientation(l);
			System.out.println(l);
		}
		else if(slabplacedon != null) {
			TileEntitySlab newslab = (TileEntitySlab) world.getBlockTileEntity(i, j, k);
			if(slabplacedon.acceptsNewBlock(world, metadata, newslab.side)) {
				slabplacedon = null;
				world.setBlockWithNotify(i, j, k, 0);
				world.removeBlockTileEntity(i, j, k);
			}
		}
		else {
			super.onBlockPlacedBy(world, i, j, k, entityliving);
		}
		slabplacedon = null;
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return super.canPlaceBlockAt(world, i, j, k);
    }
	
	public TileEntitySlab getTileEntity(IBlockAccess iblockaccess, int x, int y, int z) {
		TileEntity tileentity = iblockaccess.getBlockTileEntity(x, y, z);
		if(tileentity instanceof TileEntitySlab) return (TileEntitySlab) tileentity;
		return null;
	}
	
	public void render(RenderBlocks renderblocks, IBlockAccess iblockaccess, int x, int y, int z) {
		TileEntitySlab tileentity = getTileEntity(iblockaccess, x, y, z);
		BlockProperties blockprops = list.get(tileentity.getBlockMetadata());
		textureFile = blockprops.getTextureFile();
		ForgeHooksClient.beforeBlockRender(this, renderblocks);
		if(!blockprops.isStairs) {
			if(blockprops.rotateTexture) {
				switch(tileentity.side) {
				case 3:
					//renderblocks.field_31087_g = 2;
					//renderblocks.field_31086_h = 2;
					renderblocks.field_31085_i = 1;
					renderblocks.field_31084_j = 2;
					renderblocks.field_31083_k = 0;
					renderblocks.field_31082_l = 0;
					break;
				case 2:
					//renderblocks.field_31087_g = 0;
					//renderblocks.field_31086_h = 0;
					renderblocks.field_31085_i = 2;
					renderblocks.field_31084_j = 1;
					renderblocks.field_31083_k = 3;
					renderblocks.field_31082_l = 3;
					break;
				case 5:
					renderblocks.field_31087_g = 1;
					renderblocks.field_31086_h = 2;
					//renderblocks.field_31085_i = 3;
					//renderblocks.field_31084_j = 3;
					renderblocks.field_31083_k = 2;
					renderblocks.field_31082_l = 1;
					break;
				case 4:
					renderblocks.field_31087_g = 2;
					renderblocks.field_31086_h = 1;
					//renderblocks.field_31085_i = 3;
					//renderblocks.field_31084_j = 3;
					renderblocks.field_31083_k = 1;
					renderblocks.field_31082_l = 2;
				}
			}
			tileentity.resetBlockBounds(this);
			renderblocks.renderStandardBlock(this, x, y, z);
			renderblocks.field_31087_g = 0;
			renderblocks.field_31086_h = 0;
			renderblocks.field_31085_i = 0;
			renderblocks.field_31084_j = 0;
			renderblocks.field_31083_k = 0;
			renderblocks.field_31082_l = 0;
		}
		else {
			tileentity.firstBounds(this);
			renderblocks.renderStandardBlock(this, x, y, z);
			tileentity.secondBounds(this);
			renderblocks.renderStandardBlock(this, x, y, z);
		}
		
	}
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	public void renderInv(RenderBlocks renderblocks, Block block, int metadata) {
		BlockProperties blockprops = list.get(metadata);
		if(blockprops.isStairs) {
			this.renderType = 10;
			this.currentRender = blockprops;
		}
		else {
			this.renderType = 0;
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
		textureFile = blockprops.getTextureFile();
		ForgeHooksClient.beforeBlockRender(this, renderblocks);
		renderblocks.renderBlockOnInventory(this, metadata, 1.0F);
		this.renderType = modelID;
		this.currentRender = null;
	}
	
	private BlockProperties currentRender;
	
	@Override
	public int getBlockTextureFromSide(int side)
    {
		if(currentRender != null)
			return currentRender.getTextureId(side);
		return 0;
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z)
    {
		TileEntitySlab e = (TileEntitySlab) iblockaccess.getBlockTileEntity(x, y, z);
		if(!list.get(e.getBlockMetadata()).isStairs) {
			e.resetBlockBounds(this);
		}
		else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
    }

	@SuppressWarnings("rawtypes")
	@Override
    public void getCollidingBoundingBoxes(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, ArrayList arraylist)
    {
		TileEntitySlab tileentity = getTileEntity(world, x, y, z);
		BlockProperties blockprops = list.get(tileentity.getBlockMetadata());
		if(!blockprops.isStairs) {
	    	this.setBlockBoundsBasedOnState(world, x, y, z);
	        super.getCollidingBoundingBoxes(world, x, y, z, axisalignedbb, arraylist);
		}
		else {
			tileentity.firstBounds(this);
			super.getCollidingBoundingBoxes(world, x, y, z, axisalignedbb, arraylist);
			tileentity.secondBounds(this);
			super.getCollidingBoundingBoxes(world, x, y, z, axisalignedbb, arraylist);
		}
    }
	
	public String textureFile = super.getTextureFile();
	
	@Override
	public String getTextureFile() {
		return textureFile;
	}

	@Override
	public PropsBlockTexture getRenderProps(int metadata) {
		// TODO Auto-generated method stub
		return null;
	}
}
