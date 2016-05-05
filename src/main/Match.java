package main;

import main.score.MatchScore;
import java.util.Random;
import Jama.*;

/**
 * Match objects represent a match between two players.  A Match object is created for every simulation run.
 */
public class Match {
    Player player1;
    Player player2;
    MatchScore score;

    /**
     * Constructs a new match between the two players with the given maximum number of sets.
     * @param player1 the first player.  This player will be the first to serve
     * @param player2 the second player.  This player will receive first
     * @param maxSets the maximum allowable sets to play.  e.g. a value of 3 will create a BO3 set match
     */
    public Match(Player player1, Player player2, int maxSets){
        this.player1 = player1;
        this.player2 = player2;
        this.score = new MatchScore(maxSets);
    }

    public boolean isOver(){
        return this.score.isMatchOver();
    }

    public boolean didPLayer1Win(){
        return this.score.didPlayer1Win();
    }

    public String toString(){
        //TODO - print results
        return "TODO - Match.toString method!";
    }

    /**
     * This method plays the match.  It uses the player matrices to build a transition matrix and uses the matrix
     * to advance the simulation forward one point at a time.
     * @return True if player1 wins.  False is player2 wins.
     */
    public boolean playMatch(){
        double[][] row = {{1, 0, 0, 0}};
        Matrix rowVector = new Matrix(row);

        double[][] transMatrix = {{0, 0, 0.4, 0.6}, {0, 0, 0.2, 0.8}, {0.5, 0.5, 0, 0}, {0.7, 0.3, 0, 0}};
        Matrix transition = new Matrix(transMatrix);

        double[][] product = rowVector.times(transition).getArray();
        String ret = "";
        for(int r=0; r < product.length; r++){
            for(int col=0; col < product[0].length; col++){
                ret += product[r][col] + " ";
            }
            ret += "\n";
        }
        System.out.println(ret);
        //TODO
        return false;
    }

    // Test class
    public static void main(String[] args){
        Player p1 = new Player("Roger");
        Player p2 = new Player("Rafa");
        int maxSets = 3;

        Match match = new Match(p1, p2, maxSets);
        match.playMatch();
    }
}
