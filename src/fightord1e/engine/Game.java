//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Ability;
import fightord1e.championAssets.Assasin;
import fightord1e.championAssets.Champion;
import fightord1e.championAssets.LifeStealer;
import fightord1e.championAssets.Mage;
import fightord1e.championAssets.Tank;
import fightord1e.championAssets.Fighter;
import fightord1e.main.FightOrD1e;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import textManagement.Loggers;

public class Game implements GameActions {

    private final TurnManager tm;
    private Fight fight;
    private final StatisticTable statisticTable;
    public ArrayList<Champion> championsList;
    public Map<String, Integer> fightWinners;

    public Game(Player player1, Player player2, Map<String, Integer> fightWinners) {
        this.tm = new TurnManager(player1, player2);
        this.statisticTable = new StatisticTable(player1, player2, tm);
        this.fightWinners = fightWinners;
        this.championsList = new ArrayList<>();
    }

    public Game() {
        this.tm = new TurnManager();
        this.statisticTable = new StatisticTable();
        this.championsList = new ArrayList<>();
    }

    @Override
    public void start() {
        //tm.readFile("champions-list.txt", false, true);
        championPick();
        fight = new Fight(tm, statisticTable);
        Loggers.logMessage("=================================================\n[" + tm.getCurrentChampion().getName() + "] vs [" + tm.getNextChampion().getName() + "]", true, false);
        Loggers.logMessage("Champions description: " + tm.getCurrentChampion() + tm.getCurrentChampion().printAbilities() + tm.getNextChampion() + tm.getNextChampion().printAbilities(), false, true);
        tm.whoStart();
        Loggers.logMessage("Fight start: \n" + tm.getCurrentPlayer().getName() + " with his champion " + tm.getCurrentChampion().getName(), false, true);
        Loggers.logMessage("His opponent is : \n" + tm.getNextPlayer().getName() + " with his champion " + tm.getNextChampion().getName(), false, true);
        fight.start();
    }

    @Override
    public void end() {
        addFightWinner(fight.getWinner());
        Loggers.logMessage("""
                   You want play again?
                   [1]YES
                   [2]NO""", false, true);
    }

    public void resetChampionList() {
        if (!championsList.isEmpty()) {
            championsList.clear();
        }
        applyChampionsList();
        Loggers.clearScreen();
        Loggers.logMessage("Champions reseted", false, true);
    }

    public void applyChampionsList() {
//        Champion champion = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("champions.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] bigParts = line.split(";");
                String[] parts = bigParts[1].split(",");
                String champ = bigParts[0];
                Champion champion = switch (champ) {
                    case "LIFESTEALER" ->
                        new LifeStealer();
                    case "MAGE" ->
                        new Mage();
                    case "TANK" ->
                        new Tank();
                    case "FIGHTER" ->
                        new Fighter();
                    case "ASSASIN" ->
                        new Assasin();
                    default ->
                        throw new IllegalArgumentException("Invalid Chmapion type");
                };
                configureChampion(champion, parts);
                configureAbilities(champion, bigParts[2]);
                championsList.add(champion);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(FightOrD1e.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configureChampion(Champion champion, String[] parts) {
        champion.setName(parts[0]);
        champion.setHP(Double.parseDouble(parts[1]));
        champion.setLastRoundHP(Double.parseDouble(parts[1]));
        champion.setAttackDamage(Double.parseDouble(parts[2]));
        champion.setMagicDamage(Double.parseDouble(parts[3]));
        champion.setPhysicalResist(Double.parseDouble(parts[4]));
        champion.setMagicResist(Double.parseDouble(parts[5]));
        champion.setDistancePoint(Integer.parseInt(parts[6]));
    }

    private void configureAbilities(Champion champion, String abilitiesPart) {
        String[] abilityParts = abilitiesPart.split("/");
        Ability[] abilities = new Ability[abilityParts.length];
        for (int i = 0; i < abilityParts.length; i++) {
            abilities[i] = Ability.fromString(abilityParts[i]);
        }
        champion.setAbilities(abilities);
    }

    public void showMagicChamp() {
        championsList.forEach(champ -> {
            if (champ.getMagicDamage() > 75) {
                Loggers.logMessage("[" + champ.getName() + "] [" + champ.getMagicDamage() + "] magic damage", false, true);
            }
        });
    }

    public void showPhysicalChamp() {
        championsList.forEach(champ -> {
            if (champ.getAttackDamage() > 61) {
                Loggers.logMessage("[" + champ.getName() + "] [" + champ.getAttackDamage() + "] physical damage", false, true);
            }
        });
    }

    public void showMResistChamp() {
        championsList.forEach(champ -> {
            if (champ.getMagicResist() > 30) {
                Loggers.logMessage("[" + champ.getName() + "] [" + champ.getMagicResist() + "] magic resist", false, true);
            }
        });
    }

    public void showPResistChamp() {
        championsList.forEach(champ -> {
            if (champ.getPhysicalResist() > 30) {
                Loggers.logMessage("[" + champ.getName() + "] [" + champ.getPhysicalResist() + "] physical resist", false, true);
            }
        });
    }

    public void showChampions() {
        championsList.forEach(champ -> {
            Loggers.logMessage("[" + champ.getName() + "]", false, true);
        });
    }

    private void championPick() {
        final int[] i = {1};
        int selectChampion1;
        int selectChampion2;
        championsList.forEach(champ -> {
            Loggers.logMessage("[" + i[0]++ + "][" + champ.getName() + "]", false, true);
        });
        Loggers.logMessage("[OTHER][RANDOM]", false, true);
//        readChampions();
        Scanner input = new Scanner(System.in);
        Loggers.logMessage(tm.getCurrentPlayer().getName() + " choose your champion(type number): ", false, true);
        selectChampion1 = Loggers.choiceValidator(input);
        selectChampion2 = Loggers.choiceValidator(input);
        selectChampion1 = validateChampion(selectChampion1, selectChampion2);
        selectChampion2 = validateChampion(selectChampion2, selectChampion1);
        tm.getCurrentPlayer().setChampion(championsList.get(selectChampion1 - 1));
        tm.getNextPlayer().setChampion(championsList.get(selectChampion2 - 1));
    }

    public TurnManager getTM() {
        return tm;
    }

    private int validateChampion(int selectedChampion, int otherChampion) {
        while (selectedChampion > championsList.size() || selectedChampion < 1 || selectedChampion == otherChampion) {
            selectedChampion = new Random().nextInt(1, 13);
        }
        return selectedChampion;
    }

    private void addFightWinner(Champion winner) {
        fightWinners.merge(winner.getName(), 1, (currentValue, newValue) -> currentValue + newValue);
    }

    public StatisticTable getStatisticTable() {
        return statisticTable;
    }
}
