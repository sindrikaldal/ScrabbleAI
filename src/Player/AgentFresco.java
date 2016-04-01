package Player;

import Board.*;
import Game.Bag;
import Move.Move;
import WordCollection.Letter;
import Move.*;

import java.util.*;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class AgentFresco implements Player {

    private final int MAX_TILES_ON_HAND = 7;
    private List<Letter> rack;
    private List<Move> moveHistory;
    private Board board;
    private int totalScore;
    private Move bestMove;
    private List<Move> moves;
    private Bag bag;
    private boolean lookAhead;

    public AgentFresco(Bag bag, Board board, boolean lookAhead) {
        this.moveHistory = new ArrayList<Move>();
        this.rack = new ArrayList<Letter>();
        this.totalScore = 0;
        this.board = board;
        moves = new ArrayList<Move>();
        fillRack(bag);
        this.bag = bag;
        this.lookAhead = lookAhead;
    }

    //region getters and setters
    public int getMAX_TILES_ON_HAND() {
        return MAX_TILES_ON_HAND;
    }

    public void setRack(List<Letter> rack) {
        this.rack = rack;
    }

    @Override
    public List<Letter> getRack() {
        return rack;
    }

    @Override
    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public void setScoreHistory(List<Move> moveHistory) {
        this.moveHistory = moveHistory;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }



    //endregion getters and setters

    @Override
    public Move makeMove() {

        /* At the start of each turn, empty the moves bag and initalize the best move to null*/
        moves.clear();
        bestMove = null;

        /* Examine each anchor square vertically and horizontally for moves */
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (board.getBoard()[i][j].isAnchor()) {
                    findMoves(board.getBoard()[i][j], Direction.HORIZONTAL);
                    findMoves(board.getBoard()[i][j], Direction.VERTICAL);
                }
            }
        }

        if(bag.getBag().isEmpty() && lookAhead) {
            lookAhead();
        }

        if (bestMove != null) {
            removeFromRack(bestMove);
            moveHistory.add(bestMove);
            totalScore += bestMove.getScore();
        }
        return bestMove;
    }

    /* Function to refill the rack after a move has been made */
    @Override
    public void fillRack(Bag bag) {

        Random random = new Random();
        int rackSize = rack.size();

        for (int i = 0; i < (MAX_TILES_ON_HAND - rackSize); i++) {
            /* Only take tiles from the bag if it isn't empty */
            if(!bag.getBag().isEmpty()) {
                int randomNumber = random.nextInt(bag.getBag().size());
                rack.add(bag.getBag().get(randomNumber));
                bag.getBag().remove(randomNumber);
            }
        }
    }

    /* Remove the tiles from the rack that were used in the move*/
    public void removeFromRack(Move move) {
        for (int i = 0; i < move.getWord().length(); i++) {
            for (int j = 0; j < rack.size(); j++) {
                if (move.getDirection().equals(Direction.HORIZONTAL)) {
                    if (rack.get(j).getLetter().equals(Character.toString(move.getWord().charAt(i)).toUpperCase()) &&
                            !board.getBoard()[move.getX()][move.getY() + i].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        rack.remove(j);
                        break;
                    }
                } else {
                    if (rack.get(j).getLetter().equals(Character.toString(move.getWord().charAt(i)).toUpperCase()) &&
                            !board.getBoard()[move.getX() + i][move.getY()].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                        rack.remove(j);
                        break;
                    }
                }

            }
        }
    }

    /* Everytime we encounter a legal move, we check if it's the best current move and update it*/
    public void saveBestMove(Move move) {
        moves.add(move);

        if (bestMove == null || bestMove.getScore() < move.getScore()) {
            bestMove = move;
        }
    }

    public void findCrossCheckSets(Direction direction) {

        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (!board.getBoard()[i][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    board.getBoard()[i][j].getCrossCheckSet().clear();
                    findCrossCheckSets(board.getBoard()[i][j], direction);
                    System.out.print("");
                }
            }
        }
    }

    /* The cross check is found by finding first the words left and right to the current square. If they're both empty,
     * every letter in the rack goes in the cross check set. Otherwise, only letters from the rack that can form legal words
     * can be laid down. */
    private void findCrossCheckSets(Square square, Direction direction) {

        String leftWord = "";
        String rightWord = "";

        if (direction.equals(Direction.VERTICAL)) {
            if (square.getY() > 0) {
                leftWord = leftWord(board.getBoard()[square.getX()][square.getY() - 1], direction);
            }
            if (square.getY() < board.getBoardSize() - 1) {
                rightWord = rightWord(board.getBoard()[square.getX()][square.getY() + 1], direction);
            }
        } else {
            if (square.getX() > 0) {
                leftWord = leftWord(board.getBoard()[square.getX() - 1][square.getY()], direction);
            }
            if (square.getX() < board.getBoardSize() - 1) {
                rightWord = rightWord(board.getBoard()[square.getX() + 1][square.getY()], direction);
            }
        }
        if (rightWord.equals("") && leftWord.equals("")) {
            for (Letter l : rack) {
                square.getCrossCheckSet().add(l);
            }
        } else {
            for (Letter l : rack) {
                if (board.getWordCollection().getDawg().contains((leftWord + l.getLetter() + rightWord).toLowerCase())) {
                    square.getCrossCheckSet().add(l);
                }
            }
        }
    }


    public void findMoves(Square square, Direction direction) {

        /* Start by finding the possible left permutations of letters in the rack next to the anchor square */
        findCrossCheckSets(direction);
        List<String> leftPermutations = findLeftPermutations(square, direction, rack);
        //List<String> leftPermutationsVertical = findLeftPermutations(square, Direction.VERTICAL, rack);

        /* For every string we found, try to extend that word to the right*/
        for (String s : leftPermutations) {

            List<Letter> remainingRack = remainingRack(rack, s);

            for (Letter l : square.getCrossCheckSet()) {
                if(remainingRack.contains(l)) {
                    Iterable<String> children = board.getWordCollection().getDawg().getStringsStartingWith((s + l.getLetter()).toLowerCase());
                    if (children.iterator().hasNext()) {
                        if (direction.equals(Direction.HORIZONTAL)) {
                            if(square.getY() < board.getBoardSize() - 1) {
                                extendRight(board.getBoard()[square.getX()][square.getY() + 1], remainingRack(remainingRack, l.getLetter()), s + l.getLetter(), direction);
                            }
                        } else {
                            if(square.getX() < board.getBoardSize() - 1) {
                                extendRight(board.getBoard()[square.getX() + 1][square.getY()], remainingRack(remainingRack, l.getLetter()), s + l.getLetter(), direction);
                            }
                        }
                    }
                }
            }
        }

        /*If there were no permutations, still try to lay down a letter on the anchor square and extend a word to the right*/
        String leftWord = "";

        /* Find the word left to the square ( if there exists one ). */
        if(direction.equals(Direction.HORIZONTAL)) {
            if(square.getY() > 0) {
                leftWord = leftWord(board.getBoard()[square.getX()][square.getY() - 1], Direction.VERTICAL);
            }
        }
        else {
            if(square.getX() > 0) {
                leftWord = leftWord(board.getBoard()[square.getX() - 1][square.getY()], Direction.HORIZONTAL);
            }
        }

        /* For every letter there is in the crosscheck set and in the rack, check if the letter can be laid down.*/
        for (Letter l : square.getCrossCheckSet()) {
            if(rack.contains(l)) {
                Iterable<String> children = board.getWordCollection().getDawg().getStringsStartingWith((leftWord + l.getLetter()).toLowerCase());

                if (children.iterator().hasNext()) {
                    if (direction.equals(Direction.HORIZONTAL)) {
                        if(square.getY() < board.getBoardSize() - 1) {
                            extendRight(board.getBoard()[square.getX()][square.getY() + 1], remainingRack(rack, l.getLetter()), leftWord + l.getLetter(), direction);
                        }

                    } else {
                        if(square.getX() < board.getBoardSize() - 1) {
                            extendRight(board.getBoard()[square.getX() + 1][square.getY()], remainingRack(rack, l.getLetter()), leftWord + l.getLetter(), direction);
                        }
                    }
                }
            }
        }

    }

    /* Try to complete the word given the left permutation */
    private void extendRight(Square square, List<Letter> remainingRack, String word, Direction direction) {

        /* If there's not a letter on the current square, check if we have a legal word and save it if it is. Then try to exted the word further*/
        if (!square.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            if (board.getWordCollection().getDawg().contains(word.toLowerCase())) {
                if (direction.equals(Direction.HORIZONTAL)) {
                    saveBestMove(new Move(this, square.getX(), square.getY() - (word.length()), direction, word));
                } else {
                    saveBestMove(new Move(this, square.getX() - (word.length()), square.getY(), direction, word));
                }
            }
            for (Letter letter : remainingRack) {
                Iterable<String> it = board.getWordCollection().getDawg().getStringsStartingWith((word + letter.getLetter()).toLowerCase());
                    if(it.iterator().hasNext() && square.getCrossCheckSet().contains(letter)) {
                        if (direction.equals(Direction.HORIZONTAL)) {
                            if (square.getY() < board.getBoardSize() - 1) {
                                extendRight(board.getBoard()[square.getX()][square.getY() + 1], remainingRack(remainingRack, letter.getLetter()), word + letter.getLetter(), direction);
                            }
                        } else {
                            if (square.getX() < board.getBoardSize() - 1) {
                                extendRight(board.getBoard()[square.getX() + 1][square.getY()], remainingRack(remainingRack, letter.getLetter()), word + letter.getLetter(), direction);
                            }
                        }
                    }
            }
        } else {
            Iterable<String> it = board.getWordCollection().getDawg().getStringsStartingWith((word + square.getValue()).toLowerCase());

            if (it.iterator().hasNext()) {
                if (direction.equals(Direction.HORIZONTAL)) {
                    if (square.getY() < board.getBoardSize() - 1) {
                        extendRight(board.getBoard()[square.getX()][square.getY() + 1], remainingRack, word + square.getValue(), direction);
                    }
                } else {
                    if (square.getX() < board.getBoardSize() - 1) {
                        extendRight(board.getBoard()[square.getX() + 1][square.getY()], remainingRack, word + square.getValue(), direction);
                    }
                }
            }
        }
    }

    /* Find left permutatuion for the given square and according to the given direction*/
    public List<String> findLeftPermutations(Square square, Direction direction, List<Letter> rack) {

        List<String> permutations = new ArrayList<String>();

        int maxLeft = 0;

        if (direction.equals(Direction.HORIZONTAL)) {
            Square leftSquare = null;
            if (square.getY() > 0) {
                leftSquare = board.getBoard()[square.getX()][square.getY() - 1];
            }
            while (leftSquare != null && leftSquare.getY() > 0 && !leftSquare.isAnchor()
                    && !leftSquare.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                maxLeft++;
                leftSquare = board.getBoard()[leftSquare.getX()][leftSquare.getY() - 1];
            }
            if (leftSquare != null && !leftSquare.isAnchor() && !leftSquare.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                maxLeft++;
            }

            permutation("", rack, permutations, maxLeft);
            return permutations;
        } else {
            Square leftSquare = null;
            if (square.getX() > 0) {
                leftSquare = board.getBoard()[square.getX() - 1][square.getY()];
            }
            while (leftSquare != null && leftSquare.getX() > 0 && !leftSquare.isAnchor()
                    && !leftSquare.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                maxLeft++;
                leftSquare = board.getBoard()[leftSquare.getX() - 1][leftSquare.getY()];
            }
            if (leftSquare != null && !leftSquare.isAnchor() && !leftSquare.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                maxLeft++;
            }

            permutation("", rack, permutations, maxLeft);
            return permutations;
        }
    }

    /* Helper function to find all permutations of the letters in the rack up to the length of maxLeft*/
    private void permutation(String prefix, List<Letter> rack, List<String> permutations, int maxLeft) {

        if (prefix.length() <= maxLeft) {
            int n = rack.size();

            Iterable<String> startingWith = board.getWordCollection().getDawg().getStringsStartingWith(prefix.toLowerCase());

            if (startingWith.iterator().hasNext() && prefix.length() > 0) {
                permutations.add(prefix);
            }

            for (int i = 0; i < n; i++) {
                if (i < rack.size()) {
                    permutation(prefix + rack.get(i).getLetter(), rack.subList(i + 1, rack.size()), permutations, maxLeft);
                }
            }
        }
    }

    private String leftWord(Square square, Direction direction) {
        if (!square.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return "";
        }
        if (direction.equals(Direction.VERTICAL)) {
            if (square.getY() > 0) {
                return leftWord(board.getBoard()[square.getX()][square.getY() - 1], direction) + square.getValue();
            }
            else {
                return square.getValue();
            }
        } else {
            if (square.getX() > 0) {
                return leftWord(board.getBoard()[square.getX() - 1][square.getY()], direction) + square.getValue();
            }
            else {
                return square.getValue();
            }
        }
    }

    private String rightWord(Square square, Direction direction) {
        if (!square.getSquareType().equals(SquareType.CONTAINS_LETTER)) {
            return "";
        }
        if (direction.equals(Direction.VERTICAL)) {
            if (square.getY() < board.getBoardSize() - 1) {
                return square.getValue() + rightWord(board.getBoard()[square.getX()][square.getY() + 1], direction);
            }
            else {
                return square.getValue();
            }
        } else {
            if (square.getX() < board.getBoardSize() - 1) {
                return square.getValue() + rightWord(board.getBoard()[square.getX() + 1][square.getY()], direction);
            }
            else {
                return square.getValue();
            }
        }
    }

    private List<Letter> remainingRack(List<Letter> rack, String word) {

        List<Letter> remainingRack = new ArrayList<Letter>();
        for (Letter l : rack) {
            remainingRack.add(l);
        }
        for (int i = 0; i < word.length(); i++) {
            for (Letter l : remainingRack) {
                if (l.getLetter().equals(Character.toString(word.charAt(i)).toUpperCase())) {
                    remainingRack.remove(l);
                    break;
                }
            }
        }
        return remainingRack;
    }

    private void lookAhead() {

        int difference = Integer.MIN_VALUE;

        Collections.sort(moves, new Comparator<Move>() {
            @Override
            public int compare(Move m1, Move m2) {
                if (m1.getScore() > m2.getScore())
                    return -1;
                if (m1.getScore() < m2.getScore())
                    return 1;
                return 0;
            }
        });

        int LIMIT = 10;

        if(moves.size() < 10) {
                LIMIT = moves.size();
        }

        for(int i = 0; i < LIMIT; i++) {

            Board tempBoard = new Board(board.getWordCollection());

            for(int row = 0; row < board.getBoardSize(); row++) {
                for(int column = 0; column < board.getBoardSize(); column++) {
                    tempBoard.getBoard()[row][column] = this.board.getBoard()[row][column];
                }
            }

            tempBoard.updateBoard(moves.get(i));

            AgentFresco opponent = new AgentFresco(this.bag, tempBoard, false);

            opponent.setRack(findOpponentsRack());

            Move opponentMove = opponent.makeMove();

            if(opponentMove == null) {
                opponentMove = new Move(this, 0, 0, Direction.HORIZONTAL, "");
                opponentMove.setScore(0);
            }

            if(moves.get(i).getScore() - opponentMove.getScore() > difference) {
                difference = moves.get(i).getScore() - opponentMove.getScore();
                bestMove = moves.get(i);
            }
        }
    }

    private List<Letter> findOpponentsRack() {

        List<Letter> opponentsRack = new ArrayList<Letter>();

        Bag tempBag = new Bag(board.getWordCollection().getLetters());

        for(int i = 0; i < board.getBoardSize(); i++) {
            for(int j = 0; j < board.getBoardSize(); j++) {
                if(board.getBoard()[i][j].getSquareType().equals(SquareType.CONTAINS_LETTER)) {
                    tempBag.getBag().remove(getLetter(board.getBoard()[i][j].getValue()));
                }
            }
        }

        for(Letter l : this.rack) {
            tempBag.getBag().remove(l);
        }

        for(int i = 0; i < tempBag.getBag().size(); i++) {
            opponentsRack.add(tempBag.getBag().get(i));
        }

        return opponentsRack;
    }

    private Letter getLetter(String value) {
        for(Letter l : board.getWordCollection().getLetters()) {
            if(l.getLetter().equals(value)) {
                return l;
            }
        }
        return null;
    }


}
