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
        /* The word multiplier of the word */
        int wordMultiplier = 1;


        if(move.getDirection().equals(Direction.HORIZONTAL)) {
            for(int i = 0; i < move.getWord().length(); i++) {
                // for every letter in word, call crossWordScore
                // TODO: facta �tt
                // check if curr square is empty BEFORE placing the word
                if(!player.getBoard().getBoard()[move.getX()][move.getY() + i].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    //score += crossWordScore(move.getX(), move.getY() + i, Direction.HORIZONTAL);
                }
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
                // for every letter in word, call crossWordScore
                // TODO: facta �tt
                // check if curr square is empty BEFORE placing the word
                if(player.getBoard().getBoard()[move.getX()][move.getY()].getSquareType() != SquareType.CONTAINS_LETTER) {
                    /*System.out.println("word to check, x: " + move.getX() + " y: " + move.getY() + " currletter: "
                            + player.getBoard().getBoard()[move.getX() + i][move.getY()].getValue());
                    score += crossWordScore(move.getX() + i, move.getY(), Direction.VERTICAL);*/
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

        return score * wordMultiplier;
    }

    /**
     *
     * @param x coordinates of letter
     * @param y coordinates of letter
     * @param direction of word being placed, if horizontal we check vertical crosswords and vice versa
     * @return the score of the crosswords created by placing this letter
     */
    private int crossWordScore(int x, int y, Direction direction) {

        int score = 0;
        Square initSquare = player.getBoard().getBoard()[x][y];

        if(direction.equals(Direction.HORIZONTAL)) {
            // add letters above to crossWordScore
            for(Square s = initSquare; s.getSquareType().equals(SquareType.CONTAINS_LETTER) && s.getX() > 0; s = player.getBoard().getBoard()[x - 1][y]) {
                System.out.println("loop 1");
                // go through all letters
                for(Letter l : player.getBoard().getWordCollection().getLetters()) {
                    if(s.getValue() == l.getLetter()) {
                        score += l.getValue();
                    }
                }
            }
            // add letters below to crossWordScore
            //TODO: facta x-lengd, facta ==
            for(Square s = initSquare; s.getSquareType().equals(SquareType.CONTAINS_LETTER) && s.getX() < 14; s = player.getBoard().getBoard()[x + 1][y]) {
                System.out.println("loop 2");
                // go through all letters
                for(Letter l : player.getBoard().getWordCollection().getLetters()) {
                    if(s.getValue() == l.getLetter()) {
                        score += l.getValue();
                    }
                }
            }
        } else {
            // add letters to the right to crossWordScore
            for(Square s = initSquare; s.getSquareType().equals(SquareType.CONTAINS_LETTER) && s.getY() > 0; s = player.getBoard().getBoard()[x][y - 1]) {
                System.out.println("loop 3");
                // go through all letters
                for(Letter l : player.getBoard().getWordCollection().getLetters()) {
                    if(s.getValue() == l.getLetter()) {
                        score += l.getValue();
                    }
                }
            }
            // add letters below to crossWordScore
            //TODO: facta x-lengd
            for(Square s = initSquare; s.getSquareType().equals(SquareType.CONTAINS_LETTER) && s.getY() < 14; s = player.getBoard().getBoard()[x][y + 1]) {
                System.out.println("loop 4");
                // go through all letters
                for(Letter l : player.getBoard().getWordCollection().getLetters()) {
                    if(s.getValue() == l.getLetter()) {
                        score += l.getValue() * letterMultiplier(s);
                    }
                }
            }
        }
        return score;
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
