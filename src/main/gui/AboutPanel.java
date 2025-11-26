package main.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import main.gui.HomeButton;

public class AboutPanel extends JPanel {

    private JLabel bgLabel;
    private JTextArea contentArea;
    private String[] sections = {"intro", "howto", "credits"};
    private int currentSection = 0;

    public AboutPanel(MainFrame frame) {
        // === Background ===
        ImageIcon bgVideo = new ImageIcon("src/assets/space_loop.gif");
        bgLabel = new JLabel(bgVideo);
        bgLabel.setLayout(new GridBagLayout());

        // === Transparent overlay ===
        JPanel overlay = new JPanel();
        overlay.setOpaque(false);
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Title ===
      
        overlay.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Center section (arrows + scroll area) ===
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setMaximumSize(new Dimension(800, 450));

        // Left arrow
        JButton leftArrow = createArrowButton("←");
        leftArrow.addActionListener(e -> {
            if (currentSection > 0) {
                currentSection--;
                loadSection();
            }
        });
        centerPanel.add(leftArrow, BorderLayout.WEST);

        // Scrollable text area
        contentArea = new JTextArea();
        contentArea.setFont(new Font("Orbitron", Font.PLAIN, 18));
        contentArea.setForeground(Color.WHITE);
        contentArea.setBackground(new Color(0, 0, 0, 130));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setMargin(new Insets(20, 30, 20, 30));

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255, 100), 2, true));
        scrollPane.setPreferredSize(new Dimension(700, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Right arrow
        JButton rightArrow = createArrowButton("→");
        rightArrow.addActionListener(e -> {
            if (currentSection < sections.length - 1) {
                currentSection++;
                loadSection();
            }
        });
        centerPanel.add(rightArrow, BorderLayout.EAST);

        overlay.add(centerPanel);
        overlay.add(Box.createRigidArea(new Dimension(0, 30)));

        // === Main Menu button ===
       // === Home button ===
JButton homeBtn = HomeButton.create("🏠 Back to Main", e -> frame.showPanel("MainMenu"));
overlay.add(homeBtn);


        bgLabel.add(overlay);
        setLayout(new BorderLayout());
        add(bgLabel, BorderLayout.CENTER);

        // === Load first section ===
        loadSection();
    }

    /** Load section text from file **/
    private void loadSection() {
        String filePath = "src/assets/about_" + sections[currentSection] + ".txt";
        contentArea.setText(readTextFile(filePath));
        contentArea.setCaretPosition(0);
    }

    /** Read content from text file **/
    private String readTextFile(String path) {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (Exception e) {
            content.append("Error: Unable to load content from ").append(path);
        }
        return content.toString();
    }

    /** Creates glowing arrow buttons **/
    private JButton createArrowButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Orbitron", Font.BOLD, 26));
        btn.setForeground(Color.BLACK);
        btn.setBackground(new Color(0, 255, 255));
        btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2, true));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(70, 70));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 200, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 255, 255));
            }
        });

        return btn;
    }
}
