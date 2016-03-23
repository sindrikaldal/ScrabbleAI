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
            for(int j = 0; i < this.BOARD_SIZE; j++) {
                if(i == 0) {
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
                else if(i == 1) {
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
                else if(i == 2) {

                }
                else if(i == 3) {

                }
                else if(i == 4) {

                }
                else if(i == 5) {

                }
                else if(i == 6) {

                }
                else if(i == 7) {

                }
                else if(i == 8) {

                }
                else if(i == 9) {

                }
                else if(i == 10) {

                }
                else if(i == 11) {

                }
                else if(i == 12) {

                }
                else if(i == 13) {

                }
                else if(i == 14) {

                }
            }
        }
    }
}
