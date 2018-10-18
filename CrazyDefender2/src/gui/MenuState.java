package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import logic.Background;
import logic.ReadProperties;
import logic.SoundGame;
import logic.SoundMenu;

/**
 * Clase para que dibuja en el GUI la "ventana" Men� Principal
 * 
 * @author Fabian A. Solano Madriz
 * @version 3.0
 * 
 *
 */
public class MenuState extends GameState{
	
	
	public static boolean _up = false;
	public static boolean _down = false;
	public static boolean _enter = false;
	
	public static String playername;
	//public SerialTestJustWin input;
	private int InHealth;
	//private static final String bg_src = Game.getSetting("imgMenu");
	private Background bg;
	private int currentChoice = 4;
	private String[] options = {
			"Start",
			"Profile",
			"HighScores",
			"Help",
			"About",
			"Quit",
	};
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm, int inhealth, int inscore){
		
		this.gsm = gsm;
		init(inhealth,inscore);
		try{
			playername="Player";
			
			//Instacia el Objeto de la Musica del Menu Principal
			SoundMenu.getInstance();
			SoundMenu.music.start();
	
			
			bg = new Background(ReadProperties.file.getSetting("imgmenu"),1);
			bg.setVector(0.2,0);//Movimiento a la derecha
			
			
			titleColor = new Color(0,0,255);
			titleFont = new Font("Century Gothic", Font.PLAIN, 18);
			font = new Font("Arial",Font.PLAIN, 12);
			
			//HARDWARE, JUST IN WINDOWS, or instal library (RXTX)
			/*if(Integer.parseInt(ReadProperties.file.getSetting("Controller"))==1){
				input = new SerialTestJustWin();
				input.run();
			}*/

		}
		catch(NullPointerException e){
			e.printStackTrace(); //Para detectar algun posible error
		}
	}
	
	public void init(int inhealth, int inscore){
		InHealth = 5;
	}
	public void update(){
		bg.update();
		
		if(_up == true){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options.length -1;}
			_up=false;
		}
		if(_down == true){
			currentChoice++;
			if (currentChoice == options.length){
				currentChoice = 0;}
			_down=false;
		}
		if(_enter == true){
			if(currentChoice == 3){
				return;
			}
			select();
			_enter=false;
		}
	}

	public void draw(Graphics2D g){
		
		//Dibujar Fondo
		bg.draw(g);
		//Dibujar Texto del Titulo de Juego
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Space Invaders: Crazy Defender", 16, 30);
		//Dibujar Opciones de Menu
		g.setFont(font);
		for (int i=0; i < options.length; i++){
			if (i==currentChoice){
				g.setColor(Color.green);
			}
			else{
				g.setColor(Color.red);
			}
			g.drawString(options[i],25,100 + i * 15);
		}
		
	}
	private void select(){
		if (currentChoice == 0){
			//Iniciar Juego
			SoundMenu.music.stop(); //Detiene la Música
			SoundGame.getInstance();
			SoundGame.music.start();
			gsm.setState(GameStateManager.LEVEL1STATE,InHealth,0);
			
		}
		if (currentChoice == 1){
			playername = JOptionPane.showInputDialog("Enter your name");
			
		}
		if (currentChoice == 2){
			gsm.setState(GameStateManager.SCORESSTATE, 5, 0);
		}
		if (currentChoice == 3){
			//Ayuda
			gsm.setState(GameStateManager.HELPSTATE, 0, 0);
		}
		if (currentChoice ==4){
			//About
			gsm.setState(GameStateManager.ABOUTSTATE,0,0);
		}
		if(currentChoice ==5){
			//Salir
			System.exit(0);
		}
	}
	public void keyPressed(int k){
		if (k == KeyEvent.VK_ENTER){
			select();
		}
		if (k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options.length -1;}
		}
		if (k == KeyEvent.VK_DOWN){
			currentChoice++;
			if (currentChoice == options.length){
				currentChoice = 0;}
		}
		
	}
	public void keyReleased(int k){}
	
	public static String getname(){
		return playername;
	}
	
	protected static void setUp(){ _up=true; }
	protected static void setDown(){ _down=true; }
	protected static void setEnter(){ _enter=true; }
}
