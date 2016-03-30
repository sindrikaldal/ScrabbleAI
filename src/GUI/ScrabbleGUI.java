package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Board.Board;
import Move.*;
import Board.Square;
import Board.SquareType;
import Player.*;

public class ScrabbleGUI extends JFrame {


    public static final int LETTER_COUNT = 7;
    public static final int SQUARE_SIZE = 15;
    private static final long serialVersionUID = 1L;
    private Player playerOne;
    private Player playerTwo;

    Board board;
    BorderLayout borderLayout;

    JPanel scorePanel;
    JPanel PlayerOneScore;
    JPanel PlayerTwoScore;
    JLabel PlayerOneLabel;
    JLabel PlayerTwoLabel;
    JPanel gridPanel;
    JPanel rackPanel;
    JPanel[] rack;
    JLabel[] rackLabels;
    JLabel[][] squareLabels;
    JPanel[][] squares ;
    JPanel[][] moveHistory;
    JLabel[][] moveHistoryLabel;
    JPanel movePanel;

    public ScrabbleGUI(Board board, Player playerOne, Player playerTwo) {

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = board;
        this.setSize(new Dimension(15, 15));

        /* The size and placement of the board on the screen*/
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        setLocation(screenSize.width / 3, screenSize.height / 3);

        /* Initialize all the JFrame compontents */

        /*The squares that form the board*/
        squareLabels = new JLabel[board.getBoardSize()][board.getBoardSize()];
        squares = new JPanel[board.getBoardSize()][board.getBoardSize()];

        /* The squares that will contain the move history of the players*/
        moveHistory = new JPanel[board.getBoardSize()][2];
        moveHistoryLabel = new JLabel[board.getBoardSize()][2];
        movePanel = new JPanel();

        scorePanel = new JPanel();
        rack = new JPanel[LETTER_COUNT];
        PlayerOneScore = new JPanel();
        PlayerTwoScore = new JPanel();
        gridPanel = new JPanel();
        rackPanel = new JPanel();
        rackLabels = new JLabel[LETTER_COUNT];

        /* Initalize the board*/
        initGUI();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private void initGUI() {
        initGrid();
        initScorePanel();
        initLetterPanel();
        initMoveHistoryPanel();
        add(gridPanel, BorderLayout.CENTER);
        add(rackPanel, BorderLayout.SOUTH);
        add(scorePanel, BorderLayout.NORTH);
        add(movePanel, BorderLayout.EAST);
    }

    private void initScorePanel() {
        scorePanel.setLayout(new GridLayout(1, 2));

        PlayerOneScore.setSize(1, 2);
        PlayerTwoScore.setSize(1, 2);
        PlayerOneLabel = new JLabel("Player 1 : 0");
        PlayerTwoLabel = new JLabel("Player 2 : 0");
        PlayerOneScore.add(PlayerOneLabel);
        PlayerTwoScore.add(PlayerTwoLabel);
        scorePanel.add(PlayerOneScore);
        scorePanel.add(PlayerTwoScore);
    }

    private void initLetterPanel(){
        rackPanel.add(new JLabel("  Player letters  "));
        rackPanel.setLayout(new GridLayout(1, 10));
        rackPanel.setSize(1, 15);
        for(int i = 0; i < playerOne.getRack().size(); i++) {
            JPanel panel = new JPanel();
            JLabel label = new JLabel(playerOne.getRack().get(i).getLetter());
            panel.setBackground(Color.WHITE);
            panel.setSize(1, 5);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.add(label);
            rackPanel.add(panel);
            rackLabels[i] = label;
        }
    }

    private void initGrid() {
        gridPanel.setLayout(new GridLayout(board.getBoardSize(), board.getBoardSize()));
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int column = 0; column < board.getBoardSize(); column++) {
                Square square = board.getBoard()[row][column];
                JPanel panel = new JPanel();
                JLabel label = new JLabel(getContent(square));
                label.setBackground(Color.BLACK);
                panel.setBackground(getSquareColor(square));
                panel.setSize(50, 50);
                panel.setBorder(BorderFactory.createEtchedBorder());
                panel.add(label);
                squares[row][column] = panel;
                squareLabels[row][column] = label;
                gridPanel.add(panel);
            }
        }
    }

    private void initMoveHistoryPanel() {
        movePanel.setLayout(new GridLayout(board.getBoardSize(), 2));
        for(int i = 0; i < 2; i++) {
            JPanel panel = new JPanel();
            JLabel label = new JLabel("Player " + (i + 1));
            label.setBackground(Color.BLACK);
            panel.setBackground(Color.PINK);
            panel.setSize(50, 50);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.add(label);
            moveHistory[0][i] = panel;
            moveHistoryLabel[0][i] = label;
            movePanel.add(panel);

        }
        for(int i = 1; i < board.getBoardSize(); i++) {
            for(int j = 0; j < 2; j++) {
                JPanel panel = new JPanel();
                JLabel label = new JLabel("");
                label.setBackground(Color.BLACK);
                panel.setBackground(Color.PINK);
                panel.setSize(50, 50);
                panel.setBorder(BorderFactory.createEtchedBorder());
                panel.add(label);
                moveHistory[i][j] = panel;
                moveHistoryLabel[i][j] = label;
                movePanel.add(panel);
            }
        }
    }

    public Color getSquareColor(Square square) {
        switch(square.getSquareType()) {
            case TRIPLE_WORD:
                return Color.RED;
            case DOUBLE_WORD:
                return Color.PINK;
            case TRIPLE_LETTER:
                return Color.BLUE;
            case DOUBLE_LETTER:
                return Color.CYAN;
            case CENTER_SQUARE:
                return Color.PINK;
            default:
                return Color.WHITE;
        }
    }

    private String getContent(Square square) {

        switch(square.getSquareType()) {
            case TRIPLE_WORD:
                return "3W";
            case DOUBLE_WORD:
                return "2W";
            case TRIPLE_LETTER:
                return "3L";
            case DOUBLE_LETTER:
                return "2L";
            case CENTER_SQUARE:
                return "*";
            default:
                return "";
        }
    }

    public void updateScores(Player player, Boolean isPlayerOne) {
        if(isPlayerOne) {
            PlayerOneLabel.setText("Player 1 : " + (player.getTotalScore()));
        } else {
            PlayerTwoLabel.setText("Player 2 : " + (player.getTotalScore()));
        }
    }

    public void updateLetterPanel(Player player) {
        for(int i = 0; i < player.getRack().size(); i++) {
            rackLabels[i].setText(player.getRack().get(i).getLetter());
        }
    }

    public void updateMoveHistory(Player player, boolean isPlayerOne) {
        for(int i = 0; i < player.getMoveHistory().size(); i++) {
            if(isPlayerOne) {
                moveHistoryLabel[i + 1][0].setText(player.getMoveHistory().get(i).getWord() + " " + player.getMoveHistory().get(i).getScore());
            }
            else {
                moveHistoryLabel[i + 1][1].setText(player.getMoveHistory().get(i).getWord() + " " + player.getMoveHistory().get(i).getScore());
            }

        }
    }

    public void updateBoard(Move move, boolean isPlayerOne) {

        updateScores(move.getPlayer(), isPlayerOne);

        updateMoveHistory(move.getPlayer(), isPlayerOne);

        if(move.getDirection().equals(Direction.VERTICAL)) {
            for(int i = move.getX(); i < move.getWord().length() + move.getX(); i++) {
                if(move.getWord().charAt(i - move.getX()) != '*') {
                    squares[i][move.getY()].setBackground(Color.WHITE);
                    squareLabels[i][move.getY()].setText(Character.toString(move.getWord().charAt(i - move.getX())));
                    board.getBoard()[i][move.getY()].setSquareType(SquareType.CONTAINS_LETTER);
                    board.getBoard()[i][move.getY()].setValue(Character.toString(move.getWord().charAt(i - move.getX())));
                }
            }
        }
        else {
            for(int i = move.getY(); i < move.getWord().length() + move.getY(); i++) {
                if(move.getWord().charAt(i - move.getY()) != '*') {
                    squares[move.getX()][i].setBackground(Color.WHITE);
                    squareLabels[move.getX()][i].setText(Character.toString(move.getWord().charAt(i - move.getY())));
                    board.getBoard()[move.getX()][i].setSquareType(SquareType.CONTAINS_LETTER);
                    board.getBoard()[move.getX()][i].setValue(Character.toString(move.getWord().charAt(i - move.getY())));
                }
            }
        }


        updateLetterPanel(move.getPlayer());

    }

}
