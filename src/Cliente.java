import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.56.1", 5000); // Creamos el socket del cliente y nos conectamos al servidor en el puerto 5000
            System.out.println("Conectado al servidor");

            // Creamos un hilo para manejar la lectura de mensajes del servidor
            Thread inputThread = new Thread(new InputHandler(socket));
            inputThread.start();

            // Creamos un stream de salida para enviar mensajes al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Leemos los mensajes del usuario y los enviamos al servidor
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userInputLine;

            while ((userInputLine = userInput.readLine()) != null) {
                out.println(userInputLine);

                // Si el usuario envía el mensaje "adios", cerramos la conexión
                if (userInputLine.equals("adios")) {
                    break;
                }
            }

            // Cerramos los streams y el socket del cliente
            out.close();
            userInput.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}

class InputHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;

    public InputHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Leemos los mensajes del servidor y los imprimimos en la consola
            String serverInput;

            while ((serverInput = in.readLine()) != null) {
                System.out.println(serverInput);

                // Si el servidor envía el mensaje "adios", cerramos la conexión
                if (serverInput.equals("adios")) {
                    break;
                }
            }

            // Cerramos el stream de entrada del servidor y el socket del cliente
            in.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Error en la lectura del servidor: " + e.getMessage());
        }
    }
}
