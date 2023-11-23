package ch.heig.dai.lab.smtp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Client {

    private static int port = 1025;
    private static String ipv4 = "localhost";

    private static final String[] smtpRcvMess = {"220 ","250 ","354 ", "550 ","554 ","221 "};

    public Client(int port,String ipv4){
        Client.port = port;
        Client.ipv4 = ipv4;
    }

    public static void sendMessage(SMTPConstructor mail,BufferedWriter out,BufferedReader in) throws IOException {

        String line;
        String lineOut;

        lineOut = mail.hello();
        out.write(lineOut);
        System.out.print(lineOut);

        out.flush();
        while(!(line = in.readLine()).contains(smtpRcvMess[0])){
            System.out.println(line);
        }
        System.out.println(line);
        lineOut = mail.from();
        out.write(lineOut);
        System.out.println(lineOut);
        out.flush();

        while(!(line = in.readLine()).contains(smtpRcvMess[1])){
            System.out.println(line);
        }
        System.out.println(line);
        lineOut = mail.rcptTo();
        out.write(lineOut);
        System.out.println(lineOut);
        out.flush();
        while(!(line = in.readLine()).contains(smtpRcvMess[1])){
            System.out.println(line);
        }
        System.out.println(line);
        lineOut = mail.data();
        out.write(lineOut);
        System.out.println(lineOut);
        out.flush();
        while(!(line = in.readLine()).contains(smtpRcvMess[2])){
            System.out.println(line);
        }
        System.out.println(line);
        while(!(line = in.readLine()).contains(smtpRcvMess[1])){
            System.out.println(line);
        }
        System.out.println(line);
//        System.out.println(line);
//        lineOut = mail.quit();
//        out.write(lineOut);
//        System.out.println(lineOut);
//        out.flush();
//
//        while(!(line = in.readLine()).contains(smtpRcvMess[5])){
//            System.out.println(line);
//        }
//        System.out.println(line);

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


            sendMessage(testMail,out,in);
            TimeUnit.MILLISECONDS.sleep(1000);
            sendMessage(testMail,out,in);



            System.out.println("Client: end");



        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
