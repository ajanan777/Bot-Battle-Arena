import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The barrier item, forces robots that bump into it to bounce away
 */
public class Barrier extends Item {
    /**
     * Constructs a black square barrier with its coordinates and size
     * @param x
     * @param y
     * @param size
     */
    //Constructor
    public Barrier(double x, double y, double size) {
        super(x, y, size, Color.BLACK);
    }
    /**
     * draws the barrier
     */
    // Drawing the barrier
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(colour);
        gc.fillRect(x-(size/2), y-(size/2), size, size); // Barrier is Square shaped
    }

    @Override
    public void update() {}    // Barrier doesnt move or change
    /**
     * checks for robot colisions into the barrier
     * @param robot
     * @return true if robot is colliding with barrier, so arena can deal with the direction change
     */
    // Check for robot collision
    public boolean isCollisionRobot(Robot robot) {
        double closestX = Math.max(x - (size/2), Math.min(robot.getX(), x + (size/2)));
        double closestY = Math.max(y - (size/2), Math.min(robot.getY(), y + (size/2)));
        double distance = Math.sqrt(Math.pow(robot.getX()-closestX, 2) + Math.pow(robot.getY()-closestY, 2));
        return distance < robot.getSize()/2; // Returns true if distance is less than robot radius size
    }
}
