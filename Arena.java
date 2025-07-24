import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The Arena where all items interact with each other
 * 
 */
public class Arena {

    //Attributes
    private List<Item> items; //list of all the items which are in the arena
    private Robot selectedRobot;
    private Item selectedItem;
    /**
     * Constructs an empty arena
     */
    //Constructor
    public Arena()  {
        this.items = new ArrayList<>(); //create arraylist of items
    }



    //methods
    /**
     * adds item to the arena
     * @param item
     */
    //method to add item to arena
    public void addItem(Item item)  {
        System.out.println("Added item: " + item.getClass().getSimpleName() + " with ID: " + item.getId());
        items.add(item);
    }
    /**
     * removes item from the arena
     * @param item
     */
    //remove item from arena
    public void removeItem(Item item)   {
        items.remove(item);
    }
    /**
     * removes all items from the arena
     */
    public void removeAllItems() {
        items.clear();
    }
    /**
     * 
     * @return the currently selected robot
     */
    // get the selected robot
    public Robot getSelectedRobot() {
        return selectedRobot;
    }
    /**
     * sets the currently selected robot
     * @param selectedRobot
     */
    public void setSelectedRobot(Robot selectedRobot) {
        this.selectedRobot = selectedRobot;
    }
    /**
     * 
     * @return array list of all items in the arena
     */
    public List<Item> getItems() {
        return items;
    }
    /**
     * 
     * @return the selected item
     */
    public Item getSelectedItem() {
        return selectedItem;
    }
    /**
     * sets the currently selected item
     * @param selectedItem
     */
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * updates all items within the arena
     * removes dead robots and checks for collisions
     */
    //update items
    public void updateAll() {
        List<Item> removeItems = new ArrayList<>();

        for (Item item: items)  {
            if (item instanceof Robot)  {
                Robot robot = (Robot) item;

                if (robot.isAlive())    {
                    robot.update();
                } else {
                    removeItems.add(robot);
                }
            }
        }
        items.removeAll(removeItems);
        checkCollisions();
    }
    /**
     * draws all items onto the arena
     * @param gc
     */
    //Draw items onto arena canvas
    public void renderAll(GraphicsContext gc)   {
        for (Item item : items) {
            if (item.isActive())    {
                item.draw(gc);
            }
        }
    }
    /**
     * checks for colllisons between robots and barriers, robots and healthpacks, and robots vs robots
     * removes hp of robots who fight
     * changes direction of movement of any robot that collides
     */
    private void checkCollisions()  {
        List<Item> removeItems = new ArrayList<>();
        for (Item item1 : items) {
            for (Item item2 : items) {
                if (item1 == item2) {
                    continue; // Skip itself
                }

                // Robot vs Barrier collisions
                if (item1 instanceof Robot && item2 instanceof Barrier) {
                    
                    Robot robot = (Robot) item1;
                    Barrier barrier = (Barrier) item2;
                    if (barrier.isCollisionRobot(robot)) {
                        // Determine the collision side
                        double dx = robot.getX() - barrier.getX(); // Horizontal distance betwewen robot and barrier center 
                        double dy = robot.getY() - barrier.getY(); // Vertical distance betwewen robot and barrier center 
                        // Check which side of the barrier the robot hit
                        if (Math.abs(dx) > Math.abs(dy)) { //Checks whether the collision was more horizontal than vertical
                            if (dx > 0) { //postive dx means it hit the right side
                                robot.setDirection(180 - robot.getDirection()); //reflects direction accordingly
                            } else { //if it hit the left side
                                robot.setDirection(180 - robot.getDirection()); 
                            }
                        } else { //If closer to vertical collision
                            if (dy > 0) { //postive dy means it hit the bottom side
                                robot.setDirection(-robot.getDirection()); // Hit bottom side
                            } else { //if it hit the top side
                                robot.setDirection(-robot.getDirection()); // Hit top side
                            }
                        }
                    }
                }

                //Robot vs HealthPack collisions
                if (item1 instanceof Robot) {
                    Robot robot = (Robot) item1;
                    if (item2 instanceof HealthPack && item2.checkCollisionCirc(robot)) {
                        robot.takeDamage(-2);
                        removeItems.add(item2);
                    } else if (item2 instanceof DamageBoost && item2.checkCollisionCirc(robot)) {
                        robot.setDamage();;
                        removeItems.add(item2);
                    }
                }

                //collision check between robots
                if (item1 instanceof Robot && item2 instanceof Robot)   {
                    Robot r1 = (Robot) item1;
                    Robot r2 = (Robot) item2;

                    if (r1.checkCollisionCirc(r2)) { //if robots are colliding
                        if (r1.canTakeDamage(500)) {
                            r2.takeDamage(r1.damage);
                        }
                        if (r2.canTakeDamage(500)) {
                            r1.takeDamage(r2.damage);
                        } 
                    }

                    // Robot vs Robot collisions
                    if (r1.checkCollisionCirc(r2)) {
                        // get normal vector between r1 and r2
                        double dx = r2.getX() - r1.getX();
                        double dy = r2.getY() - r1.getY();
                        double distance = Math.sqrt(dx * dx + dy * dy);
                        // Normalize
                        double nx = dx / distance;
                        double ny = dy / distance;
                        // Update directions
                        r1.setDirection(Math.toDegrees(Math.atan2(-ny, -nx)));
                        r2.setDirection(Math.toDegrees(Math.atan2(ny, nx)));
                    }
                }                
            }
        }
        items.removeAll(removeItems);
    }
}
