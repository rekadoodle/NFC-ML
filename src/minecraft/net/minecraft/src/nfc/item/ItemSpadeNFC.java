package net.minecraft.src.nfc.item;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;
import net.minecraft.src.nfc.props.PropsItemToolMaterial;

public class ItemSpadeNFC extends ItemSpade implements ITextureProvider {

	private final PropsItemToolMaterial material;
	
	public ItemSpadeNFC(int id, PropsItemToolMaterial material) {
		super(id, EnumToolMaterial.WOOD);
		this.material = material;
		this.setMaxDamage(material.MAX_USES - 1);
        this.efficiencyOnProperMaterial = material.EFFICIENCY;
        this.damageVsEntity = 1 + material.DMG_VS_ENTITY;
        this.setIconCoord(material.getTextureIndex() % 16, 0);
        this.setItemName(new StringBuilder().append(material.getName()).append(".shovel").toString());
        ModLoader.AddName(this, new StringBuilder().append(material.NAME).append(' ').append("Shovel").toString());
		MinecraftForge.setToolClass(this, "shovel", material.TIER);
		
		ModLoader.AddRecipe(new ItemStack(this), new Object[] {
				"X",
	            "|",
	            "|", 
	            Character.valueOf('X'), material.getItemStack(),
	            Character.valueOf('|'), Item.stick
	    });
	}
	
	@Override
	public String getTextureFile() {
		return material.getTextureFile();
	}
}
