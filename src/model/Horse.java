package model;

import utils.ImageLoader;

import java.awt.*;

public class Horse {
    private Position position; // 88 possiton
    private int type;
    private boolean isSafe= false;
    //1 red ; 2 blue;3 green; 4 yellow

    private Image[] images= {
            ImageLoader.getImage("horse_red.png",getClass()),
            ImageLoader.getImage("horse_green.png",getClass()),
            ImageLoader.getImage("horse_blue.png",getClass()),
            ImageLoader.getImage("horse_yellow.png",getClass())
    };


    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }

    public Horse(Position position, int type) {
        this.position = position;
        this.type = type;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public int getType() {
        return type;
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(images[type-1],position.getX(),position.getY(),null);
    }
}
