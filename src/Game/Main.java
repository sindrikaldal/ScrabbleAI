package Game;

import Board.Board;
import GUI.ScrabbleGUI;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class Main {

    private static ScrabbleGUI gui;

    public static void main(String[] args) {
        gui = new ScrabbleGUI(new Board());
    }
}
