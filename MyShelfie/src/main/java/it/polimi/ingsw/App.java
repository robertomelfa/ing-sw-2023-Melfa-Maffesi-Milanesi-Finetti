package it.polimi.ingsw;


// TODO fare Test CommonObj : Melfa
// TODO fare Test GameLogic
// TODO fare Test CardBox
// TODO fare Test GameTable

// TODO ModelViewController
//  -mettere checkNear su client (getCardFromTable)
//  -controller startTurn e Game (in parte)

// TODO consiglio Patrick package, view - model -controller - network (client/server)

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
    }
}
