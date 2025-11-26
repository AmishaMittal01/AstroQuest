package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import model.*;

public class GameplayPanel extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private GameCharacter player;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Star> stars;
    private ArrayList<Fuel> fuels;

    private int backgroundX = 0;
    private int score = 0;
    private int levelGoal = 5;

    private boolean firstRun = true;
    private boolean gameStarted = false;
    private boolean showPlanetPopup = false;
    private boolean gamePaused = false;
    private boolean gameOver = false;

    private Image bgImage;

    private String[] planets = {
        "Mercury","Venus","Earth","Mars",
        "Jupiter","Saturn","Uranus","Neptune"
    };

    private String[] planetInfo = {
        "Closest planet to the Sun.",
        "Hottest planet with toxic clouds.",
        "Only known planet with life.",
        "Red planet rich in iron dust.",
        "Largest planet with storms.",
        "Planet with beautiful rings.",
        "Sideways rotating planet.",
        "Farthest windy ice giant."
    };

    private int currentPlanetIndex = 0;

    private Image currentPlanetImage;
    private String currentPlanetName = "";
    private String currentPlanetFact = "";
    private MainFrame frame;

    public GameplayPanel(MainFrame frame) {
        this.frame = frame;
        setFocusable(true);
        addKeyListener(this);

        bgImage = new ImageIcon(getClass().getClassLoader()
                .getResource("assets/bg_space.jpg")).getImage();

        player = new GameCharacter(100, 400, 400);
        obstacles = new ArrayList<>();
        stars = new ArrayList<>();
        fuels = new ArrayList<>();

        timer = new Timer(16, this);
        timer.start();

        System.out.println("PROJECT DIR: " + System.getProperty("user.dir"));
    }

    private void generateObjects() {
        obstacles.clear();
        stars.clear();
        fuels.clear();

        for (int i = 0; i < 6; i++)
            stars.add(new Star(700 + i * 300, 120 + (int)(Math.random() * 250)));

        for (int i = 0; i < 3; i++)
            obstacles.add(new Obstacle(950 + i * 400, 300));

        for (int i = 0; i < 2; i++)
            fuels.add(new Fuel(1000 + i * 900, 200 + (int)(Math.random() * 150)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameStarted || showPlanetPopup || gameOver) return;

        player.move();
        backgroundX -= 2;

        int w = getWidth();
        if (backgroundX <= -w) backgroundX += w;

        for (Obstacle obs : obstacles) obs.move();
        for (Star s : stars) s.move();
        for (Fuel f : fuels) f.move();

        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        Rectangle pb = player.getBounds();

        // STAR COLLISION
        for (Star s : stars) {
            if (pb.intersects(s.getBounds())) {
                score++;
                System.out.println("Score = " + score);
                s.reset(getWidth());

                if (!showPlanetPopup && score >= (currentPlanetIndex + 1) * levelGoal) {
                    unlockPlanet();
                }
            }
        }

        // OBSTACLES
        for (Obstacle obs : obstacles) {
            if (pb.intersects(obs.getBounds())) {
                player.loseLife();
                obs.reset(getWidth());
                if (player.getLives() <= 0) gameOver = true;
            }
        }

        // FUEL
        for (Fuel f : fuels) {
            if (pb.intersects(f.getBounds())) {
                player.gainLife();
                f.reset(getWidth());
            }
        }
    }

    private void loadPlanetImage(String planetName) {
        String file = planetName.toLowerCase() + "big.png";
        String path = System.getProperty("user.dir") + "/src/assets/planets/" + file;
        System.out.println("LOAD IMG: " + path);

        java.io.File f = new java.io.File(path);
        if (f.exists()) {
            ImageIcon icon = new ImageIcon(path);
            currentPlanetImage = icon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        } else {
            currentPlanetImage = null;
        }
    }

private void unlockPlanet() {
    // Prevent multiple popups at once
    if (showPlanetPopup) return;

    // === Custom Dark Theme for Dialog ===
    UIManager.put("OptionPane.background", new Color(10, 10, 25));       // dark navy background
    UIManager.put("Panel.background", new Color(10, 10, 25));
    UIManager.put("OptionPane.messageForeground", Color.WHITE);
    UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
    UIManager.put("OptionPane.border", BorderFactory.createLineBorder(new Color(80, 150, 255), 2));

    // ✅ If all planets unlocked — show final message and go to learning panel
    if (currentPlanetIndex >= planets.length) {
        JOptionPane.showMessageDialog(
            SwingUtilities.getWindowAncestor(this),
            "<html><body style='width:300px; text-align:center; font-size:14px; color:white;'>"
            + "<b>🚀 Congratulations, Space Explorer!</b><br><br>"
            + "You've unlocked all planets in the galaxy.<br>"
            + "Now, it's time to <b>learn</b> about them and discover fun facts!"
            + "</body></html>",
            "Mission Complete 🌠",
            JOptionPane.INFORMATION_MESSAGE
        );

        frame.showPanel("PlanetInfo");
        return;
    }

    // ✅ Show planet popup
    showPlanetPopup = true;
    gamePaused = true;

    // Set current planet info
    currentPlanetName = planets[currentPlanetIndex];
    currentPlanetFact = planetInfo[currentPlanetIndex];
    loadPlanetImage(currentPlanetName);

    System.out.println("Unlocked planet: " + currentPlanetName);

    // ✅ Show popup for 5 seconds
    Timer t = new Timer(5000, e -> {
        showPlanetPopup = false;
        gamePaused = false;
        currentPlanetIndex++;

        // ✅ If last planet unlocked, show final message
        if (currentPlanetIndex >= planets.length) {
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                "<html><body style='width:320px; text-align:center; font-size:14px; color:white;'>"
                + "<b>🎮 Enough of gaming!</b><br><br>"
                + "Time to explore <b>real knowledge</b> about your planets 🌍✨"
                + "</body></html>",
                "Time to Learn",
                JOptionPane.INFORMATION_MESSAGE
            );
            frame.showPanel("PlanetInfo");
        }
    });
    t.setRepeats(false);
    t.start();
}


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();
        g.drawImage(bgImage, backgroundX, 0, w, h, null);
        g.drawImage(bgImage, backgroundX + w, 0, w, h, null);

        if (gameStarted && !showPlanetPopup && !gameOver) {
            player.draw(g);
            for (Obstacle o : obstacles) o.draw(g);
            for (Star s : stars) s.draw(g);
            for (Fuel f : fuels) f.draw(g);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Fuel: " + player.getLives(), 20, 30);
            g.drawString("Stars: " + score, 20, 60);
        }

        // FIRST SCREEN / INSTRUCTIONS
