package it.polimi.ingsw;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        try {
            Game game = new Game(2);
            GameLogic gamelogic = new GameLogic(game);
            game.getGameTable().checkStatus();
            game.getGameTable().viewTable();
            Player player1 = new Player("giovanni", null);
            player1.getLibrary().insert(gamelogic.getCardFromTable());
        }catch (Exception e){}

    }
}
