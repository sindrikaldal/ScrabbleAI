package Board;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class Board {

    private int BOARD_SIZE = 15;
    private Square board[][];

    public Board() {
        initializeBoard();
    }

    private void initializeBoard() {
        board = new Square[this.BOARD_SIZE][this.BOARD_SIZE];

        for(int i = 0; i < this.BOARD_SIZE; i++) {
            for(int j = 0; j < this.BOARD_SIZE; j++) {
                if(i == 0 || i == 14) {
                    if(j % 7 == 0) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_WORD, "");
                    }
                    else if(j == 3 || j == 11) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }
                }
                else if(i == 1 || i == 13) {
                    if(j == 1 || j == 13) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "");
                    }
                    else if(j == 5 || j == 9) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_LETTER, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }

                }
                else if(i == 2 || i == 12) {
                    if(j == 2 || j == 12) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "");
                    }
                    else if(j == 6 || j == 8) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }
                }
                else if(i == 3 || i == 11) {
                    if(j % 7 == 0) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "");
                    }
                    else if(j == 3 || j == 11   ) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }
                }
                else if(i == 4 || i == 10) {
                    if(j == 4 || j == 10) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_WORD, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }
                }
                else if(i == 5 || i == 9) {
                    if( j == 1 || j == 5 || j == 9 || j == 13) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_LETTER, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }
                }
                else if(i == 6 || i == 8) {
                    if( j == 2 || j == 6 || j == 8 || j == 12) {
                        this.board[i][j] = new Square(SquareType.DOUBLE_LETTER, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }
                }
                else if(i == 7) {
                    if(j == 0 || j == 14) {
                        this.board[i][j] = new Square(SquareType.TRIPLE_WORD, "");
                    }
                    else if(j == 7) {
                        this.board[i][j] = new Square(SquareType.CENTER_SQUARE, "");
                    }
                    else {
                        this.board[i][j] = new Square(SquareType.NORMAL, "");
                    }
                }
            }
        }
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public Square[][] getBoard() {
        return board;
    }
}
