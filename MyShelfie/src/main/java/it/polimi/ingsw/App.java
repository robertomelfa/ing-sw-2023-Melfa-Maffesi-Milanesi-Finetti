package it.polimi.ingsw;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Game game = new Game();
        GameLogic gamelogic = new GameLogic(game);
        game.getGameTable().viewTable();
        Player player1 = new Player("giovanni", null);
        player1.getLibrary().insert(gamelogic.getCardFromTable());
    }
}
