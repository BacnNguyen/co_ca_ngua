package model;

public class Position {
    private int x;
    private int y;
    private int index;

    public Position(int index, int y, int x) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
