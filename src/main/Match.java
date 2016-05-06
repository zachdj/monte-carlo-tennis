package main;

import main.score.MatchScore;
import java.util.Random;
import java.util.concurrent.Exchanger;

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

    private void verbosePrint(String message, boolean verbose){
        if(verbose){
            System.out.println(message);
        }
    }

    private void recordPointForPlayer1(){
        try{
            this.score.addPlayer1Score();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }

    private void recordPointForPlayer2(){
        try{
            this.score.addPlayer2Score();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }

    /**
     * This method plays the match.  It uses the player matrices to build a transition matrix and uses the matrix
     * to advance the simulation forward one point at a time.
     * @param verbose true if the match should be played in "verbose" mode
     * @return True if player1 wins.  False is player2 wins.
     */
    public boolean playMatch(boolean verbose){
        Random generator = new Random();
        Matrix T = this.getTransitionMatrix();
        while(!this.isOver()){
            //play points until the match is over:

            if(this.score.getServingPlayer()==1){
                //Player 1 serves.  Determine what happens using P1's serve stats
                double serveResultDecider = generator.nextDouble();
                if(serveResultDecider <= player1.getServeStats().getFirstServePct()){
                    //Serve was a first service, so there's a chance of an ace or a service winner
                    double firstServeDecider = generator.nextDouble();
                    if(firstServeDecider <= player1.getServeStats().getAceRate()){
                        verbosePrint("Player 1 hits ace!", verbose);
                        recordPointForPlayer1();
                    } else if( firstServeDecider <= player1.getServeStats().getAceRate() + player1.getServeStats().getSvcWinnerRate()){
                        verbosePrint("Player 1 hits service winner!", verbose);
                        recordPointForPlayer1();
                    } else{
                        // the serve was neither an ace or a service winner, so we have to play a point
                        double[][] initialRowVector = new double[1][Settings.STATE_VECTOR_LENGTH*2];
                        //first serves count as a deep shot to a corner
                        if(this.score.servingToDeuceSide()){
                            verbosePrint("Player 1 hits first serve to deuce side!", verbose);
                            initialRowVector[0][6] = 1; //corresponds to region 7 for P1
                        } else{
                            verbosePrint("Player 1 hits first serve to ad side!", verbose);
                            initialRowVector[0][8] = 1; //corresponds to region 9 for P1
                        }
                        //play the point and see who wins!
                        if(playPoint(new Matrix(initialRowVector), T, verbose)){
                            recordPointForPlayer1();
                        } else{
                            recordPointForPlayer2();
                        }
                    }
                } else {
                    //Serve was a second service, so there's a chance of a double fault
                    double faultDecider = generator.nextDouble();
                    if(faultDecider <= player1.getServeStats().getDblFaultRate()){
                        verbosePrint("Player 1 hits double fault!", verbose);
                        recordPointForPlayer2();
                    } else{
                        //Serve went in, so we have to play a point
                        double[][] initialRowVector = new double[1][Settings.STATE_VECTOR_LENGTH*2];
                        //second serves count as a netreul shot to a region 4 or 6
                        if(this.score.servingToDeuceSide()){
                            verbosePrint("Player 1 hits second serve to deuce side!", verbose);
                            initialRowVector[0][3] = 1; //corresponds to region 4 for P1
                        } else{
                            verbosePrint("Player 1 hits second serve to ad side!", verbose);
                            initialRowVector[0][5] = 1; //corresponds to region 6 for P1
                        }
                        //play the point and see who wins!
                        if(playPoint(new Matrix(initialRowVector), T, verbose)){
                            recordPointForPlayer1();
                        } else{
                            recordPointForPlayer2();
                        }
                    }
                }
            } else{
                //Player 2 serves.  Determine what happens using P2's serve stats
                double serveResultDecider = generator.nextDouble();
                if(serveResultDecider <= player2.getServeStats().getFirstServePct()){
                    //Serve was a first service, so there's a chance of an ace or a service winner
                    double firstServeDecider = generator.nextDouble();
                    if(firstServeDecider <= player2.getServeStats().getAceRate()){
                        verbosePrint("Player 2 hits ace!", verbose);
                        recordPointForPlayer2();
                    } else if( firstServeDecider <= player2.getServeStats().getAceRate() + player2.getServeStats().getSvcWinnerRate()){
                        verbosePrint("Player 2 hits service winner!", verbose);
                        recordPointForPlayer2();
                    } else{
                        // the serve was neither an ace or a service winner, so we have to play a point
                        double[][] initialRowVector = new double[1][Settings.STATE_VECTOR_LENGTH*2];
                        //first serves count as a deep shot to a corner
                        if(this.score.servingToDeuceSide()){
                            verbosePrint("Player 2 hits first serve to deuce side!", verbose);
                            initialRowVector[0][17] = 1; //corresponds to region 7 for P2
                        } else{
                            verbosePrint("Player 2 hits first serve to ad side!", verbose);
                            initialRowVector[0][19] = 1; //corresponds to region 9 for P2
                        }
                        //play the point and see who wins!
                        if(playPoint(new Matrix(initialRowVector), T, verbose)){
                            recordPointForPlayer1();
                        } else{
                            recordPointForPlayer2();
                        }
                    }
                } else {
                    //Serve was a second service, so there's a chance of a double fault
                    double faultDecider = generator.nextDouble();
                    if(faultDecider <= player2.getServeStats().getDblFaultRate()){
                        verbosePrint("Player 2 hits double fault!", verbose);
                        recordPointForPlayer1();
                    } else{
                        //Serve went in, so we have to play a point
                        double[][] initialRowVector = new double[1][Settings.STATE_VECTOR_LENGTH*2];
                        //second serves count as a neutral shot to a region 4 or 6
                        if(this.score.servingToDeuceSide()){
                            verbosePrint("Player 2 hits second serve to deuce side!", verbose);
                            initialRowVector[0][14] = 1; //corresponds to region 4 for P2
                        } else{
                            verbosePrint("Player 2 hits second serve to ad side!", verbose);
                            initialRowVector[0][16] = 1; //corresponds to region 6 for P2
                        }
                        //play the point and see who wins!
                        if(playPoint(new Matrix(initialRowVector), T, verbose)){
                            recordPointForPlayer1();
                        } else{
                            recordPointForPlayer2();
                        }
                    }
                }
            }
            //Point has been played
            verbosePrint("Current score: " + this.score.getFullScore(), verbose);
        } //end while loop that plays points
        return this.didPLayer1Win();
    }

    /**
     * Plays a single point without changing the score of the match
     * @param rowVector the state vector describing the previous shot - must be of length STATE_VECTOR_LENGTH*2
     * @param transition the transition matrix for this match - must be square with dimension STATE_VECTOR_LENGTH*2
     * @return true if player 1 wins the point, false otherwise
     */
    public boolean playPoint(Matrix rowVector, Matrix transition, boolean verbose){
        boolean pointOver = false;
        Matrix lastShot = rowVector;
        Random generator = new Random();

        int winningPlayer = 1;
        while(!pointOver){
            //use transition matrix to get probabilities of next player's response
            double[] responseProbs = lastShot.times(transition).getArray()[0];
            // create a row vector to represent the next shot
            double[][] nextShot = new double[1][Settings.STATE_VECTOR_LENGTH*2];
            // randomly generate a number to see which shot was selected
            double nextShotDeterminer = generator.nextDouble();
            double cumulativeProb = 0;
            for(int i = 0; i < responseProbs.length; i++){
                cumulativeProb += responseProbs[i];
                if(nextShotDeterminer <= cumulativeProb){
                    nextShot[0][i] = 1; // set the appropriate index to 1 in the state vector for the next shot
                    //determine if the shot was a winner or unforced error
                    if(i == Settings.P1_WINNER_INDEX){
                        //Player 1 hit a winner!
                        verbosePrint("Player 1 hits winner!", verbose);
                        winningPlayer = 1;
                        pointOver = true;
                    } else if(i == Settings.P1_ERROR_INDEX){
                        //Player 1 made an error :(
                        verbosePrint("Player 1 makes an error", verbose);
                        winningPlayer = 2;
                        pointOver = true;
                    } else if(i == Settings.P2_WINNER_INDEX){
                        //Player 2 hit a winner!
                        verbosePrint("Player 2 hits winner!", verbose);
                        winningPlayer = 2;
                        pointOver = true;
                    } else if(i == Settings.P2_ERROR_INDEX){
                        //Player 2 hit an error :(
                        verbosePrint("Player 2 makes an error", verbose);
                        winningPlayer = 1;
                        pointOver = true;
                    }
                    // the shot has been determined, so stop summing probabilities
                    break;
                }
            }
            lastShot = new Matrix(nextShot); //update the last shot to prepare for the next point
        }

        return winningPlayer == 1;
    }

    public String shotVectorToString(Matrix vector){
        double[] vec = vector.getArray()[0];
        System.out.print("[ ");
        for(int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + ", ");
        }
        System.out.print("]\n");

        for(int i = 0; i < vec.length; i++){
            if(vec[i] == 1) {
                if(i < 9) return "Player 1 hits shot to region " + (i+1);
                else if( i== 9) return "Player 1 hits winner " + (i+1);
                else if( i== 10) return "Player 1 hits UE " + (i+1);
                else if( i < 20) return "Player 2 hits shot to region " + (i+1);
                else if( i== 20) return "Player 2 hits winner " + (i+1);
                else if( i== 21) return "Player 2 hits UE " + (i+1);
            }
        }

        return "Undetermined shot type";
    }

    /**
     * Builds the transition matrix T using the player matrices
     * This method assumes the player matrices are square (they always should be)
     * @return the transition matrix for the match
     */
    public Matrix getTransitionMatrix(){
        int player1Dimension = player1.getMatrix().getArray().length;
        int player2Dimension = player2.getMatrix().getArray().length;
        int dimension = player1Dimension + player2Dimension;
        double[][] transition = new double[dimension][dimension];

        //Place values from P1's matrix
        for(int row = player2Dimension; row < dimension; row++){ //bottom half of the matrix
            for(int col = 0; col < player1Dimension; col++){
                transition[row][col] = player1.getMatrix().getArray()[row-player2Dimension][col];
            }
        }
        //Place values from P2's matrix
        for(int row = 0; row < player2Dimension; row++){
            for(int col = player1Dimension; col < dimension; col++){
                transition[row][col] = player2.getMatrix().getArray()[row][col-player1Dimension];
            }
        }

        return new Matrix(transition);
    }

    private void printTransitionMatrix(){
        double[][] matrix = this.getTransitionMatrix().getArray();
        System.out.println("Rows: " + matrix.length);
        System.out.println("Cols: " + matrix[0].length);
        for(int row=0; row < matrix.length; row++){
            for(int col=0; col<matrix[0].length; col++){
                System.out.print(matrix[row][col] + " \t");
            }
            System.out.println();
        }
    }

    // Test class
    public static void main(String[] args){
        double player1Wins = 0;
        double player2Wins = 0;
        int trials = 1000;


        Player p1 = Settings.getPreloadedPlayers().get(0); //Fed
//        Player p2 = Settings.getPreloadedPlayers().get(1); //Nadal on clay
        Player p2 = Settings.getPreloadedPlayers().get(2); //Nadal on Hard

        for(int i = 0; i < trials; i++){
            int maxSets = 3;

            Match match = new Match(p1, p2, maxSets);
            if(match.playMatch(false)){
                player1Wins++;
            } else{
                player2Wins++;
            }
        }

        System.out.println("Player 1 Wins " + (player1Wins/ trials)*100 + "% of the time");
        System.out.println("Player 2 Wins " + (player2Wins/ trials)*100 + "% of the time");
    }
}
