package net.minecraft.src.nfc;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.forge.ForgeHooks;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.nfc.block.*;
import net.minecraft.src.nfc.item.ItemMulti;
import net.minecraft.src.nfc.item.ItemRecordNFC;
import net.minecraft.src.nfc.props.*;

public class Core {

	public static final Core instance = new Core();
	public mod_NFC basemod;
	public SoundManagerNFC soundManager = new SoundManagerNFC();
	
	public static final int blockID = 150;
	public static final int itemID = 454 - 256;

	/**
	 * Generate Props for relevant Items and Blocks
	 * @see net.minecraft.src.nfc.props.Props
	 */
	public static final PropsItemToolMaterial ALUMINUM = new PropsItemToolMaterial("Aluminum", 1, 35, 5.0F, 3, 144);
	public static final PropsItemToolMaterial BISMUTH = new PropsItemToolMaterial("Bismuth", 1, 65, 3.5F, 3, 145);
	public static final PropsItemToolMaterial COPPER = new PropsItemToolMaterial("Copper", 1, 50, 4.0F, 3, 146);
	public static final PropsItemToolMaterial LEAD = new PropsItemToolMaterial("Lead", 1, 115, 2.5F, 3, false, 147);
	public static final PropsItemToolMaterial TIN = new PropsItemToolMaterial("Tin", 1, 40, 4.5F, 3, 148);
	public static final PropsItemToolMaterial ZINC = new PropsItemToolMaterial("Zinc", 1, 80, 3.0F, 3, 149);
	public static final PropsItemToolMaterial BORON = new PropsItemToolMaterial("Boron", 2, 50, 10.0F, 4, 150);
	public static final PropsItemToolMaterial BRASS = new PropsItemToolMaterial("Brass", 2, 125, 5.0F, 4, 151);
	public static final PropsItemToolMaterial BRONZE = new PropsItemToolMaterial("Bronze", 2, 125, 5.0F, 4, 152);
	public static final PropsItemToolMaterial NICKEL = new PropsItemToolMaterial("Nickel", 2, 85, 7.0F, 4, 153);
	public static final PropsItemToolMaterial PLATINUM = new PropsItemToolMaterial("Platinum", 2, 215, 3.5F, 4, 154);
	public static final PropsItemToolMaterial SILVER = new PropsItemToolMaterial("Silver", 2, 260, 3.0F, 4, 155);
	public static final PropsItemToolMaterial CHROME = new PropsItemToolMaterial("Chrome", 3, 200, 8.0F, 6, 156);
	public static final PropsItemToolMaterial COBALT = new PropsItemToolMaterial("Cobalt", 3, 700, 4.0F, 6, 157);
	public static final PropsItemToolMaterial SILICON = new PropsItemToolMaterial("Silicon", 3, 150, 10.0F, 6, 158);
	public static final PropsItemToolMaterial STEEL = new PropsItemToolMaterial("Steel", 4, 700, 8.0F, 10, 159);
	public static final PropsItemToolMaterial TITANIUM = new PropsItemToolMaterial("Titanium", 4, 350, 14.0F, 10, 160);
	public static final PropsItemToolMaterial TUNGSTEN = new PropsItemToolMaterial("Tungsten", 4, 1100, 6.0F, 10, 161);
	public static final PropsItemToolMaterial RUBY = new PropsItemToolMaterial("Ruby", 4, 1000, 10.0F, 20, 162);
	public static final PropsItemToolMaterial SAPPHIRE = new PropsItemToolMaterial("Sapphire", 4, 1000, 10.0F, 20, 163);
	public static final PropsItemToolMaterial EMERALD = new PropsItemToolMaterial("Emerald", 4, 1000, 10.0F, 20, 164);
	public static final PropsItemToolMaterial OSMIUM = new PropsItemToolMaterial("Osmium", 5, 5000, 5.0F, 20, 165);

	public static final PropsItem.Food COOKED_EGG = new PropsItem.Food("Cooked Egg", 4, 166);
	public static final PropsItem.Food CHEESE = new PropsItem.Food("Cheese", 5, 167);

	public static final PropsItem MAGNET = new PropsItem("Magnet", 168);
	public static final PropsItem URANIUM = new PropsItem("Uranium", 170);
	public static final PropsItem ANTHRACITE = new PropsItem("Anthracite", 169);

