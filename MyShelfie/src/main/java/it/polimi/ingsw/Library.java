package it.polimi.ingsw;

public class Library {
    private Card[][] grid = new Card[6][5];
    private boolean full;

    public Library(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                grid[i][j]=Card.NONE;
            }
        }
        full=false;
    }
    public Card getPos(int x, int y){
        return grid[x][y];
    }

    public void viewGrid(){
        for (int i=0;i<6;i++){
            for (int j=0;j<5;j++){
                System.out.print(getPos(i,j).toString()+"   ");
            }
            System.out.print("\n");
        }
    }
}
