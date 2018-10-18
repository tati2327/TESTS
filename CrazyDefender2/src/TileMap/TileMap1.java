package TileMap;

import gui.GamePanel;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

public class TileMap1 {
	//Posicion
	private double x;
	private double y;
	
	//BOrdes
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	//Mapa
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//TileSet
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	//Dibujar
	private int rowOffset; //En cual fila empezar a dibujar
	private int colOffset; //En cual columna empezar a dibujar
	private int numRowsToDraw;
	private int numColsToDraw;
	
	//Constructor
	
	public TileMap1(int tileSize){
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07; 
	}
	
	public void loadTiles(String s){ //Carga las colums & filas
		try{
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++){
				subimage = tileset.getSubimage(col*tileSize,0,tileSize,tileSize);
				
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col*tileSize,tileSize,tileSize,tileSize);
				
				tiles[1][col] = new Tile(subimage,Tile.BLOCKED);
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s){ //Carga el mapa
		try{
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); //Carga
			
			numCols = Integer.parseInt(br.readLine()); //Primera Linea de txt
			numRows = Integer.parseInt(br.readLine()); //Segunda Linea de txt
			map = new int[numRows][numCols]; //Filas y Columnas
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			
			String delims = "\\s+";
			for (int row = 0; row < numRows; row++){
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public int getTileSize(){ return tileSize; }
	public int getx(){ return(int)x; }
	public int gety(){ return(int)y; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	
	public int getType(int row, int col){
		int rc = map[row][col];
		int r  = rc / numTilesAcross;
		int c  = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public void setPosition (double x, double y){ 
		//CAMARA QUE SIGUE AL JUGADOR
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds(); // Evita Bordes Extraños
		
		colOffset = (int)-this.x / tileSize; //Columna que empieza a dibujar
		rowOffset = (int)-this.y / tileSize; //Fila que empieza a dibujar

	}
	
	private void fixBounds(){
		if (x < xmin) x = xmin;
		if (y < ymin) y = ymin;
		if (y > xmax) x = xmax;
		if (y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g){
		
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++){
			if (row >= numRows) break;
			for (int col = colOffset; col < colOffset + numColsToDraw; col++){
				if (col >= numCols) break;
				if (map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tiles[r][c].getImage(),
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null 
				);
			}
		}
	}

}
