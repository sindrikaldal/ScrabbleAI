package Board;

import WordCollection.*;
import Move.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class Board {

    private int BOARD_SIZE = 15;
    private Square board[][];
    private WordCollection wordCollection;

    public Board(WordCollection wordCollection) {
        this.wordCollection = wordCollection;
        initializeBoard();
    }


    /* Initalize the board. Assign the empty string and the approproate squaretype to each square */
    private void initializeBoard() {
        board = new Square[this.BOARD_SIZE][this.BOARD_SIZE];

        for(int i = 0; i < this.BOARD_SIZE; i++) {
            for(int j = 0; j < this.BOARD_SIZE; j++) {
                if(i == 0 || i == 14) {
                    if(j % 7 == 0) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_WORD, "", i, j);
                    }
                    else if(j == 3 || j == 11) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "", i, j);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }
                }
                else if(i == 1 || i == 13) {
                    if(j == 1 || j == 13) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "", i, j);
                    }
                    else if(j == 5 || j == 9) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_LETTER, "", i, j);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }

                }
                else if(i == 2 || i == 12) {
                    if(j == 2 || j == 12) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "", i, j);
                    }
                    else if(j == 6 || j == 8) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "", i, j);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }
                }
                else if(i == 3 || i == 11) {
                    if(j % 7 == 0) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "", i, j);
                    }
                    else if(j == 3 || j == 11   ) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "", i, j);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }
                }
                else if(i == 4 || i == 10) {
                    if(j == 4 || j == 10) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "", i, j);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }
                }
                else if(i == 5 || i == 9) {
                    if( j == 1 || j == 5 || j == 9 || j == 13) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_LETTER, "", i, j);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }
                }
                else if(i == 6 || i == 8) {
                    if( j == 2 || j == 6 || j == 8 || j == 12) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "", i, j);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }
                }
                else if(i == 7) {
                    if(j == 0 || j == 14) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_WORD, "", i, j);
                    }
                    else if(j == 7) {
                        this.board[i][j] = new Square(SquareType.CENTER_SQUARE, "", i, j);
                        this.board[i][j].setAnchor(true);
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "", i, j);
                    }
                }
            }
        }
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public Square[][] getBoard() {
        return board;
    }

    /* Check whether there are any squares adjacent to the given square that contain a letter */
    public boolean hasAdjacentSquares(Square square) {

        if(square.getX() > 0 && board[square.getX() - 1][square.getY()].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return true;
        }
        else if(square.getX() < (BOARD_SIZE - 1) && board[square.getX() + 1][square.getY()].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return true;
        }
        else if(square.getY() > 0 && board[square.getX()][square.getY() - 1].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return true;
        }
        else if(square.getY() < (BOARD_SIZE - 1) && board[square.getX()][square.getY() + 1].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return true;
        }
        return false;
    }

    public Move findMoves(Square square, List<Letter> rack) {
        List<String> crossChecks = new ArrayList<String>();
        crossChecks.addAll(findCrossCheckSets(square, Direction.HORIZONTAL));
        crossChecks.addAll(findCrossCheckSets(square, Direction.VERTICAL));

        return null;
    }

    public List<String> findCrossCheckSets(Square square, Direction direction) {
        return null;
    }

    /* After each player's turn, update the anchor squares. Anchor is an empty square next to a square that contains a letter */
    public void updateAnchors() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(board[i][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    if(i < BOARD_SIZE - 1 && !board[i + 1][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i + 1][j].setAnchor(true);
                    }
                    if(i > 0 && !board[i - 1][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i - 1][j].setAnchor(true);
                    }
                    if(j < BOARD_SIZE - 1 && !board[i][j + 1].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i][j + 1].setAnchor(true);
                    }
                    if(j > 0 && !board[i][j - 1].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i][j - 1].setAnchor(true);
                    }
                }
            }
        }
    }

}
