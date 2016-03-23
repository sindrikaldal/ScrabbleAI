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
import org.quinto.dawg.ModifiableDAWGSet;
import Board.SquareType;

public class ScrabbleGUI extends JFrame {

    ModifiableDAWGSet dawg = new ModifiableDAWGSet();


    public static final int SQUARE_SIZE = 15;
    private static final long serialVersionUID = 1L;

    Board board;

    JPanel scorePanel;
    JLabel PlayerOneScore;
    JLabel Player2Score;
    JPanel gridPanel;
    JLabel[][] grid;
    JPanel[][] squares ;

    public ScrabbleGUI(Board board) {

        setLayout(new BorderLayout());
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        setLocation(screenSize.width / 4, screenSize.height / 4);

        this.board = board;

        /*Initialize all the JFrame compontents*/
        grid = new JLabel[board.getBoardSize()][board.getBoardSize()];
        squares = new JPanel[board.getBoardSize()][board.getBoardSize()];
        scorePanel = new JPanel();
        PlayerOneScore = new JLabel();
        Player2Score = new JLabel();
        gridPanel = new JPanel();

        initGUI();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private void initGUI() {
        initGrid();
        initScorePanel();
        add(gridPanel);
        add(scorePanel, BorderLayout.SOUTH);
    }

    private void initScorePanel() {
        scorePanel.setLayout(new GridLayout(1, 2));
//        score1Label.setText(player1.getClass().getName());
//        score2Label.setText(player2.getClass().getName());
//        scorePanel.add(score1Label);
//        scorePanel.add(score2Label);
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
