package entities.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import logic.ReadProperties;
import TileMap.TileMap;
import entities.Animation;
import entities.Enemy;

/**
 * Clase que hereda de la clase Enemigo. Permite la instanciacion de un objeto de enemigo tipo 4.
 * Un "Copter". Este se mueve de izquierda a derecha en un distancia de doscientos pixeles desde
 * su posicion inicial. Posee poca vida por lo que es sencilla de matar. Al colisionar con el 
 * jugador explota y le resta 2 vidas.
 * 
 * @author Fabian A. Solano Madriz
 * @version 3.0
 * 
 *
 */
public class Copter extends Enemy{
	private BufferedImage[] sprites;
	double startx; //Posiciones iniciales
	double starty;
	
	public Copter(TileMap tm){
		super(tm);
		
		moveSpeed = 0.9;
		maxSpeed = 0.9;
		fallSpeed = 0.2;
		maxFallSpeed = 5.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		dead = false;
		
		health = maxHealth = 4; //Facil de Matar
		damage= 2;
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("enemy4")));
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
		animation.setDelay(100);
		
		facingRight = false; //PAra que el enemigo empieze viendo hacia la derecha.
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
		
	}
	
	public void update(){
		//Actualizar Posicion
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		if (right && (x >= this.getStartX()+200)){
			right = false;
			left = true;
			facingRight = true;
		}
		
		if (left && (x <= this.getStartX())){
			right = true;
			left = false;
			facingRight = false;
		}
				
		//Actualizar Animacion
		animation.update();
	}
	public void draw(Graphics2D g){
		//if (notOnScreen()) return;
		setMapPosition();
		super.draw(g); //Se llama al metodo de la clase padre para dibujo del Enemigo
	}
	
	public void setStartX(double inputx){
		startx= inputx;
	}
	public void setStartY(double inputy){
		starty= inputy;
		
	}
	public double getStartX(){return startx;}
	public double getStartY(){return starty;}
	public void setStRIGHT() { right = true;}
	public void setStLEFT() { left = true;}
		

}
