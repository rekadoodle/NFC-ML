package net.minecraft.src.nfc.item;

import net.minecraft.src.ItemRecord;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.nfc.NFC;
import net.minecraft.src.nfc.Utils;

public class ItemRecordNFC extends ItemRecord implements ITextureProvider {

	public ItemRecordNFC(int id, String s, int textureIndex) {
		super(id, NFC.instance.soundManager.getRecord(s));
		this.setItemName("record");
		this.setIconIndex(textureIndex);
	}

	@Override
	public String getTextureFile() {
		return Utils.getResource("items.png");
	}

}
