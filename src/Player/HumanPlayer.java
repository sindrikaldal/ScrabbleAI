package Player;

import Board.*;
import Move.*;
import WordCollection.Letter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Game.Bag;

public class HumanPlayer implements Player {

    int RACK_COUNT = 7;
    private List<Letter> rack;
    private List<Move> moveHistory;
    private Board board;
    private int totalScore;
    private Bag bag;


    public HumanPlayer(Bag bag, Board board) {
        this.moveHistory = new ArrayList<Move>();
        rack = new ArrayList<Letter>();
        this.totalScore = 0;
        fillRack(bag);
        this.board = board;
        this.bag = bag;
    }

    //region getters and setters

    @Override
    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public void updateTotalScore(int newScore) {
        this.totalScore += newScore;
    }
    public int getMAX_TILES_ON_HAND() {
        return RACK_COUNT;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(List<Move> moveHistory) {
        this.moveHistory = moveHistory;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public List<Letter> getRack() {
        return rack;
    }
    //endregion getters an

    @Override
    public Move makeMove() {
        /* A Scanner to receieve instructions from console */
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
        } while(!(input.equals("H") || input.equals("h") || input.equals("V") || input.equals("v")));

        System.out.println("Input word to enter in field x:" + x + " y: " + y + " direction: " + direction);
        String wordToReturn = in.nextLine();
        wordToReturn = wordToReturn.toUpperCase();
        removeFromRack(wordToReturn);
        Move m = new Move(this, x, y, direction, wordToReturn);
        totalScore += m.getScore();
        moveHistory.add(m);
        return m;
    }


    @Override
    /* Fill the rack with letters from the bag */
    public void fillRack(Bag bag) {
        Random random = new Random();
        int rackSize = rack.size();
        for(int i = 0; i < (RACK_COUNT - rackSize); i++) {
            int randomNumber = random.nextInt(bag.getBag().size());
            rack.add(bag.getBag().get(randomNumber));
            bag.getBag().remove(randomNumber);
        }
    }


    /* Remove the letters from the rack that were used to place down the word */

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




}
