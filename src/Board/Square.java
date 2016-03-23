package Board;

/**
 * Created by sindrikaldal on 23/03/16.
 */

public class Square {

    private SquareType squareType;
    private String value;

    public Square(SquareType squareType, String value) {
        this.squareType = squareType;
        this.value = value;
    }

    public SquareType getSquareType() {
        return squareType;
    }

    public void setSquareType(SquareType squareType) {
        this.squareType = squareType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
