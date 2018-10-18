package logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Clase que permite la lectura de datos desde el archivo "config.properties" ubicado en la carpeta
 * ra�z del juego. Esto facilita la manipulaci�n de variables importantes a la hora de la
 * ejecuci�n del juego, as� como par�metros de m�todos y direcciones de los directorios donde se 
 * encuentran im�genes y sonidos.
 * <p>
 * Se realiz� la creaci�n de esta clase por recomendaci�n del profesor Nereo Campos.
 * 
 * @author Fabian A. Solano Madriz
 * @version 2.1
 *
 */
//Clase que permite leer el archivo properties.
public class ReadProperties {
	
	public static ReadProperties file = null;
	public static Properties props = new Properties();
	InputStream input =null;
	
	private ReadProperties(){
		
		try{
			input= new FileInputStream("config.properties");
			props.load(input);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			if (input !=null){
				try{
					input.close();
				}
				catch (IOException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	//Instancia el Archivo Properties |Solo se instancia una vez|
	public static synchronized ReadProperties getInstance(){
		if (file ==null){
			file = new ReadProperties();
		}
		return file;
	}
	
	public String getSetting(String key){
		return props.getProperty(key);
	}

}
