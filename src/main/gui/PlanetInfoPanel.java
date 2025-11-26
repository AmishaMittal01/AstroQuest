package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import model.Planet;

public class PlanetInfoPanel extends JPanel {

    private MainFrame frame;
    private ArrayList<Planet> planets;
    private int currentPlanetIndex = 0;
    private JLabel planetLabel;
    private JLabel planetNameLabel;
    private JTextArea planetDescription;
    private JButton leftArrow, rightArrow;

    public PlanetInfoPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(null);

        // ✅ Background
        ImageIcon bgImage = new ImageIcon("src/assets/planetinfo_bg.gif");
        JLabel bg = new JLabel(bgImage);
        bg.setBounds(0, 0, 1000, 700);
        add(bg);
        bg.setLayout(null);

        // ✅ Load planets
        planets = new ArrayList<>();
        planets.add(new Planet("MERCURY", "src/assets/planets/mercurybig.png", "src/assets/planets_info/mercury.txt"));
        planets.add(new Planet("VENUS", "src/assets/planets/venusbig.png", "src/assets/planets_info/venus.txt"));
        planets.add(new Planet("EARTH", "src/assets/planets/earthbig.png", "src/assets/planets_info/earth.txt"));
        planets.add(new Planet("MARS", "src/assets/planets/marsbig.png", "src/assets/planets_info/mars.txt"));
        planets.add(new Planet("JUPITER", "src/assets/planets/jupiterbig.png", "src/assets/planets_info/jupiter.txt"));
        planets.add(new Planet("SATURN", "src/assets/planets/saturnbig.png", "src/assets/planets_info/saturn.txt"));
        planets.add(new Planet("URANUS", "src/assets/planets/uranusbig.png", "src/assets/planets_info/uranus.txt"));
        planets.add(new Planet("NEPTUNE", "src/assets/planets/neptunebig.png", "src/assets/planets_info/neptune.txt"));

        // ✅ Planet display
        planetLabel = new JLabel(scaleIcon(planets.get(currentPlanetIndex).getImage(), 300, 300));
        planetLabel.setBounds(350, 80, 300, 300);
        bg.add(planetLabel);

        // ✅ Planet name
        planetNameLabel = new JLabel(planets.get(currentPlanetIndex).getName(), SwingConstants.CENTER);
        planetNameLabel.setFont(new Font("Orbitron", Font.BOLD, 28));
        planetNameLabel.setForeground(Color.WHITE);
        planetNameLabel.setBounds(300, 380, 400, 40);
        bg.add(planetNameLabel);

        // ✅ Description text (no background box)
        planetDescription = new JTextArea(planets.get(currentPlanetIndex).getDescription());
        planetDescription.setFont(new Font("Consolas", Font.PLAIN, 16));
        planetDescription.setForeground(new Color(255, 255, 255));
        planetDescription.setBackground(new Color(0, 0, 0, 0));
        planetDescription.setOpaque(false);
        planetDescription.setEditable(false);
        planetDescription.setLineWrap(true);
        planetDescription.setWrapStyleWord(true);
        planetDescription.setFocusable(false);
        planetDescription.setMargin(new Insets(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(planetDescription);
        scrollPane.setBounds(180, 440, 640, 130);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        bg.add(scrollPane);

        // ✅ Navigation arrows
        leftArrow = new JButton(new ImageIcon("src/assets/icons/leftarrow.png"));
        leftArrow.setBounds(150, 250, 100, 100);
        setupArrowButton(leftArrow);
        leftArrow.addActionListener(e -> showPreviousPlanet());
        bg.add(leftArrow);

        rightArrow = new JButton(new ImageIcon("src/assets/icons/rightarrow.png"));
        rightArrow.setBounds(750, 250, 100, 100);
        setupArrowButton(rightArrow);
        rightArrow.addActionListener(e -> showNextPlanet());
        bg.add(rightArrow);

        // ✅ Home button (bottom center)
        JButton homeButton = HomeButton.create("HOME", e -> frame.showPanel("MainMenu"));
        homeButton.setBounds(425, 590, 150, 50);
        bg.add(homeButton);
    }

    private void setupArrowButton(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void showPreviousPlanet() {
        currentPlanetIndex = (currentPlanetIndex - 1 + planets.size()) % planets.size();
        updatePlanet();
    }

    private void showNextPlanet() {
        currentPlanetIndex = (currentPlanetIndex + 1) % planets.size();
        updatePlanet();
    }

    private void updatePlanet() {
        Planet planet = planets.get(currentPlanetIndex);
        planetLabel.setIcon(scaleIcon(planet.getImage(), 300, 300));
        planetNameLabel.setText(planet.getName());
        planetDescription.setText(planet.getDescription());
    }
}
