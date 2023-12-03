package ch.heig.dai.lab.smtp;

import ch.heig.dai.lab.smtp.smtputils.SMTPConstructor;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
            GroupEmail allGroup = new GroupEmail(2, config);
            System.out.println("Prank program SMTP");

            ArrayList<ArrayList<String>> copyMessage = new ArrayList<>(config.MESSAGE_LIST);


            SMTPConstructor testMail = new SMTPConstructor("sender@example.com","Sender",
                    "recipient@example.com","Recipient",null,
                    "Test Email",
                    "Hello,\n" +
                    "\n" +
                    "This is a test email sent via SMTP .д. !\n" +
                    "\n" +
                    "Regards,\n" +
                    "Sender\n",
                    StandardCharsets.UTF_8);


              sendMessage(testMail,ipv4,port,true);
//            sendMessage(testMail,ipv4,port,true);

            GroupEmail groupEmail = new GroupEmail(13,config);

            System.out.println("Client: end");


    }

    public void pranking(GroupEmail allGroup, ArrayList<ArrayList<String>> messages){
        String mailFrom, mailFromUsername,mailTo,mailToUsername,subject,body;
        ArrayList<String> 
        for(ArrayList<String>  group : allGroup){

            for(int j = 1; j < group.get(i).size(); ++j){

            }
            //SMTPConstructor
        }
    }

}
