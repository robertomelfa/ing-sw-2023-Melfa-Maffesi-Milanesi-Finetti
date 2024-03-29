package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CommonObj;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.Serializable;

public class ControllerComObjDesc implements Serializable {

    @FXML
    private Label labelText;

    /**
     * sets the description of the common object shown in the GUI
     * @param numObj : the number of the common object
     */
    public void setLabelText(int numObj){

        CommonObj obj = new CommonObj(2,1);
        labelText.setText(obj.getDescription(numObj));
        labelText.setTextFill(Color.WHITE);
        labelText.setFont(Font.font(20));

    }

}
