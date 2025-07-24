import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.geometry.Side;
import java.text.DecimalFormat;
import java.util.Random;


/**
 * Main class for the Robot Simulation application.
 * This class initialises and manages the GUI, and handles all user interaction
 * This includes adding items to the arena, and user moving and deleteing items from the arena
 */


public class SimMain extends Application {

    private AnimationTimer animationTimer;
    private boolean isRunning = false;
    private TextArea infoText;
        
   /**
    * Starts the JavaFX appplication initializing all GUI components
    * @param primaryStage this is the main application stage
    * 
    */
    @Override
    public void start(Stage primaryStage) {
        //Root Pane
        Pane root_pane = new Pane();

        //Top toolbar Bar
        HBox toolbar = new HBox(1);
        toolbar.setStyle("-fx-background-color:rgb(207, 207, 207);");
        toolbar.setPrefHeight(25);
        toolbar.setLayoutX(1);
        toolbar.setLayoutY(0);
        toolbar.setPrefWidth(1325);

        //buttons
        Button helpButton = new Button("Help");     //Help button
        Button aboutButton = new Button("About");   //About button

        //dropdown for File
        MenuButton fileDropdown = new MenuButton("File");   //File button
        MenuItem newSim = new MenuItem("New Simulation");   //New simulation button
        MenuItem openSim = new MenuItem("Open Simulation"); //open simulation button
        MenuItem saveSim = new MenuItem("Save");            //save simulation button
        MenuItem saveSimAs = new MenuItem("Save as");       //save as simulation button
        fileDropdown.getItems().addAll(newSim, openSim, saveSim, saveSimAs);    //create dropdown with elements


        toolbar.getChildren().addAll(fileDropdown, helpButton, aboutButton);   //add buttons and drop down to top toolbar

        //Arena 
        Canvas canvas = new Canvas(800, 700);   //create arena cavas
        canvas.setLayoutX(0);
        canvas.setLayoutY(26);

        // Get graphics context to draw the border on canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int stroke = 2;
        gc.setLineWidth(stroke);
        gc.strokeRect(stroke/2, stroke/2, canvas.getWidth() - stroke, canvas.getHeight() - stroke);

        //Bottom toolbar Bar
        HBox btoolbar = new HBox(1);
        btoolbar.setStyle("-fx-background-color:rgb(207, 207, 207);");
        btoolbar.setPrefHeight(25);
        btoolbar.setLayoutX(1);
        btoolbar.setLayoutY(canvas.getHeight()+26);
        btoolbar.setPrefWidth(1325);

        //buttons
        Button startButton = new Button("Start Animation");     //button to start animation
        Button pauseButton = new Button("Pause Animation");     //button to pause animation

        //dropdown for Add Robot
        MenuButton addRobotDropdown = new MenuButton("Add Robot");
        MenuItem defaultRobot = new MenuItem("Default Robot");
        MenuItem whiskerRobot = new MenuItem("Whisker Robot");
        MenuItem beamRobot = new MenuItem("Beam Robot");
        MenuItem attackerRobot = new MenuItem("Attacker Robot");
        addRobotDropdown.getItems().addAll(defaultRobot, whiskerRobot, beamRobot, attackerRobot); //add buttons to dropdown
    
        // Ensures bottom toolbar dropdown flows upwards instead of downwards
        addRobotDropdown.showingProperty().addListener((obs, closed, opened) -> {
            if (opened) {
                addRobotDropdown.setPopupSide(Side.TOP);
            }
        });

        //dropdown for Add Items
        MenuButton addItemDropdown = new MenuButton("Add Item");
        MenuItem addBarrier = new MenuItem("Barrier");
        MenuItem addHealthPack = new MenuItem("Health Pack");
        MenuItem addDamageBoost = new MenuItem("Damage Boost");
        MenuItem extra1 = new MenuItem(" ... ");
        addItemDropdown.getItems().addAll(addBarrier, addHealthPack, addDamageBoost, extra1); //add buttons to dropdown
        
        // Ensures bottom toolbar dropdown flows upwards instead of downwards
        addItemDropdown.showingProperty().addListener((obs, closed, opened) -> {
            if (opened) {
                addItemDropdown.setPopupSide(Side.TOP);
            }
        });

        btoolbar.getChildren().addAll(startButton, pauseButton, addRobotDropdown, addItemDropdown); //bottom toolbar

        //Default spawn location for robots
        double defaultX = 400;
        double defaultY = 350;

        Random random = new Random();
        //Arena creation
        Arena arena = new Arena();
        //bottom menu
        defaultRobot.setOnAction(event -> {     //Configuring default robot add button
            double r = random.nextDouble() * 360; //to create robot facing random direction
            DefaultRobot newRobot = new DefaultRobot(defaultX, defaultY, 50, Color.BLUE, 10, 1, r);
            arena.addItem(newRobot);
        });

        attackerRobot.setOnAction(event -> {    //Configuring attacker robot add button
            double r = random.nextDouble() * 360;
            AttackerRobot newRobot = new AttackerRobot(defaultX, defaultY, 50, Color.RED, 10, 1, r);
            arena.addItem(newRobot);
        });

        whiskerRobot.setOnAction(event -> {          //Configuring whisker robot add button
            double r = random.nextDouble() * 360;
            WhiskerRobot newRobot = new WhiskerRobot(400,350 , 50, Color.GREEN, 10, 0, 1, r, arena); 
            arena.addItem(newRobot);
        });

        addBarrier.setOnAction(event -> {       //Configuring barrier item add button
            Barrier barrier = new Barrier(400, 350, 20 + random.nextDouble() * 90);
            arena.addItem(barrier);
        });

        addHealthPack.setOnAction(event -> {    //Configuring healthpack item add button
            HealthPack healthpack = new HealthPack(400, 350, 23);
            arena.addItem(healthpack);
        });

        addDamageBoost.setOnAction(event -> {   //Configuring damageboost item add button
            DamageBoost damageboost = new DamageBoost(400, 350, 23);
            arena.addItem(damageboost);
        });

        //top menu
        newSim.setOnAction(event -> {
            arena.removeAllItems(); //when new simulation is trigged delete all items on canvas

            //Default populated arena
            arena.addItem(new WhiskerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.GREEN, 10, 0, 1, random.nextDouble() * 360, arena)); 
            arena.addItem(new WhiskerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.GREEN, 10, 0, 1, random.nextDouble() * 360, arena)); 
            arena.addItem(new AttackerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.RED, 10, 1, random.nextDouble() * 360)); 
            arena.addItem(new AttackerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.RED, 10, 1, random.nextDouble() * 360)); 
            arena.addItem(new DefaultRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.BLUE, 10, 1, random.nextDouble() * 360)); 
            arena.addItem(new Barrier(random.nextDouble() * 800, random.nextDouble() * 700, random.nextDouble() * 100));  

            for (int i = 0; i < 5; i++) { //add 5 health and damage boosts
                arena.addItem(new HealthPack(25 + random.nextDouble() * 700, 25 +  random.nextDouble() * 550, 23)); 
                arena.addItem(new DamageBoost(25 + random.nextDouble() * 700, 25 +  random.nextDouble() * 550, 23)); 
            }
        });

        //About menu
        aboutButton.setOnAction(event -> {
            //Stage for popup
            Stage about = new Stage();
            about.setTitle("About");

            //Content
            Pane aboutP = new Pane();
            javafx.scene.text.Text aboutText = new javafx.scene.text.Text(("This is a Robot Simulation GUI.\n"
            + "Each robot has a pool of healthpoints shown on its body\n"
            + "Robots can attack each other, as long as they are either a attacker robot, or they have picked up a damage boost.\n"
            + "If a passive robot picks up a damage boost, they become able to attack dealing 2hp per hit.\n"
            + "If a hostile robot picks up a damage boost, their damage gets multiplied by 2! \n"
            + "Health pack items allow robots to heal 2hp per pack. \n"
            + "Student number: 32025097 \n"
            ));
            aboutText.setWrappingWidth(300);
            aboutText.setLayoutX(20);
            aboutText.setLayoutY(30);
            aboutText.setTextAlignment(TextAlignment.CENTER);
            aboutP.getChildren().add(aboutText);
            Scene aboutScene = new Scene(aboutP, 350, 200);

            about.setScene(aboutScene);
            about.setResizable(false);
            about.show();
        });
        //help menu
        helpButton.setOnAction(event -> {
            //Stage for popup
            Stage help = new Stage();
            help.setTitle("Help");

            //Content
            Pane helpP = new Pane();
            javafx.scene.text.Text helpText = new javafx.scene.text.Text(("This is the help page.\n"
            + "To start a New simulation Go to File -> New Simulation.\n"
            + "To Load a saved simulation Go to File -> Open Simulation. \n"
            + "To save a New simulation Go to File -> Save.\n"
            + "Use the bottom Toolbar to Stop/Start the animation, and add various Robots and Items from the drop down menus. \n"
            + "To move Robots and Items, Click and drag the item in the arena. \n"
            + "To delete Robots and Items, Click the item and press the delete button on your keyboard. \n"
            ));
            helpText.setWrappingWidth(300);
            helpText.setLayoutX(20);
            helpText.setLayoutY(30);
            helpText.setTextAlignment(TextAlignment.CENTER);
            helpP.getChildren().add(helpText);
            Scene helpScene = new Scene(helpP, 350, 220);

            help.setScene(helpScene);
            help.setResizable(false);
            help.show();
        });

        //Information panel
        VBox infoPanel = new VBox(10);
        infoPanel.setStyle("-fx-background-color: #f4f4f4;");
        infoPanel.setPrefWidth(520);
        infoPanel.setPrefHeight(1000);
        infoPanel.setLayoutX(canvas.getWidth()+canvas.getLayoutX());
        infoPanel.setLayoutY(25); 

        // Information panel initialization
        infoText = new TextArea("Information Panel\n-------------------");
        infoText.setEditable(false);
        infoText.setPrefHeight(780); 
        infoText.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        infoPanel.getChildren().add(infoText);

        //Animation timer
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now)    {
                //clear canvas
                gc.clearRect(stroke, stroke, canvas.getWidth()-stroke*2, canvas.getHeight()-stroke*2);

                //draw robot update
                arena.updateAll();
                arena.renderAll(gc);

                // Update information panel
                updateInfoPanel(arena);

            }
        };
        
        //animation starts as on
        animationTimer.start();
        isRunning = true;
        

        //Start animation
        startButton.setOnAction(event -> {
            if (!isRunning) {
                animationTimer.start();
                isRunning = true;
            }
        });
        //Pause animation
        pauseButton.setOnAction(event -> {
            if (isRunning) {
                animationTimer.stop();
                isRunning = false;
            }
        });



        class TempItem extends Item {
            public TempItem(double x, double y, double size) {
                super(x, y, size, Color.TRANSPARENT);
            }

            @Override
            public void draw(GraphicsContext gc) {}
        
            @Override
            public void update() {}
        }
        


        Scene scene = new Scene(root_pane, 1325, 753);

        // Select an item when clicked
        canvas.setOnMousePressed(event -> {
            for (Item item : arena.getItems()) {
                if (item.checkCollisionCirc(new TempItem(event.getX(), event.getY(), 10))) {
                    arena.setSelectedItem(item); // Select the item
                    break;
                }
            }
        });

        // Listen Delete key press
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.DELETE) { // Check if DELETE
                Item selectedItem = arena.getSelectedItem();
                if (selectedItem != null) {
                    arena.removeItem(selectedItem); // Remove the selected item
                    arena.setSelectedItem(null);   // Deselect
                }
            }
        });


        canvas.setOnMousePressed(event -> {
            // Find the item the user clicked on
            for (Item item : arena.getItems()) {
                if (item.checkCollisionCirc(new TempItem(event.getX(), event.getY(), 10))) {
                    arena.setSelectedItem(item); // Select the item
                    break;
                }
            }
        });
        
        canvas.setOnMouseDragged(event -> {
            Item selectedItem = arena.getSelectedItem();
            if (selectedItem != null) {
                // Update the item's position as the mouse moves
                selectedItem.setX(event.getX());
                selectedItem.setY(event.getY());
            }
        });
        
        canvas.setOnMouseReleased(event -> {
            // Deselect the item
            arena.setSelectedItem(null);
        });
        

        
        //Add to root pane
        root_pane.getChildren().add(toolbar);
        root_pane.getChildren().add(canvas);
        root_pane.getChildren().add(infoPanel);
        root_pane.getChildren().add(btoolbar);

        primaryStage.setTitle("Robot Sim");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        //Default populated arena
        arena.addItem(new WhiskerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.GREEN, 10, 0, 1, random.nextDouble() * 360, arena)); 
        arena.addItem(new WhiskerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.GREEN, 10, 0, 1, random.nextDouble() * 360, arena)); 
        arena.addItem(new DefaultRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.BLUE, 10, 1, random.nextDouble() * 360)); 
        arena.addItem(new AttackerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.RED, 10, 1, random.nextDouble() * 360)); 
        arena.addItem(new AttackerRobot(50 + random.nextDouble() * 700, 50 + random.nextDouble() * 550, 50, Color.RED, 10, 1, random.nextDouble() * 360)); 
        arena.addItem(new Barrier(random.nextDouble() * 800, random.nextDouble() * 700, random.nextDouble() * 100));  
        arena.addItem(new Barrier(random.nextDouble() * 800, random.nextDouble() * 700, random.nextDouble() * 100));  
        arena.addItem(new Barrier(random.nextDouble() * 800, random.nextDouble() * 700, random.nextDouble() * 100));
        for (int i = 0; i < 5; i++) {
            arena.addItem(new HealthPack(random.nextDouble() * 800, random.nextDouble() * 700, 23)); 
            arena.addItem(new DamageBoost(random.nextDouble() * 800, random.nextDouble() * 700, 23)); 
        }

    }

   /**
    * Updates information panel with details of every currently active item in arena
    * 
    * @param arena the arena contains the items which need to be displayed
    */
    //update information panel
    private void updateInfoPanel(Arena arena) {
        DecimalFormat df = new DecimalFormat("#.##");
        StringBuilder info = new StringBuilder();

        //display information for every item in arena
        for (Item item : arena.getItems()) {
            if (item instanceof Robot) {
                Robot robot = (Robot) item;
                info.append("Robot ID: " + robot.getId() + ", HP: " + robot.getHp() + ", Position: (" + df.format(robot.getX()) + ", " + df.format(robot.getY()) + "), Direction: " + df.format(robot.getDirection()) + "\n");
            } else if (item instanceof Barrier) {
                info.append("Barrier ID: " + item.getId() + ", Position: (" + df.format(item.getX()) + ", " + df.format(item.getY()) + ")\n");
            } else if (item instanceof HealthPack) {
                info.append("HealthPack ID: " + item.getId() + ", Position: (" + df.format(item.getX()) + ", " + df.format(item.getY()) + ")\n");
            } else if (item instanceof DamageBoost) {
                info.append("DamageBoost ID: " + item.getId() + ", Position: (" + df.format(item.getX()) + ", " + df.format(item.getY()) + ")\n");
            }   
        }

        infoText.setText(info.toString());
    }


   /**
    * Entry point for application
    * @param args
    * 
    */
    public static void main(String[] args) {
        launch(args);
    }
}