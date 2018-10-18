package gui;

import java.util.ArrayList;
import logic.Level1State;
import logic.Level2State;
import logic.Level3State;
/**
 * Clase que maneja las distintas "ventanas" o "estado del juego. Y dibuja los elementos de cada
 * estado en pantalla.
 * <p>
 * Esta clase esta basada en la vista en el video tutorial de ForeignGuyMike
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.2
 * @see <a href="https://www.youtube.com/watch?v=9dzhgsVaiSo">ForeignGuyMike Youtube Channel</a>
 *
 */
public class GameStateManager {
	private ArrayList<GameState> gameStates;
	protected static int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int ABOUTSTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int LEVEL2STATE = 4;
	public static final int LEVEL3STATE = 5;
	public static final int ENDSTATE = 6;
	public static final int SCORESSTATE = 7;
	public static final int HELPSTATE = 8;
	
	public GameStateManager(int inhealth, int inscore){
		
		gameStates = new ArrayList<GameState>();
		
		currentState =0;
		gameStates.add(new MenuState(this,inhealth,inscore));
		gameStates.add(new Level1State(this, 5, 0));
		gameStates.add(new AboutState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new Level2State(this, 5, 0));
		gameStates.add(new Level3State(this,5,0));
		gameStates.add(new EndState(this,0,0));
		gameStates.add(new Scores(this));
		gameStates.add(new HelpState(this));
		
	}
	
	public void setState(int state, int inhealth, int inscore){
		currentState = state;
		gameStates.get(currentState).init(inhealth, inscore);
	}
	
	public void update(){
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g){
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k){
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k){
		gameStates.get(currentState).keyReleased(k);
	}
	
	protected static int getState(){
		return currentState;
	}
}

