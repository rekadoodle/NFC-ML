package nfc;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;

public class Utils {

	public static final Minecraft mc = ModLoader.getMinecraftInstance();

	// Used for easy reflection with obfuscated or regular fields
	public static final Field getField(Class<?> target, String... names) {
		for (Field field : target.getDeclaredFields()) {
			for (String name : names) {
				if (field.getName() == name) {
					field.setAccessible(true);
					return field;
				}
			}
		}
		return null;
	}
}
