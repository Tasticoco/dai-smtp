package ch.heig.dai.lab.smtp.smtputils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * This is the SMTPConstructor class. It is used to construct the SMTP messages. {@link SMTPConstructor}
 *
 * @author      Arthur Junod
 * @author      Edwin Haeffner
 * Date :       30/11/2023
 */
public class SMTPConstructor {

    final private String mailFrom
                  ,mailFromUsername
                  ,mailTo
                  ,mailToUsername
                  ,subject
                  ,messageText;

    final private Charset charset;

    final private String[] carbonCopy; //CC in the mail

    final protected int carbonCopyLength;
    private int CCIndex = 0;

    /**
     * Constructor for the SMTPConstructor class
     * @param mailFrom          the mail address of the sender
     * @param mailFromUsername  the username of the sender
     * @param mailTo            the mail address of the receiver
     * @param mailToUsername    the username of the receiver
     * @param carbonCopy        the other email addresses to send the mail to (CC)
     * @param subject           the subject of the mail
     * @param messageText       the body of the mail
     */
    public SMTPConstructor(String mailFrom,String mailFromUsername,String mailTo,String mailToUsername,
                           String[] carbonCopy,String subject,String messageText, Charset encoding){

        this.mailFrom = mailFrom;
        this.mailFromUsername = mailFromUsername;
        this.mailTo = mailTo;
        this.mailToUsername = mailToUsername;
        this.charset = encoding;

        if (carbonCopy == null || carbonCopy.length == 0){
            carbonCopyLength = 0;
            this.carbonCopy = null;
        } else {

            carbonCopyLength = carbonCopy.length;
            this.carbonCopy = new String[carbonCopy.length];
            System.arraycopy(carbonCopy,0,this.carbonCopy,0,carbonCopy.length);
        }

        this.subject = subject;
        this.messageText = messageText;
    }

    /**
     * @return the DATA part of the mail
     */
    public String data(){

        String data;
        data = "DATA\r\n";
        data += encoding(charset);
        data += "From: " + mailFromUsername + " <" + mailFrom + ">\r\n";
        data += "To: " + mailToUsername + " <" + mailTo + ">\r\n";
        if(carbonCopyLength > 0) data += "CC: " + carbonCopy[0] + "\r\n";
        data += "Subject: " + subject + "\r\n";
        data += "\r\n";
        data += messageText + "\r\n";
        data += ".\r\n";

        return data;
    }

    public String encoding(Charset charset){
        return "Content-Type: text/plain; charset=" + charset + "\r\n";
    }

    /**
     * @return the MAIL FROM part of the mail
     */
    public String from(){
        return "MAIL FROM: <" + mailFrom + ">\r\n";
    }

    /**
     * @return the RCPT TO part of the mail
     */
    public String rcptTo(){
        return "RCPT TO: <" + mailTo + ">\r\n";
    }

    /**
     * @return the MAIL FROM part of the mail
     */
    public String rcptToCC(){
        return "RCPT TO: <" + carbonCopy[CCIndex++] + ">\r\n";
    }

    /**
     * @return the EHLO part of the mail
     */
    public String hello(){
        return "EHLO " + "https://www.youtube.com/watch?v=dQw4w9WgXcQ" + "\r\n";
    }

}
