package main;

import main.score.MatchScore;

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

    /**
     * This method plays the match.  It uses the player matrices to build a transition matrix and uses the matrix
     * to advance the simulation forward one point at a time.
     * @return True if player1 wins.  False is player2 wins.
     */
    public boolean playMatch(){
        //TODO
        return false;
    }
}
