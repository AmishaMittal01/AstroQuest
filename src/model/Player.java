package model;

import java.io.*;
import java.util.*;

public class Player {

    private static final String DATA_FILE = "src/assets/players.txt";
    private static Map<String, String> players = new HashMap<>();

    // Load players at startup
    static {
        loadPlayers();
    }

    public static boolean playerExists(String name) {
        return players.containsKey(name);
    }

    public static void addPlayer(String name, String password) {
        players.put(name, password);
        savePlayers();
    }

    public static boolean verifyPassword(String name, String password) {
        return password != null && password.equals(players.get(name));
    }

    public static List<String> getAllPlayers() {
        return new ArrayList<>(players.keySet());
    }

   private static void loadPlayers() {
    File file = new File(DATA_FILE);

    // Create the file if it doesn't exist
    if (!file.exists()) {
        try {
            // Ensure parent folder exists (assets/)
            file.getParentFile().mkdirs();
            file.createNewFile();  // create empty players.txt
        } catch (IOException e) {
            e.printStackTrace();
        }
        return; // file created, nothing to read yet
    }

    // Read existing file
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            // Each line: name:password
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                players.put(parts[0], parts[1]);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private static void savePlayers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Map.Entry<String, String> entry : players.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
