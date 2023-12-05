package ch.heig.dai.lab.smtp.smtputils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * This sends the message to a specified SMTP server. {@link SMTPSender}
 *
 * @author      Arthur Junod
 * @author      Edwin Haeffner
 * Date :       30/11/2023
 */
public class SMTPSender {
    private enum interfaceState {
        READY,
        OK,
        INPUT_START
    }

    //We only need these three answers to successfully send a mail
    private static final String[] smtpRcvMess = {"220 ", "250 ", "354 "};

    /**
     * Sends the message to the specified server and port and defaults to not displaying the messages sent and received
     * @param mail the message to send {@link SMTPConstructor}
     * @param ipv4 the ipv4 address of the server
     * @param port the port of the server
     * @throws IOException if the server is unreachable
     */
    public static void sendMessage(SMTPConstructor mail,String ipv4,int port){
        sendMessage(mail,ipv4,port,false); //Doesn't display the messages by default
    }

    /**
     * Sends the message to the specified server and port
     * @param mail          the message to send {@link SMTPConstructor}
     * @param ipv4          the ipv4 address of the server
     * @param port          the port of the server
     * @param display       if true, displays the messages sent and received
     */
    public static void sendMessage(SMTPConstructor mail,String ipv4,int port,boolean display) {

        try (Socket socket = new Socket(ipv4, port);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(),
                             StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(),
                             StandardCharsets.UTF_8)))
        {
            //Wait for the server to send the initial message
            waitResponse(in, smtpRcvMess[interfaceState.READY.ordinal()],display);

            //Send the hello to initiate the SMTP handshake
            sendData(out, mail.hello(),display);
            waitResponse(in, smtpRcvMess[interfaceState.OK.ordinal()],display);

            //Send the mail from
            sendData(out, mail.from(),display);
            waitResponse(in, smtpRcvMess[interfaceState.OK.ordinal()],display);

            //Send the rcpt to
            sendData(out, mail.rcptTo(),display);
            waitResponse(in, smtpRcvMess[interfaceState.OK.ordinal()],display);

            //If there is a carbon copy, send it
            if (mail.carbonCopyLength > 0) {
                for (int i = 0; i < mail.carbonCopyLength; i++) {
                    sendData(out, mail.rcptToCC(),display);
                    waitResponse(in, smtpRcvMess[interfaceState.OK.ordinal()],display);
                }
            }

            //Send the data and the message
            sendData(out, mail.data(),display);
            waitResponse(in, smtpRcvMess[interfaceState.INPUT_START.ordinal()],display);

            //Wait for the message to be confirmed sent
            waitResponse(in, smtpRcvMess[interfaceState.OK.ordinal()],display);

            sendData(out, "QUIT\r\n",display);

        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }

    /**
     * Waits for the server to send a specific response
     * @param in            the input stream
     * @param response      the response to wait for (Ex 220)
     * @param display       if true, displays the messages sent and received
     * @throws IOException  if the server is unreachable
     */
    static private void waitResponse(BufferedReader in, String response,boolean display) throws IOException {
        String line;
        while (!(line = in.readLine()).contains(response)) {
            if(display) System.out.println(line);
        }
        if(display) System.out.println(line);
    }

    /**
     * Sends the data to the server
     * @param out          the output stream
     * @param data         the data to send
     * @param display      if true, displays the messages sent and received
     * @throws IOException if the server is unreachable
     */
    static private void sendData(BufferedWriter out, String data,boolean display) throws IOException {
        out.write(data);
        if(display) System.out.println(data);
        out.flush();
    }
}
