package src;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Used this to help myself with the setup of the config file : https://stackoverflow.com/questions/16273174/how-to-read-a-configuration-file-in-java
public class Config {

    private ArrayList<String> victimsList = new ArrayList<>();
    private ArrayList<String> messagesList = new ArrayList<>();
    private int nbGroup;

    public Config(int nbGroup){

        Properties prop = new Properties();
        String fileName = ".\\smtpPrank.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (FileNotFoundException ex) {
            System.out.println("Config file not found !");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        //For the array, in the config file, we'll use "#" to delimit the emails and the messages
        String[] email = prop.get("emails").toString().split("#");
        if(email[0] != null){
            Collections.addAll(victimsList, email);
        }


        try{
            if(!validateAllEmail()){
                throw new RuntimeException("One of the email in the list is invalid !");
            }
        }catch (RuntimeException e){
            System.err.println(e.getMessage());
        }

        String[] messages = prop.get("messages").toString().split("#");
        Collections.addAll(messagesList, messages);

        this.nbGroup = nbGroup;
    }

    public boolean isEmailValid(String email){

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean validateAllEmail(){

        for(String s : victimsList) {
            if(!isEmailValid(s)) return false;
        }
        return true;
    }

}
