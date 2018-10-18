package logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que permite la lectura de datos desde el archivo "scores.properties" ubicado en la carpeta
 * raíz del juego. Esto facilita la manipulación de los datos de los puntajes mas altos alcanzados
 * en el juego.
 * <p>
 * Se realizó la creación de esta clase por recomendación del profesor Nereo Campos.
 * 
 * @author Fabian A. Solano Madriz
 * @version 2.1
 *
 */
public class ReadScores {
	
	public static ReadScores file = null;
	public static Properties props = new Properties();
	InputStream input =null;
	
	private ReadScores(){
		
		try{
			input= new FileInputStream("scores.properties");
			props.load(input);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//Instancia el Archivo Properties |Solo se instancia una vez|
	public static synchronized ReadScores getInstance(){
		if (file ==null){
			file = new ReadScores();
		}
		return file;
	}
	
	public String getSetting(String key){
		return props.getProperty(key);
	}

}
