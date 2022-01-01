package main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.MainController.allheroes;
import static main.MainController.allprestige;
import static main.Tools.calculateLegendaryPaths;

public class PrestigeController implements Initializable {
    static ResultsController resultsController;
    static IntegerProperty integerProperty = new SimpleIntegerProperty(0);
    static BooleanProperty booleanProperty = new SimpleBooleanProperty(true);
    static Stage stage2;

    @FXML
    private Label prestigeText;

    @FXML
    private ProgressBar prestigeProgressBar;

    @FXML
    private Button prestigeOK;

    @FXML
    void prestigeOK(ActionEvent event) {
        booleanProperty.setValue(true);

        Stage stage2 = (Stage) prestigeOK.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/results.fxml"));
        try {
            Parent parent = loader.load();
            resultsController = loader.getController();
            Scene scene = new Scene(parent, 800, 830);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Results");
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
//            stage2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        {
            Stage stage = (Stage) prestigeOK.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prestigeProgressBar.progressProperty().bind(integerProperty.divide(100.0));
        prestigeOK.setDisable(booleanProperty.getValue());
        prestigeOK.disableProperty().bind(booleanProperty);
        new Thread(() -> {
            calculateLegendaryPaths(integerProperty);
            booleanProperty.setValue(false);
//            prestigeOK.disableProperty().unbind();
        }).start();
    }
}
