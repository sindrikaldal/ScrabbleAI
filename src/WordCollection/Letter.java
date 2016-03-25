package WordCollection;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class Letter {
    private String letter;
    private int value;
    private int instances;

    public Letter(String letter, int value, int instances) {
        this.letter = letter;
        this.value = value;
        this.instances = instances;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }
}
