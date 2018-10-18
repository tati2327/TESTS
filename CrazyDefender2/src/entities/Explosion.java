package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import logic.ReadProperties;

/**
 * Clase que permite manejar los eventos de las explosiones dentro del juego
 * 
 * @author Fabian A. Solano Madriz
 *
 */
public class Explosion {
	
	private int x;
	private int y;
	private int xmap; //Coordenadas propias en el mapa
	private int ymap;
	
	private int width;
	private int height;
	
	private Animation animation;
	private BufferedImage[] sprites;
	
	private boolean remove;
	
	public Explosion( int x, int y){
		this.x = x;
		this.y = y;
		
		width = 30;
		height = 30;
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("explo"))
					);
			
			sprites = new BufferedImage[6];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(
						i*10,
						0,
						width,
						height
					);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);
	}
	
	public void update(){
		animation.update();
		if(animation.hasPlayedOnce()){
			remove = true;
		}
	}
	public boolean shouldRemove(){ return remove; }
	
	public void setMapPosition(int x, int y){
		xmap = x;
		ymap = y;
	}
	
	public void draw(Graphics2D g){
		g.drawImage(
				animation.getImage(),
				x + xmap - width / 2,
				y + ymap - height /2,
				null
			);
	}
		
}
