package logic;


import java.applet.Applet;
import java.applet.AudioClip;
import logic.ReadProperties;

/**
 * Clase que facilita la reproduccion del sonido de ambiente del juego. La clase solo permite la 
 * instanciación de un objeto de esta forma, se puede detener o continuar con la reproducción desde
 * cualquier parte del juego
 * <p>
 * Clase facilitada por el profesor Nereo Campos.
 * 
 * @author Nereo Campos
 * @version 1.1
 *
 */
public class SoundGame {
	private AudioClip audio;
	public static SoundGame music = null;
	
	
	private SoundGame(){ //Constructor
		audio = Applet.newAudioClip(Sound.class.getResource(ReadProperties.file.getSetting("sndGame")));

	}
	public static SoundGame getInstance(){
		if(music == null)
			music = new SoundGame();
		return music;
	}
	public void play(){
		audio.play();
	}
	public void start(){
		audio.loop();
	}
	public void stop(){
		audio.stop();
	}
}