	public static final PropsItem.Wrench WRENCH = new PropsItem.Wrench("Wrench", 171);
	public static final PropsItem.Telescope TELESCOPE = new PropsItem.Telescope("Telescope", 172);

	static {
		// Apply props to new item
		new ItemMulti(itemID, ALUMINUM, BISMUTH, COPPER, LEAD, TIN, ZINC, BORON, BRASS, BRONZE, NICKEL, PLATINUM,
				SILVER, CHROME, COBALT, SILICON, STEEL, TITANIUM, TUNGSTEN, RUBY, SAPPHIRE, EMERALD, OSMIUM,
				MAGNET, URANIUM, ANTHRACITE);
		new ItemMulti(itemID + 1, COOKED_EGG, CHEESE, WRENCH, TELESCOPE).setMaxStackSize(1);
	}
	
	public static final ItemRecordNFC RECORD_CHANT = new ItemRecordNFC(555, SoundManagerNFC.CHANT, 181);
	public static final ItemRecordNFC RECORD_DROOPY = new ItemRecordNFC(556, SoundManagerNFC.DROOPY, 180);
	public static final ItemRecordNFC RECORD_EMOTION = new ItemRecordNFC(567, SoundManagerNFC.EMOTION, 177);
	public static final ItemRecordNFC RECORD_BERLIN = new ItemRecordNFC(568, SoundManagerNFC.BERLIN, 178);
	public static final ItemRecordNFC RECORD_PLEASE = new ItemRecordNFC(569, SoundManagerNFC.PLEASE, 179);
	public static final ItemRecordNFC RECORD_FRONTIER = new ItemRecordNFC(570, SoundManagerNFC.FRONTIER, 182);
	public static final ItemRecordNFC RECORD_TWENTYSIX = new ItemRecordNFC(571, SoundManagerNFC.TWENTYSIX, 176);

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
	public static final PropsBlock.Ore ORE_ANTHRACITE = new PropsBlock.Ore("Anthracite", 4.0F, ANTHRACITE.item_id, ANTHRACITE.item_metadata, 16);
	public static final PropsBlock.Ore ORE_RUBY = new PropsBlock.Ore(RUBY, 8.0F, RUBY.item_id, RUBY.item_metadata, 17);
	public static final PropsBlock.Ore ORE_SAPPHIRE = new PropsBlock.Ore(SAPPHIRE, 8.0F, SAPPHIRE.item_id, SAPPHIRE.item_metadata, 18);
	public static final PropsBlock.Ore ORE_EMERALD = new PropsBlock.Ore(EMERALD, 8.0F, EMERALD.item_id, EMERALD.item_metadata, 19);
	public static final PropsBlock.Ore ORE_URANINITE = new PropsBlock.Ore("Uraninite", 8.0F, 20);
	public static final PropsBlock.Ore ORE_OSMIUM = new PropsBlock.Ore(OSMIUM, 10.0F, 21);
	public static final PropsBlock STONE_BLOCK = new PropsBlock("Stone Block", 1.0F, 10.0F, 22);
	public static final PropsBlock STONE_BLOCK_OFFSET_XY = new PropsBlock("Stone Block Offset:XY", 1.0F, 10.0F, 23);
	public static final PropsBlock STONE_BLOCK_OFFSET_X = new PropsBlock("Stone Block Offset:X", 1.0F, 10.0F, 24);
	public static final PropsBlock STONE_BLOCK_OFFSET_Y = new PropsBlock("Stone Block Offset:Y", 1.0F, 10.0F, 25);
	public static final PropsBlock STONE_BRICK = new PropsBlock("Stone Brick", 1.0F, 10.0F, 26);
	public static final PropsBlock STONE_BRICK_SMALL = new PropsBlock("Small Stone Brick", 1.0F, 10.0F, 27);

