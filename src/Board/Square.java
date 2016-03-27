package Board;

import WordCollection.Letter;

import java.util.ArrayList;
import java.util.List;

public class Square {

    private SquareType squareType;
    private String value;
    private boolean isAnchor;
    private int x;
    private int y;
    private List<Letter> crossCheckSet;

    public Square(SquareType squareType, String value, int x, int y) {
        this.squareType = squareType;
        this.crossCheckSet = new ArrayList<Letter>();
        this.value = value;
        this.isAnchor = false;
        this.x = x;
        this.y = y;
    }


    //region getters and setters
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
    public boolean isAnchor() { return isAnchor; }

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

    public List<Letter> getCrossCheckSet() { return crossCheckSet; }

    public void setCrossCheckSet(List<Letter> crossCheckSet) { this.crossCheckSet = crossCheckSet; }
    //endregion getters and setters


}
