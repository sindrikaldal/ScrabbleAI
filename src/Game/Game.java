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
    private Board board;

    Player playerOne;
    Player playerTwo;

    public Game() {

        System.out.println("Initalizing game..");

        wordCollection = new WordCollection();
        bag = new Bag(wordCollection.getLetters());
        this.board = new Board(wordCollection);
        initalizePlayers();
        gui = new ScrabbleGUI(this.board, playerOne, playerTwo);

        System.out.println("Done!");
    }

    private void initalizePlayers() {
        playerOne = new HumanPlayer(bag, board);
        playerTwo = new AgentFresco(bag, board);
    }

    public void startGame() {
        int i = 0;
        while(bag.getBag().size() > 5) {

            if(i == 5) {
                return;
            }
            /* Player's one turn*/
            Move playerOneMove = playerOne.makeMove();
            playerOne.fillRack(bag);
            gui.updateBoard(playerOneMove);


            /* Update the anchors of the board */
            board.updateAnchors();

            /* Player's two turn*/
            Move playerTwoMove = playerTwo.makeMove();
            playerTwo.fillRack(bag);
            gui.updateBoard(playerTwoMove);


            /* Update the anchors of the board */
            board.updateAnchors();
            i++;
        }
    }
}
