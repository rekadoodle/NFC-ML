package net.minecraft.src.nfc;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import net.minecraft.src.ModLoader;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPool;
import net.minecraft.src.SoundPoolEntry;

public class SoundManagerNFC {

	public final static String wrench = "wrench.ogg";
	public SoundManager mcSoundManager;
	public SoundPool soundPool;
	
	public SoundManagerNFC() {
		this.init();
	}
	
	public void init() {
		this.mcSoundManager = Utils.mc.sndManager;
		try {
			this.soundPool = (SoundPool) soundPoolField.get(Utils.mc.sndManager);
		} 
		catch (Exception e) { e.printStackTrace(); }
	}
	
	private Field soundPoolField = Utils.getField(SoundManager.class, "soundPoolSounds", "b");
	
	public void addSound(String s) 
	{
		String resource = new StringBuilder().append("sound/").append(s).toString();
		if(Utils.resourceExists(resource))
		{
			SoundPoolEntry entry = soundPool.addSound(new StringBuilder().append("nfc/").append(s).toString(), new File(""));
			entry.soundUrl = Utils.getResourceURL(resource);
		}
	}
	
	public String getSound(String s) {
		if(this.mcSoundManager != Utils.mc.sndManager) 
		{
			this.init();
			this.addSound(s);
		}
		String soundName = new StringBuilder().append("nfc.").append(s.substring(0, s.indexOf(".")).replaceAll("/", ".")).toString();
		if(soundPool.getRandomSoundFromSoundPool(soundName) == null) 
		{
			return null;
		}
		return soundName;
	}
}
