package main;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static main.Variable.*;

public class Reader {
    // http://api.pixelstarships.com/CharacterService/PrestigeCharacterFrom?characterDesignId=277
    // https://api.pixelstarships.com/CharacterService/ListAllCharacterDesigns2?languageKey=en

    // read all heroes
    public static List<Hero> newGetAllHeroes(IntegerProperty integerProperty) {
        // all heroes list
        List<Hero> heroes = new ArrayList<>();
        String inputLine;
        int localization = 55;

        try {
            integerProperty.setValue(5);

            URL oracle = new URL("https://api.pixelstarships.com/CharacterService/ListAllCharacterDesigns2?languageKey=en");
            BufferedReader in = new BufferedReader( new InputStreamReader(oracle.openStream()) );
            BufferedWriter bw = new BufferedWriter(new FileWriter("allheroes.txt"));

            integerProperty.setValue(10);

            while ( (inputLine = in.readLine()) != null ) {
                String[] array = inputLine.split("<CharacterDesign ");
                for ( String s : array ) {
                    if ( s.contains("CharacterDesignId=")) {
                        String[] cols = s.split("\"");
                        if ( cols[localization].equals("Legendary") || cols[localization].equals("Hero") || cols[localization].equals("Unique") || cols[localization].equals("Epic")) {
                            heroes.add(new Hero(Short.parseShort(cols[1]), cols[3], cols[localization]));
                            bw.write(cols[1] + "," + cols[3] + "," + cols[localization] + "\n");
                        }
                    }
                }
            }

            integerProperty.setValue(40);

            in.close();
            bw.close();

        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
        } catch ( IOException e) {
            e.getLocalizedMessage();
        }

        return heroes;
    }

