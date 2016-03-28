package Player;

import Board.*;
import Game.Bag;
import Move.Move;
import WordCollection.Letter;
import Move.*;

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
    private Move bestMove;

    public AgentFresco(Bag bag, Board board) {
        this.scoreHistory = new ArrayList<Integer>();
        this.rack = new ArrayList<Letter>();
        this.totalScore = 0;
        this.board = board;
        fillRack(bag);
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

    @Override
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

        bestMove = null;

        List<Move> moves = new ArrayList<Move>();

        for(int i = 0; i < board.getBoardSize(); i++) {
            for(int j = 0; j < board.getBoardSize(); j++) {
                if(board.getBoard()[i][j].isAnchor()) {
                    findMoves(board.getBoard()[i][j], Direction.HORIZONTAL);
                    findMoves(board.getBoard()[i][j], Direction.VERTICAL);
                }
            }
        }

        removeFromRack(bestMove.getWord());
        scoreHistory.add(bestMove.getScore());
        totalScore += bestMove.getScore();

        return bestMove;
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

    public void saveBestMove(Move move) {
        if(bestMove == null || bestMove.getScore() < move.getScore()) {
            bestMove = move;
        }
    }

    public void findMoves(Square square, Direction direction) {

        /* Start by finding the possible left permutations of letters in the rack next to the anchor square */
        findCrossCheckSets(direction);
        List<String> leftPermutations = findLeftPermutations(square, direction, rack);
        //List<String> leftPermutationsVertical = findLeftPermutations(square, Direction.VERTICAL, rack);


        /* For every string we found, try to extend that word to the right*/
        for(String s : leftPermutations) {
            List<Letter> remainingRack = remainingRack(rack, s);
            extendRight(square, remainingRack, s, Direction.HORIZONTAL);
        }
    }

    private void extendRight(Square square, List<Letter> remainingRack, String word, Direction direction) {


        if(direction.equals(Direction.HORIZONTAL)) {
            if(square.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                if(board.getWordCollection().getDawg().contains((word + square.getValue()).toLowerCase())) {
                    saveBestMove(new Move(this, square.getX(), square.getY() - word.length(), direction, word + square.getValue()));
                }
                else if(board.getWordCollection().getDawg().getStringsStartingWith((word + square.getValue()).toLowerCase()).iterator().hasNext()){
                    if(square.getY() < board.getBoardSize() - 1) {
                        extendRight(board.getBoard()[square.getX()][square.getY() + 1], remainingRack(remainingRack, square.getValue()), word + square.getValue(), direction);
                    }
                    else {
                        return;
                    }
                }
            }
            else {
                for(Letter l : square.getCrossCheckSetHorizontal()) {
                    if(board.getWordCollection().getDawg().contains((word + l.getLetter()).toLowerCase()) && remainingRack.contains(l)) {
                        saveBestMove(new Move(this, square.getX(), square.getY() - word.length(), direction, word + l.getLetter()));
                    }
                    else if(board.getWordCollection().getDawg().getStringsStartingWith((word + l.getLetter()).toLowerCase()).iterator().hasNext() && remainingRack.contains(l)){
                        if(square.getY() < board.getBoardSize() - 1) {
                            extendRight(board.getBoard()[square.getX()][square.getY() + 1], remainingRack(remainingRack, l.getLetter()), word + l.getLetter(), direction);
                        }
                        else {
                            return;
                        }
                    }
                }
            }
        }
        else {
            for(Letter l : square.getCrossCheckSetVertical()) {
                if(board.getWordCollection().getDawg().contains((word + l.getLetter()).toLowerCase()) && remainingRack.contains(l)) {

                    saveBestMove(new Move(this, square.getX() - word.length(), square.getY(), direction, word + l.getLetter()));

                }
                else if(board.getWordCollection().getDawg().getStringsStartingWith((word + l.getLetter()).toLowerCase()).iterator().hasNext() && remainingRack.contains(l)){
                    if(square.getX() < board.getBoardSize() - 1) {
                        extendRight(board.getBoard()[square.getX() + 1][square.getY()], remainingRack(remainingRack, l.getLetter()), word + l.getLetter(), direction);
                    }
                    else {
                        return;
                    }
                }
            }
        }

    }

    public List<String> findLeftPermutations(Square square, Direction direction, List<Letter> rack) {

        List<String> permutations = new ArrayList<String>();

        int maxLeft = 0;

        if(direction.equals(Direction.HORIZONTAL)) {
            Square leftSquare = null;
            if(square.getY() > 0) {
                leftSquare = board.getBoard()[square.getX()][square.getY() - 1];
            }
            while(leftSquare != null && leftSquare.getY() > 0 && !leftSquare.isAnchor()) {
                maxLeft++;
                leftSquare = board.getBoard()[leftSquare.getX()][leftSquare.getY() - 1];
            }
            if(!leftSquare.isAnchor() && !leftSquare.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                maxLeft++;
            }

            permutation("", rack, permutations, maxLeft);
            return permutations;
        }
        else {
            Square leftSquare = null;
            if(square.getY() > 0) {
                leftSquare = board.getBoard()[square.getX()][square.getY() - 1];
            }
            while(leftSquare != null && leftSquare.getX() > 0 && !leftSquare.isAnchor()) {
                maxLeft++;
                leftSquare = board.getBoard()[leftSquare.getX() - 1][leftSquare.getY()];
            }
            if(!leftSquare.isAnchor() && !leftSquare.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                maxLeft++;
            }

            permutation("", rack, permutations, maxLeft);
            return permutations;
        }
    }

    private void permutation(String prefix, List<Letter> rack, List<String> permutations, int maxLeft) {

        if(prefix.length() <= maxLeft) {
            int n = rack.size();

            Iterable<String> startingWith = board.getWordCollection().getDawg().getStringsStartingWith(prefix.toLowerCase());

            if (startingWith.iterator().hasNext() && prefix.length() > 0) {
                permutations.add(prefix);
            }

            for (int i = 0; i < n; i++) {
                if(i < rack.size()) {
                    permutation(prefix + rack.get(i).getLetter(), rack.subList(i + 1, rack.size()), permutations, maxLeft);
                }
            }
        }
    }

    private String leftWord(Square square, Direction direction) {
        if(square.getValue().equals("")) {
            return "";
        }
        if(direction.equals(Direction.HORIZONTAL)) {
            if(square.getY() > 0) {
                return leftWord(board.getBoard()[square.getX()][square.getY() - 1], Direction.HORIZONTAL) + square.getValue();
            } else {
                return "";
            }
        } else if(direction.equals(Direction.VERTICAL)) {
            if(square.getX() > 0) {
                return leftWord(board.getBoard()[square.getX() - 1][square.getY()], Direction.VERTICAL) + square.getValue();
            } else {
                return "";
            }
        }
        return "";
    }

    private String rightWord(Square square, Direction direction) {
        if(square.getValue().equals("")) {
            return "";
        }
        if(direction.equals(Direction.HORIZONTAL)) {
            if(square.getY() < board.getBoardSize() - 1) {
                return  square.getValue() + rightWord(board.getBoard()[square.getX()][square.getY() + 1], Direction.HORIZONTAL);
            } else {
                return "";
            }
        } else if(direction.equals(Direction.VERTICAL)) {
            if(square.getX() < board.getBoardSize() - 1) {
                return square.getValue() + rightWord(board.getBoard()[square.getX() + 1][square.getY()], Direction.VERTICAL);
            } else {
                return "";
            }
        }
        return "";
    }

    private List<Letter> remainingRack(List<Letter> rack, String word) {

        List<Letter> remainingRack = new ArrayList<>();
        for(Letter l : rack) {
            remainingRack.add(l);
        }
        for(int i = 0; i < word.length(); i++) {
            for(Letter l : remainingRack) {
                if(l.getLetter().equals(Character.toString(word.charAt(i)).toUpperCase())) {
                    remainingRack.remove(l);
                    break;
                }
            }
        }
        return remainingRack;
    }

    public void findCrossCheckSets(Direction direction) {

        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if(!board.getBoard()[i][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    if(board.getBoard()[i][j].isAnchor()) {
                        if(direction.equals(Direction.HORIZONTAL)) {
                            board.getBoard()[i][j].setCrossCheckSetHorizontal(new ArrayList<Letter>());
                            findCrossCheckSets(board.getBoard()[i][j], Direction.HORIZONTAL, rack);
                        }
                        else {
                            board.getBoard()[i][j].setCrossCheckSetVertical(new ArrayList<Letter>());
                            findCrossCheckSets(board.getBoard()[i][j], Direction.HORIZONTAL, rack);
                        }

                    }
                }
            }
        }

    }

    private void findCrossCheckSets(Square square, Direction direction, List<Letter> rack) {
        String leftWord = "";
        String rightWord = "";
        if(direction.equals(Direction.HORIZONTAL)) {
            if(square.getY() > 0) {
                leftWord = leftWord(board.getBoard()[square.getX()][square.getY() - 1], direction);
            }
            if(square.getY() < board.getBoardSize() - 1) {
                rightWord = rightWord(board.getBoard()[square.getX()][square.getY() + 1], direction);
            }
        }
        else {
            if(square.getX() > 0) {
                leftWord = leftWord(board.getBoard()[square.getX() - 1][square.getY()], direction);
            }
            if(square.getX() < board.getBoardSize() - 1) {
                rightWord = rightWord(board.getBoard()[square.getX() + 1][square.getY()], direction);
            }
        }
        if(rightWord.equals("") && leftWord.equals("")) {
            for(Letter l : rack) {
                if(direction.equals(Direction.HORIZONTAL)) {
                    square.getCrossCheckSetHorizontal().add(l);
                }else {
                    square.getCrossCheckSetVertical().add(l);
                }
            }
        }
        else {
            for(Letter l : rack) {
                if(board.getWordCollection().getDawg().contains((leftWord + l.getLetter() + rightWord).toLowerCase())) {
                    if(direction.equals(Direction.HORIZONTAL)) {
                        square.getCrossCheckSetHorizontal().add(l);
                    }else {
                        square.getCrossCheckSetVertical().add(l);
                    }
                }
            }
        }
    }


}
