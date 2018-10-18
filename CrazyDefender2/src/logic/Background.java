package logic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import gui.GamePanel;
import javax.imageio.ImageIO;
/**
 * Clase para que permite la instanciacion de alguna imagen para utilizarla en el fondo de alguna
 * "ventana".
 * 
 * @author Fabian A. Solano Madriz
 * @version 3.0
 * 
 *
 */
public class Background {
	
	private BufferedImage image;
	
	//private double x;
	//private double y;
	public double x;
	public double y;
	private double dx;
	private double dy;
	
	@SuppressWarnings("unused")
	private double moveScale; // Escala donde se mueve el mapa.
	
	public Background(String s, double ms){
		try{
			image = ImageIO.read(
					getClass().getResourceAsStream(s)
					);
			moveScale = ms;
			
					
		}
		catch(Exception e){
			
		}
	}
	
	public void setPosition(double x, double y){
		this.x = x; //(x*moveScale) % GamePanel.WIDTH;
		this.y = y; //(y*moveScale) % GamePanel.HEIGHT;
		
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx; //Mover automaticamente
		this.dy = dy;
	}
	
	public void update(){
		x += dx;
		y += dy;
		
	}
	
	public void draw(Graphics2D g){

		g.drawImage(image, (int)x, (int)y, null);
		if (x < 0){ //Para que cuando la imagen se acaba, dibuje una nueva. (Efecto Movimiento)
			
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
			x+=800; //Cuando la imagen llega a ser menor que 0, se devuelve al inicio.

		}
		
		if (x > 0){
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);

		}
		
		
	}
}
