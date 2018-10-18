package entities.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import entities.Animation;
import entities.Enemy;
import logic.ReadProperties;
import TileMap.TileMap;

/**
 * Clase que hereda de la clase Enemigo. Permite la instanciacion de un objeto de enemigo tipo 2.
 * Un "bomber". Posee alta resistencia a los disparos del jugador al tocarlo explota y le resta 2
 * vidas.
 * 
 * @author Fabian A. Solano Madriz
 * @version 3.0
 * 
 *
 */
public class Bomber extends Enemy{
	
	private BufferedImage[] sprites;
	
	public Bomber(TileMap tm){
		super(tm);
		moveSpeed = 0.9;
		maxSpeed = 0.9;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth =  20;
		cheight = 20;
		
		health = maxHealth = 30;
		damage = 2;
		
		//Cargar Sprites del Enemigo
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("enemy2")));
			sprites = new BufferedImage[6];
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
	
	public void setLeft(){
		left= true;
	}
}


