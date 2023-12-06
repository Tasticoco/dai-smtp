package ch.heig.dai.lab.smtp;

import ch.heig.dai.lab.smtp.smtputils.SMTPConstructor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static ch.heig.dai.lab.smtp.smtputils.SMTPSender.sendMessage;

/**
 * The object used to create an SMTP client that will prank others with spam mails.
 *
 * @author      Arthur Junod
 * @author      Edwin Haeffner
 * Date :       03/12/2023
 */
public class Client {

    private static int    port = 1025;
    private static String ipv4 = "localhost";

    public Client(int port,String ipv4){
        Client.port = port;
        Client.ipv4 = ipv4;  
    }

    /**
     * The main function of the program
     * @param args The number of groups to form
     */
    public static void main(String[] args){

        try{
            Config config = new Config();
            int nbGroup = 5;
            if(args.length == 0){
                System.out.println("Since no arguments were provided, there will be 5 groups");
            } else nbGroup = Integer.parseInt(args[0]);
            if(nbGroup <= 0){
                System.out.println("Invalid arguments provided, there will be 5 groups");
                nbGroup = 5;
            }
            System.out.println("Prank program SMTP");

            ArrayList<ArrayList<String>> copyMessage = new ArrayList<>(config.MESSAGE_LIST);
            GroupEmail groupEmail = new GroupEmail(nbGroup,config);
            pranking(groupEmail,copyMessage);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Client: end");


    }

    /**
     * Start to prank the groups with the messages given.
     *
     * @param allGroups The groups formed from the config file
     * @param messages  The messages from the config file
     */
    static public void pranking(GroupEmail allGroups, ArrayList<ArrayList<String>> messages){
        String mailFrom, mailFromUsername,mailTo,mailToUsername,subject,body;

        for(ArrayList<ArrayList<String>>  group : allGroups.groups){
            ArrayList<String> carbonCopyMail = new ArrayList<>();
            mailFrom         = group.get(0).get(0);
            mailFromUsername = group.get(0).get(1);
            mailTo           = group.get(1).get(0);
            mailToUsername   = group.get(1).get(1);

            for(int i = 2; i < group.size(); ++i){
                carbonCopyMail.add(group.get(i).get(0));
            }

            Random rand     = new Random();
            int randomIndex = rand.nextInt(0, messages.size() - 1);
            subject         = messages.get(randomIndex).get(0);
            body            = messages.get(randomIndex).get(1);

            messages.remove(randomIndex);

            SMTPConstructor mail = new SMTPConstructor(mailFrom,mailFromUsername,mailTo,mailToUsername,
                    carbonCopyMail,subject,body,StandardCharsets.UTF_8);

            sendMessage(mail,ipv4,port,true);
        }
    }

}
