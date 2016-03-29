package Score;

import Board.Square;
import Move.*;
import Player.*;
import WordCollection.*;

/**
 * Created by sindrikaldal on 27/03/16.
 */
public class Score {

    Player player;

    public Score(Player player) {
        this.player = player;
    }

    public int score(Move move) {

        /* The score to be returned */
        int score = 0;
        /* The word multiplier of the word */
        int wordMultiplier = 1;


        if(move.getDirection().equals(Direction.HORIZONTAL)) {
            for(int i = 0; i < move.getWord().length(); i++) {
                for(Letter l : player.getBoard().getWordCollection().getLetters()) {
                    /* Find the value of each letter in the word and add it to the score. Multiple the letter multipier of the square if there exists one*/
                    if(Character.toString(move.getWord().charAt(i)).equals(l.getLetter())) {
                        score += l.getValue() * letterMultiplier(player.getBoard().getBoard()[move.getX()][move.getY() + i]);
                    }
                    /* If the current square is a word multiplier square, raise the current wordmultiplier*/
                    if(wordMultiplier(player.getBoard().getBoard()[move.getX()][move.getY() + i]) > wordMultiplier) {
                        wordMultiplier = wordMultiplier(player.getBoard().getBoard()[move.getX()][move.getY() + i]);
                    }
                }
            }
        }
        /* The same as above but raise the value of x instead of y since it vertical. */
        else {
            for(int i = 0; i < move.getWord().length(); i++) {
                for(Letter l : player.getBoard().getWordCollection().getLetters()) {
                    if(move.getX() < player.getBoard().getBoardSize() - 1) {
                        if(Character.toString(move.getWord().charAt(i)).equals(l.getLetter())) {
                            score += l.getValue() * letterMultiplier(player.getBoard().getBoard()[move.getX() + i][move.getY()]);
                        }
                        if(wordMultiplier(player.getBoard().getBoard()[move.getX() + i][move.getY()]) > wordMultiplier) {
                            wordMultiplier = wordMultiplier(player.getBoard().getBoard()[move.getX() + i][move.getY()]);
                        }
                    }
                }
            }
        }

        return score * wordMultiplier;
    }

    private int wordMultiplier(Square square) {
        switch(square.getSquareType()) {
            case TRIPLE_WORD:
                return 3;
            case DOUBLE_WORD:
                return 2;
            default:
                return 1;
        }
    }

    private int letterMultiplier(Square square) {
        switch(square.getSquareType()) {
            case TRIPLE_LETTER:
                return 3;
            case DOUBLE_LETTER:
                return 2;
            default:
                return 1;
        }

    }

    private int adjacentWordsScore() {
        return 0;
    }
}
