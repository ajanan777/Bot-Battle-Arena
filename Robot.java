import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Abstract class for the robots in the simulation
 * every robot has health, damage, speed, direction and they can move
 */
public abstract class Robot extends Item {
    //Attributes
    protected int hp;
    protected int damage;
    protected double speed;
    protected double direction;
    protected long damagedTime;

    /**
     * Constructor to initalise a robot, with a specific x coordinate, y coordinate, size, colour, health points, damage, speed and direction
     * @param x
     * @param y
     * @param size
     * @param colour
     * @param hp
     * @param damage
     * @param speed
     * @param direction
     */
    //Constructor
    public Robot(double x, double y, double size, Color colour, int hp, int damage, double speed, double direction) {
        super(x, y, size, colour);
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.direction = direction;
        this.damagedTime = 0; 
    }
    /**
     * Method to move robot based on the direction its facing and its speed
     */
    //method for movement
    public void move()  {
		double radAngle = direction*Math.PI/180;		// change direction to radians
		this.x += speed * Math.cos(radAngle);          // new x pos
		this.y += speed * Math.sin(radAngle);          //new y pos 
    }
    /**
     * method to detect whether a robotis colliding with a wall
     * if so, the rbot is reflected in the other direction
     */
    //Collision with wall
    public void wallCollision() {
        if (x - size/2 < 4 || x + size/2 > 796) {       //if out of bounds horizontally 
            direction = 180 - direction;                //reflect direction horizontally 
        }
        if (y - size/2 < 4 || y + size/2 > 696) {       //if out of bounds vertically
            direction = -direction;                     //reflect direction vertically 
        }
    }
    /**
     * A method to reduce a robots hp by a specific amount when it takes damage
     * @param amount
     */
    //Methods for health and damage
    public void takeDamage(int amount)  {
        this.hp -=amount;
        if (hp < 0) {
            hp = 0;
        }
    }

    /**
     * determines if the robot is alive
     * @return true if the robot doesnt have 0 hp or lower
     */
    public boolean isAlive()    {
        return hp > 0;
    }
    /**
     * Determines whether a Robot can take damage
     * @param cooldown  once a robot collides with another,a cooldown is applied, so it cannot take damage to ensure double hits do not occur
     * @return true is the robot no longer has a cooldown and can take damage
     */
    // Damage cooldown
    public boolean canTakeDamage(long cooldown) {
        long timeNow = System.currentTimeMillis();
        if (timeNow - damagedTime >= cooldown)  {
            damagedTime = timeNow;
            return true;
        }
        return false;
    }

    /**
     * Method to draw the robot's body, hp and wheels
     */
    //draw method
    @Override
    public void draw(GraphicsContext gc) {
        // Body
        gc.setFill(colour);
        gc.fillOval(x - 25, y - 25, 50, 50); // Draw robot circle
        //hp text display
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeText(String.valueOf(hp), x-7, y+3);
        gc.fillText(String.valueOf(hp), x-7, y+3);
        gc.setFill(Color.BLACK);

        // Circle radius and wheel properties
        double radius = size / 2;
        double rectWidth = 20;
        double rectHeight = 5;

        // Draw wheels
        drawWheel(gc, x, y, radius, direction + 90, rectWidth, rectHeight); // Left wheel
        drawWheel(gc, x, y, radius, direction - 90, rectWidth, rectHeight); // Right wheel
    }
    /**
     * Draws a wheel at the specific position and angle the robot is facing
     * @param gc
     * @param centerX   x coordinate of robot center
     * @param centerY   y coordinate of robot center
     * @param offset    offset distance from the robot center, to place the wheel
     * @param angle     angle in which to place the wheel
     * @param width     width of the wheel
     * @param height    height of the wheel
     */
    // Helper method to draw a wheel
    private void drawWheel(GraphicsContext gc, double centerX, double centerY, double offset, double angle, double width, double height) {
        double wheelX = centerX + offset * Math.cos(Math.toRadians(angle));
        double wheelY = centerY + offset * Math.sin(Math.toRadians(angle));
        gc.save();
        gc.translate(wheelX, wheelY);
        gc.rotate(direction);
        gc.setFill(Color.BLACK);
        gc.fillRect(-(width/2), -(height/2), width, height);
        gc.restore();
    }
    /**
     * 
     * @return returns the direction the robot is facing
     */
    public double getDirection()    {
        return direction;
    }
    /**
     * 
     * @return the hp of the robot
     */    
    public double getHp()    {
        return this.hp;
    }
    /**
     * sets direction of the robot
     * @param direction
     */
    //Setters
    public void setDirection(double direction)   {
        this.direction = direction;
    }
    /**
     * sets damage the robot, when damage boost is picked up
     */
    public void setDamage()   {
        if (this.damage == 0) {
            this.damage = 2;
        } else {
            this.damage = damage*2;
        }
    }

    @Override
    public abstract void update(); // logic updating 

    

}
