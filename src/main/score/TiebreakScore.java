package main.score;

import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Keeps track of the score in a tiebreak
 */
public class TiebreakScore implements Score {

    private boolean tbOver; //is the tiebreak over
    private boolean player1Won; // did player1 win
    private int player1Points; //how many points have player 1 won
    private int player2Points; //how many points have player 2 won

    public TiebreakScore(){
        this.tbOver = false;
        this.player1Won = false;
        this.player1Points = 0;
        this.player2Points = 0;
    }


    @Override
    public void addPlayer1Score() throws ScoreChangeInvalidException {
        if(!this.tbOver){
            this.player1Points += 1;
            this.checkTiebreakOver();
        } else{
            throw new ScoreChangeInvalidException("Tried to add points to a tiebreak that has ended!");
        }
    }

    @Override
    public void addPlayer2Score() throws ScoreChangeInvalidException {
        if(!this.tbOver){
            this.player2Points += 1;
            this.checkTiebreakOver();
        } else{
            throw new ScoreChangeInvalidException("Tried to add points to a tiebreak that has ended!");
        }
    }

    private void checkTiebreakOver(){
        // Regular tennis tiebreaks are first to 7 points by a margin of 2
        int pointDiff = this.player1Points - this.player2Points;
        if(this.player1Points >= 7 && pointDiff >= 2){
            this.tbOver = true;
            this.player1Won = true;
        } else if(this.player2Points >= 7 && pointDiff <= -2){
            this.tbOver = true;
            this.player1Won = false;
        }
    }

    public String toString(){
        String ret = "Player 1 points: " + this.player1Points + "\nPlayer 2 Points: " + this.player2Points + "\n";
        if(this.isTiebreakOver()){
            if(this.didPlayer1Win()){
                ret += "Player 1 Won!\n";
            } else{
                ret += "Player 2 Won!\n";
            }
        }
        return ret;
    }

    public boolean isTiebreakOver() {
        return this.tbOver;
    }

    public boolean didPlayer1Win() {
        return this.tbOver && this.player1Won;
    }

    public String toScoreLine(){
        if(this.didPlayer1Win()){
            return "(" + this.player2Points + ")";
        } else if(this.isTiebreakOver()){
            return "(" + this.player1Points + ")";
        } else return "";
    }

    //method to test class
//    public static void main(String[] args){
//        TiebreakScore tb = new TiebreakScore();
//        Random generator = new Random();
//
//       while(!tb.isTiebreakOver()){
//           int rand = generator.nextInt(2);
//           if(rand==1){
//               try{
//                   tb.addPlayer1Score();
//               } catch(Exception ex){
//                   System.out.println(ex.getMessage());
//               }
//           } else{
//               try{
//                   tb.addPlayer2Score();
//               } catch(Exception ex){
//                   System.out.println(ex.getMessage());
//               }
//           }
//           System.out.println(tb.toString());
//       }
//    }
}
