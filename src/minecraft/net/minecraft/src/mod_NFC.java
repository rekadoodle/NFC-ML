package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.src.forge.MinecraftForgeClient;
import nfc.*;
import nfc.block.*;
import nfc.item.*;
import nfc.props.*;

public class mod_NFC extends BaseMod {

	
	@Override
	public String Version() {
		return "v0.1";
	}
	
	public mod_NFC() {
		ModLoader.RegisterTileEntity(TileEntityBrickOven.class, "nfc.brickoven");
		ModLoader.RegisterTileEntity(TileEntityFurnaceMetadataFix.class, "nfc.furnacefixed");
		MinecraftForgeClient.registerHighlightHandler(new StairPlacementHighlighter());
		
		slab_stair = ModLoader.getUniqueBlockModelID(this, true);
		render_ID  = ModLoader.getUniqueBlockModelID(this, true);
		new ItemMagicStick(itemID - 1);

		slab1 = new BlockMultiCustomRender(blockID + 2, Material.rock, STONE_SLAB, STONE_PLATED, BRICK_SLAB);
		slab2 = new BlockMultiCustomRender(blockID + 3, Material.wood, PLANKS_SLAB, PLANKS_STAIRS);
		slab3 = new BlockMultiCustomRender(blockID + 4, Material.rock, COBBLE_SLAB, SANDSTONE_SLAB);
		slab4 = new BlockMultiCustomRender(blockID + 5, Material.rock, SANDSTONE_STAIRS, BRICK_STAIRS);
		slab5 = new BlockMultiCustomRender(blockID + 6, Material.rock, STONE_BRICK_STAIRS, COBBLE_STAIRS);
		glass = new BlockMultiGlass(blockID + 7, Material.glass, GLASS, WINDOW_LARGE, WINDOW_DOUBLE, WINDOW);
		
		//Override recipes for new slabs/stairs and such
		this.overrideAllRecipes(new ItemStack(Block.stairSingle, 1, 0), STONE_SLAB.getItemStack());
		this.overrideAllRecipes(new ItemStack(Block.stairSingle, 1, 1), SANDSTONE_SLAB.getItemStack());
		this.overrideAllRecipes(new ItemStack(Block.stairSingle, 1, 2), PLANKS_SLAB.getItemStack());
		this.overrideAllRecipes(new ItemStack(Block.stairSingle, 1, 3), COBBLE_SLAB.getItemStack());
		this.overrideAllRecipes(new ItemStack(Block.stairCompactCobblestone), COBBLE_STAIRS.getItemStack());
		this.overrideAllRecipes(new ItemStack(Block.stairCompactPlanks), PLANKS_STAIRS.getItemStack());
		
		ModLoader.AddRecipe(BRICK_SLAB.getItemStack(), new Object[]{
				"XXX",
				Character.valueOf('X'), Block.brick
		});
		
		ModLoader.AddRecipe(STONE_BRICK_STAIRS.getItemStack(), new Object[]{
				"X  ",
				"XX ",
				"XXX",
				Character.valueOf('X'), STONE_BRICK.getItemStack()
		});
		
		ModLoader.AddRecipe(BRICK_STAIRS.getItemStack(), new Object[]{
				"X  ",
				"XX ",
				"XXX",
				Character.valueOf('X'), Block.brick
		});
		
		ModLoader.AddRecipe(STONE_PLATED.getItemStack(), new Object[]{
				"XX",
				"XX",
				Character.valueOf('X'), Block.stone
		});
		
		//Override vanilla furnace to use metadata smelt accepting tile entity
		Utils.replaceBlock(new BlockFurnaceMetadataFix(Block.stoneOvenIdle, false), "stoneOvenIdle", "aC");
		Utils.replaceBlock(new BlockFurnaceMetadataFix(Block.stoneOvenActive, true), "stoneOvenActive", "aD");
		
		FurnaceManager.instance.addSmelting(ORE_ALUMINUM.getItemStack(), ALUMINUM.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_COPPER.getItemStack(), COPPER.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_TIN.getItemStack(), TIN.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_BISMUTH.getItemStack(), BISMUTH.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_ZINC.getItemStack(), ZINC.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_NICKEL.getItemStack(), NICKEL.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_COBALT.getItemStack(), COBALT.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_MAGNETITE.getItemStack(), MAGNET.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_SILVER.getItemStack(), SILVER.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_LEAD.getItemStack(), LEAD.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_SILICON.getItemStack(), SILICON.getItemStack());
		FurnaceManager.instance.addSmelting(new ItemStack(Item.egg), COOKED_EGG.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_URANINITE.getItemStack(), URANIUM.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_PLATINUM.getItemStack(), PLATINUM.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_BORON.getItemStack(), BORON.getItemStack());
	}
	
