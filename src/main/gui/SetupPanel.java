package main.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import model.Player;

public class SetupPanel extends JPanel {

    private JLabel bgLabel;
    private JPanel initialPopup, newProfilePopup, existingProfilePopup;

    public SetupPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        // === Background GIF ===
        ImageIcon bgVideo = new ImageIcon("src/assets/space_loop.gif");
        bgLabel = new JLabel(bgVideo);
        bgLabel.setLayout(null); // manual popup positioning
        add(bgLabel, BorderLayout.CENTER);

        // === Initial Popup ===
        initialPopup = createInitialPopup(frame);
        bgLabel.add(initialPopup);

        // === New Profile Popup ===
        newProfilePopup = createNewProfilePopup(frame);
        newProfilePopup.setVisible(false);
        bgLabel.add(newProfilePopup);

        // === Existing Profile Popup ===
        existingProfilePopup = createExistingProfilePopup(frame);
        existingProfilePopup.setVisible(false);
        bgLabel.add(existingProfilePopup);
    }

    // Initial popup: New or Existing
    private JPanel createInitialPopup(MainFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 0, 200));
        panel.setBounds((1000 - 600) / 2, (700 - 250) / 2, 600, 250);

        // Message
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setOpaque(false);
        JLabel message = new JLabel("Are you a new explorer or a returning space traveler?");
        message.setFont(new Font("Orbitron", Font.BOLD, 22));
        message.setForeground(Color.YELLOW);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        messagePanel.add(message);
        panel.add(messagePanel);

        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttons.setOpaque(false);
        JButton newBtn = createStyledButton("New");
        JButton existingBtn = createStyledButton("Existing");
        buttons.add(newBtn);
        buttons.add(existingBtn);
        panel.add(buttons);

        // Home button
        JButton homeBtn = HomeButton.create("🏠 Home", e -> frame.showPanel("MainMenu"));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(homeBtn);

        // Button actions
        newBtn.addActionListener(e -> {
            panel.setVisible(false);
            newProfilePopup.setVisible(true);
        });

        existingBtn.addActionListener(e -> {
            panel.setVisible(false);
            existingProfilePopup.setVisible(true);
        });

        return panel;
    }

    // New Profile Popup
    private JPanel createNewProfilePopup(MainFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 30, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3, true));
        panel.setBounds(250, 150, 500, 350);

        JLabel title = new JLabel("Create New Profile");
        title.setFont(new Font("Orbitron", Font.BOLD, 26));
        title.setForeground(Color.YELLOW);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(0, 0, 50, 150));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2, true));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name field
        JTextField nameField = new JTextField();
        styleInputField(nameField, "Enter Name");
        inputPanel.add(nameField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Password field
        JPasswordField passField = new JPasswordField();
        styleInputField(passField, "Enter Password");
        inputPanel.add(passField);

        panel.add(inputPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Buttons
        JButton createBtn = createStyledButton("Create Profile");
        createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(createBtn);

        JButton backBtn = HomeButton.create("🏠 Back to Main", e -> {
            resetView();
            frame.showPanel("MainMenu");
        });
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(backBtn);

        // Action
        createBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (name.isEmpty() || password.isEmpty()) {
                showThemedMessage(frame, "Please enter both name and password!", "AstroQuest");
                return;
            }

            if (Player.playerExists(name)) {
                showThemedMessage(frame, "Player already exists!", "AstroQuest");
                return;
            }

            Player.addPlayer(name, password);
            showThemedMessage(frame, "Welcome aboard, " + name + "! 🚀", "AstroQuest");
            panel.setVisible(false);
            frame.showPanel("Gameplay");
        });

        return panel;
    }

    // Existing Profile Popup
    private JPanel createExistingProfilePopup(MainFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 30, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3, true));
        panel.setBounds(250, 150, 500, 350);

        JLabel title = new JLabel("Existing Player Login");
        title.setFont(new Font("Orbitron", Font.BOLD, 26));
        title.setForeground(Color.YELLOW);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(0, 0, 50, 150));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2, true));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Player selection
        JComboBox<String> playerSelect = new JComboBox<>();
        for (String p : Player.getAllPlayers()) playerSelect.addItem(p);
        playerSelect.setMaximumSize(new Dimension(400, 40));
        playerSelect.setFont(new Font("Orbitron", Font.PLAIN, 18));
        playerSelect.setBackground(new Color(0, 0, 60));
        playerSelect.setForeground(Color.YELLOW);
        inputPanel.add(playerSelect);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Password field
        JPasswordField passField = new JPasswordField();
        styleInputField(passField, "Enter Password");
        inputPanel.add(passField);

        panel.add(inputPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Buttons
        JButton loginBtn = createStyledButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(loginBtn);

        JButton backBtn = HomeButton.create("🏠 Back to Main", e -> {
            resetView();
            frame.showPanel("MainMenu");
        });
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(backBtn);

        loginBtn.addActionListener(e -> {
            String selected = (String) playerSelect.getSelectedItem();
            String password = new String(passField.getPassword()).trim();

            if (Player.verifyPassword(selected, password)) {
                showThemedMessage(frame, "Welcome back, " + selected + " 🌌", "AstroQuest");
                panel.setVisible(false);
                frame.showPanel("Gameplay");
            } else {
                showThemedMessage(frame, "Incorrect password!", "AstroQuest");
                resetView();
                frame.showPanel("MainMenu");
            }
        });

        return panel;
    }

    // Reset all popups to initial state
    public void resetView() {
        initialPopup.setVisible(true);
        newProfilePopup.setVisible(false);
        existingProfilePopup.setVisible(false);

        // Clear input fields
        for (Component comp : newProfilePopup.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component c : ((JPanel) comp).getComponents()) {
                    if (c instanceof JTextField) ((JTextField) c).setText("");
                    if (c instanceof JPasswordField) ((JPasswordField) c).setText("");
                }
            }
        }

        for (Component comp : existingProfilePopup.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component c : ((JPanel) comp).getComponents()) {
                    if (c instanceof JPasswordField) ((JPasswordField) c).setText("");
                }
            }
        }

        revalidate();
        repaint();
    }

    // Styled button helper
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 223, 88));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Orbitron", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(200, 50));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 240, 120));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 223, 88));
            }
        });
        return button;
    }

    // Style input fields
    private void styleInputField(JTextComponent field, String placeholder) {
        field.setMaximumSize(new Dimension(400, 40));
        field.setFont(new Font("Orbitron", Font.PLAIN, 18));
        field.setOpaque(false);
        field.setForeground(Color.GRAY);
        field.setCaretColor(Color.CYAN);
        field.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2, true));

        // Placeholder text
        field.setText(placeholder);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.YELLOW);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Themed message box
    private void showThemedMessage(JFrame frame, String message, String title) {
        Font defaultFont = UIManager.getFont("Label.font");

        UIManager.put("OptionPane.background", new Color(0, 0, 30, 220));
        UIManager.put("Panel.background", new Color(0, 0, 30, 220));
        UIManager.put("OptionPane.messageForeground", Color.YELLOW);
        UIManager.put("Button.background", new Color(0, 0, 60));
        UIManager.put("Button.foreground", Color.CYAN);
        UIManager.put("Button.font", new Font("Orbitron", Font.BOLD, 16));
        UIManager.put("Label.font", new Font("Orbitron", Font.PLAIN, 18));

        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);

        UIManager.put("Label.font", defaultFont);
    }
}
