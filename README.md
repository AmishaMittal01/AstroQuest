# 🌌 **AstroQuest**
**AstroQuest** is a Java Swing–based 2D space game demonstrating **Object-Oriented Programming**, **event-driven design**, **GUI development**, and **file-based data handling**.
Players collect stars, avoid obstacles, unlock planets, and view educational facts about the Solar System.
---
## ⚙️ **Tech Stack**
* **Language:** Java
* **GUI:** Swing (JPanel, JLabel, JButton, JTextArea, JScrollPane)
* **Graphics:** `Graphics2D`, `ImageIcon`
* **Game Loop:** `javax.swing.Timer`
* **Layouts:** CardLayout, BorderLayout, BoxLayout, FlowLayout
* **Data Handling:** File I/O (Scanner, BufferedReader/Writer)
---
## 🧱 **Project Structure**

```
src/
 ├── main/
 │   ├── AstroQuestGame.java
 │   └── gui/
 │        ├── MainFrame.java
 │        ├── MainMenuPanel.java
 │        ├── SetupPanel.java
 │        ├── GameplayPanel.java
 │        ├── PlanetInfoPanel.java
 │        ├── AboutPanel.java
 │        ├── HomeButton.java
 ├── model/
 │   ├── GameCharacter.java
 │   ├── Obstacle.java
 │   ├── Star.java
 │   ├── Fuel.java
 │   ├── Planet.java
 │   └── Player.java
 ├── assets/
 │   ├── icons/
 │   ├── fonts/
 │   ├── planets/
 │   ├── planets_info/
 │   ├── about_intro.txt
 │   ├── about_howto.txt
 │   ├── about_credits.txt
 │   ├── players.txt
 │   ├── space_loop.gif
 │   ├── planetinfo_bg.gif
 │   ├── bg_space.jpg
 │   ├── player.png
 │   ├── obstacle.png
 │   ├── star.png
 │   └── fuel.png
```
---
## 🧠 **OOP Concepts Implemented**
* **Encapsulation:** Game objects have private attributes with getters/setters.
* **Abstraction:** Planet, Player, and entity behaviors abstract internal logic.
* **Inheritance & Polymorphism:** Shared drawing/movement logic in model classes.
* **Composition:** Panels are composed of custom components and models.
---
## 🎮 **Gameplay Features**
* Continuous background scrolling
* Obstacle + star + fuel spawning
* Collision detection
* Planet unlock system with popup
* Player login system (new & existing)
* Separate UI screens handled via CardLayout
---
## ▶️ **How to Run**
**Compile:**
```sh
javac -d bin -sourcepath src src/main/AstroQuestGame.java
```
**Run:**
```sh
java -cp bin main.AstroQuestGame
```
## 👥 Project Team

This project was collaboratively developed as part of the  
**Object-Oriented Programming (OOP) Using Java – Semester 3** coursework by:

- **Harshita Saxena**
- **Sheen Sharma**
- **Amisha Mittal**

## 🎥 GamePlay Screen
https://github.com/user-attachments/assets/adcceac0-0f05-4743-ba3a-252c8f9dd9a1
