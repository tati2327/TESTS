package entities.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import TileMap.TileMap;
import entities.Animation;
import entities.Enemy;
import entities.Player;

/**
 * Clase que hereda de la clase Enemigo. Permite la instanciacion de un objeto de enemigo tipo 3.
 * Un "Fopter". Este es similar a los Copter, con la diferencia de que este "sigue al jugador", 
 * hasta chocar con él para matarlo. Posee poca vida por lo que es sencilla de matar. 
 * Al colisionar con el jugador explota y le resta 2 vidas.
 * 
 * @author Fabian A. Solano Madriz
 * @version 3.0
 * 
 *
 */
public class Fopter extends Enemy{
	private BufferedImage[] sprites;
	double startx; //Posiciones iniciales
	double starty;
	
	public Fopter(TileMap tm){
		super(tm);
		
		moveSpeed = 1;
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
					getClass().getResourceAsStream("/Sprites/Enemies/enemy3.gif"));
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
		
		facingRight = true; //PAra que el enemigo empieze viendo hacia la derecha.
		left=true;
	}
	
	private void getNextPosition(){
		if(left == true){
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		}
		else if (right ==true){
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
		
		this.y = Player.getYY();
		/*if(Player.getFacing() == true){
			facingRight=false;
			right=true;
			left=false;
		}
		if(Player.getFacing()==false){
			facingRight=true;
			left=true;
			right=false;
		}*/
				
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
