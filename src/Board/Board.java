package Board;

import WordCollection.*;
import Move.*;
import Player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    //region getter and setters
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public Square[][] getBoard() {
        return board;
    }

    public WordCollection getWordCollection() {
        return wordCollection;
    }

    public void setWordCollection(WordCollection wordCollection) {
        this.wordCollection = wordCollection;
    }
    //endregion getter and setters

    /* After each player's turn, update the anchor squares. Anchor is an empty square next to a square that contains a letter */
    public void updateAnchors() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setAnchor(false);

                if(!board[i][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    if(i < BOARD_SIZE - 1 && board[i + 1][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i][j].setAnchor(true);
                    }
                    if(i > 0 && board[i - 1][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i][j].setAnchor(true);
                    }
                    if(j < BOARD_SIZE - 1 && board[i][j + 1].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i][j].setAnchor(true);
                    }
                    if(j > 0 && board[i][j - 1].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        board[i][j].setAnchor(true);
                    }
                }
            }
        }

    }


    /* A function to update the move when the agent is looking ahead */
    public void updateBoard(Move move) {
        if(move.getDirection().equals(Direction.HORIZONTAL)) {
            for(int i = move.getY(); i < move.getWord().length() + move.getY(); i++) {
                board[move.getX()][i].setValue(Character.toString(move.getWord().charAt(i - move.getY())).toUpperCase());
                board[move.getX()][i].setSquareType(SquareType.CONTAINS_LETTER);
            }
        }
        else {
            for(int i = move.getX(); i < move.getWord().length() + move.getX(); i++) {
                board[i][move.getY()].setValue(Character.toString(move.getWord().charAt(i - move.getX())).toUpperCase());
                board[i][move.getY()].setSquareType(SquareType.CONTAINS_LETTER);
            }
        }
    }

}
