package entities.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import logic.ReadProperties;
import TileMap.TileMap;
import entities.Animation;
import entities.Enemy;
import entities.FireBall;
import entities.Player;

/**
 * Clase que hereda de la clase Enemigo. Permite la instanciacion de un objeto de enemigo Boss.
 * El jefe final de cada nivel. Puede replicar el disparo del jugador y de esta forma atacarlo.
 * Adem�s posee un "escudo" de 15 vidas. Es necesario destruir el escudo antes de poder matarlo.
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.1
 * 
 *
 */
public class Boss1 extends Enemy {
	private BufferedImage[] sprites;
	double startx;
	double starty;
	static int health2;
	private int fire;
	private int maxFire;
	private int fireCost;
	private static int fireBallDamage;
	private static ArrayList<FireBall> fireBalls;
	private Player player;
	
	public Boss1(TileMap tm,Player p){
		
		super(tm);
		player =p;
		moveSpeed = 0.9;
		maxSpeed = 0.9;
		fallSpeed = 0.2;
		maxFallSpeed = 5.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		dead = false;

		health = maxHealth = 15; //Facil de Matar
		health2= health;
		damage= 3;
		fire=maxFire=4;
		fireCost=3;
		fireBallDamage=1;
		fireBalls = new ArrayList<FireBall>();
		
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("boss1")));
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
		animation.setDelay(100);
		
		right = true;
		facingRight = false; //PAra que el enemigo empieze viendo hacia la derecha.
	}
	public static void checkAttack(ArrayList<Player> enemies){
		///////////////////MODO PARA ENEMIGOS 1 ///////////////////////////////
		//LOOP en los enemigos
		for(int i = 0; i < enemies.size(); i++){
			Player e = enemies.get(i);
			//Bullet
			for(int j = 0; j < fireBalls.size();j++){
				if(fireBalls.get(j).intersects(e)){
					e.hit(fireBallDamage); //Golpea al enemy con el valor de Daño
					fireBalls.get(j).setHit();
					break;
				}
			}
		}
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
		
		fire +=1;
		if (fire > maxFire) fire = maxFire;
		if(((player.getTime()/60)%3)==0){
			if (this.right== true){
				if (fire > fireCost){
					fire -= 21;
					FireBall fb = new FireBall(tileMap, facingRight);
					fb.setPosition(x, y);
					fireBalls.add(fb); //añadir al arraylist
				}
			}
		}
		for (int i = 0; i < fireBalls.size();i++){
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()){ //Para eliminar los disparos
				fireBalls.remove(i);
				i--;
			}
		}
		
		//Verificar Flinching
		if (flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 200){
				flinching = false;
			}
		}
		
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
		for(int i=0; i < fireBalls.size(); i++){
			fireBalls.get(i).draw(g);
		}
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
	
	public static long getlivs(){ return health2; }
	public void kill(int amount){
		health2-=amount;
	}
}


