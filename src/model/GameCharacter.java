package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GameCharacter {
    private int x, y;
    private int width, height;
    private int groundLevel;

    private int lives = 10;

    // 🪐 Movement physics
    private double velocityY = 0;
    private final double GRAVITY = 0.8;
    private final double JUMP_FORCE = -12;
    private final double MAX_FALL_SPEED = 12;

    private Image playerImage;

    public GameCharacter(int x, int y, int groundLevel) {
        this.x = x;
        this.y = y;
        this.groundLevel = groundLevel;

        // Load and scale sprite
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("assets/player.png"));
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            width = 60;
            height = 60;
            playerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        } else {
            width = 100;
            height = 100;
            playerImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    // ✅ Jump logic: pressing up gives an upward boost
    public void jump() {
        velocityY = JUMP_FORCE; // each press gives a new upward push
    }

    public void move() {
        velocityY += GRAVITY; // apply gravity
        if (velocityY > MAX_FALL_SPEED) velocityY = MAX_FALL_SPEED;

        y += velocityY;

        // Keep astronaut above ground
        if (y > groundLevel) {
            y = groundLevel;
            velocityY = 0;
        }

        // Prevent astronaut from flying out of top of screen
        if (y < 0) {
            y = 0;
            velocityY = 0;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, null);
    }

    // ✅ Lives (fuel) system
    public void loseLife() { lives = Math.max(0, lives - 1); }
    public void gainLife() { lives++; }
    public int getLives() { return lives; }

    // For collisions
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }

    // Utility if you need to reset position
    public void setY(int y) { this.y = y; }
}

