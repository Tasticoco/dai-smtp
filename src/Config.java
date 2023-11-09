package src;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;


//Used this to help myself with the setup of the config file : https://stackoverflow.com/questions/16273174/how-to-read-a-configuration-file-in-java
public class Config {

    private ArrayList<String> victimsList;
    private ArrayList<String> messagesList;
    private int nbGroup;

    public Config(int nbGroup){
        Properties prop = new Properties();
        String fileName = ".\\smtpPrank.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (FileNotFoundException ex) {
            System.out.println("config file not found !");
        } catch (IOException ex) {

        }
        //For the array, in the config file, we'll use "#" to delimit the emails and the messages
        String[] email = prop.get("emails").toString().split("#");
        Collections.addAll(victimsList, email);

        String[] messages = prop.get("messages").toString().split("#");
        Collections.addAll(messagesList, messages);

        this.nbGroup = nbGroup;
    }


}
