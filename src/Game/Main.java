package Game;

import Board.Board;
import GUI.ScrabbleGUI;
import WordCollection.WordCollection;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class Main {
    private static ScrabbleGUI gui;
    private static WordCollection wordCollection;

    public static void main(String[] args) {
        gui = new ScrabbleGUI(new Board());
        wordCollection = new WordCollection();
    }
}


