package game_client;

import java.io.*;
import java.net.*;

public class Game_client {

    public static void main(String[] args) throws IOException {
        int myNumber;
        String messageFromServer;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
        DataInputStream inFromServer = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        for (int i = 0; i < 5; i++) {
            System.out.println("Attempt " + (i + 1));
            System.out.println("Guess a number:");
            myNumber = Integer.parseInt(input.readLine());
            outToServer.writeBytes(Integer.toString(myNumber) + '\n');
            messageFromServer = inFromServer.readLine();
            System.out.println("From Server: " + messageFromServer);
            if (messageFromServer.contains("You Win")) {
                System.out.println("Congratulations! You guessed the number. Game over.");
                break;
            }
        }

        clientSocket.close();
    }
}


