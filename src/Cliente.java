import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {
        
        try {
            Scanner sn = new Scanner(System.in);
            
            Socket sc = new Socket("192.168.1.100", 5000);
            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            
            // Leer mensaje del servidor
            String mensaje = in.readUTF();
            System.out.println(mensaje);
            
            // Escribe el nombre y se lo manda al servidor
            String nombre = sn.next();
            out.writeUTF(nombre);
            
            // ejecutamos el hilo
            ClienteHilo hilo = new ClienteHilo(in, out);
            hilo.start();
            hilo.join();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }
    
}

class ClienteHilo extends Thread{
    
    private DataInputStream in;
    private DataOutputStream out;
    Scanner leer = new Scanner(System.in);

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }
    
    @Override
    public void run(){
        //siempre esperar un mensaje de este cliente
        while (true) {
            try {

                System.out.println(in.readUTF());
                
                System.out.println(" Cual es tu mensaje?");
                out.writeUTF(leer.nextLine());
                System.out.println("Cual es tu Destino?");
                out.writeUTF(leer.nextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}