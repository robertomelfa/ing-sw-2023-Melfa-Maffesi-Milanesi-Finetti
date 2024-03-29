package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ControllerLeaderboard implements Serializable {
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;

    /**
     * updates the leaderboard shown in the window using ScoreComparator to order the players list
     * @param players : the arrayList of player
     */
    public void updateLeaderboard(ArrayList<Player> players){

        if (players!=null){
            Collections.sort(players, new ScoreComparator());
            if (players.size()==2){
                label3.setVisible(false);
                label4.setVisible(false);
                player3.setVisible(false);
                player4.setVisible(false);
                player1.setText(players.get(0).getNickname()+" "+players.get(0).getScore()+" points");
                player2.setText(players.get(1).getNickname()+" "+players.get(1).getScore()+" points");
            } else if (players.size()==3) {
                label4.setVisible(false);
                player4.setVisible(false);
                player1.setText(players.get(0).getNickname()+" "+players.get(0).getScore()+" points");
                player2.setText(players.get(1).getNickname()+" "+players.get(1).getScore()+" points");
                player3.setText(players.get(2).getNickname()+" "+players.get(2).getScore()+" points");
            }else {
                player1.setText(players.get(0).getNickname()+" "+players.get(0).getScore()+" points");
                player2.setText(players.get(1).getNickname()+" "+players.get(1).getScore()+" points");
                player3.setText(players.get(2).getNickname()+" "+players.get(2).getScore()+" points");
                player4.setText(players.get(3).getNickname()+" "+players.get(3).getScore()+" points");
            }
        }
    }

    /**
     * Compare the score of the players. Used to sort the players list when updating the leaderboard
     */
    class ScoreComparator implements Comparator<Player>{

        @Override
        public int compare(Player p1, Player p2){
            if(p1.getScore() >= p2.getScore()){
                return -1;
            }
            else
            {
                return 1;
            }
        }
    }
}
