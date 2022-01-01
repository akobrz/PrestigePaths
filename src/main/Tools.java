package main;

import javafx.beans.property.IntegerProperty;

import java.io.*;
import java.util.*;

import static main.MainController.*;
import static main.Variable.*;

public class Tools {

    public static void calculateLegendaryPaths(IntegerProperty integerProperty) {
        integerProperty.setValue(0);
        int legendsToBuid = 0;
        double percentageBuild = 0.0;

        List<Prestige> lP = divPrestige(allprestige, allheroes, "Legendary");
        List<Prestige> hP = divPrestige(allprestige, allheroes, "Hero");
        List<Prestige> eP = divPrestige(allprestige, allheroes, "Epic");

        // duplicate inventory
        List<Hero> inv = new ArrayList<>();
        for (Hero h : inventory) {
            inv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }

        for ( Hero h: legend ) {
            legendsToBuid += h.getInventory();
        }

        integerProperty.setValue(0);

        List<Tree> resultList = new ArrayList<>();
        List<Tree> resultList2 = new ArrayList<>();

        // Round 1 - find all Hero and Epic combinations
        for ( Hero l : legend ) {
            for ( int i = 0; i < l.getInventory(); i++ ) {

                int inv_count = 0;
                for ( Hero h : inv ) {
                    inv_count += h.getInventory();
                }

                // Build small Epic Prestige List
                List<Prestige> smallEP = new ArrayList<>();

                for (Prestige p : eP) {
                    boolean first_in_inventory = false;
                    boolean second_in_inventory = false;

                    for (Hero h : inv) {
                        if (p.getFirstHero() == h.getId()) {
                            first_in_inventory = true;
                        }
                        if (p.getSecondHero() == h.getId()) {
                            second_in_inventory = true;
                        }
                        if (first_in_inventory && second_in_inventory) {
                            break;
                        }
                    }

                    if (first_in_inventory && second_in_inventory) {
                        smallEP.add(p);
                    }
                }

                List<Tree> treeList1 = new ArrayList<>();

                // Hero 1 & 2
                for (Prestige p1 : lP) {
                    if (p1.getPrestigeHero() == l.getId()) {
                        Tree at = new Tree();
                        at.setLegend(p1.getPrestigeHero());

                        at.setHero1(p1.getFirstHero());
                        if (inInventory(at.getHero1(), inv)) {
                            at.setVh1(V100);
                        } else {
                            at.setVh1(V0);
                        }
                        at.setHero2(p1.getSecondHero());
                        if (checkHero2Inventory(at, inv)) {
                            at.setVh2(V100);
                        } else {
                            at.setVh2(V0);
                        }

                        // Epic 1 & 2
                        for (Prestige e12 : hP) {
                            if (e12.getPrestigeHero() == p1.getFirstHero()) {
                                at.setEpic1(e12.getFirstHero());
                                if (inInventory(at.getEpic1(), inv)) {
                                    at.setVe1(V10);
                                } else {
                                    at.setVe1(V0);
                                }
                                at.setEpic2(e12.getSecondHero());
                                if (checkEpic2Inventory(at, inv)) {
                                    at.setVe2(V10);
                                } else {
                                    at.setVe2(V0);
                                }

                                // Epic 3 & 4
                                for (Prestige e34 : hP) {
                                    if (e34.getPrestigeHero() == p1.getSecondHero()) {
                                        at.setEpic3(e34.getFirstHero());
                                        if (checkEpic3Inventory(at, inv)) {
                                            at.setVe3(V10);
                                        } else {
                                            at.setVe3(V0);
                                        }
                                        at.setEpic4(e34.getSecondHero());
                                        if (checkEpic4Inventory(at, inv)) {
                                            at.setVe4(V10);
                                        } else {
                                            at.setVe4(V0);
                                        }

                                        if (checkHeroEpicInventory(at)) {
                                            at.setValue((short) (at.getVh1() + at.getVh2() + at.getVe1() + at.getVe2() + at.getVe3() + at.getVe4() + at.getVu1() + at.getVu2() + at.getVu3() + at.getVu4() + at.getVu5() + at.getVu6() + at.getVu7() + at.getVu8()));
                                            treeList1.add(new Tree(at.getLegend(), at.getHero1(), at.getHero2(), at.getEpic1(), at.getEpic2(), at.getEpic3(), at.getEpic4(), at.getValue(), at.getVh1(), at.getVh2(), at.getVe1(), at.getVe2(), at.getVe3(), at.getVe4()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if ( treeList1.size() == 0 ) {

                    List<Tree> treeList1b = new ArrayList<>();

                    // Hero 1 & 2
                    for (Prestige p1 : lP) {
                        if (p1.getPrestigeHero() == l.getId()) {
                            Tree at = new Tree();
                            at.setLegend(p1.getPrestigeHero());

                            at.setHero1(p1.getFirstHero());
                            if (inInventory(at.getHero1(), inv)) {
                                at.setVh1(V100);
                            } else {
                                at.setVh1(V0);
                            }
                            at.setHero2(p1.getSecondHero());
                            if (checkHero2Inventory(at, inv)) {
                                at.setVh2(V100);
                            } else {
                                at.setVh2(V0);
                            }

                            // Epic 1 & 2
                            for (Prestige e12 : hP) {
                                if (e12.getPrestigeHero() == p1.getFirstHero()) {
                                    at.setEpic1(e12.getFirstHero());
                                    if (inInventory(at.getEpic1(), inv)) {
                                        at.setVe1(V10);
                                    } else {
                                        at.setVe1(V0);
                                    }
                                    at.setEpic2(e12.getSecondHero());
                                    if (checkEpic2Inventory(at, inv)) {
                                        at.setVe2(V10);
                                    } else {
                                        at.setVe2(V0);
                                    }

                                    // Epic 3 & 4
                                    for (Prestige e34 : hP) {
                                        if (e34.getPrestigeHero() == p1.getSecondHero()) {
                                            at.setEpic3(e34.getFirstHero());
                                            if (checkEpic3Inventory(at, inv)) {
                                                at.setVe3(V10);
                                            } else {
                                                at.setVe3(V0);
                                            }
                                            at.setEpic4(e34.getSecondHero());
                                            if (checkEpic4Inventory(at, inv)) {
                                                at.setVe4(V10);
                                            } else {
                                                at.setVe4(V0);
                                            }

                                            at.setValue((short) (at.getVh1() + at.getVh2() + at.getVe1() + at.getVe2() + at.getVe3() + at.getVe4() + at.getVu1() + at.getVu2() + at.getVu3() + at.getVu4() + at.getVu5() + at.getVu6() + at.getVu7() + at.getVu8()));
                                            treeList1b.add(new Tree(at.getLegend(), at.getHero1(), at.getHero2(), at.getEpic1(), at.getEpic2(), at.getEpic3(), at.getEpic4(), at.getValue(), at.getVh1(), at.getVh2(), at.getVe1(), at.getVe2(), at.getVe3(), at.getVe4()));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    for (Tree t : treeList1b) {
                        boolean e1_found = false;
                        boolean e2_found = false;
                        boolean e3_found = false;
                        boolean e4_found = false;

                        List<Hero> inv2 = new ArrayList<>();
                        for (Hero h : inv) {
                            inv2.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
                        }

                        for (Prestige p : smallEP) {
                            if (p.getPrestigeHero() == t.getEpic1()) {
                                if (inInventory(p.getFirstHero(), inv2) & inInventory(p.getSecondHero(), inv2)) {
                                    removeInventory(p.getFirstHero(), inv2);
                                    removeInventory(p.getSecondHero(), inv2);
                                    e1_found = true;
                                    break;
                                }
                            }
                        }

                        for (Prestige p : smallEP) {
                            if (p.getPrestigeHero() == t.getEpic2()) {
                                if (inInventory(p.getFirstHero(), inv2) & inInventory(p.getSecondHero(), inv2)) {
                                    removeInventory(p.getFirstHero(), inv2);
                                    removeInventory(p.getSecondHero(), inv2);
                                    e2_found = true;
                                    break;
                                }
                            }
                        }

                        for (Prestige p : smallEP) {
                            if (p.getPrestigeHero() == t.getEpic3()) {
                                if (inInventory(p.getFirstHero(), inv2) & inInventory(p.getSecondHero(), inv2)) {
                                    removeInventory(p.getFirstHero(), inv2);
                                    removeInventory(p.getSecondHero(), inv2);
                                    e3_found = true;
                                    break;
                                }
                            }
                        }

                        for (Prestige p : smallEP) {
                            if (p.getPrestigeHero() == t.getEpic4()) {
                                if ( inInventory(p.getFirstHero(), inv2) & inInventory(p.getSecondHero(), inv2) ) {
                                    removeInventory(p.getFirstHero(), inv2);
                                    removeInventory(p.getSecondHero(),inv2);
                                    e4_found = true;
                                    break;
                                }
                            }
                        }

                        if (e1_found && e2_found && e3_found && e4_found) {
                            treeList1.add(t);
                        }

                        if ( treeList1.size() > VAR_LIMIT ) {
                            break;
                        }
                    }

                }

                List<Tree> treeList2 = new ArrayList<>();

                for (Tree t : treeList1) {
                    boolean e1_found = true;
                    boolean e2_found = true;
                    boolean e3_found = true;
                    boolean e4_found = true;

                    if (t.getVh1() == 0) {
                        if (t.getVe1() == 0) {
                            e1_found = false;
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic1()) {
                                    if ( inInventory(p.getFirstHero(), inv) & inInventory(p.getSecondHero(), inv) ) {
                                        e1_found = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (t.getVe2() == 0) {
                            e2_found = false;
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic2()) {
                                    if ( inInventory(p.getFirstHero(), inv) & inInventory(p.getSecondHero(), inv) ) {
                                        e2_found = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (t.getVh2() == 0) {
                        if (t.getVe3() == 0) {
                            e3_found = false;
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic3()) {
                                    if ( inInventory(p.getFirstHero(), inv) & inInventory(p.getSecondHero(), inv) ) {
                                        e3_found = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (t.getVe4() == 0) {
                            e4_found = false;
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic4()) {
                                    if ( inInventory(p.getFirstHero(), inv) & inInventory(p.getSecondHero(), inv) ) {
                                        e4_found = true;
                                        break;
                                    }
                                }

                            }
                        }
                    }

                    if (e1_found && e2_found && e3_found && e4_found) {
                        treeList2.add(t);
                    }
                }

                treeList1.clear();

                // 1
                List<Tree> treeList3e1 = new ArrayList<>();
                // resuply with unique heroes
                for (Tree t : treeList2) {
                    if (t.getVh1() == 0) {
                        if (t.getVe1() == 0) {
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic1()) {
                                    Tree at = makeTreeCopy(t);
                                    at.setUnique1(p.getFirstHero());
                                    at.setUnique2(p.getSecondHero());
                                    at.setVu1(V1);
                                    at.setVu2(V1);
                                    treeList3e1.add(at);
                                }
                            }
                        } else {
                            treeList3e1.add(makeTreeCopy(t));
                        }
                    } else {
                        treeList3e1.add(makeTreeCopy(t));
                    }
                }

                treeList2.clear();

                // 2
                List<Tree> treeList3e2 = new ArrayList<>();
                // resuply with unique heroes
                for (Tree t : treeList3e1) {
                    if (t.getVh1() == 0) {
                        if (t.getVe2() == 0) {
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic2()) {
                                    Tree at = makeTreeCopy(t);
                                    at.setUnique3(p.getFirstHero());
                                    at.setUnique4(p.getSecondHero());
                                    at.setVu3(V1);
                                    at.setVu4(V1);
                                    treeList3e2.add(at);
                                }
                            }
                        } else {
                            treeList3e2.add(makeTreeCopy(t));
                        }
                    } else {
                        treeList3e2.add(makeTreeCopy(t));
                    }
                }

                treeList3e1.clear();

                // 3
                List<Tree> treeList3e3 = new ArrayList<>();
                // resuply with unique heroes
                for (Tree t : treeList3e2) {
                    if (t.getVh2() == 0) {
                        if (t.getVe3() == 0) {
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic3()) {
                                    Tree at = makeTreeCopy(t);
                                    at.setUnique5(p.getFirstHero());
                                    at.setUnique6(p.getSecondHero());
                                    at.setVu5(V1);
                                    at.setVu6(V1);
                                    treeList3e3.add(at);
                                }
                            }
                        } else {
                            treeList3e3.add(makeTreeCopy(t));
                        }
                    } else {
                        treeList3e3.add(makeTreeCopy(t));
                    }
                }

                treeList3e2.clear();

                // 4
                List<Tree> treeList3e4 = new ArrayList<>();
                // resuply with unique heroes
                for (Tree t : treeList3e3) {
                    if (t.getVh2() == 0) {
                        if (t.getVe4() == 0) {
                            for (Prestige p : smallEP) {
                                if (p.getPrestigeHero() == t.getEpic4()) {
                                    Tree at = makeTreeCopy(t);
                                    at.setUnique7(p.getFirstHero());
                                    at.setUnique8(p.getSecondHero());
                                    at.setVu7(V1);
                                    at.setVu8(V1);
                                    treeList3e4.add(at);
                                }
                            }
                        } else {
                            treeList3e4.add(makeTreeCopy(t));
                        }
                    } else {
                        treeList3e4.add(makeTreeCopy(t));
                    }
                }

                treeList3e3.clear();

                List<Tree> treeList5 = new ArrayList<>();

                for (Tree t : treeList3e4) {
                    t.setValue((short) (t.getVh1() + t.getVh2() + t.getVe1() + t.getVe2() + t.getVe3() + t.getVe4() + t.getVu1() + t.getVu2() + t.getVu3() + t.getVu4() + t.getVu5() + t.getVu6() + t.getVu7() + t.getVu8()));
                    treeList5.add(t);
                }

                treeList3e4.clear();

                // sort by value
                treeList5.sort((t1, t2) -> {
                    return Short.compare(t2.getValue(), t1.getValue());
                });

                Integer max_limit = 0;
                for ( Tree t : treeList5 ) {
                    if (checkNotDelInventory(t, inv)){
                        resultList2.add(t);
                        max_limit++;
                    }
                    if (max_limit > 5){
                        break;
                    }
                }

                for ( Tree t : treeList5 ) {
                    if (checkNotDelInventory(t, inv)) {
                        checkDelInventory(t, inv);
                        resultList.add(t);
                        break;
                    }
                }

                percentageBuild += 100.0 / legendsToBuid;
                integerProperty.setValue(percentageBuild);

            }
        }

        saveResult( createResult(resultList, resultList2, allheroes) );
        integerProperty.setValue(100);

    }

    public static Tree makeTreeCopy(Tree t) {
        Tree at = new Tree();
        at.setLegend(t.getLegend());
        at.setHero1(t.getHero1());
        at.setHero2(t.getHero2());
        at.setEpic1(t.getEpic1());
        at.setEpic2(t.getEpic2());
        at.setEpic3(t.getEpic3());
        at.setEpic4(t.getEpic4());
        at.setValue(t.getValue());
        at.setUnique1(t.getUnique1());
        at.setUnique2(t.getUnique2());
        at.setUnique3(t.getUnique3());
        at.setUnique4(t.getUnique4());
        at.setUnique5(t.getUnique5());
        at.setUnique6(t.getUnique6());
        at.setUnique7(t.getUnique7());
        at.setUnique8(t.getUnique8());
        at.setVh1(t.getVh1());
        at.setVh2(t.getVh2());
        at.setVe1(t.getVe1());
        at.setVe2(t.getVe2());
        at.setVe3(t.getVe3());
        at.setVe4(t.getVe4());
        at.setVu1(t.getVu1());
        at.setVu2(t.getVu2());
        at.setVu3(t.getVu3());
        at.setVu4(t.getVu4());
        at.setVu5(t.getVu5());
        at.setVu6(t.getVu6());
        at.setVu7(t.getVu7());
        at.setVu8(t.getVu8());
        return at;
    }

    public static List<Prestige> divPrestige(List<Prestige> prestigeList, List<Hero> heroList, String c ) {
        List<Prestige> partPrestige = new ArrayList<>();
        int heroNumber = 0;

        for ( Prestige p : prestigeList ) {
            heroNumber = p.getPrestigeHero();

            for ( Hero h : heroList ) {
                if ( heroNumber == h.getId() ) {
                    if ( h.getHeroClass().equals(c) ) {
                        partPrestige.add(p);
                    }
                }
            }
        }
        return partPrestige;
    }

    public static String showOrgSingleResult(Tree t, List<Hero> heroList ) {
        String output = "\n" + findHero(t.getLegend(), heroList) + "\n";

        if (t.getVh1() > 0 ) {
            output += "     " + findHero(t.getHero1(),heroList);
        } else {
            output += "     " + findHero(t.getHero1(),heroList);
            if (t.getVe1() > 0 ) {
                output += " <- " + findHero(t.getEpic1(), heroList);
            } else {
                output += " <- " + findHero(t.getEpic1(), heroList);
                output += " (" + findHero(t.getUnique1(), heroList);
                output += ", " + findHero(t.getUnique2(), heroList) + ")";
            }
            if (t.getVe2() > 0 ) {
                output += ", " + findHero(t.getEpic2(), heroList);
            } else {
                output += ", " + findHero(t.getEpic2(), heroList);
                output += " (" + findHero(t.getUnique3(), heroList);
                output += ", " + findHero(t.getUnique4(), heroList) + ")";
            }
        }

        output += "\n";

        if (t.getVh2() > 0 ) {
            output += "     " + findHero(t.getHero2(),heroList);
        } else {
            output += "     " + findHero(t.getHero2(),heroList);
            if (t.getVe3() > 0 ) {
                output += " <- " + findHero(t.getEpic3(), heroList);
            } else {
                output += " <- " + findHero(t.getEpic3(), heroList);
                output += " (" + findHero(t.getUnique5(), heroList);
                output += ", " + findHero(t.getUnique6(), heroList) + ")";
            }
            if (t.getVe4() > 0 ) {
                output += ", " + findHero(t.getEpic4(), heroList);
            } else {
                output += ", " + findHero(t.getEpic4(), heroList);
                output += " (" + findHero(t.getUnique7(), heroList);
                output += ", " + findHero(t.getUnique8(), heroList) + ")";
            }
        }

        return output + "\n";
    }

    public static String createResult(List<Tree> tree1List, List<Tree> tree2List, List<Hero> heroList ) {
        String output = "";

        output += "Calculated prestige paths:\n";

        for ( Tree t : tree1List ) {
            output += showOrgSingleResult(t, heroList);
        }

        output += "\nOther results:\n";

        for ( Tree t : tree2List ) {
            output += showOrgSingleResult(t, heroList);
        }

        resultValue = output;

        return output;
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkHeroEpicInventory(Tree t){
        if ( t.getVh1() > 0 || t.getVh2() > 0 || t.getVe1() > 0 || t.getVe2() > 0 || t.getVe3() > 0 || t.getVe4() > 0 ) {
            return true;
        } else {
            return false;
        }
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkOrgInventory(Tree orgTree, List<Hero> inv){
        Tree tree = new Tree();
        Boolean belowZero = false;
        List<Hero> secInv = new ArrayList<>();

        tree.setHero1(orgTree.getHero1());
        tree.setHero2(orgTree.getHero2());
        tree.setEpic1(orgTree.getEpic1());
        tree.setEpic2(orgTree.getEpic2());
        tree.setEpic3(orgTree.getEpic3());
        tree.setEpic4(orgTree.getEpic4());
        tree.setUnique1(orgTree.getUnique1());
        tree.setUnique2(orgTree.getUnique2());
        tree.setUnique3(orgTree.getUnique3());
        tree.setUnique4(orgTree.getUnique4());
        tree.setUnique5(orgTree.getUnique5());
        tree.setUnique6(orgTree.getUnique6());
        tree.setUnique7(orgTree.getUnique7());
        tree.setUnique8(orgTree.getUnique8());

        for ( Hero h : inv ) {
            secInv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }

        // first Hero
        if ( inInventory(tree.getHero1(), secInv) ) {
            removeInventory(tree.getHero1(), secInv);
        } else {
            if ( inInventory(tree.getEpic1(), secInv) ) {
                removeInventory(tree.getEpic1(), secInv);
            } else {
                if ( inInventory(tree.getUnique1(), secInv) ) {removeInventory(tree.getUnique1(), secInv);} else {belowZero = true;}
                if ( inInventory(tree.getUnique2(), secInv) ) {removeInventory(tree.getUnique2(), secInv);} else {belowZero = true;}
            }
            if ( inInventory(tree.getEpic2(), secInv) ) {
                removeInventory(tree.getEpic2(), secInv);
            } else {
                if ( inInventory(tree.getUnique3(), secInv) ) {removeInventory(tree.getUnique3(), secInv);} else {belowZero = true;}
                if ( inInventory(tree.getUnique4(), secInv) ) {removeInventory(tree.getUnique4(), secInv);} else {belowZero = true;}
            }
        }

        // second Hero
        if ( inInventory(tree.getHero2(), secInv) ) {
            removeInventory(tree.getHero2(), secInv);
        } else {
            if ( inInventory(tree.getEpic3(), secInv) ) {
                removeInventory(tree.getEpic3(), secInv);
            } else {
                if ( inInventory(tree.getUnique5(), secInv) ) {removeInventory(tree.getUnique5(), secInv);} else {belowZero = true;}
                if ( inInventory(tree.getUnique6(), secInv) ) {removeInventory(tree.getUnique6(), secInv);} else {belowZero = true;}
            }
            if ( inInventory(tree.getEpic4(), secInv) ) {
                removeInventory(tree.getEpic4(), secInv);
            } else {
                if ( inInventory(tree.getUnique7(), secInv) ) {removeInventory(tree.getUnique7(), secInv);} else {belowZero = true;}
                if ( inInventory(tree.getUnique8(), secInv) ) {removeInventory(tree.getUnique8(), secInv);} else {belowZero = true;}
            }
        }

        if ( !belowZero ) {
            for ( Hero i : inv) {
                for ( Hero s : secInv ) {
                    if ( i.getId() == s.getId() ) {
                        i.setInventory(s.getInventory());
                    }
                }
            }
        }

        return !belowZero;
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkDelInventory(Tree tree, List<Hero> inv){
        Boolean belowZero = false;
        List<Hero> secInv = new ArrayList<>();

        for ( Hero h : inv ) {
            secInv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }

        if (tree.getVh1() > 0 ) {
            if ( inInventory(tree.getHero1(), secInv)) {
                removeInventory(tree.getHero1(), secInv);
            } else {
                belowZero = true;
            }
        } else {
            if (tree.getVe1() > 0 ) {
                if ( inInventory(tree.getEpic1(), secInv)) {
                    removeInventory(tree.getEpic1(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique1(), secInv)) {
                    removeInventory(tree.getUnique1(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique2(), secInv)) {
                    removeInventory(tree.getUnique2(), secInv);
                } else {
                    belowZero = true;
                }
            }
            if (tree.getVe2() > 0 ) {
                if ( inInventory(tree.getEpic2(), secInv)) {
                    removeInventory(tree.getEpic2(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique3(), secInv)) {
                    removeInventory(tree.getUnique3(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique4(), secInv)) {
                    removeInventory(tree.getUnique4(), secInv);
                } else {
                    belowZero = true;
                }
            }

        }

        if (tree.getVh2() > 0 ) {
            if ( inInventory(tree.getHero2(), secInv)) {
                removeInventory(tree.getHero2(), secInv);
            } else {
                belowZero = true;
            }
        } else {
            if (tree.getVe3() > 0 ) {
                if ( inInventory(tree.getEpic3(), secInv)) {
                    removeInventory(tree.getEpic3(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique5(), secInv)) {
                    removeInventory(tree.getUnique5(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique6(), secInv)) {
                    removeInventory(tree.getUnique6(), secInv);
                } else {
                    belowZero = true;
                }
            }
            if (tree.getVe4() > 0 ) {
                if ( inInventory(tree.getEpic4(), secInv)) {
                    removeInventory(tree.getEpic4(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique7(), secInv)) {
                    removeInventory(tree.getUnique7(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique8(), secInv)) {
                    removeInventory(tree.getUnique8(), secInv);
                } else {
                    belowZero = true;
                }
            }
        }

        if ( !belowZero ) {
            for ( Hero i : inv) {
                for ( Hero s : secInv ) {
                    if ( i.getId() == s.getId() ) {
                        i.setInventory(s.getInventory());
                    }
                }
            }
        }

        return !belowZero;
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkNotDelInventory(Tree tree, List<Hero> inv){
        Boolean belowZero = false;
        List<Hero> secInv = new ArrayList<>();

        for ( Hero h : inv ) {
            secInv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }

        if (tree.getVh1() > 0 ) {
            if ( inInventory(tree.getHero1(), secInv)) {
                removeInventory(tree.getHero1(), secInv);
            } else {
                belowZero = true;
            }
        } else {
            if (tree.getVe1() > 0 ) {
                if ( inInventory(tree.getEpic1(), secInv)) {
                    removeInventory(tree.getEpic1(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique1(), secInv)) {
                    removeInventory(tree.getUnique1(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique2(), secInv)) {
                    removeInventory(tree.getUnique2(), secInv);
                } else {
                    belowZero = true;
                }
            }
            if (tree.getVe2() > 0 ) {
                if ( inInventory(tree.getEpic2(), secInv)) {
                    removeInventory(tree.getEpic2(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique3(), secInv)) {
                    removeInventory(tree.getUnique3(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique4(), secInv)) {
                    removeInventory(tree.getUnique4(), secInv);
                } else {
                    belowZero = true;
                }
            }

        }

        if (tree.getVh2() > 0 ) {
            if ( inInventory(tree.getHero2(), secInv)) {
                removeInventory(tree.getHero2(), secInv);
            } else {
                belowZero = true;
            }
        } else {
            if (tree.getVe3() > 0 ) {
                if ( inInventory(tree.getEpic3(), secInv)) {
                    removeInventory(tree.getEpic3(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique5(), secInv)) {
                    removeInventory(tree.getUnique5(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique6(), secInv)) {
                    removeInventory(tree.getUnique6(), secInv);
                } else {
                    belowZero = true;
                }
            }
            if (tree.getVe4() > 0 ) {
                if ( inInventory(tree.getEpic4(), secInv)) {
                    removeInventory(tree.getEpic4(), secInv);
                } else {
                    belowZero = true;
                }
            } else {
                if ( inInventory(tree.getUnique7(), secInv)) {
                    removeInventory(tree.getUnique7(), secInv);
                } else {
                    belowZero = true;
                }
                if ( inInventory(tree.getUnique8(), secInv)) {
                    removeInventory(tree.getUnique8(), secInv);
                } else {
                    belowZero = true;
                }
            }
        }

        return !belowZero;
    }

    // save results
    public static void saveResult(String output) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("result.txt"));
            bw.write(output);
            bw.close();
        } catch ( FileNotFoundException e) {
            e.getLocalizedMessage();
        } catch ( IOException e) {
            e.getLocalizedMessage();
        }
    }

    public static Boolean removeInventory(int id, List<Hero> inventory ) {
        for ( Hero h : inventory ) {
            if ( h.getId() == id ) {
                h.setInventory( h.getInventory() - 1 );
                return true;
            }
        }
        return false;
    }

    // check if in inventory
    public static Boolean inInventory(int id, List<Hero> inventory) {
        for ( Hero h : inventory ) {
            if ( h.getId() == id && h.getInventory() > 0 ) {
                return true;
            }
        }
        return false;
    }

    // find Hero
    public static String findHero(int id, List<Hero> heroList ) {
        for ( Hero h : heroList ) {
            if ( h.getId() == id ) {
                return h.getName();
            }
        }
        return "-";
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkHero2Inventory(Tree tree, List<Hero> inv){
        List<Hero> secInv = new ArrayList<>();

        for ( Hero h : inv ) {
            secInv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }

        if ( inInventory(tree.getHero1(), secInv ) ) {
            removeInventory(tree.getHero1(), secInv);
        }

        if ( inInventory(tree.getHero2(), secInv ) ) {
            return true;
        } else {
            return false;
        }
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkEpic2Inventory(Tree tree, List<Hero> inv){
        List<Hero> secInv = new ArrayList<>();

        for ( Hero h : inv ) {
            secInv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }

        if ( inInventory(tree.getEpic1(), secInv ) ) {
            removeInventory(tree.getEpic1(), secInv);
        }
        if ( inInventory(tree.getEpic2(), secInv ) ) {
            return true;
        } else {
            return false;
        }
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkEpic3Inventory(Tree tree, List<Hero> inv){
        List<Hero> secInv = new ArrayList<>();

        for ( Hero h : inv ) {
            secInv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }
        if ( inInventory(tree.getEpic1(), secInv ) ) {
            removeInventory(tree.getEpic1(), secInv);
        }
        if ( inInventory(tree.getEpic2(), secInv ) ) {
            removeInventory(tree.getEpic2(), secInv);
        }
        if ( inInventory(tree.getEpic3(), secInv ) ) {
            return true;
        } else {
            return false;
        }
    }

    // check if it is possible to build such tree from inventory
    public static Boolean checkEpic4Inventory(Tree tree, List<Hero> inv){
        List<Hero> secInv = new ArrayList<>();

        for ( Hero h : inv ) {
            secInv.add(new Hero(h.getId(), h.getName(), h.getHeroClass(), h.getInventory()));
        }

        if ( inInventory(tree.getEpic1(), secInv ) ) {
            removeInventory(tree.getEpic1(), secInv);
        }
        if ( inInventory(tree.getEpic2(), secInv ) ) {
            removeInventory(tree.getEpic2(), secInv);
        }
        if ( inInventory(tree.getEpic3(), secInv ) ) {
            removeInventory(tree.getEpic3(), secInv);
        }
        if ( inInventory(tree.getEpic4(), secInv ) ) {
            return true;
        } else {
            return false;
        }
    }
}
