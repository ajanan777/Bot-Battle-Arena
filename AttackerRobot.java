import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Attacker robot, a robot that deals damage on collision
 */
public class AttackerRobot extends Robot{
    /**
     * * Constructor to initalise a attackerrobot, with a specific x coordinate, y coordinate, size, colour, health points, specifically 2 damage, speed and direction
     * @param x
     * @param y
     * @param size
     * @param colour
     * @param hp
     * @param speed
     * @param direction
     */
    //Constructor
    public AttackerRobot(double x, double y, double size, Color colour, int hp, double speed, double direction)  {
        super(x, y, size, colour, hp, 2, speed, direction);
    }

    /**
     * draws attacker robot
     */
    // Drawing AttackerRobot
    @Override
    public void draw(GraphicsContext gc) {
        // Body
        gc.setFill(colour);
        gc.fillOval(x - size / 2, y - size / 2, size, size);
        // Draw letter A in robot to show its attacker robot
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeText(String.valueOf(hp), x-7, y+3);
        gc.fillText(String.valueOf(hp), x-7, y+3);
        super.draw(gc); // Draw the robot's body
    }

    /**
     * updates robot to check for wall collisions and to move it
     */
    // update robot(wall collisions and moving)
    @Override
    public void update()    {
        move();
        wallCollision();
    }
}
