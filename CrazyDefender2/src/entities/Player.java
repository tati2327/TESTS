package entities;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import TileMap.TileMap;
import entities.Enemies.Boss1;
import entities.Enemies.Fire;
import entities.Enemies.FireballGetter;
import entities.Enemies.Heart;
import entities.Enemies.Relic;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import logic.ReadProperties;
import logic.Sound;

public class Player extends MapObject {
	
	//Caracteristicas de Jugador
	
	private static int health; //SI HAY ERROR CAMBIAR sin STATIC
	@SuppressWarnings("unused")
	private int lives;
	private int reliquias;
	private int score;
	private int exitCounter;
	private int maxHealth;
	private int fire;
	private int maxFire;
	public static int bosskill;
	@SuppressWarnings("unused")
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	private int time;
	
	public static boolean fcright;
	public static double yy;
	
	//Caracter�sticas del Disaro
	private boolean firing;
	private static int fireType;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	//Caracter�sticas Ataque Secundario
	private boolean scratching;
	private int scratchDamage; 
	private int scratchRange;
	
	//Planeo
	private boolean gliding;
	
	//Diferentes Animaciones del Jugador
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 2, 8, 5, 1, 4, 2, 5}; //Cantidad de imágenes requeridas 


															//en cada animación.
	//Acciones de las Animaciones
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;

	//Constructor
	public Player(TileMap tm, int inhealth, int inscore){
		super(tm); //Llama a la clase padre MapObject
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.11;
		maxFallSpeed = 4.0;
		if(Integer.parseInt(ReadProperties.file.getSetting("Controller"))==1){
			jumpStart = -5.0;
			stopJumpSpeed = 0.3;
		}
		else{
			jumpStart = -4.8;
			stopJumpSpeed = 0.3;
		}
		
		
		facingRight = true; //El jugador inicia viendo hacia la derecha,
		
		health = maxHealth = inhealth;
		fire = maxFire = 2500;
		reliquias = 0;
		score = inscore;
		exitCounter = 0;
		bosskill = 0;
		
		fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>();
		fireType = 1;
		
		scratchDamage = 8;
		scratchRange = 40;
		
		time = Integer.parseInt(ReadProperties.file.getSetting("time")); //Por la conversion de ms a s
		
		//CARGA DE LAS IMAGENES
		try{
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("player")));
			
			
			sprites = new ArrayList<BufferedImage[]>();
			
			// 7, por las 7 animaciones
			for(int i = 0; i < 7; i++){
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++){
					
					if (i != 6){ //Para la ultima animacion por ser != 30*30
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
								);
					}
					else{
						bi[j] = spritesheet.getSubimage(
								j * width * 2, // Tamaño de la img 60*30
								i * height,
								width * 2,
								height
								);
					}		
				}
				sprites.add(bi);
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	

	public void setFiring(){
		firing = true;
	}
	
	public void setScratching(){
		scratching = true;
	}
	public void setGliding(boolean b){ //Boolean porque puede dejar de hacerse cuando sea
		gliding = b;				  //Se invoca desde Level1State KeyPressed-Released
	}
	
	public void checkFire(ArrayList<Fire> fires){
		//LOOP sobre los fuegos
		for(int i = 0; i<fires.size(); i++){
			Fire f = fires.get(i);
			if(intersects(f)){
				health-=1;
				this.setFireType(1);		//PIERDE EL ATAQUE ESPECIAL
				this.setFireBallDamage(5);	//AL IMPACTAR CON ENEMIGO
				if (facingRight == true){
					x= this.getx()-25;
				}
				else{
					x= this.getx() + 25;
				}
			}
			//Bullet
			for(int j = 0; j < fireBalls.size();j++){
				if(fireBalls.get(j).intersects(f)){
					f.hit(fireBallDamage); //Golpea al enemy con el valor de Daño
					fireBalls.get(j).setHit();
					f.stinguish();
				}
			}
			
		}
	}
	
	public void checkHearts(ArrayList<Heart> hearts){
		//LOOP sobre los corazones
		for(int i = 0; i<hearts.size(); i++){
			Heart h = hearts.get(i);
			if(intersects(h)){
				h.takeHeart();
			}
		}
	}
	public void checkGetters(ArrayList<FireballGetter> getters){
		//LOOP sobre los corazones
		for(int i = 0; i<getters.size(); i++){
			FireballGetter fbg = getters.get(i);
			if(intersects(fbg)){
				fbg.takeWeapon();
				
			}
		}
	}
	
	public void checkCapture(ArrayList<Relic> relics){
		//LOOP sobre las reliquias
		for(int i = 0; i<relics.size(); i++){
			Relic r = relics.get(i);
			if(intersects(r)){
				r.take();
				reliquias+=1;
			}
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// VERIFICA ATAQUE HACIA ENEMIGOS & CONTACTOS ///////////////
	/////////////////////////////////////////////////////////////////////////////////////
	public void checkAttack(ArrayList<Enemy> enemies, int mode){
		///////////////////MODO PARA ENEMIGOS 1 ///////////////////////////////
		if(mode == 1){
			//LOOP en los enemigos
			for(int i = 0; i < enemies.size(); i++){
				Enemy e = enemies.get(i);
				//Scratch
				if(scratching){
					if(facingRight){
						if(
							e.getx() > x &&
							e.getx() < x + scratchRange &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2
						){
							e.hit(scratchDamage); //Golpea al enemy con el valor de Daño
						}
					}
					else{
						if(
							e.getx() < x &&
							e.getx() > x - scratchRange &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2
						){
							e.hit(scratchDamage);
						}
					}
				}
				//Bullet
				for(int j = 0; j < fireBalls.size();j++){
					if(fireBalls.get(j).intersects(e)){
						e.hit(fireBallDamage); //Golpea al enemy con el valor de Daño
						fireBalls.get(j).setHit();
						break;
					}
				}
				//Verificar Colision con el enemigo
				if(intersects(e)){
					hit(e.getDamage());
					
				}
			}
		}
		else if(mode ==2){
			//LOOP en los enemigos
			for(int i = 0; i < enemies.size(); i++){
				Enemy e = enemies.get(i);
				//Scratch
				if(scratching){
					if(facingRight){
						if(
							e.getx() > x &&
							e.getx() < x + scratchRange &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2
						){
							e.hit(scratchDamage); //Golpea al enemy con el valor de Daño
						}
					}
					else{
						if(
							e.getx() < x &&
							e.getx() > x - scratchRange &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2
						){
							e.hit(scratchDamage);
						}
					}
				}
				//Bullet
				for(int j = 0; j < fireBalls.size();j++){
					if(fireBalls.get(j).intersects(e)){
						e.hit(fireBallDamage); //Golpea al enemy con el valor de Daño
						fireBalls.get(j).setHit();
						break;
					}
				}
				//Verificar Colision con el enemigo
				if(intersects(e)){
					e.dead = true;
					hit(e.getDamage());
				}
			}
		}
	}
//////////////////////////////////////////////////////////////////////////////////////
////////////////////////// VERIFICA ATAQUE HACIA BOSS & CONTACTOS ///////////////
/////////////////////////////////////////////////////////////////////////////////////
	public void checkAttackBoss(ArrayList<Boss1> enemies, int mode){
		if(mode == 1){
			//LOOP en los enemigos
			for(int i = 0; i < enemies.size(); i++){
				Boss1 e = enemies.get(i);
				//Scratch
				if(scratching){
					if(facingRight){
						if(
							e.getx() > x &&
							e.getx() < x + scratchRange &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2
						){
							e.hit(scratchDamage); //Golpea al enemy con el valor de Daño
						}
					}
					else{
						if(
							e.getx() < x &&
							e.getx() > x - scratchRange &&
							e.gety() > y - height / 2 &&
							e.gety() < y + height / 2
						){
							e.hit(scratchDamage);
						}
					}
				}
				//Bullet
				for(int j = 0; j < fireBalls.size();j++){
					if(fireBalls.get(j).intersects(e)){
						if(e.facingRight == false){
							if(fireBalls.get(j).getx() <= e.getx()){
								e.hit(fireBallDamage); //Golpea al enemy con el valor de Daño
								e.kill(fireBallDamage);
								fireBalls.get(j).setHit();
							}
						}
					}
				}
				//Verificar Colision con el enemigo
				if(intersects(e)){
					e.dead = false;
					hit(e.getDamage());
					x-=30;
					y-=30;
				}
			}
		}

	}
	public void hit(int damage){
		if(flinching) return;
		health -= damage;
		this.setFireType(1);		//PIERDE EL ATAQUE ESPECIAL
		this.setFireBallDamage(5);	//AL IMPACTAR CON ENEMIGO
		lives -= 1;
		if (health < 0) health = 0;
		if (health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
		
	}
	
	private void getNextPosition(){
		//Movimiento del Jugador
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
		else{
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		//No se puede atacar al estarse movimiento
		if((currentAction == SCRATCHING || currentAction == FIREBALL) &&
		!(jumping || falling)){
			dx = 0;
		}
		
		if(jumping && !falling){
			dy = jumpStart;
			falling = true;
		}
		
		//Caida
		if(falling){
			if(dy > 0 && gliding) dy += fallSpeed * 0.009; //VELOCIDAD DE HABILIDAD PLANEO
			else dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////        UPDATE       /////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	
	public void update(){
		//CONTADOR
		time--;
		yy = y;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//Verificar si se detiene el ataque
		if(currentAction == SCRATCHING){
			if (animation.hasPlayedOnce()) scratching = false;
		}
		if(currentAction == FIREBALL){
			if(animation.hasPlayedOnce()) firing = false;
		}
		
		//Ataque básico.
		fire +=1;
		if (fire > maxFire) fire = maxFire;
		if (firing && currentAction != FIREBALL){
			if (fire > fireCost){
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				fb.setPosition(x, y);
				fireBalls.add(fb); //añadir al arraylist
			}
		}
		//Update los disparos
		//si no se emple
		for (int i = 0; i < fireBalls.size();i++){
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()){ //Para eliminar los disparos
				fireBalls.remove(i);
				i--;
			}
		}
		//Checkear el Parpadeo despues de atacar
		//////////////////////////////////////////////////////////////////////////////////
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1500){
				flinching = false;
			}
		}
		
		//Animaciones
		if(scratching){
			if (currentAction != SCRATCHING){
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 60;
			}
		}
		else if(firing) {
			if(currentAction != FIREBALL){
				Sound.blast.play();
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy > 0){
			if (gliding){
				if(currentAction != GLIDING){
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
				}
			}
			
			else if(currentAction != FALLING){
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 30;
				}
		}
		else if(dy < 0){
			if(currentAction != JUMPING){
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
				}
			}
		else if (left || right){
			if (currentAction != WALKING){
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 30;
			}
		}
		else{
			if (currentAction != IDLE){
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
		}
		animation.update();
		//Direccion que el jugador esta viend
		if (currentAction != SCRATCHING && currentAction != FIREBALL){
			if(right) {
				facingRight = true;
				fcright= true;
			}
			if(left){
				facingRight = false;
				fcright = false;
			}
		}
	}
	
	public void draw(Graphics2D g){
		setMapPosition(); 
		//Dibujar Bullets
		for(int i=0; i < fireBalls.size(); i++){
			fireBalls.get(i).draw(g);
		}
		//Dibujar al Jugador
		if(flinching){
			long elapsed = (System.nanoTime()- flinchTimer)/ 1000000;
			
			if (elapsed / 100 % 2 == 0){
				return;
			}
		}
		super.draw(g);
	}
	
	
	//GETTERS
	public int getHealth() {return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getFire() { return fire; }
	public int getMaxFire() {return maxFire; }
	public int getRelics() { return reliquias; }
	public int getScore() { return score; }
	public static int getFireType() { return fireType; }
	public int getexitCounter() { return exitCounter; }
	public long getTime() { return time; }
	public void setTime(int t) { time += t; }
	public int getbossKill() { return bosskill; }
	public boolean getJumping(){return jumping;}
	public boolean getGliding(){return gliding;}
	public static boolean getFacing(){return fcright;}
	public static double getYY(){ return yy;}
	
	//SETTERS
	public void sety(int location){ y = location;}
	public void setx(int location){ x = location;}
	public void setRelic(int amount){ reliquias+=amount;}
	public void setScore(int amount){ score+=amount;}
	public void setFireType(int type){ fireType = type;}
	public void setFireBallDamage(int type){ fireBallDamage = type;}
	public void setExitCounter(int times){ exitCounter += times;}
	public void setHealth(int i) { health = i; }
	public static void setLives(int i) { health += i; }
	public void setDead() { health = 0; stop();}
	public void setBossKill() { bosskill+=1;}
	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();}
	public void stop() {left = right = up = down = flinching = jumping=false;}
	public static void resetBossCount(){bosskill =0;}
}
