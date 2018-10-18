package logic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Clase que permite la escritura del archivo scores.properties. Básicamente vuelve a escribir los
 * datos que estaban en él anteriormente a la edición y posteriormenten reemplaza el dato necesario
 * con el nuevo valor asignado
 * 
 * @author Fabian A. Solano Madriz
 * @version 1.6
 *
 */
public class SaveScores {
	
	public static SaveScores file = null;
	public static Properties props = new Properties();
	OutputStream input =null;
	
	private SaveScores(String iname, String iscore, String ireplace){
		
		try{
			//System.out.print("Scores Saved" + " savescores");
				
			input= new FileOutputStream("scores.properties");

			props.setProperty("player1", ReadScores.file.getSetting("player1"));
			props.setProperty("player1s", ReadScores.file.getSetting("player1s"));
			props.setProperty("player2", ReadScores.file.getSetting("player2"));
			props.setProperty("player2s", ReadScores.file.getSetting("player2s"));
			props.setProperty("player3", ReadScores.file.getSetting("player3"));
			props.setProperty("player3s", ReadScores.file.getSetting("player3s"));
			props.setProperty("player4", ReadScores.file.getSetting("player4"));
			props.setProperty("player4s", ReadScores.file.getSetting("player4s"));
			
			props.replace(ireplace,iname+"                            "+iscore);
			props.replace(ireplace+"s",iscore);
			
			props.store(input,null);
			
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//Instancia el Archivo Properties |Solo se instancia una vez|
	public static SaveScores getInstance(String iname, String iscore, String ireplace){
		if (file ==null){
			file = new SaveScores(iname,iscore,ireplace);
		}
		return file;
	}

}
