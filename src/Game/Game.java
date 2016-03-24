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

    public Game() {
        System.out.println("Initalizing game..");
        gui = new ScrabbleGUI(new Board());
        wordCollection = new WordCollection();
        System.out.println("Done!");
    }

    public void startGame() {

    }
}
