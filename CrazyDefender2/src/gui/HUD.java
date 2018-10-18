package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import entities.Player;
import logic.ReadProperties;

/**
 * Clase para que dibuja en el GUI la el HUD 1, el cual muestra las vidas del jugador, y la bateria
 * disponible para disparar cohetes.
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.7
 * 
 *
 */
public class HUD {
	
	private Player player;
	private BufferedImage image;
	private Font font;
	
	public HUD(Player p){
		player = p;
		try{
			image = ImageIO.read(
					getClass().getResourceAsStream(ReadProperties.file.getSetting("HUD")));
			font = new Font("Arial", Font.PLAIN, 14);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	}
	public void draw(Graphics2D g){
		g.drawImage(image,0,10,null); //Coordenadas del HUD en Pantalla
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(player.getHealth() + "/" + 5,30,25);
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire()/100, 30,45);
	}

}
