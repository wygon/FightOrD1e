//Szymon Wygoński
package fightord1e.engine;

import textManagement.Loggers;

public class StatisticTable {

    private Player player1;
    private Player player2;
    private TurnManager tm;

    public StatisticTable(Player player1, Player player2, TurnManager turnManager) {
        this.player1 = player1;
        this.player2 = player2;
        this.tm = turnManager;
    }

    public StatisticTable() {
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public TurnManager getTurnManager() {
        return tm;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.tm = turnManager;
    }

    public void displayBattleStatistics() {
        Loggers.logMessage("[" + tm.getCurrentChampion().getName() + "] " + tm.getCurrentChampion().lessStats(), false, true);
        Loggers.logMessage("[" + tm.getNextChampion().getName() + "] " + tm.getNextChampion().lessStats(), false, true);
    }

}
