package main;

import javafx.fxml.*;
import javafx.event.*;

public class Controller {

    @FXML
    private void startClicked(ActionEvent event) {
        // Button was clicked, do something...

        System.out.println("CLICKED!");
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something...
//        outputTextArea.appendText("Button Action\n");
    }

//    public void startClicked(){
//        System.out.println("CLICKED!");
//    }
}
