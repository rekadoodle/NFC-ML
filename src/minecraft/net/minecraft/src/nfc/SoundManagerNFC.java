package net.minecraft.src.nfc;

import java.io.File;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPool;
import net.minecraft.src.SoundPoolEntry;

public class SoundManagerNFC {

	public final static String WRENCH = "wrench.ogg";
	public final static String CHANT = "chant.ogg";
	public final static String DROOPY = "droopy likes your face.ogg";
	public final static String EMOTION = "i lack an emotion.ogg";
	public final static String BERLIN = "in berlin people act differently.ogg";
	public final static String PLEASE = "please do.ogg";
	public final static String FRONTIER = "snuppeluppen - new frontier.ogg";
	public final static String TWENTYSIX = "26.ogg";
	
	public SoundManager mcSoundManager;
	public SoundPool soundPool;
	public SoundPool streamingPool;
	
	private Utils.EasyField<SoundPool> soundPoolField = new Utils.EasyField<SoundPool>(SoundManager.class, "soundPoolSounds", "b");
	private Utils.EasyField<SoundPool> streamingPoolField = new Utils.EasyField<SoundPool>(SoundManager.class, "soundPoolStreaming", "c");
	
	public SoundManagerNFC() {
		this.init();
	}
	
	public void init() {
		this.mcSoundManager = Utils.mc.sndManager;
		this.soundPool = this.soundPoolField.get(this.mcSoundManager);
		this.streamingPool = this.streamingPoolField.get(this.mcSoundManager);
		this.streamingPool.field_1657_b = false;
		
		this.addSound(WRENCH);
		
		this.addRecord(CHANT);
		this.addRecord(DROOPY);
		this.addRecord(EMOTION);
		this.addRecord(BERLIN);
		this.addRecord(PLEASE);
		this.addRecord(FRONTIER);
		this.addRecord(TWENTYSIX);
	}
	
	public void addAudio(String audioName, SoundPool pool, String resourcePath) 
	{
		if(Utils.resourceExists(resourcePath))
		{
			SoundPoolEntry entry = pool.addSound(audioName, new File(""));
			entry.soundUrl = Utils.getResourceURL(resourcePath);
		}
	}
	
	public void addAudio(String audioName, SoundPool pool) 
	{
		String resourcePath = new StringBuilder().append("sound/").append(audioName).toString();
		this.addAudio(audioName, pool, resourcePath);
	}
	
	public String getAudio(String audioName, SoundPool pool) {
		if(this.mcSoundManager != Utils.mc.sndManager)
		{
			this.init();
		}
		String soundName = new StringBuilder().append(audioName.substring(0, audioName.indexOf(".")).replaceAll("/", ".")).toString();
		if(pool.getRandomSoundFromSoundPool(soundName) == null) 
		{
			return null;
		}
		return soundName;
	}
	
	public void addSound(String audioName) 
	{
		String soundName = new StringBuilder().append("nfc/").append(audioName).toString();
		String resourcePath = new StringBuilder().append("sound/").append(audioName).toString();
		this.addAudio(soundName, soundPool, resourcePath);
	}
	
	public String getSound(String audioName) {
		return this.getAudio(new StringBuilder().append("nfc.").append(audioName).toString(), soundPool);
	}
	
	public void addRecord(String audioName) 
	{
		String resourcePath = new StringBuilder().append("sound/records/").append(audioName).toString();
		this.addAudio(audioName, streamingPool, resourcePath);
	}
	
	public String getRecord(String audioName) {
		return this.getAudio(audioName, streamingPool);
	}
}
