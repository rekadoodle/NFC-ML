package nfc.props;

import net.minecraft.src.*;
import nfc.item.ItemArmorNFC;
import nfc.item.ItemAxeNFC;
import nfc.item.ItemHoeNFC;
import nfc.item.ItemPickaxeNFC;
import nfc.item.ItemSpadeNFC;
import nfc.item.ItemSwordNFC;

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
		this.spade = new ItemSpadeNFC(id++, this);
		this.pickaxe = new ItemPickaxeNFC(id++, this);
		this.axe = new ItemAxeNFC(id++, this);
		this.sword = new ItemSwordNFC(id++, this);
		this.hoe = new ItemHoeNFC(id++, this);
		if(this.HAS_ARMOR) {
			this.helmet = new ItemArmorNFC(id++, this, 0);
			this.chestPiece = new ItemArmorNFC(id++, this, 1);
			this.leggings = new ItemArmorNFC(id++, this, 2);
			this.boots = new ItemArmorNFC(id++, this, 3);
		}
		return id;
	}
	
	public String getTextureFile() {
		switch (item_metadata / 16) {
		case 0:
			return new StringBuilder(mod_NFC.resources).append("items.png").toString();
		default:
			return new StringBuilder(mod_NFC.resources).append("items2.png").toString();
		}
	}

}
