package model;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

public class Planet {
    private String name;
    private ImageIcon image;
    private String infoPath;

    public Planet(String name, String imagePath, String infoPath) {
        this.name = name;
        this.image = new ImageIcon(imagePath);
        this.infoPath = infoPath;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getDescription() {
        StringBuilder content = new StringBuilder();
        try (Scanner sc = new Scanner(new File(infoPath))) {
            while (sc.hasNextLine()) content.append(sc.nextLine()).append("\n");
        } catch (Exception e) {
            content.append("Information not available for " + name + ".");
            System.err.println("⚠️ Could not read file: " + infoPath);
        }
        return content.toString().trim();
    }
}