if (firstRun) {
    Graphics2D g2d = (Graphics2D) g;

    // Semi-transparent panel
    g2d.setColor(new Color(0, 0, 0, 200));
    g2d.fillRoundRect(120, 100, 560, 350, 25, 25);

    // Title
    g2d.setColor(Color.CYAN);
    g2d.setFont(new Font("Orbitron", Font.BOLD, 32));
    g2d.drawString("🌌 Instructions 🌌", 140, 150);

    // Instructions text
    g2d.setColor(Color.YELLOW);
    g2d.setFont(new Font("Orbitron", Font.PLAIN, 20));
    int textX = 140;
    int startY = 200;
    int lineHeight = 30;

    String[] instructions = {
        "• Collect 5 stars to unlock each planet.",
        "• Avoid obstacles! Colliding will deplete your fuel.",
        "• Collect fuel canisters to restore fuel.",
        "• Use SPACE or UP arrow to jump.",
        "• Survive and unlock all planets to complete the game.",
        "Press SPACE to Start your adventure!"
    };

    for (int i = 0; i < instructions.length; i++) {
        g2d.drawString(instructions[i], textX, startY + i * lineHeight);
    }
}


        // GAME OVER
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 34));
            g.setColor(Color.RED);
            g.drawString("Game Over! Press SPACE", 230, 300);
        }

        // PLANET POPUP
        if (showPlanetPopup) {
            g.setColor(new Color(0,0,0,220));
            g.fillRoundRect(100, 80, 600, 400, 25, 25);

            g.setColor(Color.CYAN);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString(currentPlanetName + " Unlocked!", 230, 135);

            if (currentPlanetImage != null) {
                g.drawImage(currentPlanetImage, 220, 200, 200, 200, this);
            } else {
                g.setColor(Color.RED);
                g.drawString("Image Missing!", 260, 260);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(currentPlanetFact, 260, 430);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == KeyEvent.VK_SPACE || k == KeyEvent.VK_UP) {

            if (firstRun) {
                firstRun = false;
                gameStarted = true;
                generateObjects();
                return;
            }

            if (gameOver) {
                score = 0;
                currentPlanetIndex = 0;
                player = new GameCharacter(100, 400, 400);
                gameOver = false;
                generateObjects();
                return;
            }

            if (showPlanetPopup) return;
            player.jump();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}

