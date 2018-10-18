package entities.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entities.Animation;
import entities.Enemy;
import logic.ReadProperties;
import TileMap.TileMap;

/**
 * Clase que hereda de la clase Enemigo. Permite la instanciacion de un "Fuego", el cual es un enemigo
 * estatico que le reduce las vidas al jugador en 1. Este puede ser destruido pero tiene una gran
 * resistencia.
 * 
 * @author Fabian A. Solano Madriz
 * @version 2.0
 * 
 *
 */
public class Fire extends Enemy{
	private BufferedImage[] sprites;
	
	public Fire(TileMap tm){
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		dead = false;
		
		health = maxHealth = 150; //Dificil de Matar
		damage= 1;
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("fire")));
			sprites = new BufferedImage[3];
			for (int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true; //PAra que el enemigo empieze viendo hacia la derecha.
	}
	
	public void update(){
		//Actualizar Posicion
		
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
	
	public void stinguish(){
		if(this.health <= 0){
			dead = true;
		}
		else{
			this.health-=1;
		}
		
	}

}
