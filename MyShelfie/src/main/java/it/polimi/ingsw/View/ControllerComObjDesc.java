package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CommonObj;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControllerComObjDesc {

    @FXML
    private Label labelText;

    public void setLabelText(int numObj){

        CommonObj obj = new CommonObj(2,1);
        labelText.setText(obj.getDescription(numObj));
        labelText.setTextFill(Color.WHITE);
        labelText.setFont(Font.font(20));

    }

}
