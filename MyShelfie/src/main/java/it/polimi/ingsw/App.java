package it.polimi.ingsw;

public class App 
{
    public static void main( String[] args ) throws Exception {
        Game game = new Game(2);
        GameLogic gamelogic = new GameLogic(game);
        Player player1 = new Player("giovanni");
        Player player2 = new Player("luca");

        game.addNewPlayer(player1);
        game.addNewPlayer(player2);

        game.setChairOrder();

        while(true){
            gamelogic.startTurn();
        }


     // at the end of the game:
     //   game.checkEnd();
    }
}
