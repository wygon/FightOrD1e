//Szymon Wygoński
package fightord1e.engine;

import fightord1e.championAssets.Champion;

public class Player {

    private String name;
    private Champion chosenChampion;
    private int wins;

    public Player(String name, Champion chosenChampion) {
        this.name = name;
        this.chosenChampion = chosenChampion;
        this.wins = 0;
    }

    public Player(String name) {
        this.name = name;
        this.wins = 0;
    }

    public Player() {
        this.name = "";
        this.wins = 0;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public Champion getChampion() {
        return chosenChampion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChampion(Champion champion) {
        this.chosenChampion = champion;
    }

    public void addWin() {
        this.wins++;
    }

}