	private final Field recipeOutput = Utils.getField(ShapedRecipes.class, "recipeOutput");
	
	public void overrideAllRecipes(ItemStack vanilla, ItemStack newitems) {
		@SuppressWarnings("unchecked")
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for(IRecipe recipe : recipes) {
			if(recipe instanceof ShapedRecipes) {
				if(recipe.getRecipeOutput().isItemEqual(vanilla)) {
					try {
						recipeOutput.set(recipe, newitems);
					} 
					catch (Exception e) { e.printStackTrace(); }
				}
			}
		}
	}
	
	@Override
	public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int x, int y, int z, Block block, int renderType)
    {
		((BlockMultiTexture) block).renderBlockWorld(renderblocks, iblockaccess.getBlockMetadata(x, y, z), x, y, z);
        return true;
    }
	
	@Override
	public void RenderInvBlock(RenderBlocks renderblocks, Block block, int metadata, int renderType)
    {
		((BlockMultiTexture) block).renderBlockInv(renderblocks, metadata);;
    }
	
	//wood
	//stone
	public static final PropsItemToolMaterial ALUMINUM = new PropsItemToolMaterial("Aluminum", 1, 35, 5.0F, 3, 144);
	public static final PropsItemToolMaterial BISMUTH = new PropsItemToolMaterial("Bismuth", 1, 65, 3.5F, 3, 145);
	public static final PropsItemToolMaterial COPPER = new PropsItemToolMaterial("Copper", 1, 50, 4.0F, 3, 146);
	public static final PropsItemToolMaterial LEAD = new PropsItemToolMaterial("Lead", 1, 115, 2.5F, 3, false, 147);
	public static final PropsItemToolMaterial TIN = new PropsItemToolMaterial("Tin", 1, 40, 4.5F, 3, 148);
	public static final PropsItemToolMaterial ZINC = new PropsItemToolMaterial("Zinc", 1, 80, 3.0F, 3, 149);
	//gold
	public static final PropsItemToolMaterial BORON = new PropsItemToolMaterial("Boron", 2, 50, 10.0F, 4, 150);
	public static final PropsItemToolMaterial BRASS = new PropsItemToolMaterial("Brass", 2, 125, 5.0F, 4, 151);
	public static final PropsItemToolMaterial BRONZE = new PropsItemToolMaterial("Bronze", 2, 125, 5.0F, 4, 152);
	public static final PropsItemToolMaterial NICKEL = new PropsItemToolMaterial("Nickel", 2, 85, 7.0F, 4, 153);
	public static final PropsItemToolMaterial PLATINUM = new PropsItemToolMaterial("Platinum", 2, 215, 3.5F, 4, 154);
	public static final PropsItemToolMaterial SILVER = new PropsItemToolMaterial("Silver", 2, 260, 3.0F, 4, 155);
	public static final PropsItemToolMaterial CHROME = new PropsItemToolMaterial("Chrome", 3, 200, 8.0F, 6, 156);
	public static final PropsItemToolMaterial COBALT = new PropsItemToolMaterial("Cobalt", 3, 700, 4.0F, 6, 157);
	//iron
	public static final PropsItemToolMaterial SILICON = new PropsItemToolMaterial("Silicon", 3, 150, 10.0F, 6, 158);
	public static final PropsItemToolMaterial STEEL = new PropsItemToolMaterial("Steel", 4, 700, 8.0F, 10, 159);
	public static final PropsItemToolMaterial TITANIUM = new PropsItemToolMaterial("Titanium", 4, 350, 14.0F, 10, 160);
	public static final PropsItemToolMaterial TUNGSTEN = new PropsItemToolMaterial("Tungsten", 4, 1100, 6.0F, 10, 161);
	public static final PropsItemToolMaterial RUBY = new PropsItemToolMaterial("Ruby", 4, 1000, 10.0F, 20, 162);
	public static final PropsItemToolMaterial SAPHIRE = new PropsItemToolMaterial("Saphire", 4, 1000, 10.0F, 20, 163);
	public static final PropsItemToolMaterial EMERALD = new PropsItemToolMaterial("Emerald", 4, 1000, 10.0F, 20, 164);
	//diamond
	public static final PropsItemToolMaterial OSMIUM = new PropsItemToolMaterial("Osmium", 5, 5000, 5.0F, 20, 165);
	
	public static final PropsItem.Food COOKED_EGG = new PropsItem.Food("Cooked Egg", 4, 166);
	public static final PropsItem.Food CHEESE = new PropsItem.Food("Cheese", 5, 167);
	
	public static final PropsItem MAGNET = new PropsItem("Magnet", 168);
	public static final PropsItem URANIUM = new PropsItem("Uranium", 170);
	public static final PropsItem ANTHRICITE = new PropsItem("Anthricite", 169);

	public static int render_ID;
	public int slab_stair;
	public static final String resources = "/nfc/resources/";
	public static final int blockID = 150;
	public static final int itemID = 454 - 256;
	static int id = itemID;
	static int ingotID = 0;
	
	public static ItemMulti item = new ItemMulti(itemID, ALUMINUM, BISMUTH, COPPER, LEAD, TIN, ZINC, BORON, BRASS, BRONZE, NICKEL, PLATINUM, SILVER, CHROME, COBALT, SILICON, STEEL, TITANIUM, TUNGSTEN, RUBY, SAPHIRE, EMERALD, OSMIUM, COOKED_EGG, CHEESE, MAGNET, URANIUM, ANTHRICITE);
	
	public static final PropsBlock.Ore ORE_COPPER = new PropsBlock.Ore(COPPER, 3.0F, 0);
	public static final PropsBlock.Ore ORE_TIN = new PropsBlock.Ore(TIN, 3.0F, 1);
	public static final PropsBlock.Ore ORE_ZINC = new PropsBlock.Ore(ZINC, 3.0F, 2);
	public static final PropsBlock.Ore ORE_ALUMINUM = new PropsBlock.Ore(ALUMINUM, 3.0F, 3);
	public static final PropsBlock.Ore ORE_LEAD = new PropsBlock.Ore(LEAD, 3.0F, 4);
	public static final PropsBlock.Ore ORE_BISMUTH = new PropsBlock.Ore(BISMUTH, 3.0F, 5);
	public static final PropsBlock.Ore ORE_BORON = new PropsBlock.Ore(BORON, 3.5F, 6);
	public static final PropsBlock.Ore ORE_SILVER = new PropsBlock.Ore(SILVER, 3.5F, 7);
	public static final PropsBlock.Ore ORE_CHROME = new PropsBlock.Ore(CHROME, 4.0F, 8);
	public static final PropsBlock.Ore ORE_NICKEL = new PropsBlock.Ore(NICKEL, 3.5F, 9);
	public static final PropsBlock.Ore ORE_PLATINUM = new PropsBlock.Ore(PLATINUM, 3.5F, 10);
	public static final PropsBlock.Ore ORE_TUNGSTEN = new PropsBlock.Ore(TUNGSTEN, 6.0F, 11);
	public static final PropsBlock.Ore ORE_SILICON = new PropsBlock.Ore(SILICON, 4.0F, 12);
	public static final PropsBlock.Ore ORE_COBALT = new PropsBlock.Ore(COBALT, 4.0F, 13);
	public static final PropsBlock.Ore ORE_MAGNETITE = new PropsBlock.Ore("Magnetite", 4.0F, 14);
	public static final PropsBlock.Ore ORE_TITANIUM = new PropsBlock.Ore(TITANIUM, 6.0F, 15);
	public static final PropsBlock.Ore ORE_ANTHRACITE = new PropsBlock.Ore("Anthracite", 4.0F, ANTHRICITE.item_id, ANTHRICITE.item_metadata, 16);
	public static final PropsBlock.Ore ORE_RUBY = new PropsBlock.Ore(RUBY, 8.0F, RUBY.item_id, RUBY.item_metadata, 17);
	public static final PropsBlock.Ore ORE_SAPHIRE = new PropsBlock.Ore(SAPHIRE, 8.0F, SAPHIRE.item_id, SAPHIRE.item_metadata, 18);
	public static final PropsBlock.Ore ORE_EMERALD = new PropsBlock.Ore(EMERALD, 8.0F, EMERALD.item_id, EMERALD.item_metadata, 19);
	public static final PropsBlock.Ore ORE_URANINITE = new PropsBlock.Ore("Uraninite", 8.0F, 20);
	public static final PropsBlock.Ore ORE_OSMIUM = new PropsBlock.Ore(OSMIUM, 10.0F, 21);
	public static final PropsBlock STONE_BLOCK = new PropsBlock("Stone Block", 1.0F, 10.0F, 22);
	public static final PropsBlock STONE_BLOCK_OFFSET_XY = new PropsBlock("Stone Block Offset:XY", 1.0F, 10.0F, 23);
	public static final PropsBlock STONE_BLOCK_OFFSET_X = new PropsBlock("Stone Block Offset:X", 1.0F, 10.0F, 24);
	public static final PropsBlock STONE_BLOCK_OFFSET_Y = new PropsBlock("Stone Block Offset:Y", 1.0F, 10.0F, 25);
	public static final PropsBlock STONE_BRICK = new PropsBlock("Stone Brick", 1.0F, 10.0F, 26);
	public static final PropsBlock STONE_BRICK_SMALL = new PropsBlock("Small Stone Brick", 1.0F, 10.0F, 27);
	
	private static final BlockMulti stone1 = new BlockMulti(blockID, Material.rock, ORE_COPPER, ORE_TIN, ORE_ZINC, ORE_ALUMINUM, ORE_LEAD, ORE_BISMUTH, ORE_BORON, ORE_SILVER, ORE_CHROME, ORE_NICKEL, ORE_PLATINUM, ORE_TUNGSTEN, ORE_SILICON, ORE_COBALT, ORE_MAGNETITE, ORE_TITANIUM);
	private static final BlockMulti stone2 = new BlockMulti(blockID + 1, Material.rock, ORE_ANTHRACITE, ORE_RUBY, ORE_SAPHIRE, ORE_EMERALD, ORE_OSMIUM, STONE_BLOCK, STONE_BLOCK_OFFSET_XY,  STONE_BLOCK_OFFSET_X, STONE_BLOCK_OFFSET_Y, STONE_BRICK, STONE_BRICK_SMALL);
	
	public static final PropsBlockDummyCustom STONE_PLATED = new PropsBlockDummyCustom(new PropsBlockTexture("Plated Stone", "/terrain.png", 1.0F, 10.0F, 6));
	public static final PropsBlockSlabRotatedTexture STONE_SLAB = new PropsBlockSlabRotatedTexture("Stone", Block.stairDouble, 0);
	public static final PropsBlockStairs COBBLE_STAIRS = new PropsBlockStairs("Stone", Block.cobblestone);
	public static final PropsBlockSlab PLANKS_SLAB = new PropsBlockSlab("Wood", Block.planks);
	public static final PropsBlockStairs PLANKS_STAIRS = new PropsBlockStairs("Wood", Block.planks);
	public static final PropsBlockSlab COBBLE_SLAB = new PropsBlockSlab("Cobble", Block.cobblestone);
	public static final PropsBlockSlab SANDSTONE_SLAB = new PropsBlockSlab("Sandstone", Block.sandStone);
	public static final PropsBlockStairs SANDSTONE_STAIRS = new PropsBlockStairs("Sandstone", Block.sandStone);
	public static final PropsBlockStairs BRICK_STAIRS = new PropsBlockStairs("Brick", Block.brick);
	public static final PropsBlockStairs STONE_BRICK_STAIRS = new PropsBlockStairs("Stone", Block.blocksList[STONE_BRICK.block_id], STONE_BRICK.block_metadata);
	public static final PropsBlockSlab BRICK_SLAB = new PropsBlockSlab("Brick", Block.brick);
	public static final PropsBlockTexture GLASS = new PropsBlockTexture("Glass", Block.glass);
	public static final PropsBlock WINDOW_LARGE = new PropsBlock("Large Window", 0.3F, 1.5F, 28);
	public static final PropsBlock WINDOW_DOUBLE = new PropsBlock("Double Window", 0.3F, 1.5F, 29);
	public static final PropsBlock WINDOW = new PropsBlock("Window", 0.3F, 1.5F, 30);
	
	private final BlockMultiCustomRender slab1;
	private final BlockMultiCustomRender slab2;
	private final BlockMultiCustomRender slab3;
	private final BlockMultiCustomRender slab4;
	private final BlockMultiCustomRender slab5;
	private final BlockMultiTexture glass;
	
	public static final Block BRICKOVEN_IDLE = new BlockBrickOven(230, false, 32);
	public static final Block BRICKOVEN_ACTIVE = new BlockBrickOven(231, true, 34);
	
	
	
}
