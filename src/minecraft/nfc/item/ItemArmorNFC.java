package nfc.item;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;
import nfc.props.PropsItemToolMaterial;

public class ItemArmorNFC extends ItemArmor implements ITextureProvider, IArmorTextureProvider {

	private static final int maxDamageArray[] = { 130, 160, 150, 110 };
	private static final int maxDamageMaterialArray[] = { 30, 60, 120, 250, 500 };
	
	private final PropsItemToolMaterial material;
	
	public ItemArmorNFC(int id, PropsItemToolMaterial material, int armourType) {
		super(id, 0, -1, armourType);
		this.material = material;
		this.setMaxDamage(maxDamageMaterialArray[material.TIER - 1] * maxDamageArray[armourType] / 100);
		this.setIconCoord(material.getTextureIndex() % 16, armourType + 5);
		String name;
		String niceName;
		switch(armourType) {
			case 0:
				name = ".helmet";
				niceName = "Helmet";
				break;
			case 1:
				name = ".chestplate";
				niceName = "Chestplate";
				break;
			case 2:
				name = ".leggings";
				niceName = "Leggings";
				break;
			default:
				name = ".boots";
				niceName = "Boots";
				break;
		}
		this.setItemName(new StringBuilder().append(material.getName()).append(name).toString());
		ModLoader.AddName(this, new StringBuilder().append(material.NAME).append(' ').append(niceName).toString());
	}
	
	@Override
	public String getTextureFile() {
		return material.getTextureFile();
	}
	
	@Override
	public String getArmorTextureFile() {
		return new StringBuilder().append(mod_NFC.resources).append("armor/").append(material.NAME.toLowerCase()).append("_").append(armorType != 2 ? 1 : 2).append(".png").toString();
	}
}
