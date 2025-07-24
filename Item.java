import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Abstract Class for each item in the simulation
 * All items have a position, size, colour, unique ID and a activity state
 */
public abstract class Item {
    //Attributes
    protected double x; //x pos
    protected double y; //y pos
    protected double size;  //size
    protected Color colour; //colour of the item 
    protected boolean active; //is it in arena 
    private final int id; //unique id for each item 

    // id counter
    private static int idCounter = 0;
    /**
     * Constructor to initalise an item, with a specific x coordinate, y coordinate, size and colour
     * @param x
     * @param y
     * @param size
     * @param colour
     */
    //constructor 
    public Item(double x, double y, double size, Color colour)  {
        this.x = x;
        this.y = y;
        this.size = size;
        this.colour = colour;
        this.active = true;
        this.id = ++idCounter;
    }

    // Abstract Methods
    //abstract draw method
    /**
     * Abstract method to draw the item on the canvas
     * @param gc
     */
    public abstract void draw(GraphicsContext gc);
    /**
     * Abstract method to update the items state
     */
    //abstract update method 
    public abstract void update();

    /**
     * Method to check if item collides with another item with circle shape
     * @param target    the target item to check for a collision with
     * @return  true if they are colliding
     */
    //Methods
    //Collision check method for circle items 
    public boolean checkCollisionCirc(Item target)  {
        double distance = Math.sqrt((this.x - target.x)*(this.x - target.x)+(this.y - target.y)*(this.y - target.y));
        return distance < (this.size/2 + target.size/2);    // If distance is smaller than addition of both radius return True
    }

    //Getters
    /**
     * Get's the items unique ID
     * @return the items unique ID
     */
    public int getId() {
        return id; 
    }
    /**
     * Get's the items X coordinate
     * @return the items X coordinate
     */
    public double getX()    {
        return x;
    }
    /**
     * Get's the items Y coordinate
     * @return the items Y coordinate
     */
    public double getY()    {
        return y;
    }
    /**
     * Get's the items size
     * @return the items size
     */
    public double getSize() {
        return size;
    } 
    /**
     * Get's the items colour
     * @return the items colour
     */
    public Color getColour()    {
        return colour;
    }
    /**
     * Check if the item is active
     * @return true if the item is active
     */
    public boolean isActive()   {
        return active;
    }

    //Setters
    /**
     * Sets the x coordinate of the items position
     * @param x
     */
    public void setX(double x)   {
        this.x = x;
    }
    /**
     * Sets the y coordinate of the items position
     * @param y
     */
    public void setY(double y)    {
        this.y = y;
    }
    /**
     * Sets the item size
     * @param size
     */
    public void setSize(double size) {
        this.size = size;
    } 
    /**
     * Sets the item's colour
     * @param colour
     */
    public void setColour(Color colour)    {
        this.colour = colour;
    }
    /**
     * Sets the active state of the item
     * @param active
     */
    public void setActive(boolean active)   {
        this.active = active;
    }
    
}