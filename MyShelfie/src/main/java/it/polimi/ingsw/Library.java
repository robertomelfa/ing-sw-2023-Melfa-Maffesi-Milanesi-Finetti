package it.polimi.ingsw;

public class Library {
    private Card[][] grid = new Card[6][5];
    private boolean full;

    /**
     * Constructor for the Library class.
     * It sets all elements in grid to NONE
     */
    public Library(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                grid[i][j]=Card.NONE;
            }
        }
        full=false;
    }

    /**
     *
     * @param x
     * @param y
     * @return the Card in position [x][y] in grid
     */
    public Card getPos(int x, int y){
        return grid[x][y];
    }

    /**
     * This method prints the player's library
     */
    public void viewGrid(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                System.out.print(getPos(i,j).toString()+"   ");
            }
            System.out.print("\n");
        }
    }
}
