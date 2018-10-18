package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import logic.Background;
import logic.ReadProperties;

/**
 * Clase para que dibuja en el GUI la "ventana" Help.
 * 
 * @author Fabian A. Solano Madriz
 * @version 3.0
 * 
 *
 */
public class HelpState extends GameState {
	public static boolean _up = false;
	public static boolean _down = false;
	public static boolean _enter = false;
	private Background bg;
	private int currentChoice = 0;
	private String[] menu = {
			"Menu",
			"Exit"
	};
	@SuppressWarnings("unused")
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	
	public HelpState(GameStateManager gsm){
		super();
		this.gsm = gsm;
		try{
			bg = new Background(ReadProperties.file.getSetting("imghelp"),1);
			//bg.setVector(0.3,0);//Movimiento a la derecha
			
			titleColor = new Color(0,0,255);
			titleFont = new Font("Century Gothic", Font.PLAIN, 20);
			font = new Font("Arial",Font.PLAIN, 11);
			
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
		//Dibujar Texto a mostrar
		g.setFont(font);
		g.setColor(Color.white);
		for (int i=0; i < menu.length; i++){
			if (i==currentChoice){
				g.setColor(Color.green);
			}
			else{
				g.setColor(Color.red);
			}
			g.drawString(menu[i],245+i*40,235);
		}
		
	}
	
	private void select(){
		if(currentChoice==0){
			//Regresar
			gsm.setState(GameStateManager.MENUSTATE,0,0);
		}
		if(currentChoice==1){
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
				currentChoice = menu.length -1;}
		}
		if (k == KeyEvent.VK_DOWN){
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
