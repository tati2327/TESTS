package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import logic.ReadProperties;
import logic.Sound;
import TileMap.TileMap;
import entities.Player;


/**
 * Clase que permite la instanciacion de proyectiles tanto del enemigo como del jugador. Hereda
 * de la super clase MapObject por lo tanto se pueden saber sus posiciones x, y en el juego. 
 * Además por medio de la clase Sound, reproduce sonidos al moverse a través del mapa y al chocar 
 * con algún objeto del juego.
 * 
 * @author Fabian A. Solano Madriz
 *
 */
public class FireBall extends MapObject {
	
	public static int FBCounter=0;
	
	private boolean hit; //Bala pego con algo
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	public FireBall(TileMap tm, boolean right){
		super(tm);
		FBCounter+=1;
		moveSpeed = 3.8;
		if (right) {
			dx = moveSpeed;
			this.facingRight= true;
		}
		else {
			dx = -moveSpeed;
			this.facingRight = false;
		}
		
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 12;
		
		//Cargar Sprites
		try{
			if(Player.getFireType() == 1){
				BufferedImage spritesheet = ImageIO.read(
						getClass().getResourceAsStream(ReadProperties.file.getSetting("bullet"))
						);
				sprites = new BufferedImage[4]; //4 tiles de la imagen
				for(int i = 0; i < sprites.length; i++){
					sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
				}
				
				hitSprites = new BufferedImage[3];
				for(int i = 0; i < hitSprites.length; i++){
					hitSprites[i] = spritesheet.getSubimage(i*width, height, width, height);
														 //(i*width, height, width, height
				}
			}
			else if (Player.getFireType()==2){
				BufferedImage spritesheet = ImageIO.read(
						getClass().getResourceAsStream(ReadProperties.file.getSetting("bullet2"))
						);
				sprites = new BufferedImage[4]; //4 tiles de la imagen
				for(int i = 0; i < sprites.length; i++){
					sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
				}
				
				hitSprites = new BufferedImage[3];
				for(int i = 0; i < hitSprites.length; i++){
					hitSprites[i] = spritesheet.getSubimage(i*width, height, width, height);
														 //(i*width, height, width, height
				}
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//Para verificar si la bala choco con algo,y asi poder eliminar enemigos
	public void setHit(){
		Sound.blast.stop();
		Sound.explo.stop();
		Sound.explo.play();
		if(hit) return; //evita estar formateando los frames
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx=0;
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update(){
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		animation.update();
		
		
		if(dx == 0 && !hit){ //Permite que al colisionar hit = True
			
			setHit();
		}
		if(hit && animation.hasPlayedOnce()){
			remove = true;
		}
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
	public void setRemove(){
		remove = true;
	}
	
	public void setX(double x){
		this.x = x;
	}

}
