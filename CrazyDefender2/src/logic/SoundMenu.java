package logic;


import java.applet.Applet;
import java.applet.AudioClip;
import logic.ReadProperties;

/**
 * Clase que facilita la reproduccion del sonido del menú del juego. La clase solo permite la 
 * instanciación de un objeto de esta forma, se puede detener o continuar con la reproducción desde
 * cualquier parte del juego
 * <p>
 * Clase facilitada por el profesor Nereo Campos.
 * 
 * @author Nereo Campos
 * @version 1.1
 *
 */
public class SoundMenu {
	private AudioClip audio;
	public static SoundMenu music = null;
	
	
	private SoundMenu(){ //Constructor
		audio = Applet.newAudioClip(Sound.class.getResource(ReadProperties.file.getSetting("sndMenu")));

	}
	public static SoundMenu getInstance(){
		if(music == null)
			music = new SoundMenu();
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

