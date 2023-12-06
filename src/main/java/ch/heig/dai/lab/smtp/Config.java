package ch.heig.dai.lab.smtp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This is able to read json files that give the email and messages to be used in the spam mails {@link GroupEmail}
 *
 * @author      Arthur Junod
 * @author      Edwin Haeffner
 * Date :       30/11/2023
 */
public class Config {
    protected final ArrayList<ArrayList<String>> VICTIM_LIST  = new ArrayList<>();
    protected final ArrayList<ArrayList<String>> MESSAGE_LIST = new ArrayList<>();
    String configEmail   = "configEmail.json";
    String configMessage = "configMessage.json";

    /**Constructor
     * @throws RuntimeException if one of the email in the list is invalid
     */
    public Config() throws RuntimeException{

        try(BufferedReader reader = new BufferedReader(new FileReader(configEmail));) {
            String line = reader.readLine();
            String email;
            String username;
            int counter = 0;

            while (line != null) {
                if (line.contains("email")) {
                    //We remove the " and the , in the string that are artifacts of the json file
                    email = line.split(":",2)[1].replaceAll("[\",]", "").trim();
                    VICTIM_LIST.add(new ArrayList<>());
                    VICTIM_LIST.get(counter).add(email);
                } else if (line.contains("username")) {
                    username = line.split(":",2)[1].replaceAll("[\",]", "").trim();
                    VICTIM_LIST.get(counter).add(username);
                    counter++;
                }

                line = reader.readLine();
            }

            reader.close();

            if (!validateAllEmail()) {
                throw new RuntimeException("One of the email in the list is invalid !");
            }


        } catch (FileNotFoundException e){
            throw new RuntimeException("File \"" + configEmail + "\" not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configMessage));){

            String line = reader.readLine();
            String subject;
            String message;
            int counter = 0;

            while (line != null) {
                if (line.contains("subject")) {
                    subject = line.split(":",2)[1].replaceAll("[\",]", "").trim();
                    MESSAGE_LIST.add(new ArrayList<>());
                    MESSAGE_LIST.get(counter).add(subject);
                } else if (line.contains("body")) {
                    message = line.split(":",2)[1].replaceAll("[\",]", "").trim();
                    MESSAGE_LIST.get(counter).add(message);
                    counter++;
                }
                line = reader.readLine();

            }

        } catch (FileNotFoundException e){
            throw new RuntimeException("File \"" + configMessage + "\" not found");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**Check if the email is valid using a regex pattern and prints the invalid email
     * @param email the email to check
     * @return true if the email is valid, false otherwise
     */
     public boolean isEmailValid(String email) {
        if (email == null)
            return false;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    /**Check if all the email in the list are valid
     * @return true if all the email are valid, false otherwise
     */
    private boolean validateAllEmail() {

        for (ArrayList<String> a : VICTIM_LIST) {

            if (!isEmailValid(a.get(0))) {
                System.out.println("Invalid email : " + a.get(0));
                return false;
            }
        }

        return true;
    }

}
