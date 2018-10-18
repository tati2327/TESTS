package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import logic.ReadProperties;
import entities.Player;
import entities.Enemies.Boss1;

/**
 * Clase para que dibuja en el GUI la el HUD 2, el cual muestra el tiempo restante, la cantidad de
 * reliquias recolectadas y los puntos del jugador.
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.7
 * 
 *
 */
public class HUD2 {
	
	private Player player;
	private BufferedImage image;
	private Font font;
	
	public HUD2(Player p){
		player = p;
		try{
			image = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("HUD2")));
			font = new Font("Arial", Font.PLAIN, 10);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	}
	public void draw(Graphics2D g){
		g.drawImage(image,240,10,null); //Coordenadas del HUD en Pantalla
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		if (player.getexitCounter() >= 2){
			g.setColor(Color.GREEN);
			g.drawString("Enemy's",240,60);
			g.drawString("Shield:",240,70);
			g.setColor(Color.WHITE);
			
			if(Boss1.getlivs() <= 0){
				g.drawString(0 + "/" + "15", 290, 65);
			}
			else{
				g.drawString(Boss1.getlivs() + "/" + "15", 290, 65);
			}
		}
									//Se divide entre 60, por conversion de ms a s
		g.drawString(player.getTime()/60 + "", 290, 20);
		g.drawString(player.getRelics() + "/" + "3",292,47); //Reliquias
		if (player.getScore() == 0){						//Puntaje +"" Conversion a String
			g.drawString(player.getScore() + "",288,34); 
		}
		else if (player.getScore() <= 99){						
			g.drawString(player.getScore() + "",284,34); 
			
		}
		else if (player.getScore() <= 999){						
			g.drawString(player.getScore() + "",277,34); 
			
		}
		else if (player.getScore() <= 9999){						
			g.drawString(player.getScore() + "",270,34); 
			
		}
		else if (player.getScore() <= 99999){						
			g.drawString(player.getScore() + "",263,34); 
			
		}
		
	}

}
