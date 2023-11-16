package src;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {


    public static void main(String args[]) {
        try (Socket socket = new Socket("localhost", 1234);
        var in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(),
                        StandardCharsets.UTF_8));
        var out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(),
                        StandardCharsets.UTF_8))){
            for (int i = 0; i < 10; i++) {
                out.write("Hello " + i + "\n");
                out.flush();
                System.out.println("Echo: " + in.readLine());
            }
        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }

}
