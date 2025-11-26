package model;

import java.awt.*;
import javax.swing.*;

public class Obstacle {
    private int x, y;
    private int width = 80, height = 80;
    private Image obstacleImage;

    // 🌊 Floating animation variables
    private int baseY;
    private double angle = Math.random() * Math.PI * 2; // random start phase
    private double floatSpeed = 0.05 + Math.random() * 0.02; // how fast it oscillates
    private int floatAmplitude = 30 + (int)(Math.random() * 10); // how high it moves up/down

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
        this.baseY = y;

        obstacleImage = new ImageIcon(getClass().getClassLoader().getResource("assets/obstacle.png"))
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public void move() {
        // Move horizontally
        x -= 5;
        if (x < -width) {
            x = 800 + (int)(Math.random() * 400);
            baseY = 220 + (int)(Math.random() * 120); // respawn new height
        }

        // 🪶 Floating up & down
        angle += floatSpeed;
        y = baseY + (int)(Math.sin(angle) * floatAmplitude);
    }

    public void draw(Graphics g) {
        g.drawImage(obstacleImage, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void reset(int screenWidth) {
        x = screenWidth + (int)(Math.random() * 400);
        baseY = 220 + (int)(Math.random() * 120);
    }
}

