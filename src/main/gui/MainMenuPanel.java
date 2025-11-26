package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
/**
 * Main Menu Panel for AstroQuest
 */
public class MainMenuPanel extends JPanel {

    private JLabel bgLabel;

    public MainMenuPanel(MainFrame frame) {

        // === Load Animated GIF Background ===
        // Put a smooth looping GIF named "space_loop.gif" inside /assets/
        ImageIcon bgVideo = new ImageIcon("src/assets/space_loop.gif");
        bgLabel = new JLabel(bgVideo);
        bgLabel.setLayout(new GridBagLayout());

        // === Transparent Overlay ===
        JPanel overlay = new JPanel();
        overlay.setOpaque(false);
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Title Box (Glowing Yellow) ===
        JPanel titleBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0,
                        new Color(255, 223, 0, 230),  // bright yellow
                        getWidth(), getHeight(),
                        new Color(255, 180, 0, 200)   // orange glow
                );
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                g2d.setColor(new Color(255, 255, 180, 160)); // soft outer glow
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 30, 30);
            }
        };
        titleBox.setOpaque(false);
        titleBox.setPreferredSize(new Dimension(600, 120));
        titleBox.setLayout(new GridBagLayout());

        JLabel title = new JLabel("ASTROQUEST");
        title.setFont(new Font("Orbitron", Font.BOLD, 50));
        title.setForeground(new Color(20, 20, 20)); // slightly off-black for contrast

        titleBox.add(title);
        titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Buttons ===
        JButton startBtn = createGlowingButton("Start");
        JButton aboutBtn = createGlowingButton("About");
        JButton exitBtn = createGlowingButton("Exit");

        // Button Actions
        startBtn.addActionListener(e -> {
    frame.showPanel("Setup");
});


       aboutBtn.addActionListener(e -> {
    frame.mainPanel.add(new AboutPanel(frame), "About");
    frame.showPanel("About");
});


        exitBtn.addActionListener(e -> System.exit(0));

        // === Add components to overlay ===
        overlay.add(titleBox);

        // === Description Line ===
        JLabel description = new JLabel("Explore the universe, one discovery at a time.");
        description.setFont(new Font("Orbitron", Font.PLAIN, 22));
        description.setForeground(new Color(255, 230, 100));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        overlay.add(description);

        overlay.add(Box.createRigidArea(new Dimension(0, 40)));
        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(startBtn);
        buttonRow.add(aboutBtn);
        buttonRow.add(exitBtn);

        overlay.add(buttonRow);

        // === Center Overlay on Background ===
        bgLabel.add(overlay);
        setLayout(new BorderLayout());
        add(bgLabel, BorderLayout.CENTER);
    }

// === Custom Glowing Button ===
private JButton createGlowingButton(String text) {
    JButton btn = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background gradient
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(0, 200, 255, 180),
                    getWidth(), getHeight(), new Color(0, 120, 255, 180)
            );
            g2d.setPaint(gp);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

            // Glowing border
            g2d.setColor(new Color(0, 255, 255, 200));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 25, 25);

            // Draw text manually (no super.paintComponent)
            FontMetrics fm = g2d.getFontMetrics(getFont());
            int textWidth = fm.stringWidth(getText());
            int textHeight = fm.getAscent();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() + textHeight) / 2 - 5;
            g2d.setColor(getForeground());
            g2d.drawString(getText(), x, y);

            g2d.dispose();
        }

        @Override
        public boolean isOpaque() {
            return false;
        }
    };

    // Button styling
    btn.setFont(new Font("Orbitron", Font.BOLD, 22));
    btn.setForeground(Color.BLACK);
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(false);
    btn.setBorderPainted(false);
    btn.setOpaque(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    // === Hover Animation ===
    btn.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent evt) {
            btn.setFont(new Font("Orbitron", Font.BOLD, 24));
        }
        public void mouseExited(MouseEvent evt) {
            btn.setFont(new Font("Orbitron", Font.BOLD, 22));
        }
    });

    return btn;
}

}
