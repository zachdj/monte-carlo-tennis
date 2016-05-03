package main.score;

/*
Thrown when a score change is invalid
 */

public class ScoreChangeInvalidException extends Exception {

    public ScoreChangeInvalidException(String message){
        super(message);
    }

}
