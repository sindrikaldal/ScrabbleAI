package Game;

import GUI.ScrabbleGUI;
import Player.*;
import WordCollection.WordCollection;
import Board.Board;
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
        gui = new ScrabbleGUI(new Board());
        initalizePlayers();


        System.out.println("Done!");
    }

    private void initalizePlayers() {
        playerOne = new HumanPlayer();
        playerTwo = new AgentFresco();
    }

    public void startGame() {
    }
}
