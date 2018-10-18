package TileMap;

import java.awt.image.BufferedImage;

/**
 * Clase que verifica la identidad de un bloque de mapa. Es decir si este es un bloque normal,
 * o uno que es sólido
 * <p>
 * Esta clase esta basada en la vista en el video tutorial de ForeignGuyMike
 * @author Fabian A. Solano Madriz
 * @version 1.0.1
 * @see <a href="https://www.youtube.com/watch?v=9dzhgsVaiSo">ForeignGuyMike Youtube Channel</a>
 *
 */
public class Tile {
	
	private BufferedImage image;
	private int type;
	
	//Tipos de Bloques
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	//Constructor
	public Tile(BufferedImage image, int type){
		this.image = image;
		this.type = type;
	}
	public BufferedImage getImage(){return image;}
	public int getType() {return type;}
}
