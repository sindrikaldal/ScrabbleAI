package Move;

import Board.Square;
import Player.Player;
import WordCollection.*;
import Score.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindrikaldal on 24/03/16.
 */
public class Move {

    Player player;
    int x, y;
    Direction direction;
    String word;
    List<Letter> lettersUsed;
    int score;

    public Move(Player player, int x, int y, Direction direction, String word) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.word = word;
        score = new Score(player).score(this);
        lettersUsed = new ArrayList<Letter>();
    }

    //region getters and setters
    public Player getPlayer() { return player; }

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

    public int getScore() { return score;
    }

    public void setScore(int score) { this.score = score; }

    public List<Letter> getLettersUsed() {
        return lettersUsed;
    }

    public void setLettersUsed(List<Letter> lettersUsed) {
        this.lettersUsed = lettersUsed;
    }
    //endregion getters and setters
}
