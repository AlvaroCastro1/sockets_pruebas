import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Servidor iniciado. Esperando conexiones...");

        while (true) {
            // Esperamos a que un cliente se conecte
            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

                // Creamos un hilo para atender al cliente
                Thread t = new Thread(new mensaje(clientSocket));
                t.start();
            } catch (IOException e) {
                System.err.println("Error aceptando la conexión");
            }
        }
    }
}

// Clase para manejar cada conexión de forma individual en un hilo separado
class mensaje implements Runnable {
    private Socket socket_cliente;

    public mensaje(Socket socket) {
        this.socket_cliente = socket;
    }

    public void run() {
        try {
            // Creamos los streams de entrada y salida para comunicarnos con el cliente
            PrintWriter writter = new PrintWriter(socket_cliente.getOutputStream(), true);
            BufferedReader leer = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));

            // Leemos los mensajes del cliente y los imprimimos en la consola
            String mensaje_del_cliente;

            while ((mensaje_del_cliente = leer.readLine()) != null) {
                System.out.println("Cliente: " + mensaje_del_cliente);

                // Le respondemos al cliente con el mismo mensaje que nos envió
                writter.println(mensaje_del_cliente);

                // Si el cliente envía el mensaje "adios", cerramos la conexión
                if (mensaje_del_cliente.equals("adios")) {
                    break;
                }
            }

            // Cerramos los streams y el socket del cliente
            writter.close();
            leer.close();
            socket_cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
