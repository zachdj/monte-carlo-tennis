package main.score;

/**
 * Requires implementing classes to have a "addPlayer1Score" and "AddPlayer2Score"
 */
public interface Score {

    void addPlayer1Score() throws ScoreChangeInvalidException;
    void addPlayer2Score() throws ScoreChangeInvalidException;
}
