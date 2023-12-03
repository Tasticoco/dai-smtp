package ch.heig.dai.lab.smtp;

import java.util.ArrayList;
import java.util.Random;


public class GroupEmail {

    int nbGroup;

    //Each group will be an Arraylist of strings
    protected ArrayList<ArrayList<ArrayList<String>>> groups;

    /**
     * Create a GroupEmail object that create groups of the same number of randomly chosen victims
     *
     * @param nbGroup Number of group we want to create
     * @param confEmail The Config that allow us to read the list of victims
     */
    public GroupEmail(int nbGroup, Config confEmail) {
        this.nbGroup = nbGroup;
        groups = new ArrayList<>(nbGroup);
        ArrayList<ArrayList<String>> copyVictim = new ArrayList<>(confEmail.VICTIM_LIST);

        // Calculate the maximum number of people that can be assigned to each group
        int maxPeoplePerGroup = (int) Math.floor((double) confEmail.VICTIM_LIST.size() / nbGroup);

        // Make sure the number of people per group is between 2 and 5
        maxPeoplePerGroup = Math.max(2, Math.min(maxPeoplePerGroup, 5));

        // Assign people to groups
        Random rand = new Random();
        for (int j = 0; j < nbGroup; ++j) {
            int nbByGroup = rand.nextInt(2, maxPeoplePerGroup + 1);
            groups.add(new ArrayList<>());
            for (int i = 0; i < nbByGroup; i++) {
                int randomIndex = rand.nextInt(0, copyVictim.size() - 1);
                groups.get(j).add(copyVictim.get(randomIndex));
                copyVictim.remove(randomIndex);
            }
        }
    }

    /**
     * Get the group of the index we give
     *
     * @param indexGroup The number of the groups we want to get
     * @return null if the index given isn't valid, else the ArrayList of string representing the group
     */
     public ArrayList<ArrayList<String>> getGroup(int indexGroup){
        if(indexGroup >= groups.size())
            return null;
        else
            return groups.get(indexGroup);
     }

}
