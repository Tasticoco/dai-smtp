package ch.heig.dai.lab.smtp;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Config {
    protected final ArrayList<ArrayList<String>> VICTIM_LIST = new ArrayList<>();
    protected final ArrayList<ArrayList<String>> MESSAGE_LIST = new ArrayList<>();
    String configEmail = "configEmail.json";
    String configMessage = "configMessage.json";

    public Config() {

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(configEmail));
            String line = reader.readLine();
            String email;
            String username;
            int counter = 0;

            while (line != null) {
                if (line.contains("email")) {
                    email = line.split(":")[1].replaceAll("[\",]", "").trim();
                    VICTIM_LIST.add(new ArrayList<>());
                    VICTIM_LIST.get(counter).add(email);
                } else if (line.contains("username")) {
                    username = line.split(":")[1].replaceAll("[\",]", "").trim();
                    VICTIM_LIST.get(counter).add(username);
                    counter++;
                }

                line = reader.readLine();
            }


            reader.close();


            try {
                if (!validateAllEmail()) {
                    throw new RuntimeException("One of the email in the list is invalid !");
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            reader = new BufferedReader(new FileReader(configMessage));
            String line = reader.readLine();
            String subject;
            String message;
            int counter = 0;

            while (line != null) {
                if (line.contains("subject")) {
                    subject = line.split(":")[1].replaceAll("[\",]", "").trim();
                    MESSAGE_LIST.add(new ArrayList<>());
                    MESSAGE_LIST.get(counter).add(subject);
                } else if (line.contains("body")) {
                    message = line.split(":")[1].replaceAll("[\",]", "").trim();
                    MESSAGE_LIST.get(counter).add(message);
                    counter++;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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


    public boolean validateAllEmail() {

        for (ArrayList<String> a : VICTIM_LIST) {

            if (!isEmailValid(a.get(0))) {
                System.out.println("Invalid email : " + a.get(0));
                return false;
            }
        }


        return true;
    }

}
