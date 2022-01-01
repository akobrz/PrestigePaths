package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    @FXML
    private TextArea resultsText;

    @FXML
    private Button resultsOK;

    @FXML
    void resultsAction(ActionEvent event) {
        Stage stage = (Stage) resultsOK.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        PrestigeController.stage2.close();
        resultsText.setText(MainController.resultValue);
    }
}
