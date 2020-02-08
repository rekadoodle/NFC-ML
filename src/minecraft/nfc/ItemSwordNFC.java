package nfc;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ItemSwordNFC extends ItemSword implements ITextureProvider {

	private final PropsItemToolMaterial material;
	private final int weaponDamage;
	
	protected ItemSwordNFC(int id, PropsItemToolMaterial material) {
		super(id, EnumToolMaterial.WOOD);
		this.material = material;
		this.setMaxDamage((material.TIER + 2)*(material.TIER + 2)*10);
        this.weaponDamage = 4 + material.DMG_VS_ENTITY * 2;
        this.setIconCoord(material.getTextureIndex() % 16, 3);
        this.setItemName(new StringBuilder().append(material.getName()).append(".sword").toString());
        ModLoader.AddName(this, new StringBuilder().append(material.NAME).append(' ').append("Sword").toString());
		
		ModLoader.AddRecipe(new ItemStack(this), new Object[] {
				"X",
	            "X",
	            "|", 
	            Character.valueOf('X'), material.getItemStack(),
	            Character.valueOf('|'), Item.stick
	    });
	}
	
	@Override
	public String getTextureFile() {
		return material.getTextureFile();
	}
	
	@Override
	public int getDamageVsEntity(Entity entity)
    {
        return weaponDamage;
    }

}
