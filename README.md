# Bot-Battle-Arena
An interactive Java-based robot simulation GUI. Features include multiple robot types, animated movement, collision handling, and user controls to add, remove, and manipulate arena objects.
## Overview

This is a Java-based robot simulation with a graphical user interface, created for an OOP coursework project. The simulation takes place in an arena where different types of robots and items move around and interact with each other. The GUI lets users control the simulation, add or remove robots and items, and watch how everything behaves in real time.

---

## How It Works

The program opens in a window with menu options along the top and bottom. When the simulation starts, it automatically loads a default arena that includes a few robots, barriers, and items placed randomly.

Robots move in various directions and bounce off objects they collide with. They react differently depending on what they encounter—some just avoid obstacles, while others can deal or take damage. The simulation runs as an animation that can be paused, resumed, or reset.

Users can interact with the arena by adding new robots or items through the menu. Items and robots can be dragged around with the mouse, and selected objects can be deleted by pressing the delete key.

---

## Robots and Items
![ezgif-7616ec68156f49](https://github.com/user-attachments/assets/a048aa0f-8b1f-46d4-a16a-7f0173124fa5)

### Robot Types

- **DefaultRobot**  
  A simple robot that moves straight ahead and changes direction when it hits something.

- **AttackerRobot**  
  A robot that damages other robots on contact. It starts with normal damage and becomes stronger if it collects a damage boost.

- **WhiskerRobot**  
  A robot with sensors ("whiskers") that stick out in front. These allow it to detect obstacles before it hits them and adjust its path.

All robots have a health value, which is displayed on their body. When their health reaches zero, they are removed from the arena.

### Item Types

- **Barrier**  
  A fixed object that blocks movement. Robots bounce off it.

- **HealthPack**  
  Heals robots that move over it, restoring some of their health points.

- **DamageBoost**  
  Boosts a robot's damage. AttackerRobots get stronger, and non-hostile robots gain the ability to deal damage.


## User Interaction

![add del](https://github.com/user-attachments/assets/5d102404-c1d1-47f1-8a0a-2af98c93adeb)

The simulation is designed to be interactive and user-friendly. Most actions can be done using the menus or directly within the arena window.

- **Starting and Stopping**  
  The animation can be started or paused using the buttons in the bottom menu bar.

- **Adding Robots and Items**  
  Use the dropdown menus to add specific types of robots or items to the arena at any time. New objects appear in a random position.

- **Moving Objects**  
  Click and drag any robot or item to move it around the arena. This works while the animation is running or paused.

- **Deleting Objects**  
  Select an object by clicking on it, then press the **Delete** key to remove it from the arena.

- **Creating a New Arena**  
  The "New Arena" option resets the simulation and loads a default setup with a few robots, barriers, and items randomly placed.

- **Help and About**  
  The top menu includes a Help section that explains how to use the program, and an About section with basic information.

This setup allows users to experiment with different combinations of robots and items, and observe how they behave in real time.
