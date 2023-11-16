package ch.heig.dai.lab.smtp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {

    private static int port = 1080;
    private static String ipv4 = "localhost";

    static Config config;

    public Client(int port,String ipv4){
        Client.port = port;
        Client.ipv4 = ipv4;
    }

    public static void main(String[] args) {

        try (Socket socket = new Socket(ipv4, port);
             BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(),
                        StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(),
                        StandardCharsets.UTF_8)))
        {

            config = new Config(4);


        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }

}
