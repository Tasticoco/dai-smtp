package ch.heig.dai.lab.smtp;

public class SMTPConstructor {

    final private String mailFrom
                  ,mailFromUsername
                  ,mailTo
                  ,mailToUsername
                  ,subject
                  ,messageText;

    final private String[] carbonCopy; //CC in the mail

    final public int carbonCopyLength;

    public SMTPConstructor(String mailFrom,String mailFromUsername,String mailTo,String mailToUsername,String[] carbonCopy,String subject,String messageText){
        this.mailFrom = mailFrom;
        this.mailFromUsername = mailFromUsername;
        this.mailTo = mailTo;
        this.mailToUsername = mailToUsername;

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

    public String data(){

        String data;

        data = "From: " + mailFromUsername + " <" + mailFrom + ">\r\n";
        data += "To: " + mailToUsername + " <" + mailTo + ">\r\n";
        if(carbonCopyLength > 0) data += "CC: " + carbonCopy[0] + "\r\n";
        data += "Subject: " + subject + "\r\n";
        data += "\r\n";
        data += messageText + "\r\n";
        data += ".\r\n";

        return data;
    }

    public String from(){
        return "MAIL FROM: <" + mailFrom + ">\r\n";
    }

    public String rcptTo(){
        return "RCPT TO: <" + mailTo + ">\r\n";
    }

    public String rcptToCC(){
        return "RCPT TO: <" + carbonCopy + ">\r\n";
    }

    public String hello(){
        return "EHLO " + "https://www.youtube.com/watch?v=dQw4w9WgXcQ" + "\r\n";
    }

    public String quit(){
        return "QUIT\r\n";
    }




}
