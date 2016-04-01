package Score;

import Board.Square;
import Board.SquareType;
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
        int crosswordScore = 0;
        int bonusScore = 0;
        int tilesLaidOut = 0;
        /* The word multiplier of the word */
        int wordMultiplier = 1;


        if(move.getDirection().equals(Direction.HORIZONTAL)) {
            for(int i = 0; i < move.getWord().length(); i++) {

                /* If the square we're about to lay a letter on doesn't contain a letter, check bot right and left if
                 * there are word's we've added a letter to. */
                if(!player.getBoard().getBoard()[move.getX()][move.getY() + i].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    tilesLaidOut++;
                    if(move.getX() > 0) {
                        crosswordScore += adjacentLeftWordsScore(player.getBoard().getBoard()[move.getX() - 1][move.getY() + i], move.getDirection());
                    }
                    if(move.getX() < player.getBoard().getBoardSize() - 1) {
                        crosswordScore += adjacentRightWordsScore(player.getBoard().getBoard()[move.getX() + 1][move.getY() + i], move.getDirection());
                    }
                }
                for(Letter l : player.getBoard().getWordCollection().getLetters()) {
                    /* Find the value of each letter in the word and add it to the score. Multiple the letter multipier of the square if there exists one*/
                    if(Character.toString(move.getWord().charAt(i)).equals(l.getLetter())) {
                        score += l.getValue() * letterMultiplier(player.getBoard().getBoard()[move.getX()][move.getY() + i]);
                    }
                    /* If the current square is a word multiplier square, raise the current wordmultiplier*/
                    if(wordMultiplier(player.getBoard().getBoard()[move.getX()][move.getY() + i]) > wordMultiplier) {
                        wordMultiplier *= wordMultiplier(player.getBoard().getBoard()[move.getX()][move.getY() + i]);
                    }
                }
            }
        }
        /* The same as above but raise the value of x instead of y since it vertical. */
        else {
            for(int i = 0; i < move.getWord().length(); i++) {
                if(!player.getBoard().getBoard()[move.getX() + i][move.getY()].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    tilesLaidOut++;
                    if(move.getY() > 0) {
                        crosswordScore += adjacentLeftWordsScore(player.getBoard().getBoard()[move.getX() + i][move.getY() - 1], move.getDirection());
                    }
                    if(move.getY() < player.getBoard().getBoardSize() - 1) {
                        crosswordScore += adjacentRightWordsScore(player.getBoard().getBoard()[move.getX() + i][move.getY() + 1], move.getDirection());
                    }
                }
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

        if(tilesLaidOut == 7) {
            bonusScore = 50;
        }
        return (score * wordMultiplier) + crosswordScore + bonusScore;
    }


    private int wordMultiplier(Square square) {
        switch(square.getSquareType()) {
            case TRIPLE_WORD:
                return 3;
            case DOUBLE_WORD:
                return 2;
            case CENTER_SQUARE:
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

    private int adjacentLeftWordsScore(Square square, Direction direction) {

        if(!square.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return 0;
        }
        for(Letter l : player.getBoard().getWordCollection().getLetters()) {
            if(square.getValue().equals(l.getLetter())) {
                if(direction.equals(Direction.HORIZONTAL)) {
                    if(square.getX() > 0) {
                        return adjacentLeftWordsScore(player.getBoard().getBoard()[square.getX() - 1][square.getY()], direction) +l.getValue();
                    }
                    else {
                        return l.getValue();
                    }
                }
                else {
                    if(square.getY() > 0) {
                        return adjacentLeftWordsScore(player.getBoard().getBoard()[square.getX()][square.getY() - 1], direction) + l.getValue();
                    }
                    else {
                        return l.getValue();
                    }
                }
            }
        }
        return 0;
    }

    private int adjacentRightWordsScore(Square square, Direction direction) {

        if(!square.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return 0;
        }

        for(Letter l : player.getBoard().getWordCollection().getLetters()) {
            if(square.getValue().equals(l.getLetter())) {
                if(direction.equals(Direction.HORIZONTAL)) {
                    if(square.getX() < player.getBoard().getBoardSize() - 1) {
                        return adjacentRightWordsScore(player.getBoard().getBoard()[square.getX() + 1][square.getY()], direction) +l.getValue();
                    }
                    else {
                        return l.getValue();
                    }
                }
                else {
                    if(square.getY() < player.getBoard().getBoardSize() - 1) {
                        return adjacentRightWordsScore(player.getBoard().getBoard()[square.getX()][square.getY() + 1], direction) + l.getValue();
                    }
                    else {
                        return l.getValue();
                    }
                }
            }
        }
        return 0;
    }
}
