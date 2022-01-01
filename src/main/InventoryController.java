package main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static main.MainController.*;
import static main.Reader.*;
import static main.Variable.*;

public class InventoryController implements Initializable {
    private final Record currentRecord = new Record();
    private final ObservableList<Record> records = FXCollections.observableArrayList();
    private final HashMap<Integer, Record> recordsMap = new HashMap<>();

    @FXML
    private TableView<Record> recordsList;

    @FXML
    private TableColumn<Record, String> RankColumn;

    @FXML
    private TableColumn<Record, String> NameColumn;

    @FXML
    private TableColumn<Record, Integer> VolumeColumn;

    @FXML
    private ComboBox<String> RankCombo;

    @FXML
    private ComboBox<String> NameCombo;

    @FXML
    private Spinner<Integer> SpinnerVolume;

    @FXML
    private Button AddButton;

    @FXML
    private Button CancelButton;

    @FXML
    private Button DeleteButton;

    @FXML
    void AddAction(ActionEvent event) {
        boolean isOnList = false;
        for ( Hero h: allheroes ) {
            if (h.getName().equals(currentRecord.getName())) {
                currentRecord.setId(h.getId());
                currentRecord.setRank(h.getHeroClass());
                break;
            }
        }
        for ( Record r: records ) {
            if ( r.getId().equals(currentRecord.getId()) ) {
                r.setVolume(currentRecord.getVolume());
                isOnList = true;
                break;
            }
        }
        if ( !isOnList ) {
            for ( Hero h: allheroes ) {
                if (h.getName().equals(currentRecord.getName())) {
                    currentRecord.setId(h.getId());
                    currentRecord.setRank(h.getHeroClass());
                    break;
                }
            }
            records.add(new Record(currentRecord.getId(), currentRecord.getRank(), currentRecord.getName(), currentRecord.getVolume()));
        } else {
            for ( Record r: records) {
                if ( r.getId().equals(currentRecord.getId()) ) {
                    r.setVolume(currentRecord.getVolume());
                    r.setRank(currentRecord.getRank());
                    break;
                }
            }
        }
        recordsList.setItems(records);
    }

    @FXML
    void CancelAction(ActionEvent event) {
        if ( saveInventoryFile(records) ) {
            inventory = readInventoryFile(allheroes);
        }
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void DeleteAction(ActionEvent event) {
        int recordToDelete = 0;

        for (int i = 0; i < records.size(); i++ ) {
            if (currentRecord.getName().equals(records.get(i).getName())) {
                recordToDelete = i;
                break;
            }
        }

        records.remove(recordToDelete);
        recordsList.setItems(records);

        if ( records.size() > 0 ) {
            setCurrentRecord(records.get(0));
            recordsList.getSelectionModel().select(0);
            recordsList.getSelectionModel().focus(0);
        } else {
            setCurrentRecord(null);
            DeleteButton.setDisable(true);
            AddButton.setDisable(false);
            AddButton.setText(ADD_BUTTON);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Hero h : inventory ){
            records.add(new Record(h.getId(), h.getHeroClass(), h.getName(), h.getInventory()));
        }

        setCurrentRecord(null);

        RankCombo.getItems().addAll(ClassCombo);
        NameCombo.getItems().clear();

        for ( Hero h : InventoryCombo ) {
            if ( h.getHeroClass().equals(currentRecord.getRank())) {
                NameCombo.getItems().add(h.getName());
            }
        }

        RankCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String newClass = "";
                String newName = "";
                int newId = 0;
                NameCombo.getItems().clear();
                for ( Hero h : InventoryCombo ) {
                    if ( h.getHeroClass().equals(newValue)) {
                        NameCombo.getItems().add(h.getName());
                    }
                }
                for ( Hero h : InventoryCombo) {
                    if ( h.getHeroClass().equals(newValue)) {
                        newClass = h.getHeroClass();
                        newName = h.getName();
                        newId = h.getId();
                        break;
                    }
                }
                currentRecord.idProperty().setValue(newId);
                currentRecord.rankProperty().setValue(newClass);
                currentRecord.setName(newName);
            }
        });

        RankCombo.valueProperty().bindBidirectional(currentRecord.rankProperty());

        NameCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean isFound = false;
                DeleteButton.setDisable(true);
                AddButton.setDisable(false);
                SpinnerVolume.setDisable(false);
                for ( Record r: records ) {
                    if ( r.getName().equals(newValue)) {
                        isFound = true;
                    }
                }
                if ( isFound ) {
                    AddButton.setText("Update");
                } else {
                    AddButton.setText("Add");
                }
                if (NameCombo.valueProperty() != null ) {
                    AddButton.setDisable(false);
                } else {
                    AddButton.setDisable(true);
                }
            }
        });

        NameCombo.valueProperty().bindBidirectional(currentRecord.nameProperty());
        SpinnerVolume.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        SpinnerVolume.getValueFactory().valueProperty().bindBidirectional(currentRecord.volumeProperty());
        SpinnerVolume.getValueFactory().valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                boolean isFound = false;
                DeleteButton.setDisable(true);
                AddButton.setDisable(false);
                for ( Record r: records ) {
                    if ( r.getName().equals(currentRecord.getName())) {
                        isFound = true;
                    }
                }
                if ( isFound ) {
                    AddButton.setText("Update");
                } else {
                    AddButton.setText("Add");
                }
            }
        });

        recordsList.setItems(records);
        recordsList.getSelectionModel().focus(0);

        RankColumn.setCellValueFactory(rowData -> rowData.getValue().rankProperty());
        NameColumn.setCellValueFactory(rowData -> rowData.getValue().nameProperty());
        VolumeColumn.setCellValueFactory(rowData -> rowData.getValue().volumeProperty());

        recordsList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Record> observable, Record oldValue, Record newValue) -> {
            setCurrentRecord(newValue);
            DeleteButton.setDisable(false);
            AddButton.setText("Update");
            AddButton.setDisable(true);
        });

        setCurrentRecord(null);

        AddButton.setDisable(true);
        DeleteButton.setDisable(false);
    }

    private void setCurrentRecord(Record selectedRecord){
        if ( selectedRecord != null ) {
            for ( Hero h: allheroes ) {
                if ( selectedRecord.getName().equals(h.getName())) {
                    currentRecord.setId(h.getId());
                    currentRecord.setRank(selectedRecord.getRank());
                    currentRecord.setName(selectedRecord.getName());
                    currentRecord.setVolume(selectedRecord.getVolume());
                    break;
                }
            }
        } else {
            if ( records.size() > 0 ) {
                currentRecord.setId(records.get(0).getId());
                currentRecord.setName(records.get(0).getName());
                currentRecord.setRank(records.get(0).getRank());
                currentRecord.setVolume(records.get(0).getVolume());
            } else {
                currentRecord.setId(InventoryCombo.get(0).getId());
                currentRecord.setRank(InventoryCombo.get(0).getHeroClass());
                currentRecord.setName(InventoryCombo.get(0).getName());
                currentRecord.setVolume(1);
            }
        }
    }

}
