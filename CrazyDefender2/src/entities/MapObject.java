package entities;

import TileMap.TileMap;
import gui.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle; //Para crear rectangulo para deteccion de colisiones
import TileMap.Tile;

/**
 * Clase madre de los objetos dentro del juego. Todos los objetos del juego poseen las variables
 * y métodos de esta clase para de esta forma tenerlos agrupados y acceder a sus atributos de 
 * manera sencilla y ordenada.
 * <p>
 * Esta clase esta basada en la vista en el video tutorial de ForeignGuyMike
 * @author Fabian A. Solano Madriz
 * @version 1.2
 * @see <a href="https://www.youtube.com/watch?v=9dzhgsVaiSo">ForeignGuyMike Youtube Channel</a>
 *
 */
//Root para todos los objetos.
public abstract class MapObject {
	//Protected para que las subclases lo puedan utilizar
	// x,y
	protected static TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//Posicion y Vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//Dimensions
	protected int width;
	protected int height;
	
	//Caja de Colisión, para detectar las colisiones
	protected int cwidth;
	protected int cheight;
	
	//colisiones
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp; //Variable Temporal X
	protected double ytemp; //Variable Temporal Y
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	//Animacion
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight; //Si la nave ve hacia la derecha o izquierda.
	
	//Movimientos, Booleans para que esta haciendo el objeto
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	//Atributos de Movimiento
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed; //Desaceleracion
	protected double fallSpeed; //Gravedad
	protected double maxFallSpeed; 
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	//Constructor
	public MapObject(TileMap tm){
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	//Para ver las colisiones, si los dos objetos han colisionado
	public boolean intersects(MapObject o){
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
		
	}
	public Rectangle getRectangle(){
		return new Rectangle(
				(int)x - cwidth,
				(int)y - cheight,
				cwidth,
				cheight
		);
	}
	
	//Calcula las colisiones con objetos, Ej: Jugador con bloques,etc
	public void calculateCorners(double x, double y){
		int leftTile  = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize; //-1 para que no choque
		int topTile   = (int)(y - cwidth / 2) / tileSize;    //con el pixel del bloque
		int bottomTile= (int)(y + cwidth / 2 - 1) / tileSize;
		
		//Cuatro Esquinas
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		//Variables de la sesquinas
		topLeft    = tl == Tile.BLOCKED; //Si choca con una esquina que es tipo block 
		topRight   = tr == Tile.BLOCKED; // y no tile normal
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight= br == Tile.BLOCKED;
		
		
				
	}
	
	//Verificar si se colisiona con un tile normal o un block tile
	public void checkTileMapCollision(){
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		//////////////////////CALCULO COORDENADAS DEL OBJETO ///////////////////////////////
		//CALCULO DE X
		calculateCorners(x, ydest); //Direccion en x
		
		if(dy < 0){
			if(topLeft || topRight){
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else{
				ytemp += dy;
			}
		}
		if(dy > 0){
			if(bottomLeft || bottomRight){
				dy=0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else{
				ytemp+=dy;
			}
		}
		
		//CALCULO DE Y
		calculateCorners(xdest, y);
		if(dx < 0){
			if(topLeft || bottomLeft){ //Si es cierto choca con un block, tiene que parar
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else{
				xtemp += dx;
			}
		}
		if(dx > 0){
			if(topRight || bottomLeft){
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else{
				xtemp+=dx;
			}
		}
		
		if (!falling){
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight ){
				falling = true;
			}
		}
	}
	
	//Para obtener variables del objeto de widht,heith,x,y
	public int getx()      {return (int)x; }
	public int gety()      {return (int)y; }
	public int getWidth()  {return width;  }
	public int getHeight() {return height; }
	public int getCWidth() {return cwidth; }
	public int getCHeight(){return cheight;}
	
	//Setters
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	//Todo Objeto tiene dos posiciones, la del mapa y la que se encuentra
	//Position del Mapa
	public void setMapPosition(){
		xmap = tileMap.getx(); // Posicion Final seria = x + xmap, y + ymap
		ymap = tileMap.gety();
	}
	
	//Acciones de movimiento para el objeto
	public void setLeft(boolean b)   {left = b;}
	public void setRight(boolean b)  {right=b;}
	public void setUp(boolean b)     {up = b;}
	public void setDown(boolean b)   {down=b;}
	public void setJumping(boolean b){jumping = b;}
	
	public boolean notOnScreen(){
		return x + xmap + width < 0 || 
				x + xmap - width > GamePanel.WIDTH ||
				y + xmap + height < 0 ||
				y + xmap - height > GamePanel.HEIGHT;
	}
	
	public void draw(Graphics2D g){
		//Dibujo del Sprite correspondiente
		if(facingRight){
			g.drawImage(
					animation.getImage(),
					(int)(x+xmap - width / 2),
					(int)(y+ymap - height/ 2),
					width,
					height,
					null
				);
		}
		else{
			g.drawImage(
					animation.getImage(),
					(int)(x+xmap - width / 2 + width),
					(int)(y+ymap - height/ 2),
					-width,
					height,
					null
				);
		}
	}
}
