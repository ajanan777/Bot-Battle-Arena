import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents whisker robot, this robot has two whiskers extending out from it to dectect collision far away from itself
 */
public class WhiskerRobot extends Robot {
    private Line leftWhisker;
    private Line rightWhisker;
    private Arena arena;
    private static final double WHISKER_ANGLE = 22.5; // Angle offset for whiskers
    private static final double WHISKER_LENGTH = 50; // Length of each whisker
    /**
     * Constructs a whisker robot with a specific x coordinate, y coordinate, size, colour, health points, damage, speed, direction and the arena
     * @param x
     * @param y
     * @param size
     * @param colour
     * @param hp
     * @param damage
     * @param speed
     * @param direction
     * @param arena
     */
    public WhiskerRobot(double x, double y, double size, Color colour, int hp, int damage, double speed, double direction, Arena arena) {
        super(x, y, size, colour, hp, damage, speed, direction);
        this.arena = arena;
        updateWhiskers(); // Initialize whiskers
    }
    /**
     * Updates the positions of the whiskers based on the robots position and the direction its facing
     */
    private void updateWhiskers() {
        // Calculate the angles for left and right whiskers
        double radLeft = Math.toRadians(direction - WHISKER_ANGLE);
        double radRight = Math.toRadians(direction + WHISKER_ANGLE);

        // Calculate endpoints of the left whisker
        double leftEndX = x + (25 + WHISKER_LENGTH) * Math.cos(radLeft);
        double leftEndY = y + (25 + WHISKER_LENGTH) * Math.sin(radLeft);

        // Calculate endpoints of the right whisker
        double rightEndX = x + (25 + WHISKER_LENGTH) * Math.cos(radRight);
        double rightEndY = y + (25 + WHISKER_LENGTH) * Math.sin(radRight);

        // Create the whiskers using the Line class
        leftWhisker = new Line(x, y, leftEndX, leftEndY);
        rightWhisker = new Line(x, y, rightEndX, rightEndY);
    }
    /**
     * Updates whiskerRobot's movement, its whisker positions, and checks whether the whiskers collide with the arena boundaries,
     * if so, it turns 45 degrees away from the boundary, also checks for collisions with order robots and barriers through checking whether the whisker end points are touching the items
     */
    @Override
    public void update() {
        super.move(); // Update the robot's position
        updateWhiskers(); // Update whisker positions
        boolean leftColl = false;   //ensures that if both whiskers collide at the same time, there is only one direction change.
        boolean rightColl = false;
        if (leftWhisker.coords[2] >= 800 || leftWhisker.coords[2] <= 0 || leftWhisker.coords[3] >= 700 || leftWhisker.coords[3] <= 0) {
            leftColl = true;
        }
        if (rightWhisker.coords[2] >= 800 || rightWhisker.coords[2] <= 0 || rightWhisker.coords[3] >= 700 || rightWhisker.coords[3] <= 0) {
            rightColl = true;
        }
        if (leftColl && rightColl == true) {
            direction += 180;
        } else if (leftColl == true) {
            direction +=45;
        } else if (rightColl == true) {
            direction -=45;
        }

        
        //Whisker robot vs robot collision
        for (Item item : arena.getItems()) {
            if (item instanceof Robot && item != this) {
                Robot otherRobot = (Robot) item;
    
                // Calculate distance between whisker endpoints and robot center
                double leftDistance = Line.distance(leftWhisker.coords[2], leftWhisker.coords[3], otherRobot.getX(), otherRobot.getY());
                double rightDistance = Line.distance(rightWhisker.coords[2], rightWhisker.coords[3], otherRobot.getX(), otherRobot.getY());
    
                // Check if left whisker collides with the other robot
                if (leftDistance < otherRobot.getSize() / 2) {
                    direction += 45; // Turn right
                    return; // Only one whisker needs to change direction
                }
    
                // Check if right whisker collides with the other robot
                if (rightDistance < otherRobot.getSize() / 2) {
                    direction -= 45; // Turn left
                    return; // Only one whisker needs to change direction
                }
            }


            //Whisker robot vs Barrier collision
            if (item instanceof Barrier) {
                Barrier barrier = (Barrier) item;
    
                // Barrier bounds
                double barrierLeft = barrier.getX() - barrier.getSize() / 2;
                double barrierRight = barrier.getX() + barrier.getSize() / 2;
                double barrierTop = barrier.getY() - barrier.getSize() / 2;
                double barrierBottom = barrier.getY() + barrier.getSize() / 2;
    
                // Check left whisker collision
                if (leftWhisker.coords[2] >= barrierLeft && leftWhisker.coords[2] <= barrierRight &&
                    leftWhisker.coords[3] >= barrierTop && leftWhisker.coords[3] <= barrierBottom) {
                    // Collision with left whisker, change direction
                    direction += 45; // Turn right to avoid
                }
    
                // Check right whisker collision
                if (rightWhisker.coords[2] >= barrierLeft && rightWhisker.coords[2] <= barrierRight &&
                    rightWhisker.coords[3] >= barrierTop && rightWhisker.coords[3] <= barrierBottom) {
                    // Collision with right whisker, change direction
                    direction -= 45; // Turn left to avoid
                }
            }
        }
    }
    /**
     * Draws the whiskerRobot
     */
    @Override
    public void draw(GraphicsContext gc) {

        // Draw the whiskers
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(
            leftWhisker.coords[0], leftWhisker.coords[1], // Start point of left whisker
            leftWhisker.coords[2], leftWhisker.coords[3]  // End point of left whisker
        );
        gc.strokeLine(
            rightWhisker.coords[0], rightWhisker.coords[1], // Start point of right whisker
            rightWhisker.coords[2], rightWhisker.coords[3]  // End point of right whisker
        );
        super.draw(gc); // Draw the robot's body
    }
}
