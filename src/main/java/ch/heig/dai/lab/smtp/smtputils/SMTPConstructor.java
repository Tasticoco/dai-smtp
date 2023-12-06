package ch.heig.dai.lab.smtp.smtputils;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * This is the SMTPConstructor class. It is used to construct the SMTP messages. {@link SMTPConstructor}
 *
 * @author      Arthur Junod
 * @author      Edwin Haeffner
 * Date :       30/11/2023
 */
public class SMTPConstructor {

    private final static boolean BCC = true;
    final private String mailFrom
                  ,mailFromUsername
                  ,mailTo
                  ,mailToUsername
                  ,subject
                  ,messageText;

    final private Charset charset;

    final private ArrayList<String> carbonCopy; //CC in the mail

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
     * @param encoding          the encoding used for the mail
     */
    public SMTPConstructor(String mailFrom,String mailFromUsername,String mailTo,String mailToUsername,
                           ArrayList<String> carbonCopy,String subject,String messageText, Charset encoding){

        this.mailFrom = mailFrom;
        this.mailFromUsername = mailFromUsername;
        this.mailTo = mailTo;
        this.mailToUsername = mailToUsername;
        this.charset = encoding;

        if (carbonCopy == null || carbonCopy.isEmpty()){
            carbonCopyLength = 0;
            this.carbonCopy = null;
        } else {

            this.carbonCopy = new ArrayList<>(carbonCopy);
            carbonCopyLength = carbonCopy.size();
        }

        this.subject = subject;
        this.messageText = messageText;
    }

    /**
     * @return the DATA part of the mail
     */
    public String data(){
        CCIndex = 0;
        StringBuilder data;

        data = new StringBuilder("DATA\r\n");
        data.append(encoding(charset));
        data.append("From: ").append(mailFromUsername).append(" <").append(mailFrom).append(">\r\n");
        data.append("To: ").append(mailToUsername).append(" <").append(mailTo).append(">\r\n");
        while(!BCC && carbonCopyLength > CCIndex){
            data.append("CC: ").append(carbonCopy.get(CCIndex++)).append("\r\n");
        }
        data.append("Subject: ").append(subject).append("\r\n");
        data.append("\r\n");
        data.append(messageText).append("\r\n");
        data.append(".\r\n");

        return data.toString();
    }

    public String encoding(Charset charset){
        return "Content-Type: text/html; charset=" + charset + "\r\n";
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
     * @return the RCP TO part of the mail for the CC emails
     */
    public String rcptToCC(){
        return "RCPT TO: <" + carbonCopy.get(CCIndex++) + ">\r\n";
    }

    /**
     * @return the EHLO part of the mail
     */
    public String hello(){
        return "EHLO " + "https://www.youtube.com/watch?v=dQw4w9WgXcQ" + "\r\n";
    }

}
