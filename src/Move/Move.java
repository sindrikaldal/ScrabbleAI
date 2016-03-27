package Move;

import Board.Square;
import Player.Player;
import WordCollection.*;
import Score.Score;

import java.util.List;

/**
 * Created by sindrikaldal on 24/03/16.
 */
public class Move {

    Player player;
    int x, y;
    Direction direction;
    String word;
    List<Square> squares;
    Score score;

    public Move(Player player, int x, int y, Direction direction, String word) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.word = word;
        score = new Score();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    /* Calculate the score of the word */
    public int score() {
        return score.score(this);
    }
}
