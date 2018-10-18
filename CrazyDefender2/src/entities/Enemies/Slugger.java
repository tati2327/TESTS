package entities.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entities.Animation;
import entities.Enemy;
import logic.ReadProperties;
import TileMap.TileMap;

/**
 * Clase que hereda de la clase Enemigo. Permite la instanciacion de un objeto de enemigo tipo 1.
 * Este se mueve de izquierda a derecha hasta chocar con algun obstáculo y se devuelve en 
 * direccion contrario. Si colisiona con el jugador le resta una vida. Es sencillo de matar.
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.5
 * 
 *
 */
public class Slugger extends Enemy{
	
	private BufferedImage[] sprites;
	
	
	public Slugger(TileMap tm){
		super(tm);
		
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth =  20;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		//Cargar Sprites del Enemigo
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("enemy1")));
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
	
	private void getNextPosition(){
		if(left){
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		}
		else if (right){
			dx += moveSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}
		}
		
		//Si se llega a caer
		if (falling){
			dy += fallSpeed;
		}
	}
	
	public void update(){
		//Actualizar Posicion
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		//Verificar Flinching
		if (flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		
		//Si el enemigo choca con una pared, camina en el lado opuesto
		if (right && dx == 0){
			right = false;
			left = true;
			facingRight = false;
		}
		else if (left && dx == 0){
			right = true;
			left = false;
			facingRight = true;
		}
		
		//Actualizar Animacion
		animation.update();
	}
	public void draw(Graphics2D g){
		//if (notOnScreen()) return;
		setMapPosition();
		super.draw(g); //Se llama al metodo de la clase padre para dibujo del Enemigo
	}
}
