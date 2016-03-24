package Player;

import Board.Board;
import WordCollection.Letter;

import java.util.ArrayList;
import java.util.List;

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
    public void makeMove() {
        
    }

}
