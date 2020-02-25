package net.minecraft.src.nfc.props;

import net.minecraft.src.*;
import net.minecraft.src.nfc.Core;
import net.minecraft.src.nfc.Utils;
import net.minecraft.src.nfc.item.*;

public class PropsItemToolMaterial extends PropsItem {
	
	public final int TIER;
	public final int MAX_USES;
	public final float EFFICIENCY;
	public final int DMG_VS_ENTITY;
	private final boolean HAS_ARMOR;
	
	public ItemSpade spade;
	public ItemPickaxe pickaxe;
	public ItemAxe axe;
	public ItemSword sword;
	public ItemHoe hoe;
	public ItemArmor helmet;
	public ItemArmor chestPiece;
	public ItemArmor leggings;
	public ItemArmor boots;
	
	public PropsItemToolMaterial(String name, int tier, int maxUses, float efficiency, int dmgVsEntity, int textureIndex) {
		this(name, tier, maxUses, efficiency, dmgVsEntity, true, textureIndex);
	}

	public PropsItemToolMaterial(String name, int tier, int maxUses, float efficiency, int dmgVsEntity, boolean hasArmor, int textureIndex) {
		super(name, textureIndex);
		this.TIER = tier;
		this.MAX_USES = maxUses;
		this.EFFICIENCY = efficiency;
		this.DMG_VS_ENTITY = dmgVsEntity;
		this.HAS_ARMOR = hasArmor;
		this.addLocalisation(new StringBuilder().append(name).append(' ').append("Ingot").toString());
	}
	
	public int setupTools(int id) {
		this.spade = new ItemSpadeNFC(Core.getItemID("spade" + NAME, id++), this);
		this.pickaxe = new ItemPickaxeNFC(Core.getItemID("pickaxe" + NAME, id++), this);
		this.axe = new ItemAxeNFC(Core.getItemID("axe" + NAME, id++), this);
		this.sword = new ItemSwordNFC(Core.getItemID("sword" + NAME, id++), this);
		this.hoe = new ItemHoeNFC(Core.getItemID("hoe" + NAME, id++), this);
		if(this.HAS_ARMOR) {
			this.helmet = new ItemArmorNFC(Core.getItemID("helmet" + NAME, id++), this, 0);
			this.chestPiece = new ItemArmorNFC(Core.getItemID("chestpiece" + NAME, id++), this, 1);
			this.leggings = new ItemArmorNFC(Core.getItemID("leggings" + NAME, id++), this, 2);
			this.boots = new ItemArmorNFC(Core.getItemID("boots" + NAME, id++), this, 3);
		}
		return id;
	}
	
	public String getTextureFile() {
		switch (item_metadata / 16) {
		case 0:
			return Utils.getResource("items.png");
		default:
			return Utils.getResource("items2.png");
		}
	}

}
