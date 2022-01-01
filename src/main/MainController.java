package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static main.Reader.*;
import static main.Variable.*;

public class MainController implements Initializable {
    static ReloadController reloadController;
    static InventoryController inventoryController;
    static LegendaryController legendaryController;
    static HeroicController heroicController;
    static PrestigeController prestigeController;
    static AboutController aboutController;
    static List<Hero> allheroes = new ArrayList<>();
    static List<Prestige> allprestige = new ArrayList<>();
    static List<Hero> inventory = new ArrayList<>();
    static List<Hero> legend =  new ArrayList<>();
    static List<Hero> hero = new ArrayList<>();
    static String resultValue = "";
    static List<Hero> legendCombo = new ArrayList<>();
    static List<Hero> heroicCombo = new ArrayList<>();
    static List<String> ClassCombo = Arrays.asList("Hero","Epic", "Unique");
    static List<Hero> InventoryCombo = new ArrayList<>();

    ObservableList<Hero> inventoryCollection = FXCollections.observableArrayList();
    ObservableList<Hero> legendCollection = FXCollections.observableArrayList();

    @FXML
    private Button AboutButton;

    @FXML
    private Button ReloadButton;

    @FXML
    private Button InventoryButton;

    @FXML
    private Button LegendsButton;

    @FXML
    private Button HeroesButton;

    @FXML
    private Button QuitButton;

    @FXML
    void AboutAction(ActionEvent event) {
        menu_disabler(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/info.fxml"));
        try {
            Parent parent = loader.load();
            aboutController = loader.getController();
            Scene scene = new Scene(parent, 300, 155);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("About");
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu_disabler(false);
    }

    @FXML
    void InventoryButtonClicked(ActionEvent event) {
        menu_disabler(true);
        InventoryCombo.clear();
        for ( Hero h : allheroes ) {
            if ( h.getHeroClass().equals(HERO_RANK) || h.getHeroClass().equals(EPIC_RANK) || h.getHeroClass().equals(UNIQUE_RANK) ) {
                InventoryCombo.add(h);
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(INVENTORY_FXML));
        try {
            Parent parent = loader.load();
            inventoryController = loader.getController();
            Scene scene = new Scene(parent, 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Inventory");
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu_disabler(false);
    }

    @FXML
    void LegendsButtonClicked(ActionEvent event) {
        menu_disabler(true);
        legendCombo.clear();
        for ( Hero h : allheroes ) {
            if ( h.getHeroClass().equals(LEGEND_RANK)) {
                legendCombo.add(h);
            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/legendary.fxml"));
        try {
            Parent parent = loader.load();
            legendaryController = loader.getController();
            Scene scene = new Scene(parent, 900, 450);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Legends to Prestige");
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu_disabler(false);
    }

    @FXML
    void HeroesButtonClicked(ActionEvent event) {
        menu_disabler(true);
        heroicCombo.clear();
        for ( Hero h : allheroes ) {
            if ( h.getHeroClass().equals(HERO_RANK)) {
                heroicCombo.add(h);
            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/heroic.fxml"));
        try {
            Parent parent = loader.load();
            heroicController = loader.getController();
            Scene scene = new Scene(parent, 900, 450);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Heroes to Prestige");
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu_disabler(false);
    }

    @FXML
    void QuitButtonClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void ReloadButtonClicked(ActionEvent event) {
        menu_disabler(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/reload.fxml"));
        try {
            Parent parent = loader.load();
            reloadController = loader.getController();
            Scene scene = new Scene(parent, 300, 160);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Reload Data");
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menu_disabler(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (checkAllHeroesFile() && checkPrestigeFile() ) {
            allheroes = readAllHeroesFile();
            allprestige = readPrestigeFile(allheroes);
            if ( recreateFiles(allheroes) ) {
                inventory = readInventoryFile(allheroes);
                legend = readLegendaryFile(allheroes);
                hero = readHeroFile(allheroes);
            }
        } else {
            set_menu_reload();
        }
    }

    public void menu_disabler(boolean status){
        AboutButton.setDisable(status);
        ReloadButton.setDisable(status);
        InventoryButton.setDisable(status);
        LegendsButton.setDisable(status);
        HeroesButton.setDisable(status);
        QuitButton.setDisable(status);
    }

    public void set_menu_reload(){
        AboutButton.setDisable(false);
        ReloadButton.setDisable(false);
        InventoryButton.setDisable(true);
        LegendsButton.setDisable(true);
        HeroesButton.setDisable(true);
        QuitButton.setDisable(true);
    }

}
