package Game;

import GUI.ScrabbleGUI;
import WordCollection.WordCollection;
import Board.Board;
/**
 * Created by sindrikaldal on 24/03/16.
 */
public class Game {

    private ScrabbleGUI gui;
    private WordCollection wordCollection;
    private Bag bag;


    public Game() {
        System.out.println("Initalizing game..");
        wordCollection = new WordCollection();
        gui = new ScrabbleGUI(new Board());
        bag = new Bag(wordCollection.getLetters());
        System.out.println("Done!");
    }

    public void startGame() {

    }
}
