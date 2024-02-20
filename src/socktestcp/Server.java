package socktestcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 6500;
    private static final int MAX_CLIENTS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTS);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Serveur en attente de connexions...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket);

                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream ps = new PrintStream(clientSocket.getOutputStream());

                String messageFromClient = br.readLine();
                System.out.println("Message reçu du client " + clientSocket + " : " + messageFromClient);

                // Traitement du message (inversion de la chaîne)
                String reversedMessage = new StringBuilder(messageFromClient).reverse().toString();
                ps.println(reversedMessage);

                // Ajout d'une pause avec Thread.sleep pour bloquer le client en attente
                Thread.sleep(20000);  // 5000 millisecondes (5 secondes)

                System.out.println("Message inversé envoyé avec succès au client " + clientSocket);

                // Fermeture de la connexion avec le client
                clientSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
