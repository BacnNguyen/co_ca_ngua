package gui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static int WIDTH = 1200;
    public static int HEIGHT = 800;
    public GameFrame() {
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        add(panel);
        setVisible(true);
    }
}
