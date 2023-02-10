package model;

import utils.ImageLoader;

import java.awt.*;

public class Dice {
    private int number;

    private Image[] images = {
            ImageLoader.getImage("dice1.png",getClass()),
            ImageLoader.getImage("dice2.png",getClass()),
            ImageLoader.getImage("dice3.png",getClass()),
            ImageLoader.getImage("dice4.png",getClass()),
            ImageLoader.getImage("dice5.png",getClass()),
            ImageLoader.getImage("dice6.png",getClass())
    };

    public void draw(Graphics2D g2d){
        g2d.drawImage(images[number-1],900,50,null);
    }

    public Dice(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }


    public void setNumber(int number) {
        this.number = number;
    }
}
