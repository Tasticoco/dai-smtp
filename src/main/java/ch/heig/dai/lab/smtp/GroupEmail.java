package ch.heig.dai.lab.smtp;

import java.util.ArrayList;
import java.util.Random;


public class GroupEmail {

    int nbGroup;

    //Each group will be an Arraylist of strings
    protected ArrayList<ArrayList<String>> groups = new ArrayList<>(nbGroup);

    /**
     * Create a GroupEmail object that create groups of the same number of randomly chosen victims
     *
     * @param nbGroup Number of group we want to create
     * @param confEmail The Config that allow us to read the list of victims
     */
    public GroupEmail(int nbGroup, Config confEmail){
        this.nbGroup = nbGroup;
        ArrayList<String> copyVictim = new ArrayList<>(confEmail.victimsList);

        int nbByGroup = copyVictim.size() / nbGroup;

        Random rand = new Random();

        for(int j = 0; j < nbGroup - 1; ++j) {
            for (int i = 0; i < nbByGroup; i++) {
                int randomIndex = rand.nextInt(copyVictim.size());
                groups.get(j).add(copyVictim.get(randomIndex));
                copyVictim.remove(randomIndex);
            }
        }
        groups.getLast().addAll(copyVictim);
    }

    /**
     * Get the group of the index we give
     *
     * @param indexGroup The number of the groups we want to get
     * @return null if the index given isn't valid, else the ArrayList of string representing the group
     */
     public ArrayList<String> getGroup(int indexGroup){
        if(indexGroup >= groups.size())
            return null;
        else
            return groups.get(indexGroup);
     }

}
