package entities.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entities.Animation;
import entities.Enemy;
import logic.ReadProperties;
import TileMap.TileMap;

/**
 * Clase que hereda de la clase Enemigo. Permite la instanciacion de una reliquia las cuales son
 * requeridas para pasar de nivel (3 Por Nivel).
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.1
 * 
 *
 */
public class Relic extends Enemy {
	private BufferedImage[] sprites;
	
	public Relic (TileMap tm){
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		took = false;
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("relic")));
			sprites = new BufferedImage[3];
			for (int i = 0; i < sprites.length;i++)
			{
				sprites[i] = spritesheet.getSubimage(i*width,0,width,height);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
		
	}
	public void update(){
		//Actualizar Posicion
		
		//getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		//Actualizar Animacion
		animation.update();
	}
	public void draw(Graphics2D g){
		//if (notOnScreen()) return;
		setMapPosition();
		super.draw(g); //Se llama al metodo de la clase padre para dibujo del Enemigo
	}

}
