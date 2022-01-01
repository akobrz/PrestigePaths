package main;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static main.MainController.allheroes;
import static main.MainController.allprestige;
import static main.MainController.inventory;
import static main.MainController.legend;
import static main.MainController.hero;
import static main.Reader.*;

public class ReloadController implements Initializable {
    Reader reader = new Reader();

    @FXML
    private Label reloadingText;

    @FXML
    private ProgressBar reloadingProgressBar;

    @FXML
    private Button reloadingOK;

    @FXML
    void reloadingOK(ActionEvent event) {
        if (checkAllHeroesFile() && checkPrestigeFile() ) {
            allheroes = readAllHeroesFile();
            allprestige = Reader.readPrestigeFile(allheroes);
            main.Reader.recreateFiles(allheroes);
            inventory = readInventoryFile(allheroes);
            legend = readLegendaryFile(allheroes);
            hero = readHeroFile(allheroes);

        }
        Stage stage = (Stage) reloadingOK.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IntegerProperty integerProperty = new SimpleIntegerProperty(0);
        BooleanProperty booleanProperty = new SimpleBooleanProperty(true);
        reloadingProgressBar.progressProperty().bind(integerProperty.divide(100.0));
        reloadingOK.setDisable(booleanProperty.getValue());
        reloadingOK.disableProperty().bind(booleanProperty);

        new Thread(() -> {
            allheroes = newGetAllHeroes(integerProperty);
            allprestige = newGetAllPrestige(allheroes, integerProperty);
            booleanProperty.setValue(false);
        }).start();
    }

}
