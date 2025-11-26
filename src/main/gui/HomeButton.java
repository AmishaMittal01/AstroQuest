package main.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HomeButton {

    public static JButton create(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Orbitron", Font.BOLD, 22));
        btn.setForeground(Color.BLACK);
        btn.setBackground(new Color(0, 255, 255));
        btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3, true));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(action);

        
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
