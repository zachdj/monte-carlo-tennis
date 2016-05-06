package main;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

//This class provides useful constants for the rest of the application

public class Settings {

    /**
     * The state vector describes the probability of a certain shot occurring at the current stage
     * The state vector's length is determined by the number of shot types we consider
     * Groundstrokes: 9 court regions
     * Also there's potential for a player to make an unforced error or hit a winner
     * Total possibilities = 9 + 2 = 11
     */
    public static final int STATE_VECTOR_LENGTH = 11;
    public static final int P1_WINNER_INDEX = 9; // index of the position in the state vector that indicates a winner was hit by Player 1
    public static final int P1_ERROR_INDEX = 10; // index of the position in the state vector that indicates an error was hit by Player 1
    public static final int P2_WINNER_INDEX = 20; // index of the position in the state vector that indicates a winner was hit by Player 2
    public static final int P2_ERROR_INDEX = 21; // index of the position in the state vector that indicates an error was hit by Player 2

    public static ArrayList<Player> getPreloadedPlayers(){
        ArrayList<Player> players = new ArrayList<Player>(10);
        //TODO preload some players
        double[][] fedMatrix = {
                {0.025,0.025,0.025,0.05,0.05,0.1,0.125,0.1,0.2,0.25,0.05},
                {0.025,0.025,0.025,0.1,0.05,0.1,0.15,0.075,0.15,0.25,0.05},
                {0.025,0.025,0.025,0.125,0.05,0.05,0.2,0.1,0.125,0.2,0.075},
                {0.03,0.03,0.03,0.2,0.1,0.085,0.2,0.05,0.075,0.1,0.1},
                {0.025,0.025,0.025,0.11,0.1,0.11,0.15,0.08,0.15,0.15,0.075},
                {0.075,0.025,0.05,0.075,0.1,0.2,0.05,0.15,0.15,0.075,0.05},
                {0.05,0.02,0.03,0.1,0.1,0.1,0.2,0.2,0.05,0.05,0.1},
                {0.025,0.05,0.05,0.1,0.05,0.1,0.15,0.15,0.15,0.1,0.075},
                {0.025,0.05,0.075,0.05,0.075,0.2,0.05,0.15,0.15,0.05,0.125},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,1,0}
        };
        ServeStats fedServeStats = new ServeStats(0.62, 0.1, 0.15, 0.025);
        players.add(new Player("Roger Federer", fedMatrix, fedServeStats));

        double[][] nadalMatrixClay = {
                {0.025,0.025,0.025,0.05,0.05,0.125,0.1,0.125,0.225,0.2,0.05},
                {0.025,0.025,0.025,0.05,0.05,0.15,0.15,0.05,0.25,0.2,0.025},
                {0.025,0.025,0.025,0.05,0.05,0.1,0.2,0.05,0.2,0.25,0.025},
                {0.03,0.03,0.03,0.2,0.1,0.085,0.2,0.1,0.1,0.075,0.05},
                {0.025,0.025,0.025,0.05,0.1,0.15,0.2,0.075,0.2,0.1,0.05},
                {0.05,0.025,0.05,0.05,0.1,0.225,0.05,0.075,0.25,0.1,0.025},
                {0.075,0.075,0.03,0.17,0.1,0.05,0.15,0.15,0.05,0.05,0.1},
                {0.025,0.05,0.075,0.1,0.1,0.175,0.1,0.125,0.15,0.05,0.05},
                {0.025,0.025,0.025,0.05,0.075,0.2,0.05,0.15,0.25,0.1,0.05},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,1,0}
        };
        ServeStats nadalServeStats = new ServeStats(0.67, 0.03, 0.1, 0.01);
        players.add(new Player("Rafael Nadal (clay)", nadalMatrixClay, nadalServeStats));

        double[][] nadalMatrixHard = {
                {0.025,0.025,0.025,0.05,0.05,0.125,0.1,0.125,0.225,0.2,0.05},
                {0.025,0.025,0.025,0.05,0.05,0.15,0.15,0.05,0.25,0.2,0.025},
                {0.025,0.025,0.025,0.05,0.05,0.1,0.175,0.1,0.2,0.2,0.05},
                {0.05,0.05,0.05,0.2,0.1,0.075,0.15,0.1,0.1,0.05,0.075},
                {0.025,0.025,0.025,0.075,0.125,0.15,0.15,0.075,0.2,0.075,0.075},
                {0.05,0.025,0.05,0.05,0.1,0.2,0.05,0.1,0.225,0.1,0.05},
                {0.075,0.075,0.05,0.2,0.125,0.05,0.15,0.1,0.025,0.05,0.1},
                {0.05,0.05,0.075,0.15,0.1,0.15,0.1,0.1,0.125,0.05,0.05},
                {0.025,0.025,0.025,0.1,0.125,0.2,0.05,0.125,0.125,0.125,0.075},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,1,0}
        };
        ServeStats nadalServeStatsHard = new ServeStats(0.62, 0.05, 0.1, 0.025);
        players.add(new Player("Rafael Nadal (hard)", nadalMatrixHard, nadalServeStatsHard));

        double[][] godMatrix = {
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,1,0}
        };
        ServeStats godServeStats = new ServeStats(1, 0, 0, 0);
        Player GOD = new Player("God", godMatrix, godServeStats);
        players.add(GOD);

        double[][] dogMatrix = {
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0,1,0}
        };
        ServeStats dogServeStats = new ServeStats(0, 0, 0, 0);
        Player DOG = new Player("Dog", dogMatrix, dogServeStats);
        players.add(DOG);

        return players;
    }



}
