import java.io.*;
import java.net.*;
import java.util.Random;

public class Game_server {

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        int serverNumber = random.nextInt(1000);
        System.out.println(serverNumber);
        System.out.println("Enter your secret number:");
        System.out.println("Game start: ");
        try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                Thread clientThread = new Thread(() -> {
                    try {
                        handleClient(connectionSocket, serverNumber);
                    } catch (IOException e) {
                    }
                });
                clientThread.start();
                
            }
            
        }
    }

    private static void handleClient(Socket connectionSocket, int serverNumber) throws IOException {
        String messageToClient;
        DataInputStream inFromClient = new DataInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        for (int i = 0; i < 5; i++) {
            int clientNumber = Integer.parseInt(inFromClient.readLine());
            System.out.println("Client guess :" + clientNumber);
            if (clientNumber == serverNumber) {
                messageToClient = "You Win Amigo!!" + '\n';
                outToClient.writeBytes(messageToClient);
                messageToClient = "You Win Amigo!!" + '\n';
                break;
            } else if (clientNumber < serverNumber) {
                messageToClient = "Your number is low :(" + '\n';
            } else {
                messageToClient = "Your number is high brother calm down" + '\n';
            }
            outToClient.writeBytes(messageToClient);
        }
        connectionSocket.close();
    }
}
