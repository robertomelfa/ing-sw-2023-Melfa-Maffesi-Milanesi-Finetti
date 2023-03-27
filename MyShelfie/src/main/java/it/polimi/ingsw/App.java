package it.polimi.ingsw;

public class App 
{
    public static void main( String[] args ) throws Exception {
        Game game = new Game(3);
        GameLogic gamelogic = new GameLogic(game);
        Player player1 = new Player("giovanni", null);
        Player player2 = new Player("luca", null);
        Player player3 = new Player("marco", null);

        game.addNewPlayer(player1);
        game.addNewPlayer(player2);
        game.addNewPlayer(player3);

        game.setChairOrder();

        while(true){
            gamelogic.startTurn();
        }

    }
}
