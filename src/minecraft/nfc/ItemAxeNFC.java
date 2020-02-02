package nfc;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ItemAxeNFC extends ItemAxe implements ITextureProvider {

private final PropsItemToolMaterial material;
	
	protected ItemAxeNFC(int id, PropsItemToolMaterial material) {
		super(id, EnumToolMaterial.WOOD);
		this.material = material;
		this.setMaxDamage(material.MAX_USES - 1);
        this.efficiencyOnProperMaterial = material.EFFICIENCY;
        this.damageVsEntity = 3 + material.DMG_VS_ENTITY;
        this.setIconCoord(material.item_metadata % 16, 2);
        this.setItemName(new StringBuilder().append(material.getName()).append(".axe").toString());
        ModLoader.AddName(this, new StringBuilder().append(material.NAME).append(' ').append("Axe").toString());
		MinecraftForge.setToolClass(this, "axe", material.TIER);
		
		ModLoader.AddRecipe(new ItemStack(this), new Object[] {
				"XX",
	            "X|",
	            " |", 
	            Character.valueOf('X'), material.getItemStack(),
	            Character.valueOf('|'), Item.stick
	    });
	}
	
	@Override
	public String getTextureFile() {
		return material.getTextureFile();
	}

}
