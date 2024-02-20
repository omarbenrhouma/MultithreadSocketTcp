package socktestcp;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6500);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream ps = new PrintStream(socket.getOutputStream());

            // Envoi de la chaîne au serveur

            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez un message à envoyer au serveur: ");
            String messageToServer = scanner.nextLine();
            ps.println(messageToServer);

            // Réception de la réponse inversée du serveur
            String reversedMessage = br.readLine();
            System.out.println("Message inversé reçu du serveur : " + reversedMessage);

            // Pause avec Thread.sleep pour bloquer le client
            //Thread.sleep(3000);  // 3000 millisecondes (3 secondes)

            // Fermeture de la connexion avec le serveur
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
