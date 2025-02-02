//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Ability;
import fightord1e.championAssets.Champion;
import fightord1e.championAssets.SpellType;
import java.util.Random;
import textManagement.Loggers;

public class TurnManager {

    private Player cp;
    private Player np;
    private int tourPoint;
    private int totalMovesCount;
    private Champion ally;
    private Champion enemy;
    Random rand = new Random();

    public TurnManager(Player player1, Player player2) {
        this.cp = player1;
        this.np = player2;
        this.tourPoint = 0;
        this.totalMovesCount = 0;
    }

    public TurnManager() {
        this.tourPoint = 0;
        this.totalMovesCount = 0;
    }

    public void swapPlayers() {
        Player fakePlayer = np;
        np = cp;
        cp = fakePlayer;
        ally = getCurrentChampion();
        enemy = getNextChampion();
    }

    public Player whoStart() {
        int n = rand.nextInt(2);
        if (n == 0) {
            return cp;
        } else {
            swapPlayers();
            return cp;
        }
    }

    @Override
    public String toString() {
        String a = "";
        a += "\nStats " + getCurrentPlayer().getName() + ": " + ally.toString();
        a += "\n\nStats " + getNextPlayer().getName() + ": " + enemy.toString();
        return a;
    }

    //Function useAbility is responsible for using right ability 
    public void useAbility(Ability ability) {
        if (ability.isAvailable()) {
            double multipilier = 1;
            int unLuckyNumber = 1;
            String mess = "[" + totalMovesCount + "][" + tourPoint + "/3][" + ally.getName() + "]";
            if (unLuckyNumber == rand.nextInt(10)) {
                mess += "Missed move.";
            } else {
                if (ally.getSpecialSpellType() == SpellType.CRIT)
                {
                    if(1 == rand.nextDouble(5))
                    {
                        multipilier = 1.25;
                        mess += "[CRIT]";
                    }
                }
                if (ability.getType().startsWith("aa")) {
                    enemy.addHP((-((ally.getAttackDamage())) * (1 - enemy.getPhysicalResist() / 70)) * multipilier);
                    mess += "Normal attack";
                } else if (ability.getType().startsWith("boost")) {
                    switch (ability.getType()) {
                        case "boostad":
                            ally.addAttackDamage(ability.getValue() * multipilier);
                            mess += "Attack damage boosted: " + ally.getAttackDamage() * multipilier;
                            break;
                        case "boostmd":
                            ally.addMagicDamage(ability.getValue() * multipilier);
                            mess += "Magic damage boosted: " + ally.getMagicDamage() * multipilier;
                            break;
                        case "boostresist":
                            ally.addPhysicalResist(ability.getValue() * multipilier);
                            ally.addMagicResist(ability.getValue() * multipilier);
                            mess += "Physical and magic resist boosted: physical: " + ally.getPhysicalResist() * multipilier + " magic: " + ally.getMagicResist() * multipilier;
                            break;
                        case "boosthp":
                            ally.addHP(((ally.getMagicDamage() * 0.003 * ability.getValue()) + ability.getValue()) * multipilier);
                            mess += "Healed: " + String.format("%.2f", ((ally.getMagicDamage() * 0.003 * ability.getValue()) + ability.getValue()) * multipilier);
                            break;
                        case "boostpr":
                            ally.addPhysicalResist(ability.getValue() * multipilier);
                            mess += "Physical resist boosted: " + ally.getPhysicalResist() * multipilier;
                            break;
                        case "boostmr":
                            ally.addMagicResist(ability.getValue() * multipilier);
                            mess += "Magic resist boosted " + ally.getMagicResist() * multipilier;
                            break;
                        default:
                            break;
                    }
                } else if (ability.getType().startsWith("dmg")) {
                    switch (ability.getType()) {
                        case "dmgmd":
                            enemy.addHP((-(((ally.getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - enemy.getMagicResist() / 100)) * multipilier);
                            mess += "Magic damage dealt: " + String.format("%.2f", ((((ally.getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - enemy.getMagicResist() / 100)) * multipilier);
                            break;
                        case "dmgad":
                            enemy.addHP((-(((ally.getAttackDamage() * 0.01) * ability.getValue()) + ability.getValue()) * (1 - enemy.getPhysicalResist() / 100)) * multipilier);
                            mess += "Physical damage dealt: " + String.format("%.2f", ((((ally.getAttackDamage() * 0.01) * ability.getValue()) + ability.getValue()) * (1 - enemy.getPhysicalResist() / 100)) * multipilier);
                            break;
                        case "dmgpoison":
                            enemy.setPoison(true);
                            enemy.addPoisonDmg((-(((ally.getMagicDamage() * 0.005 * ability.getValue())) + ability.getValue()) * (1 - enemy.getMagicResist() / 100)) * multipilier);
                            enemy.setPoisonMove(totalMovesCount + 11);
                            mess += "Poison applied to " + enemy.getName();
                            break;
                        default:
                            break;
                    }
                } else if (ability.getType().startsWith("add")) {
                    if (ability.getType().equals("addad2")) {
                        enemy.addHP((-((ally.getAttackDamage()) * ability.getValue()) * (1 - enemy.getPhysicalResist() / 70)) * multipilier);
                        mess += "DOUBLE ATTACK!";
                    }
                } else if (ability.getType().startsWith("turn")) {
                    if (ability.getType().equals("turn")) {
                        ally.addDistancePoint(1);
                        mess += "Tour point added.";
                    }
                } else if (ability.getType().startsWith("lifesteal")) {
                    if (ability.getType().equals("lifesteal")) {
                        ally.setSpecialSpellType(SpellType.LIFESTEAL);
                        ally.setSpecialSpellValue(ability.getValue() * multipilier);
                        if (ability.getUsesLeft() > 100) {
                            ally.setSpecialSpellMove(totalMovesCount + 500);
                        } else {
                            ally.setSpecialSpellMove(totalMovesCount + 11);
                        }
                        mess += "Lifesteal turned ON!";
                    }
                } else if (ability.getType().startsWith("thorns")) {
                    if (ability.getType().equals("thorns")) {
                        ally.setSpecialSpellType(SpellType.THORNS);
                        ally.setSpecialSpellValue(ability.getValue() * multipilier);
                        if (ability.getUsesLeft() > 100) {
                            ally.setSpecialSpellMove(totalMovesCount + 500);
                        } else {
                            ally.setSpecialSpellMove(totalMovesCount + 11);
                        }
                        mess += "Thorns turned ON!";
                    }
                }
                if (ally.getSpecialSpellType() == SpellType.LUCK) {
                    int luckyNumber = 1;
                    if (luckyNumber == rand.nextInt(5)) {
                        ability.addUsesLeft(1);
                        mess += "[LUCKY]";
                    }
                }
            }
            Loggers.clearScreen();
            Loggers.logMessage("=================================================", false, true);
            Loggers.logMessage(mess, true, true);
            ability.addUsesLeft(-1);
        }

    }

    //Checking if any effect is applied and - if: doing it work 
    public void effectsManagement() {
        String mess = "";
        //Poison management
        if (ally.isPoison()) {
            if (getTotalMovesCount() >= ally.getPoisonMove()) {
                ally.setPoison(false);
                ally.addPoisonDmg(-ally.getPoisonDmg());
                mess += "[PASSIVE][" + enemy.getName() + "] poison ended";
            } else {
                ally.addHP(ally.getPoisonDmg());
                mess += "[PASSIVE][" + ally.getName() + "] poisoned damage " + ally.getPoisonDmg();
            }
        }
        //Lifesteal management
        if (enemy.getSpecialSpellType() == SpellType.LIFESTEAL) {
            if (!mess.equals("")) {
                mess += "\n";
            }
            if (getTotalMovesCount() >= enemy.getSpecialSpellMove()) {
                enemy.setSpecialSpellType(SpellType.OFF);
                mess += "[PASSIVE][" + ally.getName() + "]Lifesteal ended";
            } else {
                enemy.addHP((ally.getLastRoundHP() - ally.getHP()) * (enemy.getSpecialSpellValue()) * 0.02);
                mess += "[PASSIVE][" + enemy.getName() + "]Healed for " + String.format("%.2f", (ally.getLastRoundHP() - ally.getHP()) * (enemy.getSpecialSpellValue()) * 0.02);
            }
        }
        //Thorns management
        if (ally.getSpecialSpellType() == SpellType.THORNS) {
            if (!mess.equals("")) {
                mess += "\n";
            }
            if (getTotalMovesCount() >= ally.getSpecialSpellMove()) {
                ally.setSpecialSpellType(SpellType.OFF);
                ally.setSpecialSpellValue(0);
                mess += "[PASSIVE]" + "Thorns from [" + ally.getName() + "] to [" + enemy.getName() + "] ended.";
            } else {
                enemy.addHP(-(ally.getLastRoundHP() - ally.getHP()) * (ally.getSpecialSpellValue() * 0.015));
                mess += "[PASSIVE]Thorns hit [" + enemy.getName() + "] for " + String.format("%.2f", (ally.getLastRoundHP() - ally.getHP()) * (ally.getSpecialSpellValue() * 0.015));
            }
        }
        if (!mess.equals("")) {
            Loggers.logMessage(mess, true, true);
            Loggers.logMessage("=================================================", false, true);
        }
    }


    //Checking - if: range is okay to start fight - if not: ending tour
    public void rangeCheck() {
        //Decreasing range - if: champion is far away
        if (ally.getDistancePoint() < enemy.getDistancePoint()) {
            enemy.addDistancePoint(-1);
//            effectsManagement();
            String mess = "[" + getTotalMovesCount() + "][" + getTourPoint() + "/3] " + ally.getName() + " is losing tour caused by range difference.";
            Loggers.logMessage(mess + "\n=================================================", false, true);
            mess += getNextPlayer().getName() + " [" + enemy.getName() + "] ITS YOUR TURN!";
            Loggers.logMessage(mess, true, false);
            endTurn();
        }
    }

    //Function endTurn() is responsible for manage things after single player tour
    public void endTurn() {
        ally.setLastRoundHP(ally.getHP());
        swapPlayers();
    }

    //Checking - if: game is over
    public boolean isGameOver() {
        return getCurrentPlayer().getChampion().getHP() <= 0 || getNextPlayer().getChampion().getHP() <= 0;
    }

    public Player getCurrentPlayer() {
        return cp;
    }

    public Player getNextPlayer() {
        return np;
    }

    public Champion getCurrentChampion() {
        return getCurrentPlayer().getChampion();
    }

    public Champion getNextChampion() {
        return getNextPlayer().getChampion();
    }

    public int getTourPoint() {
        return tourPoint;
    }

    public int getTotalMovesCount() {
        return totalMovesCount;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.cp = currentPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.np = nextPlayer;
    }

    public void setTourPoint(int tourPoint) {
        this.tourPoint = tourPoint;
    }

    public void setTotalMovesCount(int totalMovesCount) {
        this.totalMovesCount = totalMovesCount;
    }

    public void addTourPoint(int tourPoint) {
        this.tourPoint += tourPoint;
        totalMovesCount++;
    }
}
