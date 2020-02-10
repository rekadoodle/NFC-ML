package net.minecraft.src.nfc.item;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;
import net.minecraft.src.nfc.props.PropsItemToolMaterial;

public class ItemPickaxeNFC extends ItemPickaxe implements ITextureProvider {

	private final PropsItemToolMaterial material;
	
	public ItemPickaxeNFC(int id, PropsItemToolMaterial material) {
		super(id, EnumToolMaterial.WOOD);
		this.material = material;
		this.setMaxDamage(material.MAX_USES - 1);
        this.efficiencyOnProperMaterial = material.EFFICIENCY;
        this.damageVsEntity = 2 + material.DMG_VS_ENTITY;
        this.setIconCoord(material.getTextureIndex() % 16, 1);
        this.setItemName(new StringBuilder().append(material.getName()).append(".pickaxe").toString());
        ModLoader.AddName(this, new StringBuilder().append(material.NAME).append(' ').append("Pickaxe").toString());
		MinecraftForge.setToolClass(this, "pickaxe", material.TIER);
		
		ModLoader.AddRecipe(new ItemStack(this), new Object[] {
				"XXX",
	            " | ",
	            " | ", 
	            Character.valueOf('X'), material.getItemStack(),
	            Character.valueOf('|'), Item.stick
	    });
	}
	
	@Override
	public String getTextureFile() {
		return material.getTextureFile();
	}
}
