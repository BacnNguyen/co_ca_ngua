package model;

import utils.ImageLoader;

import java.awt.*;

public class Map {
    private int x;
    private int y;
    private int bit;

    private Image[] images = {
            ImageLoader.getImage("rao.jpg",getClass()),
            ImageLoader.getImage("grass.png", getClass()),
            ImageLoader.getImage("red.png", getClass()),
            ImageLoader.getImage("green.png", getClass()),
            ImageLoader.getImage("blue.png", getClass()),
            ImageLoader.getImage("yellow.png", getClass()),
            ImageLoader.getImage("soil.png", getClass())
    };


    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
    }

    public void draw(Graphics2D g2d) {
        if(bit<7) g2d.drawImage(images[bit], x, y, null);
    }

    public int getBit() {
        return bit;
    }

    public Rectangle getRect() {
        int w = images[bit - 1].getWidth(null);
        int h = images[bit - 1].getHeight(null);
        if (bit == 3) {
            w = 38;
            h = 38;
        }
        return new Rectangle(x, y, w, h);
    }

}
