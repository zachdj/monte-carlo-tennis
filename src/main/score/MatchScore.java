package main.score;

import java.util.Random;

/**
 * MatchScore objects represent the score of a tennis match
 */
public class MatchScore implements Score {
    private int maxSets;  // 3 for best of 3, 5 for best of 5
    private SetScore[] sets;

    private boolean matchOver;
    private boolean player1Won;

    //default constructor assumes best of 3 sets (most common)
    public MatchScore(){
        this.maxSets = 3;
        this.sets = new SetScore[this.maxSets];
        for(int i = 0; i < this.sets.length; i++){
            this.sets[i] = new SetScore();
        }
        this.matchOver = false;
        this.player1Won = false;
    }

    /**
     * Constructs a matchScore with the given number of maximum sets.
     * If maxSets is an even number, this.maxSets will be set to maxSets+1 to eliminate the possibility of a tie
     * @param maxSets maximum possible number of sets.  Should be an odd number.  e.g. maxSets = 3 results in a best of 3 set match
     */
    public MatchScore(int maxSets){
        this.maxSets = (maxSets%2==0) ? maxSets+1 : maxSets;
        this.sets = new SetScore[this.maxSets];
        for(int i = 0; i < this.sets.length; i++){
            this.sets[i] = new SetScore();
        }
        this.matchOver = false;
        this.player1Won = false;
    }

    @Override
    public void addPlayer1Score() throws ScoreChangeInvalidException {
        if(!this.isMatchOver()){
            //find the first unfinished set and add the point to that set
            for(int i = 0; i < this.sets.length; i++){
                if(!this.sets[i].isSetOver()){
                    this.sets[i].addPlayer1Score();
                    break;
                }
            }
        } else{
            throw new ScoreChangeInvalidException("Tried to add score to a match that was over!");
        }
        this.checkMatchOver();
    }

    @Override
    public void addPlayer2Score() throws ScoreChangeInvalidException {
        if(!this.isMatchOver()){
            //find the first unfinished set and add the point to that set
            for(int i = 0; i < this.sets.length; i++){
                if(!this.sets[i].isSetOver()){
                    this.sets[i].addPlayer2Score();
                    break;
                }
            }
        } else{
            throw new ScoreChangeInvalidException("Tried to add score to a match that was over!");
        }
        this.checkMatchOver();
    }

    private void checkMatchOver(){
        int setsRequired = this.maxSets/2  + 1;  // Note the intentional integer division
        int player1Sets = 0;
        int player2Sets = 0;

        for(int i = 0; i < this.sets.length; i++){
            if(this.sets[i] != null && this.sets[i].isSetOver()){
                if(this.sets[i].didPlayer1Win()){
                    player1Sets++;
                } else{
                    player2Sets++;
                }
            }
        }
        //check if one player or the other has won
        if(player1Sets >= setsRequired){
            this.matchOver = true;
            this.player1Won = true;
        } else if(player2Sets >= setsRequired){
            this.matchOver = true;
            this.player1Won = false;
        }
    }

    public String toString(){
        String ret = "";
        for(int i = 0; i < this.sets.length; i++){
            if(this.sets[i].isSetStarted()){
                ret += this.sets[i].toString();
            }
        }
        return ret;
    }

    public String getFullScore(){
        String ret = "";
        for(int i = 0; i < this.sets.length; i++){
            if(this.sets[i].isSetStarted()){
                ret += this.sets[i].getFullScore();
            }
        }
        return ret;
    }

    public boolean isMatchOver(){
        return this.matchOver;
    }

    public boolean didPlayer1Win(){
        return this.matchOver && this.player1Won;
    }

    public boolean servingToDeuceSide(){
        for(int i = 0; i < this.sets.length; i++){
            //Find the first set that's not over, and call that set's servingToDeuceSide method
            if(!this.sets[i].isSetOver()){
                return this.sets[i].servingToDeuceSide();
            }
        }
        return false;  //This statement will only be reached if the match is over (in which case it doesn't matter which side)
    }

    /**
     * Determines which player should currently be serving.
     * @return 1 for player1, 2 for player2
     */
    public int getServingPlayer(){
        for(int i = 0; i < this.sets.length; i++){
            //Find the first set that's not over, and call that set's getServingPlayer method to find the currently serving player
            if(!this.sets[i].isSetOver()){
                return this.sets[i].getServingPlayer();
            }
        }
        return 1;  //This statement will only be reached if the match is over (in which case it doesn't matter who is serving)
    }

//    public static void main(String[] args){
//        MatchScore match = new MatchScore(1);
//        Random generator = new Random();
//
//        while(!match.isMatchOver()){
//            int rand = generator.nextInt(2);
//            try{
//                if(rand==1){
//                    match.addPlayer1Score();
//                } else{
//                    match.addPlayer2Score();
//                }
//            } catch (Exception ex){
//                System.out.println(ex.getMessage());
//            }
//        }
//        System.out.println(match.didPlayer1Win());
//        System.out.println(match.toString());
//    }
}
