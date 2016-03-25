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


    public AgentFresco(Bag bag) {
        this.scoreHistory = new ArrayList<Integer>();
        this.rack = new ArrayList<Letter>();
        this.totalScore = 0;
        fillRack(bag);
    }

    @Override
    public Move makeMove() {
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

}
