package nfc.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.forge.IUseItemFirst;
import nfc.props.PropsItem;
import nfc.props.PropsItemToolMaterial;

public class ItemMulti extends Item implements ITextureProvider, IUseItemFirst  {
	private final List<PropsItem> propsList = new ArrayList<PropsItem>();
	
	public ItemMulti(int id, PropsItem ...items) {
		super(id++);
		this.setHasSubtypes(true);
		this.setItemName("nfc.multi");
		for(PropsItem itemprop : items) {
			itemprop.item_id = this.shiftedIndex;
			itemprop.item_metadata = this.propsList.size();
			this.propsList.add(itemprop);
			if(itemprop instanceof PropsItemToolMaterial) {
				id = ((PropsItemToolMaterial)itemprop).setupTools(id);
			}
		}
	}
	
	@Override
	public int getIconFromDamage(int metadata)
    {
        return propsList.get(metadata).getTextureIndex();
    }
	
	@Override
    public String getItemNameIS(ItemStack itemstack)
    {
    	return propsList.get(itemstack.getItemDamage()).getName();
    }

	@Override
	public String getTextureFile() {
		return new StringBuilder(mod_NFC.resources).append("items.png").toString();
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
		PropsItem itemprop = propsList.get(itemstack.getItemDamage());
		if(itemprop instanceof PropsItem.Food) {
			((PropsItem.Food)itemprop).onItemRightClick(itemstack, world, entityplayer);
		}
		else if(itemprop instanceof PropsItem.Telescope) {
			((PropsItem.Telescope)itemprop).onItemRightClick(itemstack, world, entityplayer);
		}
        return itemstack;
    }

	@Override
	public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side) {
		PropsItem itemprop = propsList.get(itemstack.getItemDamage());
		if(itemprop instanceof PropsItem.Wrench) {
			return ((PropsItem.Wrench)itemprop).onItemUseFirst(itemstack, player, world, x, y, z, side);
		}
		return false;
	}
}
