package nfc.item;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;
import nfc.props.PropsItemToolMaterial;

public class ItemHoeNFC extends ItemHoe implements ITextureProvider {

	private final PropsItemToolMaterial material;
	
	public ItemHoeNFC(int id, PropsItemToolMaterial material) {
		super(id, EnumToolMaterial.WOOD);
		this.material = material;
		this.setMaxDamage(material.MAX_USES - 1);
        this.setIconCoord(material.getTextureIndex()% 16, 4);
        this.setItemName(new StringBuilder().append(material.getName()).append(".hoe").toString());
        ModLoader.AddName(this, new StringBuilder().append(material.NAME).append(' ').append("Hoe").toString());
		
		ModLoader.AddRecipe(new ItemStack(this), new Object[] {
				"XX",
	            " |",
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
