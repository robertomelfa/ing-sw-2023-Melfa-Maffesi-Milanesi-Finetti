package it.polimi.ingsw;

import java.util.List;


public class PlayerObj {
    private List<SingleObj> playerObjs ;
    private final int[] POINT = {12,9,6,4,2,1};
    private int i;

    /**
     *
     * @param library, library to check
     * @return POINT, differenze between the points reached and the points already taken
     * Method that check the status of the personal goal and return the points
     */
    public int checkObj(Library library) throws Exception{

        //contatore delle caselle obiettivo completate
        int count = 0;

        //per ogni casella obiettivo controllo che nella stessa posizione della library ci sia la stessa carta e aggiorno il contatore
        for (SingleObj singleObj : playerObjs) {
            if (library.getPos(singleObj.x, singleObj.y) == singleObj.type) count++;

        }

        if (count > i) {

            this.i = count;

            return POINT[count]-POINT[i];

        }else return 0;
    }

    /**
     * Constructor for the PlayerObj class: it makes the personal goal with a list of single goals
     * with every single goal set on a different line
     */
    public PlayerObj(){
        for(int i=0; i < 6; i++){
            assert false;                               //lo ha messo java per completezza
            playerObjs.add(new SingleObj(i));

        }
    }
}