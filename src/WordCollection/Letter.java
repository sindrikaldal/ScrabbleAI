package WordCollection;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class Letter {
    private char letter;
    private int value;

    public Letter(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
