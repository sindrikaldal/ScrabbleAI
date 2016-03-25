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

    int MAX_TILES_ON_HAND = 7;
    private List<Letter> rack;
    private List<Integer> scoreHistory;
    private Board board;
    private int totalScore;


    public HumanPlayer(Bag bag) {
        this.scoreHistory = new ArrayList<Integer>();
        rack = new ArrayList<Letter>();
        this.totalScore = 0;
        fillRack(bag);
    }

    //region getters and setters

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
        return MAX_TILES_ON_HAND;
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
        } while(!(input.equals("H") || input.equals("h") || input.equals("V") || input.equals("v") || input.equals("v")));

        System.out.println("Input word to enter in field x:" + x + " y: " + y + " direction: " + direction);
        String wordToReturn = in.nextLine();

        removeFromRack(wordToReturn);
        return new Move(this, x, y, direction, wordToReturn);
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

    private void removeFromRack(String word) {
        for(int i = 0; i < word.length(); i++) {
            for(int j = 0; j < rack.size(); j++) {
                if(rack.get(j).getLetter().equals(Character.toString(word.charAt(i)).toUpperCase())) {
                    rack.remove(j);
                    break;
                }
            }
        }
    }


    @Override
    public List<Letter> getRack() {
        return rack;
    }

}
