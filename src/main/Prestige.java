package main;

public class Prestige {
    private short firstHero;
    private short secondHero;
    private short prestigeHero;

    public Prestige(short firstHero, short secondHero, short prestigeHero) {
        this.firstHero = firstHero;
        this.secondHero = secondHero;
        this.prestigeHero = prestigeHero;
    }

    public short getFirstHero() {
        return firstHero;
    }

    public void setFirstHero(short firstHero) {
        this.firstHero = firstHero;
    }

    public short getSecondHero() {
        return secondHero;
    }

    public void setSecondHero(short secondHero) {
        this.secondHero = secondHero;
    }

    public short getPrestigeHero() {
        return prestigeHero;
    }

    public void setPrestigeHero(short prestigeHero) {
        this.prestigeHero = prestigeHero;
    }

    @Override
    public String toString() {
        return "Prestige{" +
                "firstHero=" + firstHero +
                ", secondHero=" + secondHero +
                ", prestigeHero=" + prestigeHero +
                '}';
    }
}
