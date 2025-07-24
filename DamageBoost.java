
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * the DamageBoost item in the simulation
 * When a hostile robot passes over a DamageBoost, the robot's damage doubles
 * When a non-hostile robot passes over a DamageBoost, its damage is set to 2, and becomes hostile
 * DamageBoosts can continually stack
 * After a robot collision the DamageBoost is then removed from the arena
 */
public class DamageBoost extends Item{
    /**
     * Constructor to initalise the Damageboost at a position with a size
     * @param x     x coordinate
     * @param y     y coordinate
     * @param size  diameter size of Damageboost
     * 
     */
    // Constructor
    public DamageBoost(double x, double y, double size) {
        super(x, y, size, Color.DARKRED);
    }

    /**
     * draws the healthpack with color Dark red
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(colour);
        gc.fillOval(x - size/2, y - size/2, size, size);
    }

    @Override
    public void update() {}
}
