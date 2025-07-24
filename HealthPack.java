import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * the HealthPack item in the simulation
 * When a robot passes over a Healthpack, the robot's health points increases by 2
 * After a robot collision the Healthpack is then removed from the arena
 */
public class HealthPack extends Item{
/**
 * Constructor to initalise the Healthpack at a position with a size
 * @param x     x coordinate
 * @param y     y coordinate
 * @param size  diameter size of healthpack
 * 
 */
    // Constructor
    public HealthPack(double x, double y, double size) {
        super(x, y, size, Color.GREEN);
    }


    /**
     * draws the healthpack with color green
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(colour);
        gc.fillOval(x - size/2, y - size/2, size, size);
    }



    /**
     * As healthpack is static it doesnt update anything
     */
    @Override
    public void update() {}
}