package dataAccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
 
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
 
/**
 * @author hkr
 *
 */
public class AtiendeConexion implements Runnable {
        // private ObjectOutputStream salida;
        private Socket cliente;
        private ObjectOutputStream salida;
        private ObjectInputStream entrada;
        private JTextArea areaPantalla;
 
        public AtiendeConexion(Socket client, JTextArea area) {
                this.cliente = client;
                this.areaPantalla = area;
        }
        public synchronized void stop(){
                cliente = null;
                salida = null;
                entrada = null;
                areaPantalla = null;
        }
        public void run() {
                while (true) {
                        try {
                                obtenerFlujos();
                                procesarConexion();
                        } catch (IOException e) {
                                //e.printStackTrace();
                                try {
                                        mostrarMensaje("\n"
                                                        + (String) cliente.getInetAddress().getHostName()
                                                        + " se desconecto.");
                                        salida.close();
                                        entrada.close();
                                        cliente.close();
                                        stop();
                                } catch (IOException e1) {
                                        e1.printStackTrace();
                                }
                        }
                }
        }
 
        // obtener flujos para enviar y recibir datos
        private void obtenerFlujos() throws IOException {
                // establecer flujo de salida para los objetos
                salida = new ObjectOutputStream(cliente.getOutputStream());
                salida.flush(); // vaciar búfer de salida para enviar información de
                                                // encabezado
 
                // establecer flujo de entrada para los objetos
                entrada = new ObjectInputStream(cliente.getInputStream());
                mostrarMensaje("\nSe recibieron los flujos de E/S\n");
        }
 
        // procesar la conexión con el cliente
        private void procesarConexion() throws IOException {
                // enviar mensaje de conexión exitosa al cliente
                String mensaje = "Conexión exitosa";
                // enviarDatos(mensaje);
 
                // habilitar campoIntroducir para que el usuario del servidor pueda
                // enviar mensajes
                // establecerCampoTextoEditable(true);
 
                do { // procesar los mensajes enviados por el cliente
 
                        // leer el mensaje y mostrarlo en pantalla
                        try {
                                mensaje = (String) cliente.getInetAddress().getHostName() + " dice: "
                                                + entrada.readObject();
                                mostrarMensaje("\n" + mensaje);
                        }
 
                        // atrapar problemas que pueden ocurrir al tratar de leer del
                        // cliente
                        catch (ClassNotFoundException excepcionClaseNoEncontrada) {
 
                        }
 
                } while (!mensaje.equals("CLIENTE>>> TERMINAR"));
 
        } // fin del método procesarConexion
                // cerrar flujos y socket
 
        private void cerrarConexion() {
                // mostrarMensaje("\nFinalizando la conexión\n");
                // establecerCampoTextoEditable(false); // deshabilitar campoIntroducir
 
                try {
                        salida.close();
                        entrada.close();
                        cliente.close();
                } catch (IOException excepcionES) {
                        excepcionES.printStackTrace();
                }
        }
 
        private void mostrarMensaje(final String mensajeAMostrar) {
                // mostrar mensaje del subproceso de ejecución despachador de eventos
                SwingUtilities.invokeLater(new Runnable() { // clase interna para
                                                                                                        // asegurar que la GUI se
                                                                                                        // actualice apropiadamente
 
                                        public void run() // actualiza areaPantalla
                                        {
                                                areaPantalla.append(mensajeAMostrar);
                                                areaPantalla.setCaretPosition(areaPantalla.getText()
                                                                .length());
                                        }
 
                                } // fin de la clase interna
 
                                ); // fin de la llamada a SwingUtilities.invokeLater
        }
}