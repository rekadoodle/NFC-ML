package net.minecraft.src.nfc.references.betatweaks;

import betatweaks.EntityRendererProxyFOV;
import net.minecraft.src.nfc.Utils;
import net.minecraft.src.nfc.references.HandlerBetaTweaks;

public class ConcreteHandlerBetaTweaks extends HandlerBetaTweaks {

	private Utils.EasyField<Double> cameraZoomField;

	public boolean entityRendererOverrideEnabled(boolean bool) {
		if(Utils.mc.entityRenderer instanceof EntityRendererProxyFOV) {
			if(cameraZoomField == null) {
				cameraZoomField = new Utils.EasyField<Double>(EntityRendererProxyFOV.class, "cameraZoom");
			}
			cameraZoomField.set(bool ? 8.0D : 1.0D);
			return true;
		}
		return false;
	}
}
