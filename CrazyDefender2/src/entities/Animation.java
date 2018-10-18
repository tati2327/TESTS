package entities;

import java.awt.image.BufferedImage;

/**
 * Clase que permite controlar las animaciones necesarias en la ejecución del juego
 * de manera que utiliza la clase BufferedImage y System.nanotime() para refrescar
 * la animación.
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.1
 *
 */
public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	private long startTime;
	private long delay;
	private boolean playedOnce;
	
	public Animation() {
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) { delay = d; }
	public void setFrame(int i) { currentFrame = i; }
	
	public void update(){
		
		if (delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length){
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	//Para obtener las imagenes que se tiene que dibujar
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return playedOnce; }
	

}
