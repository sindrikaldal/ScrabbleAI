package Game;

import GUI.ScrabbleGUI;
import Player.*;
import WordCollection.WordCollection;
import Board.Board;
import Move.*;
/**
 * Created by sindrikaldal on 24/03/16.
 */
public class Game {

    private ScrabbleGUI gui;
    private WordCollection wordCollection;
    private Bag bag;

    Player playerOne;
    Player playerTwo;

    public Game() {

        System.out.println("Initalizing game..");

        wordCollection = new WordCollection();
        bag = new Bag(wordCollection.getLetters());
        initalizePlayers();
        gui = new ScrabbleGUI(new Board(), playerOne, playerTwo);

        System.out.println("Done!");
    }

    private void initalizePlayers() {
        playerOne = new HumanPlayer(bag);
        playerTwo = new AgentFresco(bag);
    }

    public void startGame() {
        while(true) {
            Move move = playerOne.makeMove();
            playerOne.fillRack(bag);
            gui.updateBoard(move);

        }
    }
}
