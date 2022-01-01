package main;

import javafx.beans.property.*;

public class Record {
    private final ObjectProperty<Integer> id = new SimpleObjectProperty(0);
    private final StringProperty rank = new SimpleStringProperty("");
    private final StringProperty name = new SimpleStringProperty("");
    private final ObjectProperty<Integer> volume = new SimpleObjectProperty(0);

    public Record() {
    }

    public Record(int id, String rank, String name, int volume){
        this.id.setValue(id);
        this.rank.setValue(rank);
        this.name.setValue(name);
        this.volume.setValue(volume);
    }

    public Integer getId() {
        return id.get();
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getRank() {
        return rank.get();
    }

    public StringProperty rankProperty() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank.set(rank);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getVolume() {
        return volume.get();
    }

    public ObjectProperty<Integer> volumeProperty() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume.set(volume);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", rank=" + rank +
                ", name=" + name +
                ", volume=" + volume +
                '}';
    }
}
