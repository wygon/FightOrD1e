//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Ability;
import fightord1e.championAssets.Champion;
import fightord1e.championAssets.LifeStealer;
import fightord1e.championAssets.Mage;
import fightord1e.championAssets.Tank;
import fightord1e.championAssets.Fighter;
import fightord1e.main.FightOrD1e;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements GameActions{

    private final TurnManager tm;
    private Fight fight;
    private final StatisticTable statisticTable;
    public Map<String, Integer> fightWinners;

    public Game(Player player1, Player player2, Map<String, Integer> fightWinners) {
        this.tm = new TurnManager(player1, player2);
        this.statisticTable = new StatisticTable(player1, player2, tm);
        this.fightWinners = fightWinners;
    }

    public Game() {
        this.tm = new TurnManager();
        this.statisticTable = new StatisticTable();
    }

    public void readChampions() {
        tm.readFile("champions-list.txt", false, true);
    }
    @Override
    public void start() {
        //tm.readFile("champions-list.txt", false, true);
        championPick();
        fight = new Fight(tm, statisticTable);
        TurnManager.logMessage("=================================================\n[" + tm.getCurrentChampion().getName() + "] vs [" + tm.getNextChampion().getName() + "]", true, false);
        TurnManager.logMessage("Champions description: " + tm.getCurrentChampion() + tm.getCurrentChampion().printAbilities() + tm.getNextChampion() + tm.getNextChampion().printAbilities(), false, true);
        tm.whoStart();
        System.out.println("Fight start: \n" + tm.getCurrentPlayer().getName() + " with his champion " + tm.getCurrentChampion().getName());
        System.out.println("His opponent is : \n" + tm.getNextPlayer().getName() + " with his champion " + tm.getNextChampion().getName());
        fight.start();
        end();
    }

    private void championPick() {
//        tm.readFile("champions-list.txt", false, true);
        readChampions();
        Scanner input = new Scanner(System.in);
        System.out.println(tm.getCurrentPlayer().getName() + " choose your champion(fullname): ");
        String selectChampion1 = input.nextLine();
        System.out.println(tm.getNextPlayer().getName() + " choose your champion(fullname): ");
        String selectChampion2 = input.nextLine();
        Champion champion = null;
        Champion champion2 = null;
        //ENUM
        try {
            BufferedReader reader = new BufferedReader(new FileReader("champions.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] champions = line.split("<");
                String[] bigParts = line.split(";");
                String[] parts = bigParts[0].split(",");
                for (String ch : champions) {
                    if (ch.startsWith(selectChampion1)) {
                        String part = bigParts[2];
                        switch (part) {
                            case "LIFESTEALER":
                                champion = new LifeStealer();
                                break;
                            case "MAGE":
                                champion = new Mage();
                                break;
                            case "TANK":
                                champion = new Tank();
                                break;
                            case "FIGHTER":
                                champion = new Fighter();
                                break;
                            default:
                                break;
                        }
                        champion.setName(parts[0]);
                        champion.setHP(Double.parseDouble(parts[1]));
                        champion.setLastRoundHP(Double.parseDouble(parts[1]));
                        champion.setAttackDamage(Double.parseDouble(parts[2]));
                        champion.setMagicDamage(Double.parseDouble(parts[3]));
                        champion.setPhysicalResist(Double.parseDouble(parts[4]));
                        champion.setMagicResist(Double.parseDouble(parts[5]));
                        champion.setDistancePoint(Integer.parseInt(parts[6]));
                        String[] abilityParts = bigParts[1].split("/");
                        Ability[] abilities = new Ability[abilityParts.length];
                        for (int i = 0; i < abilityParts.length; i++) {
                            abilities[i] = Ability.fromString(abilityParts[i]);
                        }
                        champion.setAbilities(abilities);
                        break;
                    }
                    if (ch.startsWith(selectChampion2)) {
                        String part = bigParts[2];
                        switch (part) {
                            case "LIFESTEALER":
                                champion2 = new LifeStealer();
                                break;
                            case "MAGE":
                                champion2 = new Mage();
                                break;
                            case "TANK":
                                champion2 = new Tank();
                                break;
                            case "FIGHTER":
                                champion2 = new Fighter();
                                break;
                            default:
                                break;
                        }
                        champion2.setName(parts[0]);
                        champion2.setHP(Double.parseDouble(parts[1]));
                        champion2.setLastRoundHP(Double.parseDouble(parts[1]));
                        champion2.setAttackDamage(Double.parseDouble(parts[2]));
                        champion2.setMagicDamage(Double.parseDouble(parts[3]));
                        champion2.setPhysicalResist(Double.parseDouble(parts[4]));
                        champion2.setMagicResist(Double.parseDouble(parts[5]));
                        champion2.setDistancePoint(Integer.parseInt(parts[6]));
                        String[] abilityParts = bigParts[1].split("/");
                        Ability[] abilities = new Ability[abilityParts.length];
                        for (int i = 0; i < abilityParts.length; i++) {
                            abilities[i] = Ability.fromString(abilityParts[i]);
                        }
                        champion2.setAbilities(abilities);
                        break;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(FightOrD1e.class.getName()).log(Level.SEVERE, null, ex);
        }
        tm.getCurrentPlayer().setChampion(champion);
        tm.getNextPlayer().setChampion(champion2);
    }

    public TurnManager getTM() {
        return tm;
    }
    @Override
    public void end()
    {
        addFightWinner(fight.getWinner());
        System.out.println("""
                   You want play again?
                   [1]YES
                   [X]NO""");
    }
    private void addFightWinner(Champion winner) {
        fightWinners.merge(winner.getName(), 1, (currentValue, newValue) -> currentValue + newValue);
    }

    public StatisticTable getStatisticTable() {
        return statisticTable;
    }
}
