package model;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Star {
    private int x, y, width, height;
    private int speed = 3; // slower, more natural
    private static final Random rand = new Random();
    private Image image;

    public Star(int x, int y) {
        this.x = x;
        this.y = y;

        // Bigger stars
        this.width = 50;
        this.height = 50;

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("assets/star.png"));
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    public void move() {
        x -= speed;
        if (x + width < 0) reset(1000);
    }

    public void reset(int panelWidth) {
        x = panelWidth + rand.nextInt(500);
        y = 100 + rand.nextInt(200);
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}