	static {
		// Apply props to new blocks
		new BlockMulti(blockID, Material.rock, ORE_COPPER, ORE_TIN, ORE_ZINC, ORE_ALUMINUM, ORE_LEAD, ORE_BISMUTH,
				ORE_BORON, ORE_SILVER, ORE_CHROME, ORE_NICKEL, ORE_PLATINUM, ORE_TUNGSTEN, ORE_SILICON, ORE_COBALT,
				ORE_MAGNETITE, ORE_TITANIUM);
		new BlockMulti(blockID + 1, Material.rock, ORE_ANTHRACITE, ORE_RUBY, ORE_SAPPHIRE, ORE_EMERALD, ORE_URANINITE,
				ORE_OSMIUM, STONE_BLOCK, STONE_BLOCK_OFFSET_XY, STONE_BLOCK_OFFSET_X, STONE_BLOCK_OFFSET_Y, STONE_BRICK,
				STONE_BRICK_SMALL);
	}

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

	public static final Block BRICKOVEN_IDLE = new BlockBrickOven(230, false, 32);
	public static final Block BRICKOVEN_ACTIVE = new BlockBrickOven(231, true, 34);

	public String version() {
		return "v0.27";
	}

	// Name for mine_diver's ModMenu
	public String name() {
		return "New Frontier Craft ML";
	}

	// Description for mine_diver's ModMenu
	public String description() {
		return "A new adventure in old Minecraft";
	}
	
	// Icon for mine_diver's ModMenu
	public String icon() {
		return Utils.getResource("logo.png");
	}

