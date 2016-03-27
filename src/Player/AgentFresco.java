package Player;

import Board.Board;
import Game.Bag;
import Move.Move;
import WordCollection.Letter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class AgentFresco implements Player {

    private final int MAX_TILES_ON_HAND = 7;
    private List<Letter> rack;
    private List<Integer> scoreHistory;
    private Board board;
    private int totalScore;
    private Move nextMove;


    public AgentFresco(Bag bag, Board board) {
        this.scoreHistory = new ArrayList<Integer>();
        this.rack = new ArrayList<Letter>();
        this.totalScore = 0;
        this.board = board;
        fillRack(bag);
        nextMove = null;
    }

    //region getters and setters
    public int getMAX_TILES_ON_HAND() {
        return MAX_TILES_ON_HAND;
    }

    public void setRack(List<Letter> rack) {
        this.rack = rack;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    //endregion getters and setters

    @Override
    public Move makeMove() {

        List<Move> moves = new ArrayList<Move>();

        for(int i = 0; i < board.getBoardSize(); i++) {
            for(int j = 0; j < board.getBoardSize(); j++) {
                if(board.getBoard()[i][j].isAnchor()) {
                    moves.add(board.findMoves(board.getBoard()[i][j], rack));
                }
            }
        }
        return null;
    }

    @Override
    public void fillRack(Bag bag) {
        Random random = new Random();
        int rackSize = rack.size();
        for(int i = 0; i < (MAX_TILES_ON_HAND - rackSize); i++) {
            int randomNumber = random.nextInt(bag.getBag().size());
            rack.add(bag.getBag().get(randomNumber));
            bag.getBag().remove(randomNumber);
        }
    }

    @Override
    public List<Letter> getRack() {
        return rack;
    }

    @Override
    public void removeFromRack(String word) {
        for(int i = 0; i < word.length(); i++) {
            for(int j = 0; j < rack.size(); j++) {
                if(rack.get(j).getLetter().equals(Character.toString(word.charAt(i)).toUpperCase())) {
                    rack.remove(j);
                    break;
                }
            }
        }
    }

    public void checkBestMove(Move move) {

    }

}
