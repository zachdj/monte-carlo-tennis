package main.score;

import java.util.Random;

/**
 * Represents the score of a single game
 * A game continues until one player reaches 4 points
 */
public class GameScore implements Score {
    private boolean gameOver; //is the game over
    private boolean player1Won; // did player1 win
    private int player1Points; //how many points have player 1 won
    private int player2Points; //how many points have player 2 won

    public GameScore(){
        this.gameOver = false;
        this.player1Won = false;
        this.player1Points = 0;
        this.player2Points = 0;
    }

    //Adds to player 1's score and checks for game ending conditions
    @Override
    public void addPlayer1Score() throws ScoreChangeInvalidException{
        //check game over
        if(!this.gameOver){
            //if the game isn't over, add one to player 1's score and check game over conditions
            this.player1Points += 1;
            this.checkGameOver();
        } else{
            throw new ScoreChangeInvalidException("Tried to add score to a game that has ended!");
        }
    }

    @Override
    public void addPlayer2Score() throws ScoreChangeInvalidException{
        //check game over
        if(!this.gameOver){
            //if the game isn't over, add one to player 1's score and check game over conditions
            this.player2Points += 1;
            this.checkGameOver();
        } else{
            throw new ScoreChangeInvalidException("Tried to add score to a game that has ended!");
        }
    }

    private void checkGameOver(){
        int pointDifference = this.player1Points - this.player2Points;
        if(this.player1Points >= 4 && pointDifference >= 2){ // win conditions for player 1
            this.gameOver = true;
            this.player1Won = true;
        } else if(this.player2Points >= 4 && pointDifference <= -2){ // win conditions for player 2
            this.gameOver = true;
            this.player1Won = false;
        }
    }

    public String toString(){
        String ret = "";
        ret += "Player 1 Score: " + this.player1Points + "\n";
        ret += "Player 2 Score: " + this.player2Points + "\n";
        if(this.didPlayer1Win()){
            ret += "Player 1 Wins";
        } else if(this.gameOver){
            ret += "Player 2 Wins";
        }
        return ret;
    }

    public boolean isGameOver(){
        return this.gameOver;
    }

    public boolean didPlayer1Win(){
        return this.gameOver && this.player1Won;
    }

    //method to test class
//    public static void main(String[] args){
//        GameScore aGame = new GameScore();
//        Random generator = new Random();
//
//        for(int i = 0; i < 5; i++){
//            try {
//                aGame.addPlayer1Score();
//            } catch(Exception ex){
//                System.out.println(ex.getMessage());
//            }
//        }
//    }
}
