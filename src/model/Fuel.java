package model;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Fuel {
    private int x, y, width, height;
    private int speed = 4;
    private static final Random rand = new Random();
    private Image image;

    public Fuel(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 60;
        image = new ImageIcon(getClass().getClassLoader().getResource("assets/fuel.png")).getImage();
    }

    public void move() {
        x -= speed;
        if (x + width < 0) reset(1000);
    }

    public void reset(int panelWidth) {
    x = panelWidth + rand.nextInt(700);
    y = 350 + rand.nextInt(120);   // mid band
}

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
