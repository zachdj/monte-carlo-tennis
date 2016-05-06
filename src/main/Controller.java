package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

public class Controller implements Initializable {

    @FXML public Button startButton;
    @FXML public ProgressBar progressBar;
    @FXML public TextArea outputText;
    @FXML public Slider trialsSlider;
    @FXML public TextField trialsCounter;

    @FXML public RadioButton singleSetOption;
    @FXML public RadioButton threeSetOption;
    @FXML public RadioButton fiveSetOption;

    @FXML public ChoiceBox<String> player1ChoiceBox;
    @FXML public ChoiceBox<String> player2ChoiceBox;

    @FXML public CheckBox verboseModeCB;
    @FXML public CheckBox printMatchScoresCB;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'main.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'main.fxml'.";
        assert outputText != null : "fx:id=\"outputText\" was not injected: check your FXML file 'main.fxml'.";
        assert trialsSlider != null : "fx:id=\"trialsSlider\" was not injected: check your FXML file 'main.fxml'.";
        assert trialsCounter != null : "fx:id=\"trialsCounter\" was not injected: check your FXML file 'main.fxml'.";
        assert singleSetOption != null : "fx:id=\"singleSetOption\" was not injected: check your FXML file 'main.fxml'.";
        assert threeSetOption != null : "fx:id=\"threeSetOption\" was not injected: check your FXML file 'main.fxml'.";
        assert fiveSetOption != null : "fx:id=\"fiveSetOption\" was not injected: check your FXML file 'main.fxml'.";
        assert player1ChoiceBox != null : "fx:id=\"player1ChoiceBox\" was not injected: check your FXML file 'main.fxml'.";
        assert player2ChoiceBox != null : "fx:id=\"player2ChoiceBox\" was not injected: check your FXML file 'main.fxml'.";
        assert verboseModeCB != null : "fx:id=\"verboseModeCB\" was not injected: check your FXML file 'main.fxml'.";
        assert printMatchScoresCB != null : "fx:id=\"printSetScoresCB\" was not injected: check your FXML file 'main.fxml'.";


        ArrayList<Player> players = Settings.getPreloadedPlayers();
        ObservableList<String> playerNameList = FXCollections.observableArrayList();
        for(int i = 0; i < players.size(); i++){
            playerNameList.add(players.get(i).getName());
        }
        player1ChoiceBox.setItems(playerNameList);
        player2ChoiceBox.setItems(playerNameList);
        player1ChoiceBox.setValue(players.get(0).getName());
        player2ChoiceBox.setValue(players.get(1).getName());

        trialsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                trialsCounter.setText(new_val.intValue() + "");
                trialsSlider.setValue(new_val.intValue());
            }
        });
        trialsSlider.setShowTickLabels(true);
        trialsSlider.setShowTickMarks(true);
//        trialsSlider.setSnapToTicks(true);
//        trialsSlider.snapToTicksProperty().set(true);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //Start button was clicked
                startClicked(e);
            }
        });
    }


    @FXML
    private void startClicked(ActionEvent event) {
        progressBar.setProgress(0.0);
        progressBar.progressProperty().set(0.0);
        outputText.setText("");

        new Thread(){
            public void run(){
                int trials = 1;
                try{
                    trials = Integer.parseInt(trialsCounter.getText());
                } catch(Exception ex){
                    outputText.setText("If you're seeing this, it means JavaFX broke.  It's not my fault!!");
                }

                int numSets = 1;
                if(threeSetOption.isSelected()){
                    numSets = 3;
                } else if(fiveSetOption.isSelected()){
                    numSets = 5;
                }

                boolean verbose = verboseModeCB.isSelected();
                boolean printMatchScores = printMatchScoresCB.isSelected();

                //get player1
                ArrayList<Player> players = Settings.getPreloadedPlayers();
                Player p1 = null;  Player p2 = null;
                for(Player p : players){
                    if(p.getName().equalsIgnoreCase(player1ChoiceBox.getValue())){
                        p1 = p;
                    }
                    if(p.getName().equalsIgnoreCase(player2ChoiceBox.getValue())){
                        p2 = p;
                    }
                }
                if(p1==null || p2==null){
                    outputText.setText("Uh-oh!  One or more of the players could not be found!\n");
                } else{
                    float p1Wins = 0;
                    float p2Wins = 0;

                    for(int i = 0; i < trials; i++){
                        //update the progress bar
                        final double progress = (i+1.0)/trials;
                        Platform.runLater(() -> progressBar.setProgress(progress));

                        //setup a match between these players:
                        Match match = new Match(p1, p2, numSets);
                        match.playMatch(verbose);
                        if(match.didPLayer1Win()){
                            if(printMatchScores){
                                final String text = p1.getName() + " wins!\n";
                                Platform.runLater(() -> outputText.appendText(text));
                            }
                            p1Wins++;
                        } else{
                            if(printMatchScores){
                                final String text = p2.getName() + " wins!\n";
                                Platform.runLater(() -> outputText.appendText(text));
                            }
                            p2Wins++;
                        }

                        if(printMatchScores){
                            final String text = match.score.toString() + "\n";
                            Platform.runLater(() -> outputText.appendText(text));
                        }
                        try{
                            Thread.sleep(5);
                        } catch(InterruptedException ex){
                            System.out.println("Thread sleep was interrupted!  How rude.");
                        }
                    }

                    //print the final results:
                    final String text1 = "Player 1 Wins " + (p1Wins/ trials)*100 + "% of the matches\n";
                    Platform.runLater(() -> outputText.appendText(text1));
                    final String text2 = "Player 2 Wins " + (p2Wins/ trials)*100 + "% of the matches\n";
                    Platform.runLater(() -> outputText.appendText(text2));

                }
            }
        }.start();

    }

}
