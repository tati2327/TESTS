package entities;

import TileMap.TileMap;

/**
 * Clase abstracta Enemy, de la cual heredan todas las clases que correspondan a enemigos
 * de los niveles del juego. Contiene variables necesarias para indicar la vida, el daño 
 * que realiza, el máximo de la vida, y una variable "Took" la cual se utiliza para los objetos
 * los cuales el jugador puede obtener.
 * 
 * @author Fabian A. Solano Madriz
 *
 */
public abstract class Enemy extends MapObject{
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	protected boolean took;
	
	
	public Enemy(TileMap tm){
		super(tm);
	}
	
	public boolean isDead() { return dead; }
	public boolean isTook() { return took; }
	public int getDamage() { return damage; }
	
	public void take(){
		if(took != true){
			took = true;
		}
	}
	public void hit(int damage){
		if(dead || flinching) return;
		health -= damage; //Le resta a la vida del enemigo
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update(){}
}
