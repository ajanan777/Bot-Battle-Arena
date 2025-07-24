import javafx.scene.paint.Color;

/**
 * default robot with bump sensor
 */
public class DefaultRobot extends Robot{
    /** Constructor to initalise a defaultrobot, with a specific x coordinate, y coordinate, size, colour, health points, specifically 0 damage, speed and direction
     * 
     * @param x
     * @param y
     * @param size
     * @param colour
     * @param hp
     * @param speed
     * @param direction
     */
    //Constructor
    public DefaultRobot(double x, double y, double size, Color colour, int hp, double speed, double direction)  {
        super(x, y, size, colour, hp, 0, speed, direction);
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
