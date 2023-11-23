package ch.heig.dai.lab.smtp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {

    private static int port = 1025;
    private static String ipv4 = "localhost";

    private static final String[] smtpRcvMess = {"250 HELP", "250 OK", "354 Start mail input; end with <CRLF>.<CRLF>", "550 No such user here","554 Transaction has failed "};

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
            System.out.println("Prank program SMTP");


            SMTPConstructor testMail = new SMTPConstructor("sender@example.com","Sender",
                    "recipient@example.com","Recipient",null,
                    "Test Email",
                    "Hello,\n" +
                    "\n" +
                    "This is a test email sent via SMTP.\n" +
                    "\n" +
                    "Regards,\n" +
                    "Sender\n");

            String line;
            out.write(testMail.hello());
            while(!(line = in.readLine()).equals(smtpRcvMess[0])){
                System.out.println(line);
            }
            out.write(testMail.from());
            if((line = in.readLine()).equals(smtpRcvMess[1])){
                System.out.println(line);
                out.write(testMail.rcptTo());
            }
            while((line = in.readLine()).equals(smtpRcvMess[1])){
                System.out.println(line);
                out.write(testMail.rcptToCC());
            }
            if((line = in.readLine()).equals(smtpRcvMess[2])){
                System.out.println(line);
                out.write(testMail.data());
            }
            if((line = in.readLine()).equals(smtpRcvMess[1])){
                System.out.println(line);
                out.write(testMail.quit());
            }



        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }

}
