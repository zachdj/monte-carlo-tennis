package main;

//This class provides useful constants for the rest of the application

public class Settings {

    /**
     * The state vector describes the probability of a certain shot occurring at the current stage
     * The state vector's length is determined by the number of shot types we consider:
     * We start each point with a serve, but serves aren't considered here
     * Groundstrokes: 9 court regions * 3 spin types = 27 possibilities
     * Also there's potential for a player to make an unforced error or hit a winner
     * Total possibilities = 27 + 2 = 29
     */
    public static final int STATE_VECTOR_LENGTH = 29;

}
