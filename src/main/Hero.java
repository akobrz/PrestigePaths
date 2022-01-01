package main;

public class Hero {
    private short id;
    private String name;
    private String heroClass;
    private String fileName;
    private int inventory = 0;

    public Hero(String name, String heroClass, String fileName) {
        this.name = name;
        this.heroClass = heroClass;
        this.fileName = fileName;
    }

    public Hero(String name, int inventory) {
        this.name = name;
        this.inventory = inventory;
        this.heroClass = "unknown";
    }

    public Hero(short id, String name, String heroClass) {
        this.id = id;
        this.name = name;
        this.heroClass = heroClass;
    }

    public Hero(short id, String name, String heroClass, int inventory ) {
        this.id = id;
        this.name = name;
        this.heroClass = heroClass;
        this.inventory = inventory;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", heroClass='" + heroClass + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
