package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Board.Board;
import Board.Square;
import Board.SquareType;

public class ScrabbleGUI extends JFrame {


    public static final int LETTER_COUNT = 7;
    public static final int SQUARE_SIZE = 15;
    private static final long serialVersionUID = 1L;

    Board board;

    JPanel scorePanel;
    JPanel PlayerOneScore;
    JPanel PlayerTwoScore;
    JPanel gridPanel;
    JPanel letterPanel;
    JPanel[] letters;
    JLabel[][] grid;
    JPanel[][] squares ;

    public ScrabbleGUI(Board board) {

        this.board = board;
        this.setSize(new Dimension(15, 15));

        /* The size and placement of the board on the screen*/
        setLayout(new BorderLayout());
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        setLocation(screenSize.width / 3, screenSize.height / 3);

        /* Initialize all the JFrame compontents */
        grid = new JLabel[board.getBoardSize()][board.getBoardSize()];
        squares = new JPanel[board.getBoardSize()][board.getBoardSize()];
        scorePanel = new JPanel();
        letters = new JPanel[LETTER_COUNT];
        PlayerOneScore = new JPanel();
        PlayerTwoScore = new JPanel();
        gridPanel = new JPanel();
        letterPanel = new JPanel();

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
        add(gridPanel, BorderLayout.NORTH);
        add(letterPanel, BorderLayout.SOUTH);
        add(scorePanel, BorderLayout.EAST);
    }

    private void initScorePanel() {
        scorePanel.setLayout(new GridLayout(1, 2));
        PlayerOneScore.setSize(1, 2);
        PlayerTwoScore.setSize(1, 2);
        PlayerOneScore.add(new JLabel("Player 1 : 0"));
        PlayerTwoScore.add(new JLabel("Player 2 : 0"));
        scorePanel.add(PlayerOneScore);
        scorePanel.add(PlayerTwoScore);
    }

    private void initLetterPanel(){
        letterPanel.add(new JLabel("  Player letters  "));
        letterPanel.setLayout(new GridLayout(1, 10));
        letterPanel.setSize(1, 15);
        for(int i = 0; i < LETTER_COUNT; i++) {
            Square square = new Square(SquareType.RACK, "A");
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setSize(1, 5);
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.add(new JLabel("A"));
            letterPanel.add(panel);
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
                grid[row][column] = label;
                gridPanel.add(panel);
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
            default:
                return "";
        }
    }

//    public void updateScores(Player player, int turn) {
//        String text = (turn < 0) ? player1.getClass().getSimpleName() : player2
//                .getClass().getSimpleName();
//
//        text = text + ": " + " (" + player.getScore() + ") points!";
//        if (turn < 0)
//            score1Label.setText(text);
//        else
//            score2Label.setText(text);
//    }
//    public void updateBoard() {
//        for (int row = 0; row < Board.BOARD_SIZE; row++) {
//            for (int column = 0; column < Board.BOARD_SIZE; column++) {
//                JLabel label = grid[row][column];
//                Square square = board.getSquare(row, column);
//                if (square.containsLetter())
//                    squares[row][column].setBackground(Color.WHITE);
//                label.setText(getContent(square));
//            }
//        }
//    }

}
