package Player;

import Board.*;
import Move.*;
import WordCollection.Letter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class HumanPlayer implements Player {
    int MAX_TILES_ON_HAND = 8;
    List<Letter> currentTiles;
    List<Integer> scoreHistory;
    Board board;

    public HumanPlayer(List<Letter> currentTiles, Board board) {
        this.currentTiles = currentTiles;
        this.scoreHistory = new ArrayList<Integer>();
        this.board = board;
    }

    //region getters and setters
    public int getMAX_TILES_ON_HAND() {
        return MAX_TILES_ON_HAND;
    }

    public List<Letter> getCurrentTiles() {
        return currentTiles;
    }

    public void setCurrentTiles(List<Letter> currentTiles) {
        this.currentTiles = currentTiles;
    }

    public List<Integer> getScoreHistory() {
        return scoreHistory;
    }

    public void setScoreHistory(List<Integer> scoreHistory) {
        this.scoreHistory = scoreHistory;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    //endregion getters an

    @Override
    public Move makeMove() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the x coordinates of the word");
        int x = in.nextInt();
        System.out.println("Enter the y coordinates of the word");
        int y = in.nextInt();
        String input = null;
        Direction direction = null;
        do {
            System.out.println("Press H/h for horizontal word, V/v for vertical");
            input = in.nextLine();
            if(input.equals("H") || input.equals("h")){
                direction = Direction.HORIZONTAL;
            } else if (input.equals("V") || input.equals("v")){
                direction = Direction.VERTICAL;
            }
        } while(input.equals("H") || input.equals("h") || input.equals("V") || input.equals("v") || input.equals("v"));
        System.out.println("Input word to enter in field x:" + x + " y: " + y + " direction: " + direction);
        String wordToReturn = in.nextLine();

        return new Move(this, x, y, direction, wordToReturn);
    }

}
