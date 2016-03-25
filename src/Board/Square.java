package Board;

/**
 * Created by sindrikaldal on 23/03/16.
 */

public class Square {

    private SquareType squareType;
    private String value;
    private boolean isAnchor;
    private int x;
    private int y;

    public Square(SquareType squareType, String value, int x, int y) {
        this.squareType = squareType;
        this.value = value;
        this.isAnchor = false;
        this.x = x;
        this.y = y;
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
    public boolean isAnchor() {
        return isAnchor;
    }

    public void setAnchor(boolean anchor) {
        isAnchor = anchor;
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
}
