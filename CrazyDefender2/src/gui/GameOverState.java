package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import logic.Background;
import logic.ReadProperties;
import logic.SoundGame;
import logic.SoundMenu;

public class GameOverState extends GameState{
	
	public static boolean _up;
	public static boolean _down;
	public static boolean _enter;
	private Background bg;
	private int currentChoice = 0;
	
	private String[] texto = {
			"Do you want to",
			"    try again?"
	};
	private String[] menu = {
			"Yes",
			"No"
	};
	
	@SuppressWarnings("unused")
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public GameOverState(GameStateManager gsm){
		super();
		this.gsm = gsm;
		try{
			bg = new Background(ReadProperties.file.getSetting("imgmenu"),1);
			bg.setVector(0.3,0);//Movimiento a la derecha
			
			titleColor = new Color(0,0,255);
			titleFont = new Font("Century Gothic", Font.PLAIN, 20);
			font = new Font("Arial",Font.PLAIN, 15);
			
		}
		catch(Exception e){
			e.printStackTrace(); //Para detectar algún posible error
		}
	}
	public void init(int inhealth, int inscore){}
	public void update(){
		bg.update();
		if(_up == true){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = menu.length -1;}
			_up=false;
		}
		if(_down == true){
			currentChoice++;
			if (currentChoice == menu.length){
				currentChoice = 0;}
			_down=false;
		}
		if(_enter == true){
			select();
			_enter=false;
		}
	}
	public void draw(java.awt.Graphics2D g){
		//Dibujar Fondo
		bg.draw(g);
		//Dibujar Texto del Titulo de Juego
		g.setColor(Color.green);
		g.setFont(titleFont);
		g.drawString("GAME OVER",90,20);
		//Dibujar Texto a mostrar
		g.setFont(font);
		g.setColor(Color.white);
		for (int i=0; i < texto.length; i++){
			g.drawString(texto[i],100,100+i*25);
		}
		for (int i=0; i < menu.length; i++){
			if (i==currentChoice){
				g.setColor(Color.BLUE);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.drawString(menu[i],120+i*50,180);
		}
		
	}
	
	private void select(){
		if(currentChoice==0){
			//Regresar
			
			gsm.setState(GameStateManager.LEVEL1STATE,5,0);
			SoundGame.music.start();
			
		}
		else if(currentChoice==1){
			//Salir
			SoundMenu.music.start();
			//Player.setLives(5);
			gsm.setState(GameStateManager.MENUSTATE,0,0);
		}
	}

	public void keyPressed(int k){
		if (k == KeyEvent.VK_ENTER){
			select();
		}
		if (k == KeyEvent.VK_RIGHT){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = menu.length -1;}
		}
		if (k == KeyEvent.VK_LEFT){
			currentChoice++;
			if (currentChoice == menu.length){
				currentChoice = 0;}
		}
	}
	public void keyReleased(int k){}
	protected static void setUp(){ _up=true; }
	protected static void setDown(){ _down=true; }
	protected static void setEnter(){ _enter=true; }
}
