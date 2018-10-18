package dataAccess;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;



public class SSocket extends Thread{
	
	int puerto;
	private ServerSocket server;
	private Socket socket;
	public DataOutputStream salida;
	private String msjRecibido;
	private BufferedReader entrada;
	private String local_ID;
	public Lock lock;
	int counter = 0;
	private boolean running = false;
	
	/**
	 * Método para inicializar el socket.
	 * @param pPuerto: Número entero correspondiente al número de puerto que se desea abrir.
	 */
	public SSocket(Facade pFacade,int pPuerto){
		puerto = pPuerto;
		running = true;
		initServer();
	}
	
	public void initServer(){
		
		try
		{
			server = new ServerSocket(puerto);
			socket = new Socket();
				System.out.println("$$ServerMsg$$ Esperando Conexión... $$ServerMsg$$");
			run();
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run(){
		while(running){
			try{
				socket = server.accept();
				entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				salida = new DataOutputStream(socket.getOutputStream());
				salida.writeUTF("Message ");
				
				
				msjRecibido = entrada.readLine();
				//TODO VERIFICAR COMO OBTENER EL ID DE DESTINO ->, ACTUAL CON FINES DE PRUEBA
				
				String[] msg_and_ID = msjRecibido.split("#");
				
				System.out.println(msjRecibido);
				
				
			}
			catch(Exception e){
				
			}
		}
	}
	
	
	public static void main(String[] args) {
    	try {
			SSocket p = new SSocket(new Facade(), 9090);

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	

}
