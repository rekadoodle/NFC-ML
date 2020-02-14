package net.minecraft.src.nfc.references.hmi;

import net.minecraft.src.BaseMod;
import net.minecraft.src.mod_HowManyItems;
import net.minecraft.src.nfc.references.HandlerHMI;

public class ConcreteHandlerHMI extends HandlerHMI {
	
	@Override
	public void init(BaseMod basemod) {
		mod_HowManyItems.addModTab(new TabSmeltingMetadata(basemod));
		mod_HowManyItems.addModTab(new TabBrickOven(basemod));
	}
}
