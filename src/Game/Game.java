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
        playerOne = new AgentFresco(bag, board, true);
        //playerOne = new HumanPlayer(bag, board);
        playerTwo = new AgentFresco(bag, board, false);
    }

    public void startGame() {

        /* Variables to keep track of how often in a row player passes a move*/
        int playerOnePasses = 0;
        int playerTwoPasses = 0;

        while(!gameOver()) {

            /* Player's one turn*/
            Move playerOneMove = playerOne.makeMove();

            if(playerOneMove == null) {
                playerOnePasses++;
                /* If player has passed move three times in a row, quit the game*/
                if(playerOnePasses == 3) {
                    break;
                }
                System.out.println("Player One couldn't find a move" );
            }
            else {
                playerOnePasses = 0;
                playerOne.fillRack(bag);
                gui.updateBoard(playerOneMove, true);
                /* Update the anchors of the board */
                board.updateAnchors();
            }

            /* Player's two turn*/
            Move playerTwoMove = playerTwo.makeMove();

            if(playerTwoMove == null) {
                playerTwoPasses++;
                /* If player has passed move three times in a row, quit the game*/
                if(playerTwoPasses == 3) {
                    break;
                }
                System.out.println("Player Two couldn't find a move" );
            }
            else {
                playerOnePasses = 0;
                playerTwo.fillRack(bag);
                gui.updateBoard(playerTwoMove, false);
                /* Update the anchors of the board */
                board.updateAnchors();
            }
        }

        if(playerOne.getTotalScore() > playerTwo.getTotalScore()) {
            System.out.println("Player One won with " + playerOne.getTotalScore() + " points");
        }
        else if(playerOne.getTotalScore() < playerTwo.getTotalScore()) {
            System.out.println("Player Two won with " + playerTwo.getTotalScore() + " points");
        }
        else {
            System.out.println("A tie! Both players had " + playerOne.getTotalScore());
        }
    }

    private boolean gameOver() {
        if(bag.getBag().isEmpty() && (playerOne.getRack().isEmpty() || playerTwo.getRack().isEmpty())) {
            return true;
        }
        return false;
    }
}
