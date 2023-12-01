package ch.heig.dai.lab.smtp;

import ch.heig.dai.lab.smtp.smtputils.SMTPConstructor;

import java.io.*;
import java.util.ArrayList;

import static ch.heig.dai.lab.smtp.smtputils.SMTPSender.sendMessage;

public class Client {

    private static int port = 1025;
    private static String ipv4 = "localhost";


    public Client(int port,String ipv4){
        Client.port = port;
        Client.ipv4 = ipv4;
    }


    public static void main(String[] args) throws InterruptedException, IOException {

            Config config = new Config();
            System.out.println("Prank program SMTP");

            ArrayList<ArrayList<String>> copyVictim = new ArrayList<>(config.VICTIM_LIST);
            ArrayList<ArrayList<String>> copyMessage = new ArrayList<>(config.MESSAGE_LIST);


            SMTPConstructor testMail = new SMTPConstructor("sender@example.com","Sender",
                    "recipient@example.com","Recipient",null,
                    "Test Email",
                    "Hello,\n" +
                    "\n" +
                    "This is a test email sent via SMTP.\n" +
                    "\n" +
                    "Regards,\n" +
                    "Sender\n");


            sendMessage(testMail,ipv4,port);
            sendMessage(testMail,ipv4,port);

            System.out.println("Client: end");


    }

}
