import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {

        try {
            ServerSocket socket_servidor = new ServerSocket(5000);
            Socket socket_cliente;

            System.out.println("Servidor iniciado");
            while (true) {

                // Espero la conexion del cliente
                socket_cliente = socket_servidor.accept();

                DataInputStream in = new DataInputStream(socket_cliente.getInputStream());
                DataOutputStream out = new DataOutputStream(socket_cliente.getOutputStream());

                // Pido al cliente el nombre al cliente
                out.writeUTF("Servidor: Cual es tu nombre?");
                String nombreCliente = in.readUTF();

                System.out.println("Creada la conexion con el cliente " + nombreCliente);

                // Inicio el hilo
                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente,
                        socket_cliente.getInetAddress().getHostName());
                hilo.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

class ServidorHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    private String host;

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombreCliente, String host) {
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
        this.host = host;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println("------"+nombreCliente);
                Thread.sleep(100);
                String mensaje = in.readUTF();
                Thread.sleep(100);
                String destino_host = in.readUTF();
                Thread.sleep(100);
                System.out.println("\tPara: " + destino_host);
                // escribir en el cliente
                Socket sc = new Socket(destino_host, 5000);
                DataOutputStream out_dest = new DataOutputStream(sc.getOutputStream());
                out_dest.writeUTF("De " +nombreCliente+": "+mensaje);


            } catch (IOException e) {
                System.out.println(e);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}