package main.gui;

import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame {
    public CardLayout cardLayout;
    public JPanel mainPanel;

    // Make panel references accessible
    public MainMenuPanel mainMenu;
    public SetupPanel setupPanel;
    public GameplayPanel gameplayPanel;
    public PlanetInfoPanel planetInfoPanel;  // new

    public MainFrame() {
        setTitle("AstroQuest - Space Mission Simulator");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create all panels
        mainMenu = new MainMenuPanel(this);
        setupPanel = new SetupPanel(this);
        gameplayPanel = new GameplayPanel(this);
        planetInfoPanel = new PlanetInfoPanel(this);  // new panel

        // Add them to CardLayout
        mainPanel.add(mainMenu, "MainMenu");
        mainPanel.add(setupPanel, "Setup");
        mainPanel.add(gameplayPanel, "Gameplay");
        mainPanel.add(planetInfoPanel, "PlanetInfo");  // add PlanetInfoPanel

        gameplayPanel.requestFocusInWindow();

        add(mainPanel);
        setVisible(true);

        // Show main menu initially
        showPanel("MainMenu");
    }

    public void showPanel(String name) {
    cardLayout.show(mainPanel, name);

    // If showing GameplayPanel, request focus
    if (name.equals("Gameplay")) {
        Component comp = mainPanel.getComponent(2); // index of GameplayPanel
        comp.requestFocusInWindow();
    }
}

}