	public void init(mod_NFC basemod) {
		this.basemod = basemod;
		
		ModLoader.SetInGameHook(basemod, true, false);
		MinecraftForgeClient.registerHighlightHandler(new StairPlacementHighlighter());
		
		ModLoader.RegisterTileEntity(TileEntityBrickOven.class, "nfc.brickoven");
		ModLoader.RegisterTileEntity(TileEntityFurnaceMetadataFix.class, "nfc.furnacefixed");
		
		for (String texture : new String[] {"items.png", "items2.png", "terrain.png"}) {
			if(Utils.resourceExists(texture))
				MinecraftForgeClient.preloadTexture(Utils.getResource(texture));
		}
        
		//This render ID is used for all blocks with custom renders
		int renderID  = ModLoader.getUniqueBlockModelID(basemod, true);

		//Apply props to new blocks
		new BlockMultiCustomRender(blockID + 2, renderID, Material.rock, STONE_SLAB, STONE_PLATED, BRICK_SLAB);
		new BlockMultiCustomRender(blockID + 3, renderID, Material.wood, PLANKS_SLAB, PLANKS_STAIRS);
		new BlockMultiCustomRender(blockID + 4, renderID, Material.rock, COBBLE_SLAB, SANDSTONE_SLAB);
		new BlockMultiCustomRender(blockID + 5, renderID, Material.rock, SANDSTONE_STAIRS, BRICK_STAIRS);
		new BlockMultiCustomRender(blockID + 6, renderID, Material.rock, STONE_BRICK_STAIRS, COBBLE_STAIRS);
		
		//Override vanilla glass block
		Block glass = new BlockMultiGlass(Utils.clearBlockID(Block.glass), renderID, Material.glass, GLASS, WINDOW_LARGE, WINDOW_DOUBLE, WINDOW);
		Utils.replaceBlock(glass, "glass", "N");
		
		//Window recipes
		ModLoader.AddRecipe(WINDOW.getItemStack(3), new Object[] {
				"XYX",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('Y'), Block.glass
		});
		
		ModLoader.AddRecipe(WINDOW_DOUBLE.getItemStack(4), new Object[] {
				" X ",
				"XYX",
				" X ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('Y'), Block.glass
		});
		
		ModLoader.AddShapelessRecipe(WINDOW_LARGE.getItemStack(2), new Object[] {
				Block.planks, Block.glass
		});
		
		//Override recipes for new slabs/stairs and such
		Utils.overrideShapedRecipes(new ItemStack(Block.stairSingle, 1, 0), STONE_SLAB.getItemStack());
		Utils.overrideShapedRecipes(new ItemStack(Block.stairSingle, 1, 1), SANDSTONE_SLAB.getItemStack());
		Utils.overrideShapedRecipes(new ItemStack(Block.stairSingle, 1, 2), PLANKS_SLAB.getItemStack());
		Utils.overrideShapedRecipes(new ItemStack(Block.stairSingle, 1, 3), COBBLE_SLAB.getItemStack());
		Utils.overrideShapedRecipes(new ItemStack(Block.stairCompactCobblestone), COBBLE_STAIRS.getItemStack());
		Utils.overrideShapedRecipes(new ItemStack(Block.stairCompactPlanks), PLANKS_STAIRS.getItemStack());
		
		//Various recipes
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
		
		ModLoader.AddRecipe(SANDSTONE_STAIRS.getItemStack(), new Object[]{
				"X  ",
				"XX ",
				"XXX",
				Character.valueOf('X'), Block.sandStone
		});
		
		ModLoader.AddRecipe(STONE_PLATED.getItemStack(), new Object[]{
				"XX",
				"XX",
				Character.valueOf('X'), Block.stone
		});

		ModLoader.AddRecipe(new ItemStack(STONE_SLAB.block_id, 1, STONE_SLAB.block_metadata + 6), new Object[]{
				"X",
				"X",
				Character.valueOf('X'), STONE_SLAB.getItemStack()
		});
		
		ModLoader.AddShapelessRecipe(STONE_SLAB.getItemStack(2), new Object[] {
				new ItemStack(STONE_SLAB.block_id, 1, STONE_SLAB.block_metadata + 6)
		});
		
		ModLoader.AddShapelessRecipe(STONE_BLOCK.getItemStack(), new Object[] {
				Block.stone
		});
		
		ModLoader.AddRecipe(STONE_BLOCK_OFFSET_X.getItemStack(2), new Object[]{
				"XX",
				Character.valueOf('X'), STONE_BLOCK.getItemStack()
		});
		
		ModLoader.AddRecipe(STONE_BLOCK_OFFSET_Y.getItemStack(2), new Object[]{
				"X",
				"X",
				Character.valueOf('X'), STONE_BLOCK.getItemStack()
		});
		
		ModLoader.AddRecipe(STONE_BLOCK_OFFSET_XY.getItemStack(4), new Object[]{
				"XX",
				"XX",
				Character.valueOf('X'), STONE_BLOCK.getItemStack()
		});
		
		ModLoader.AddRecipe(STONE_BRICK.getItemStack(3), new Object[]{
				"XXX",
				Character.valueOf('X'), STONE_BLOCK.getItemStack()
		});
		
		ModLoader.AddRecipe(STONE_BRICK_SMALL.getItemStack(9), new Object[]{
				"XXX",
				"XXX",
				"XXX",
				Character.valueOf('X'), STONE_BLOCK.getItemStack()
		});
		
		ModLoader.AddRecipe(new ItemStack(BRICKOVEN_IDLE), new Object[]{
				"XXX",
				"X X",
				"XXX",
				Character.valueOf('X'), Block.brick
		});
		
		ModLoader.AddRecipe(TELESCOPE.getItemStack(), new Object[]{
				"GX ",
				"XL ",
				"  X",
				Character.valueOf('X'), BRASS.getItemStack(),
				Character.valueOf('G'), Block.glass,
				Character.valueOf('L'), Item.leather
		});
		
		ModLoader.AddRecipe(WRENCH.getItemStack(), new Object[]{
				"X X",
				" L ",
				" X ",
				Character.valueOf('X'), Item.ingotIron,
				Character.valueOf('L'), Item.leather
		});
		
		//Override vanilla furnace to use metadata smelt accepting tile entity
		Utils.replaceBlock(new BlockFurnaceMetadataFix(Block.stoneOvenIdle, false), "stoneOvenIdle", "aC");
		Utils.replaceBlock(new BlockFurnaceMetadataFix(Block.stoneOvenActive, true), "stoneOvenActive", "aD");
		
		//NFC Ore Smelting
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
		FurnaceManager.instance.addSmelting(ORE_CHROME.getItemStack(), CHROME.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_OSMIUM.getItemStack(), OSMIUM.getItemStack());
		
		//NFC Gem Smelting for consistency
		FurnaceManager.instance.addSmelting(ORE_RUBY.getItemStack(), RUBY.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_SAPPHIRE.getItemStack(), SAPPHIRE.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_EMERALD.getItemStack(), EMERALD.getItemStack());
		FurnaceManager.instance.addSmelting(ORE_ANTHRACITE.getItemStack(), ANTHRACITE.getItemStack());
		
		//Prevent gems from being named 'Gem Ingot'
		RUBY.addLocalisation(RUBY.NAME);
		SAPPHIRE.addLocalisation(SAPPHIRE.NAME);
		EMERALD.addLocalisation(EMERALD.NAME);
		
		//Override vanilla tool tier settings
		new Utils.EasyField<Boolean>(ForgeHooks.class, "toolInit").set(true);
		
		MinecraftForge.setToolClass(Item.pickaxeWood, "pickaxe", 0);
		MinecraftForge.setToolClass(Item.pickaxeStone, "pickaxe", 0);
		MinecraftForge.setToolClass(Item.pickaxeSteel, "pickaxe", 3);
		MinecraftForge.setToolClass(Item.pickaxeGold, "pickaxe", 2);
		MinecraftForge.setToolClass(Item.pickaxeDiamond, "pickaxe", 5);

		MinecraftForge.setToolClass(Item.axeWood, "axe", 0);
		MinecraftForge.setToolClass(Item.axeStone, "axe", 0);
		MinecraftForge.setToolClass(Item.axeSteel, "axe", 3);
		MinecraftForge.setToolClass(Item.axeGold, "axe", 2);
		MinecraftForge.setToolClass(Item.axeDiamond, "axe", 5);

		MinecraftForge.setToolClass(Item.shovelWood, "shovel", 0);
		MinecraftForge.setToolClass(Item.shovelStone, "shovel", 0);
		MinecraftForge.setToolClass(Item.shovelSteel, "shovel", 3);
		MinecraftForge.setToolClass(Item.shovelGold, "shovel", 2);
		MinecraftForge.setToolClass(Item.shovelDiamond, "shovel", 5);

		MinecraftForge.setBlockHarvestLevel(Block.obsidian, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(Block.oreDiamond, "pickaxe", 4);
		MinecraftForge.setBlockHarvestLevel(Block.blockDiamond, "pickaxe", 4);
		MinecraftForge.setBlockHarvestLevel(Block.oreIron, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Block.blockSteel, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Block.oreLapis, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Block.blockLapis, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Block.oreRedstone, "pickaxe", 3);
		MinecraftForge.setBlockHarvestLevel(Block.oreRedstoneGlowing, "pickaxe", 3);
		MinecraftForge.removeBlockEffectiveness(Block.oreRedstone, "pickaxe");
		MinecraftForge.removeBlockEffectiveness(Block.oreRedstoneGlowing, "pickaxe");

		for (Block block : new Block[] { Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone,
				Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal,
				Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack,
				Block.oreGold, Block.blockGold }) {
			MinecraftForge.setBlockHarvestLevel(block, "pickaxe", 0);
		}
	}
	
	public void onTickIngame() 
	{
		if(TELESCOPE.isZooming()) 
		{
			ItemStack heldItem = Utils.mc.thePlayer.getCurrentEquippedItem();
			if(heldItem != null && heldItem.itemID == TELESCOPE.item_id && heldItem.getItemDamage() == TELESCOPE.item_metadata) 
			{
				TELESCOPE.renderOverlay();
			}
			else 
			{
				TELESCOPE.setZoom(false);
			}
		}
	}
	
	public void renderWorldBlock(RenderBlocks renderblocks, BlockMultiTexture block, int metadata, int x, int y, int z) 
	{
		block.renderBlockWorld(renderblocks, metadata, x, y, z);
	}
	
	public void renderInvBlock(RenderBlocks renderblocks, BlockMultiTexture block, int metadata)
    {
		block.renderBlockInv(renderblocks, metadata);
    }
	
	public void generateSurface(World world, Random rand, int x, int z)
    {
		//Undo vanilla ore generation
		for(int y = 0; y < 128; y++) {
			Block block = Block.blocksList[world.getBlockId(x, y, z)];
			if(block == Block.oreDiamond || block == Block.oreCoal || block == Block.oreGold || block == Block.oreIron
					|| block == Block.oreLapis || block == Block.oreRedstone || block == Block.oreRedstoneGlowing)
			world.setBlock(x, y, z, Block.stone.blockID);
		}
		
		//Generate ores
		int genX;
		int genY;
		int genZ;
		int spawnProbability = rand.nextInt(100);
		if (spawnProbability <= 3) 
		{
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(16);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinable(Block.oreDiamond.blockID, 8)).generate(world, rand, genX, genY, genZ);
		} 
		else if (spawnProbability == 4) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(12);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_OSMIUM, 9)).generate(world, rand, genX, genY, genZ);
		}

		genX = x + rand.nextInt(16);
		genY = rand.nextInt(96);
		genZ = z + rand.nextInt(16);
		(new WorldGenConcentrated(Block.oreCoal.blockID, 8, 2, 16)).generate(world, rand, genX, genY, genZ);

		if (rand.nextInt(2) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(16) + rand.nextInt(16);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(Block.oreLapis.blockID, 6)).generate(world, rand, genX, genY, genZ);
		}

		for (int i = 0; i < 3; i++) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(16);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinable(Block.oreRedstone.blockID, 7)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(8) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(50);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(ORE_ALUMINUM, 8, 2, 90)).generate(world, rand, genX, genY, genZ);
		}

		int numberOfSpawns = 3 + rand.nextInt(2);
		for (int i = 0; i < numberOfSpawns; i++) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(50) + 10;
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_TIN, 5)).generate(world, rand, genX, genY, genZ);
		}

		genX = x + rand.nextInt(16);
		genY = rand.nextInt(50);
		genZ = z + rand.nextInt(16);
		(new WorldGenMinableNFC(ORE_ZINC, 10)).generate(world, rand, genX, genY, genZ);

		if (rand.nextInt(6) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(48);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_LEAD, 12)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(8) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(64);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_BISMUTH, 15)).generate(world, rand, genX, genY, genZ);
		}

		for (int i = 0; i < 3; i++) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(36) + 32;
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(ORE_COPPER, 4, 1, 16)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(4) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(70);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(ORE_COPPER, 5, 1, 40)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(64) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(60);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(Block.oreGold.blockID, 10, 0, 32)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(20) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(24) + 4;
			genZ = z + rand.nextInt(16);
			(new WorldGenConcentrated(ORE_ANTHRACITE, 10, 3, 10)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(5) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(40);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(ORE_BORON, 4, 0, 10)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(16) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(50);
			genZ = z + rand.nextInt(16);
			(new WorldGenConcentrated(ORE_SILVER, 12, 6, 8)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(12) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(24) + rand.nextInt(24);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(ORE_NICKEL, 6, 4, 24)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(20) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(40);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_PLATINUM, 18)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(42) <= 2) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(24) + rand.nextInt(8) + 2;
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(Block.oreIron.blockID, 16, 2, 200)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(16) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(32);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_COBALT, 16)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(5) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(18) + 2;
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_CHROME, 10)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(15) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(24);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(ORE_SILICON, 5, 4, 15)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(25) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(16);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableCloud(ORE_TITANIUM, 8, 3, 25)).generate(world, rand, genX, genY, genZ);
		}

		genX = x + rand.nextInt(16);
		genY = rand.nextInt(6) + 3;
		genZ = z + rand.nextInt(16);
		(new WorldGenMinableNFC(ORE_TUNGSTEN, 1)).generate(world, rand, genX, genY, genZ);

		// {
		// int i3 = x + rand.nextInt(16);
		// int j6 = rand.nextInt(3) + 2;
		// int k9 = z + rand.nextInt(16);
		// (new WorldGenMinableNFC(NFC.FakeBedrock.blockID, 1)).generate(world, rand,
		// i3, j6, k9);
		// }

		if (rand.nextInt(32) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(32);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_URANINITE, 16)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(10) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(32);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_MAGNETITE, 5)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(7) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(16);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_RUBY, 1)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(7) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(16);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_EMERALD, 1)).generate(world, rand, genX, genY, genZ);
		}

		if (rand.nextInt(7) == 1) {
			genX = x + rand.nextInt(16);
			genY = rand.nextInt(16);
			genZ = z + rand.nextInt(16);
			(new WorldGenMinableNFC(ORE_SAPPHIRE, 1)).generate(world, rand, genX, genY, genZ);
		}
    }

	public void modsLoaded() {
		Utils.modsLoaded(basemod);
	}
}
