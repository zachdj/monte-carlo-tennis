package main.score;

import java.util.Random;

/**
 * Keeps track of the score in a set.  Calling one of the addScore methods will add a point to the corresponding player
 * and update the score of the current game.
 */
public class SetScore implements Score {
    private boolean setOver;
    private boolean player1Won;
    private GameScore currentGame;
    private TiebreakScore setTiebreak;
    private int player1Games;
    private int player2Games;

    public SetScore(){
        this.currentGame = new GameScore();
        this.setTiebreak = null;  // tiebreak will be null unless a tiebreak is required to decide the set
        this.player1Games = 0;
        this.player2Games = 0;
    }

    @Override
    public void addPlayer1Score() throws ScoreChangeInvalidException {
        if(!this.setOver){
            if(this.setTiebreak!=null){
                //if this set has gone to a tiebreak, add the score to the tiebreak score
                this.setTiebreak.addPlayer1Score();
            } else{
                //we aren't in a tiebreak but rather in a normal game
                this.currentGame.addPlayer1Score();
            }
            checkCurrentGameOver();
            checkTBNeeded();
            checkSetOver();
        } else{
            throw new ScoreChangeInvalidException("Tried to add score to a set that's over!");
        }
    }

    @Override
    public void addPlayer2Score() throws ScoreChangeInvalidException {
        if(!this.setOver){
            if(this.setTiebreak!=null){
                //if this set has gone to a tiebreak, add the score to the tiebreak score
                this.setTiebreak.addPlayer2Score();
            } else{
                //we aren't in a tiebreak but rather in a normal game
                this.currentGame.addPlayer2Score();
            }
            checkCurrentGameOver();
            checkTBNeeded();
            checkSetOver();
        } else{
            throw new ScoreChangeInvalidException("Tried to add score to a set that's over!");
        }
    }

    private void checkCurrentGameOver(){
        if(this.currentGame.isGameOver()){
            if(this.currentGame.didPlayer1Win()){
                this.player1Games++;
            } else{
                this.player2Games++;
            }
            this.currentGame = new GameScore(); //start a new game if the current one has ended
        }
    }

    //checks if the set needs to go to a Tiebreak and creates a tiebreak score object if needed
    private void checkTBNeeded(){
        if(this.player1Games==6 && this.player2Games==6 && this.setTiebreak==null){
            this.setTiebreak = new TiebreakScore();
        }
    }

    //checks if the set has been completed and sets the setOver and player1Won fields accordingly
    private void checkSetOver(){

        //check for win condition for a normal set
        int gameDiff = this.player1Games - this.player2Games;
        if(this.player1Games >= 6 && gameDiff >= 2){ // win condition for player 1
            this.setOver = true;
            this.player1Won = true;
            this.currentGame = null;
        } else if(this.player2Games >= 6 && gameDiff <= -2){
            this.setOver = true;
            this.player1Won = false;
            this.currentGame = null;
        }

        //check win condition for a tiebreak
        if(!this.setOver && this.setTiebreak != null){
            if(this.setTiebreak.didPlayer1Win()){ //tiebreak over and player 1 victorious
                this.setOver = true;
                this.player1Won = true;
                this.player1Games += 1;
            } else if(this.setTiebreak.isTiebreakOver()){  //tiebreak over but player 1 lost
                this.setOver = true;
                this.player1Won = false;
                this.player2Games += 1;
            }
        }
    }

    public boolean isSetOver() {
        return this.setOver;
    }

    public boolean didPlayer1Win() {
        return this.setOver && this.player1Won;
    }

    public boolean isSetStarted(){
        return this.player1Games!=0 || this.player2Games!=0;
    }

    public boolean servingToDeuceSide(){
        if(!this.isSetOver()){
            if(this.setTiebreak == null){ //if the set is NOT in a tiebreak
                return this.currentGame.servingToDeuceSide();
            } else { //if the set IS in a tiebreak
                return this.setTiebreak.servingToDeuceSide();
            }
        } else return false;
    }

    public int getServingPlayer(){
        if(!this.isSetOver()){
            if(this.setTiebreak == null){ //if the set is NOT in a tiebreak
                return (this.player1Games + this.player2Games) % 2 + 1;  //returns 1 if the sum of games is even and 2 if the sum of games is odd
            } else { //if the set IS in a tiebreak
                return this.setTiebreak.getServingPlayer();
            }
        } else return 1;
    }

    public String toString(){
        String ret = this.player1Games + "-" + this.player2Games + " ";
        if(this.setTiebreak!=null && this.setTiebreak.isTiebreakOver()){
            ret += this.setTiebreak.toScoreLine() + " ";
        }
        return ret;
    }

    public String getFullScore(){
        String ret = this.player1Games + "-" + this.player2Games + " ";
        if(this.setTiebreak!=null && this.setTiebreak.isTiebreakOver()){
            ret += this.setTiebreak.toScoreLine() + " ";
        }
        if(this.currentGame!=null && !this.currentGame.isGameOver() && this.setTiebreak==null){
            ret += "\n" + this.currentGame.toString();
        }
        return ret;
    }

    //method to test class
//    public static void main(String[] args){
//        SetScore set = new SetScore();
//        Random generator = new Random();
//
//        while(!set.isSetOver()){
//            int rand = generator.nextInt(2);
//            try {
//                if(rand==1){
//                    set.addPlayer1Score();
//                } else{
//                    set.addPlayer2Score();
//                }
//            } catch(Exception ex){
//                System.out.println(ex.getMessage());
//            }
//            System.out.println(set.toString());
//        }
//    }
}
