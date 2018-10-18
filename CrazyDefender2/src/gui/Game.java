package gui;

import javax.swing.JFrame;
import logic.ReadProperties;

/**
 * Clase que instancia inicialmente la ventan del juego.
 * <p>
 * Esta clase esta basada en la vista en el video tutorial de ForeignGuyMike
 * @author Fabian A. Solano Madriz
 * @version 1.0.1
 * @see <a href="https://www.youtube.com/watch?v=9dzhgsVaiSo">ForeignGuyMike Youtube Channel</a>
 *
 */
public class Game {
	public static void main(String[] args){ //Clase Main Principal
		//Instancia ReadProperties para la lectura del archivo config.properties, y cargar configuraciones
		ReadProperties.getInstance();
		JFrame window = new JFrame("Crazy Defender");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	  }
}