    // read all prestige records for all heroes
    public static List<Prestige> newGetAllPrestige(List<Hero> heroes, IntegerProperty integerProperty ) {
        List<Prestige> prestiges = new ArrayList<>();
        String inputLine;
        BufferedWriter bw;
        int size = heroes.size();
        int counter = 0;

        for ( Hero h : heroes ) {
            if ( h.getHeroClass().equals("Legendary") || h.getHeroClass().equals("Hero") || h.getHeroClass().equals("Unique") || h.getHeroClass().equals("Epic")) {
                try {
                    URL oracle = new URL("http://api.pixelstarships.com/CharacterService/PrestigeCharacterFrom?characterDesignId=" + Integer.toString(h.getId()));

                    BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

                    counter++;
                    integerProperty.setValue(40 + 60*(int)counter/size);

                    while ((inputLine = in.readLine()) != null) {
                        String[] array = inputLine.split("<Prestige ");
                        for (String s : array) {
                            if (s.contains("CharacterDesignId1")) {
                                String[] col = s.split("\"");
                                prestiges.add(new Prestige(Short.parseShort(col[1]), Short.parseShort(col[3]), Short.parseShort(col[5])));
                            }
                        }
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.getLocalizedMessage();
                } catch (IOException e) {
                    e.getLocalizedMessage();
                }
            }
        }
        try {
            bw = new BufferedWriter(new FileWriter("prestige.txt"));
            for ( Prestige p : prestiges ) {
                bw.write(""+ p.getFirstHero() + "," + p.getSecondHero() + "," + p.getPrestigeHero() + "\n");
            }
            bw.close();
        } catch(IOException e) {
            e.getLocalizedMessage();
        }

        return prestiges;
    }

    public static boolean checkAllHeroesFile() {
        // read heroes from web site
        try {
            FileReader fr = new FileReader("allheroes.txt");
            fr.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean checkPrestigeFile() {
        // read prestige from web site
        try {
            FileReader fr = new FileReader("prestige.txt");
            fr.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static List<Hero> readAllHeroesFile() {
        List<Hero> heroList = new ArrayList<>();
        Boolean isFileEnd = false;
        String[] entry;

        try {
            BufferedReader br = new BufferedReader(new FileReader("allheroes.txt"));
            while ( !isFileEnd ) {
                String line = br.readLine();
                if ( line == null ) {
                    isFileEnd = true;
                } else {
                    entry = line.split(",");
                    heroList.add(new Hero(Short.parseShort(entry[0]), entry[1], entry[2]));
                }
            }
            br.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
        } catch ( IOException e) {
            e.getLocalizedMessage();
        } finally {
        }

        heroList.sort(new Comparator<Hero>() {
            @Override
            public int compare(Hero o1, Hero o2) {
                if ( o1.getName().compareTo(o2.getName()) > 0 ) {
                    return 1;
                } else if ( o1.getName().compareTo(o2.getName()) < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return heroList;
    }

    public static boolean saveLegendFile(List<Record> records ) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(LEGEND_FILE));
            for ( Record r : records ) {
                bw.write(r.getName()+","+r.getVolume()+"\n");
            }
            bw.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
            return false;
        } catch ( IOException e) {
            e.getLocalizedMessage();
            return false;
        }
        return true;
    }

    public static boolean saveHeroFile(List<Record> records ) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(HERO_FILE));
            for ( Record r : records ) {
                bw.write(r.getName()+","+r.getVolume()+"\n");
            }
            bw.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
            return false;
        } catch ( IOException e) {
            e.getLocalizedMessage();
            return false;
        }
        return true;
    }

    public static boolean saveInventoryFile(List<Record> records ) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE));
            for ( Record r : records ) {
                bw.write(r.getName()+","+r.getVolume()+"\n");
            }
            bw.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
            return false;
        } catch ( IOException e) {
            e.getLocalizedMessage();
            return false;
        }
        return true;
    }

    public static List<Hero> readInventoryFile(List<Hero> heroList ) {
        List<Hero> inventory = new ArrayList<>();
        Boolean isFileEnd = false;
        String[] entry;

        try {
            BufferedReader br = new BufferedReader(new FileReader("inventory.txt"));
            while ( !isFileEnd ) {
                String line = br.readLine();
                if ( line == null ) {
                    isFileEnd = true;
                } else {
                    entry = line.split(",");
                    if ( Integer.parseInt(entry[1]) > 0 ) {
                        inventory.add(new Hero(entry[0], Integer.parseInt(entry[1].trim())));
                    }
                }
            }
            br.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
        } catch ( IOException e) {
            e.getLocalizedMessage();
        }

        for ( Hero i : inventory ) {
            Boolean isFound = false;
            for (Hero h : heroList) {
                if (i.getName().equals(h.getName())) {
                    isFound = true;
                    i.setId(h.getId());
                    i.setHeroClass(h.getHeroClass());
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Inventory: Hero : " + i.getName() + " not found");
            }
        }

        return inventory;
    }

    public static List<Prestige> readPrestigeFile(List<Hero> heroList ){
        List<Prestige> prestigeList = new ArrayList<>();
        int numberErrors = 0;
        Boolean isFound = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader("prestige.txt"));
            Boolean isFileEnd = false;

            while ( !isFileEnd ) {
                String line = br.readLine();

                if ( line == null ) {
                    isFileEnd = true;
                } else {
                    String[] entry = line.split(",");

                    short firstHero = Short.parseShort(entry[0]);
                    short secondHero = Short.parseShort(entry[1]);
                    short prestigeHero = Short.parseShort(entry[2]);

                    isFound = false;
                    for ( Prestige p : prestigeList ) {
                        if ( p.getPrestigeHero() == prestigeHero && p.getFirstHero() == secondHero && p.getSecondHero() == firstHero ) {
                            isFound = true;
                        }
                    }
                    if ( !isFound ) {
                        prestigeList.add(new Prestige(firstHero, secondHero, prestigeHero));
                    }
                }
            }
            br.close();
        } catch ( FileNotFoundException e) {
            System.out.println("ERROR: File not found!!!");
            numberErrors++;
        } catch ( IOException e) {
            e.getLocalizedMessage();
        }
        if ( numberErrors > 0 ) {
            System.out.println("Errors during read Prestige list : " + numberErrors);
        }
        return prestigeList;
    }

    public static List<Hero> readLegendaryFile(List<Hero> heroList ) {
        List<Hero> legendary = new ArrayList<>();
        Boolean isFileEnd = false;
        String[] entry;

        try {
            BufferedReader br = new BufferedReader(new FileReader(LEGEND_FILE));
            while ( !isFileEnd ) {
                String line = br.readLine();
                if ( line == null ) {
                    isFileEnd = true;
                } else {
                    entry = line.split(",");
                    if ( Integer.parseInt(entry[1]) > 0 ) {
                        Hero hero = new Hero(entry[0], Integer.parseInt(entry[1].trim()));
                        for ( Hero h : heroList ) {
                            if ( h.getName().equals(hero.getName())) {
                                hero.setId(h.getId());
                                break;
                            }
                        }
                        legendary.add(hero);
                    }
                }
            }
            br.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
        } catch ( IOException e) {
            e.getLocalizedMessage();
        }
        return legendary;
    }

    public static List<Hero> readHeroFile(List<Hero> heroList ) {
        List<Hero> heroes = new ArrayList<>();
        Boolean isFileEnd = false;
        String[] entry;

        try {
            BufferedReader br = new BufferedReader(new FileReader(HERO_FILE));
            while ( !isFileEnd ) {
                String line = br.readLine();
                if ( line == null ) {
                    isFileEnd = true;
                } else {
                    entry = line.split(",");
                    if ( Integer.parseInt(entry[1]) > 0 ) {
                        Hero hero = new Hero(entry[0], Integer.parseInt(entry[1].trim()));
                        for ( Hero h : heroList ) {
                            if ( h.getName().equals(hero.getName())) {
                                hero.setId(h.getId());
                                break;
                            }
                        }
                        heroes.add(hero);
                    }
                }
            }
            br.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
        } catch ( IOException e) {
            e.getLocalizedMessage();
        }

        return heroes;
    }

    public static boolean recreateFiles(List<Hero> heroList){
        // create Legend file
        try{
            FileReader fr = new FileReader("legend.txt");
            fr.close();
        } catch(FileNotFoundException e ) {
            try {
                FileWriter fw = new FileWriter("legend.txt");
                for ( Hero h : heroList ) {
                    if ( h.getHeroClass().equals("Legendary")) {
                        fw.write(h.getName() + ",0\n");
                    }
                }
                fw.close();
            } catch (IOException ex){
                return false;
            }
        } catch (IOException e) {
            try {
                FileWriter fw = new FileWriter("legend.txt");
                for ( Hero h : heroList ) {
                    if ( h.getHeroClass().equals("legendary")) {
                        fw.write(h.getName() + ",0\n");
                    }
                }
                fw.close();
            } catch (IOException ex){
                return false;
            }
        }

        // create Hero file
        try{
            FileReader fr = new FileReader("hero.txt");
            fr.close();
        } catch(IOException e ) {
            try {
                FileWriter fw = new FileWriter("hero.txt");
                for ( Hero h : heroList ) {
                    if ( h.getHeroClass().equals("Hero")) {
                        fw.write(h.getName() + ",0\n");
                    }
                }
                fw.close();
            } catch (IOException ex){
                return false;
            }
        }

        // create Inventory file
        try{
            FileReader fr = new FileReader("inventory.txt");
            fr.close();
        } catch(FileNotFoundException e ) {
            try {
                FileWriter fw = new FileWriter("inventory.txt");
                for ( Hero h : heroList ) {
                    if ( h.getHeroClass().equals("Hero") || h.getHeroClass().equals("Epic") || h.getHeroClass().equals("Unique")) {
                        fw.write(h.getName() + ",0\n");
                    }
                }
                fw.close();
            } catch (IOException ex){
                return false;
            }
        } catch (IOException e) {
            try {
                FileWriter fw = new FileWriter("inventory.txt");
                for ( Hero h : heroList ) {
                    if ( h.getHeroClass().equals("hero") || h.getHeroClass().equals("epic") || h.getHeroClass().equals("unique")) {
                        fw.write(h.getName() + ",0\n");
                    }
                }
                fw.close();
            } catch (IOException ex){
                return false;
            }
        }

        return true;
    }

}
