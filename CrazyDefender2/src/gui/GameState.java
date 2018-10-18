package gui;

/**
 * Super Clase de la cual heredan todos "estados del juego". De esta forma cada estado posee sus 
 * métodos init(), update(), draw(), keyPressed() y keyReleased().
 * <p>
 * Esta clase esta basada en la vista en el video tutorial de ForeignGuyMike
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.0.1
 * @see <a href="https://www.youtube.com/watch?v=9dzhgsVaiSo">ForeignGuyMike Youtube Channel</a>
 *
 */
public abstract class GameState { //Todas las "ventanas" del juego heredan de esta clase
	public static boolean pause = false;
	protected GameStateManager gsm;
	
	public abstract void init(int inhealth, int inscore);
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);

}
