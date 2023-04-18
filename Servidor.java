import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            // Creamos el socket del servidor y lo enlazamos al puerto 5000
            serverSocket = new ServerSocket(5000);
            System.out.println("Servidor iniciado. Esperando conexiones...");
        } catch (IOException e) {
            System.err.println("No se pudo abrir el puerto 5000");
            System.exit(1);
        }

        while (true) {
            // Esperamos a que un cliente se conecte
            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

                // Creamos un hilo para atender al cliente
                Thread t = new Thread(new ServidorHilo(clientSocket));
                t.start();
            } catch (IOException e) {
                System.err.println("Error aceptando la conexión");
                System.exit(1);
            }
        }
    }
}

// Clase para manejar cada conexión de forma individual en un hilo separado
class ServidorHilo implements Runnable {
    private Socket clientSocket;

    public ServidorHilo(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            // Creamos los streams de entrada y salida para comunicarnos con el cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Leemos los mensajes del cliente y los imprimimos en la consola
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Cliente: " + inputLine);

                // Le respondemos al cliente con el mismo mensaje que nos envió
                out.println(inputLine);

                // Si el cliente envía el mensaje "adios", cerramos la conexión
                if (inputLine.equals("adios")) {
                    break;
                }
            }

            // Cerramos los streams y el socket del cliente
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
