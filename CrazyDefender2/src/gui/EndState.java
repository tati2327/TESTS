package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import logic.Background;
import logic.ReadProperties;
import logic.ReadScores;
import logic.SaveScores;
import logic.SoundMenu;

/**
 * Clase para que dibuja en el GUI la "ventana" de fin de juego.
 * 
 * @author Fabian A. Solano Madriz
 * @version 3.0
 * 
 *
 */
public class EndState extends GameState{
	public static boolean _up = false;
	public static boolean _down = false;
	public static boolean _enter = false;
	private Background bg;
	private int counter;
	private int savecounter;
	private int currentChoice = 0;
	private String[] texto = {
			"Congratulations!",
	};
	private String[] menu = {
			"Menu",
			"Exit"
	};
	
	@SuppressWarnings("unused")
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private Font font2;
	private int pHealth;
	private int pScore;
	
	
	public EndState(GameStateManager gsm, int inhealth, int inscore){
		super();
		this.gsm = gsm;
		init(inhealth,inscore);
		counter=28800;
		savecounter=0;
		try{
			bg = new Background(ReadProperties.file.getSetting("imgmenu"),1);
			bg.setVector(0.3,0);//Movimiento a la derecha
			
			titleColor = new Color(0,0,255);
			titleFont = new Font("Century Gothic", Font.PLAIN, 20);
			font = new Font("Arial",Font.PLAIN, 15);
			font2 = new Font("Arial",Font.PLAIN, 11);
			
		}
		catch(Exception e){
			e.printStackTrace(); //Para detectar algún posible error
		}
	}
	public void init(int inhealth, int inscore){
		pHealth=inhealth;
		pScore=inscore;
	}
	public void update(){
		bg.update();
		counter--;
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
		g.drawString("GAME OVER",100,20);
		g.setFont(font);
		g.setColor(Color.orange);
		g.drawString("Lives",100,140);
		g.drawString("Points",170,140);
		g.setColor(Color.white);
		g.drawString(pHealth+"",114,170);
		g.drawString(pScore+"",170,170);
		
		for (int i=0; i < texto.length; i++){
			g.drawString(texto[i],100,75+i*25);
		}
		g.setColor(Color.red);
		if(MenuState.getname()==null){
			g.drawString("Player", 135, 95);
			
		}
		else{
			g.drawString(MenuState.getname(),135,95);
		}
		
		for (int i=0; i < menu.length; i++){
			if (i==currentChoice){
				g.setColor(Color.BLUE);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.drawString(menu[i],120+i*50,230);
		}
		
		g.setColor(Color.red);
		//Verificacion de Puntaje
		if(pScore >= Integer.parseInt(ReadScores.file.getSetting("player1s"))){
			if(savecounter ==0){
				SaveScores.getInstance(MenuState.getname(),pScore+"","player1");
				savecounter=1;
			}
			if((counter/60)%2 == 0){
				g.drawString("NEW HIGHSCORE", 95, 200);
				
			}
			if(savecounter==1){
				g.setFont(font2);
				g.setColor(Color.yellow);
				g.drawString("Score Saved", 125, 210);
			}
		}
		else if(pScore >= Integer.parseInt(ReadScores.file.getSetting("player2s"))){
			if(savecounter ==0){
				SaveScores.getInstance(MenuState.getname(),pScore+"","player2");
				savecounter=1;
			}
			if((counter/60)%2 == 0){
				g.drawString("NEW HIGHSCORE", 95, 200);
			}
			if(savecounter==1){
				g.setFont(font2);
				g.setColor(Color.yellow);
				g.drawString("Score Saved", 125, 210);
			}
		}
		else if(pScore >= Integer.parseInt(ReadScores.file.getSetting("player3s"))){
			if(savecounter ==0){
				SaveScores.getInstance(MenuState.getname(),pScore+"","player3");
				savecounter=1;
			}
			if((counter/60)%2 == 0){
				g.drawString("NEW HIGHSCORE", 95, 200);
			}
			if(savecounter==1){
				g.setFont(font2);
				g.setColor(Color.yellow);
				g.drawString("Score Saved", 125, 210);
			}
		}
		else if(pScore >= Integer.parseInt(ReadScores.file.getSetting("player4s"))){
			if(savecounter ==0){
				SaveScores.getInstance(MenuState.getname(),pScore+"","player4");
				savecounter=1;
			}
			if((counter/60)%2 == 0){
				g.drawString("NEW HIGHSCORE", 95, 200);
			}
			if(savecounter==1){
				g.setFont(font2);
				g.setColor(Color.yellow);
				g.drawString("Score Saved", 125, 210);
			}
		}
		
	}
	
	private void select(){
		if(currentChoice==0){
			//Regresar
			
			gsm.setState(GameStateManager.MENUSTATE,5,0);
			
		}
		else if(currentChoice==1){
			//Salir
			SoundMenu.music.start();
			//Player.setLives(5);
			System.exit(0);
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
