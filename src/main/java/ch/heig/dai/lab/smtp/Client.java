package ch.heig.dai.lab.smtp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Client {

    private static int port = 1025;
    private static String ipv4 = "localhost";

    private static final String[] smtpRcvMess = {"250 HELP", "250 SMTPUTF8", "250 Accepted", "354 Start mail input; end with <CRLF>.<CRLF>", "550 No such user here","554 Transaction has failed "};

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

            TimeUnit.MILLISECONDS.sleep(1000);


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
            String lineOut;

            lineOut = testMail.hello();
            out.write(lineOut);
            System.out.println(lineOut);

            out.flush();
            while(!(line = in.readLine()).equals(smtpRcvMess[1])){
                System.out.println(line);
            }
            System.out.println(line);
            lineOut = testMail.from();
            out.write(lineOut);
            System.out.println(lineOut);
            out.flush();

            while(!(line = in.readLine()).equals(smtpRcvMess[2])){
                System.out.println(line);
            }
            System.out.println(line);
            lineOut = testMail.rcptTo();
            out.write(lineOut);
            System.out.println(lineOut);
            out.flush();
            while(!(line = in.readLine()).equals(smtpRcvMess[2])){
                System.out.println(line);
            }
            out.write("DATA\r\n");
            System.out.println("DATA\r\n");
//            while(!(line = in.readLine()).equals(smtpRcvMess[0])){
//                System.out.println(line);
//            }
            System.out.println(line);
            lineOut = testMail.data();
            out.write(lineOut);
            System.out.println(lineOut);
            out.flush();
            while(!(line = in.readLine()).equals(smtpRcvMess[0])){
                System.out.println(line);
            }
            System.out.println(line);
            lineOut = testMail.quit();
            out.write(lineOut);
            System.out.println(lineOut);
            out.flush();





        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
