// Class: ProjectPane
// Author: Aaron Van De Brook
// Last Modified: 12/02/2018
//
// Description: Class will extend the javafx Pane class 
//              to create and setup the GUI elements before
//              adding them to the scene and showing them in
//              the GUI class
// 
// Constructor(s): ProjectPane(void)
//
// Methods: +drawWelcomeScreen(void): void
//          +setWelcomeScreenActions(void): void
//          +drawButtons(void): void
//          +setButtonActions(void): void
//
// Attributes: -Button startBt, aboutBt, changeFloorsBt, changeElevatorsBt, changeArrivalRateBt, exportDataBt, importDataBt, runSimulationBt, viewStatsBt, durationBt, largeBuildingBt, normalBuildingBt
//             -Elevator elevator
//             -GenericBuilding building
//

import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import java.io.FileNotFoundException;

public class ProjectPane extends Pane {

    // Create buttons to display relevant info
    private Button aboutBt, changeFloorsBt, changeElevatorsBt, changeArrivalRateBt, exportDataBt, 
    importDataBt, runSimulationBt, viewStatsBt, durationBt, largeBuildingBt, normalBuildingBt, randomStartBt,
    changeCapacityBt, changeWaitDurationBt;
    private Elevator elevator;
    private GenericBuilding building;
    private ElevatorStudy sim;
    private FileManager fileMgr;

    // No-arg constructor to draw all elements when the object is created
    public ProjectPane() {
        
        elevator = new Elevator();
        sim = new ElevatorStudy();
        fileMgr = new FileManager();
        // Display initial screen (about the program, etc.)
        drawWelcomeScreen();
        // Set welcome screen button actions
        setWelcomeScreenActions();
    }

    // Draws button for the home screen of the program
    public void drawWelcomeScreen() {
        // Button to start simulations
        randomStartBt = new Button("Start w/ a Random Building Type");
        randomStartBt.setLayoutX(220);
        randomStartBt.setLayoutY(100);

        // Button to start with a normal building type
        normalBuildingBt = new Button("Start w/ a Normal Building Type");
        normalBuildingBt.setLayoutX(220);
        normalBuildingBt.setLayoutY(130);
        
        // Button to start with a large building type
        largeBuildingBt = new Button("Start w/ a Large Building Type");
        largeBuildingBt.setLayoutX(220);
        largeBuildingBt.setLayoutY(160);
        
        // Button to show info about the program
        aboutBt = new Button("About This Program");
        aboutBt.setLayoutX(220);
        aboutBt.setLayoutY(190);

        // Add all buttons to the pane
        getChildren().addAll(randomStartBt, normalBuildingBt, largeBuildingBt, aboutBt);
    }

    // Sets all actions for buttons on the homescreen of the program
    public void setWelcomeScreenActions() {
        // Set start button to draw simulation screen options
        randomStartBt.setOnAction(e -> {
            // Hide welcome screen
            randomStartBt.setVisible(false);
            aboutBt.setVisible(false);
            normalBuildingBt.setVisible(false);
            largeBuildingBt.setVisible(false);

            // Draw simulation screen and set relevant actions
            drawButtons();
            setButtonActions();

            // Randomly select a building type
            if(Math.round(Math.random() * 2) == 1) {
                // Set building type to a normal building
                building = new NormalBuilding();
                // Disable button to change number of elevators
                changeElevatorsBt.setDisable(true);
            } else {
                building = new LargeBuilding();
            }
        });

        normalBuildingBt.setOnAction(e -> {
            // Create an object of NormalBuilding type
            building = new NormalBuilding();

            // Hide welcome screen
            randomStartBt.setVisible(false);
            aboutBt.setVisible(false);
            normalBuildingBt.setVisible(false);
            largeBuildingBt.setVisible(false);

            // Draw simulation screen and set relevant actions
            drawButtons();
            setButtonActions();

            // Disable change elevator button since the building will only ever have 1 elevator
            changeElevatorsBt.setDisable(true);
        });

        largeBuildingBt.setOnAction(e -> {
            // Create an object of LargeBuilding type
            building = new LargeBuilding();

            // Hide welcome screen
            randomStartBt.setVisible(false);
            aboutBt.setVisible(false);
            normalBuildingBt.setVisible(false);
            largeBuildingBt.setVisible(false);
            
            // Draw simulation screen and set relevant actions
            drawButtons();
            setButtonActions();
        });

        // Set about button to display info about the program
        aboutBt.setOnAction(e -> {
            // Content of the alert
            String aboutText = "This program is an elevator study that simulates things such as best and worst case wait times"
            + " as well as the average wait time of an elevator or elevators in a building based on the flow rates either specified"
            + " by the user or created randomly by the simulation.\nThere are two building types represented in this simulation,"
            + " a large building which has several elevators and usually a large number of floors and a normal building which has "
            + "one elevator and can have any mumber of floors.\nOn the main menu of the program you can choose between 3 options, "
            + "have the program randomly select a building type for you, choose a normal building or choose a large building.\n"
            + "In the normal building you can view and edit all variables in the simulation except for the number of elevators, which is locked at 1"
            + "\nSimilarly, in the large building you can view and edit all variables including the number of elevators in the building.\n"
            + "When a building type is selected all variables will be generated randomly at first, but can be edited at any time before or after the simulation is run.";
            
            // Set alert type, content and exit button
            Alert aboutPopup = new Alert(Alert.AlertType.INFORMATION, aboutText, ButtonType.OK);
            // Set header, title and behavior
            aboutPopup.setHeaderText("About this Program");
            aboutPopup.setTitle("About");
            aboutPopup.showAndWait();
        });
    }

    // Creates buttons that show up after starting simulation
    public void drawButtons() {
        // Draw button to change the number of floors in the building model
        changeFloorsBt = new Button("Change/View Number of Floors");
        changeFloorsBt.setLayoutX(10);
        changeFloorsBt.setLayoutY(10);
        // Draw button to change the number of elevators in the building
        changeElevatorsBt = new Button("Change/View Number of Elevators");
        changeElevatorsBt.setLayoutX(10);
        changeElevatorsBt.setLayoutY(40);
        // Draw button to change the capacity of the elevators in the building
        changeCapacityBt = new Button("Change/View Elevator Capacity");
        changeCapacityBt.setLayoutX(10);
        changeCapacityBt.setLayoutY(70);
        // Draw button to change the wait duration of the elevator
        changeWaitDurationBt = new Button("Change/View Elevator Wait Duration");
        changeWaitDurationBt.setLayoutX(10);
        changeWaitDurationBt.setLayoutY(100);
        // Draw button to change the arrival rate of people
        changeArrivalRateBt = new Button("Change/View Arrival Rate");
        changeArrivalRateBt.setLayoutX(10);
        changeArrivalRateBt.setLayoutY(130);
        // Draw button to set/change simulation duration
        durationBt = new Button("Change/View Simulation Duration");
        durationBt.setLayoutX(10);
        durationBt.setLayoutY(160);
        // Draw button to view stats
        viewStatsBt = new Button("View Simulation Stats");
        viewStatsBt.setLayoutX(10);
        viewStatsBt.setLayoutY(190);
        // Draw button to run simulation
        runSimulationBt = new Button("Run Simulaion");
        runSimulationBt.setLayoutX(10);
        runSimulationBt.setLayoutY(220);
        // Draw button to write data to a file
        exportDataBt = new Button("Export Data");
        exportDataBt.setLayoutX(10);
        exportDataBt.setLayoutY(250);
        // Draw button to import data from a file
        importDataBt = new Button("Import Data");
        importDataBt.setLayoutX(10);
        importDataBt.setLayoutY(280);
        // Add all buttons to the pane
        getChildren().addAll(changeFloorsBt, changeElevatorsBt, changeCapacityBt, changeWaitDurationBt, changeArrivalRateBt, exportDataBt, importDataBt, runSimulationBt, viewStatsBt, durationBt);
    }

    // Set simulation screen button actions
    public void setButtonActions() {
        // When the change floors button is clicked it creates a new window
        // where the values can be viewed/editted.
        changeFloorsBt.setOnAction(e -> {
            Pane pane = new Pane();
            Stage stage = new Stage();
            Scene scene = new Scene(pane, 300, 75);

            // Text telling the user what the value in the text field is
            Text text = new Text(10, 20, "Number of Floors: ");
            
            // Text field that shows current value and allows it to be editted
            TextField textfield = new TextField(Integer.toString(building.getFloors()));
            textfield.setLayoutX(110);
            textfield.setLayoutY(10);

            // Changes the value of the number of floors to the value entered by the user
            // when the press enter
            textfield.setOnAction(event -> {
                // Check for valid input
                int newFloorNum = Integer.parseInt(textfield.getText());
                if(newFloorNum > 1) {
                    building.setFloors(newFloorNum);
                    building.setFlowRateSize(newFloorNum);
                    stage.close();
                } else {
                    // Tell the user to enter valid input is the input is invalid
                    Alert alertPopup = new Alert(Alert.AlertType.WARNING, "Enter a value of at least 2.", ButtonType.OK);
                    alertPopup.setHeaderText("Invalid input");
                    alertPopup.setTitle("Invalid input");
                    alertPopup.showAndWait();
                }
            });

            // Add elements to pane
            pane.getChildren().addAll(text, textfield);

            // Set scene, title, and show the window
            stage.setScene(scene);
            stage.setTitle("Change/View Floors");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });

        // When the button is clicked a new window is created
        // where the user can change/view the number of elevators.
        changeElevatorsBt.setOnAction(e -> {
            Pane pane = new Pane();
            Stage stage = new Stage();
            Scene scene = new Scene(pane, 300, 75);

            // Text indicating what the text field is
            Text text = new Text(10, 20, "Number of Elevators: ");
            
            // Create text field to edit/display current value
            TextField textfield = new TextField(Integer.toString(building.getElevators()));
            textfield.setLayoutX(125);
            textfield.setLayoutY(10);

            // Set new value when the user pressed the enter key
            textfield.setOnAction(event -> {
                int newElevatorNum = Integer.parseInt(textfield.getText());
                if(newElevatorNum > 0) {
                    building.setElevators(newElevatorNum);
                    stage.close();
                } else {
                    Alert alertPopup = new Alert(Alert.AlertType.WARNING, "Enter a value of at least 1.", ButtonType.OK);
                    alertPopup.setHeaderText("Invalid input");
                    alertPopup.setTitle("Invalid input");
                    alertPopup.showAndWait();
                }
            });


            // Add elements to the pane
            pane.getChildren().addAll(text, textfield);

            // Set scene, set window title, and show the window
            stage.setScene(scene);
            stage.setTitle("Change/View Elevators");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });

        // When the button is clicked a window will be displayed showing the current flow rates
        // in a text field that will enable the user to change the flow rates for each floor
        changeArrivalRateBt.setOnAction(e -> {
            // Text and text fields (number determined by number of floors in building)
            // to change the flow rate on each floor
            Text[] flowRateText = new Text[building.getFloors()];
            TextField[] flowRateFields = new TextField[building.getFloors()];
            Pane pane = new Pane();
            Stage stage = new Stage();

            // Changes the window size based on the number of elements that need to be displayed
            Scene scene = new Scene(pane, 300, building.getFloors()*25 + 15);

            try {
                // Creates a label and text field for viewing/editing each floor
                for(int i = 0; i < building.getFloors(); i++) {
                    int currentLocation = i; // Compiler complains if i is used inside a lambda expression, this is the workaround
                    flowRateText[i] = new Text(5, 25*i + 20, String.format("Floor %d", i+1));

                    // Displays the values of each rate of flow for each floor in the simulation
                    flowRateFields[i] = new TextField(String.format("%.2f", building.getFlowRates()[i]));
                    flowRateFields[i].setLayoutX(75);
                    flowRateFields[i].setLayoutY(25*i + 5);

                    // Sets an individual flow rate to a value entered by the user
                    flowRateFields[i].setOnAction(event -> {
                        building.setSingleFlowRate(currentLocation, Double.parseDouble(flowRateFields[currentLocation].getText()));
                    });

                    // Add all elements to the pane
                    pane.getChildren().addAll(flowRateText[i], flowRateFields[i]);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                // This shouldn't happen anymore, but I'm keeping it here just in case
                System.err.println("Attempted to go outside the bounds of the array...");
                building.setFlowRateSize(building.getFloors());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Set scene, window title and show the window
            stage.setScene(scene);
            stage.setTitle("Change/View Flow Rates");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });

        // Set write to file button to create and write relevant data to a file (either .csv or a .txt)
        exportDataBt.setOnAction(e -> {
            fileMgr.exportData(building, elevator, sim);
        });

        importDataBt.setOnAction(e -> {
            try {
                fileMgr.importData(building, elevator, sim);
            } catch (FileNotFoundException ex) {
                Alert noFilePopup = new Alert(Alert.AlertType.ERROR, "File not found, make sure the file is in the same directory as the program and that it is called \"output_data.txt\" ", ButtonType.OK);
                noFilePopup.setHeaderText("File not found");
                noFilePopup.setTitle("File not found");
                noFilePopup.showAndWait();
            }
        });

        // Set button to run the elevator simulation when pressed
        // simulation runs then relevant stats are shown
        runSimulationBt.setOnAction(e -> {
            sim.runSimulation(building, elevator);
            viewStatsBt.fire();
        });

        // Set button action to show relevant stats when pressed
        viewStatsBt.setOnAction(e -> {
            Pane pane = new Pane();
            Stage stage = new Stage();
            Scene scene = new Scene(pane, 400, 200 + building.getFloors()*15);
            
            // Display number of floors
            Text floors = new Text(10, 10, String.format("Floors: %d", building.getFloors()));
            // Display number of elevators
            Text elevators = new Text(10, 25, String.format("Elevators: %d", building.getElevators()));
            // Display elevator speed 
            Text speed = new Text(10, 40, String.format("Speed: %.2f", elevator.getSpeed()));
            // Display the elevator capacity
            Text capacity = new Text(10, 55, String.format("Capacity: %d", elevator.getCapacity()));
            
            Text flowTitle = new Text(10, 70, "Flow Rates (people/second):");
            Text[] flowRates = new Text[building.getFloors()];
            // Loop to display individual flow rates
            for(int i = 0; i < building.getFloors(); i++) {
                flowRates[i] = new Text(15, 85 + 15*i, String.format("Floor %d: %.2f", i+1, building.getFlowRates()[i]));
                pane.getChildren().add(flowRates[i]);
            }

            // Display floor w/ highest flow rate
            Text highestFlowRate = new Text(170, 10, String.format("Floor w/ highest flow rate: %s", building.getHighestFlowRateFloor()));
            // Display optimal floor
            Text optimalFloor = new Text(170, 25, String.format("Optimal floor: %d", building.findOptimalFloor()));
            // Display min wait time
            Text minTime = new Text(170, 40, String.format("Min wait time (seconds): %.2f", building.findMinWaitTime()/building.getElevators()));
            // Display max wait time
            Text maxTime = new Text(170, 55, String.format("Max wait time (seconds): %.2f", building.findMaxWaitTime()/building.getElevators()));
            // Display mean wait time
            Text avgTime = new Text(170, 70, String.format("Mean wait time (seconds): %.2f", building.findAvgWaitTime()/building.getElevators()));
            // Display building type
            Text buildingType = new Text(170, 85, String.format("Building type: %s", building.getBuildingType()));

            Text waitTitle = new Text(170, 100, "Wait Times (seconds):");
            Text[] waitTimes = new Text[building.getFloors()];
            for(int i = 0; i < building.getFloors(); i++) {
                waitTimes[i] = new Text(175, 115 + 15*i, String.format("Floor %d: %.2f", i+1, building.getWaitTimes()[i]));
                pane.getChildren().add(waitTimes[i]);
            }

            pane.getChildren().addAll(floors, elevators, speed, capacity, flowTitle, waitTitle, highestFlowRate, optimalFloor, minTime, maxTime, avgTime, buildingType);

            stage.setScene(scene);
            stage.setTitle("Simulation Stats");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });

        // Set button action to show/change the duration for the simulation
        durationBt.setOnAction(e -> {
            Pane pane = new Pane();
            Stage stage = new Stage();
            Scene scene = new Scene(pane, 300, 75);

            Text durationTxt = new Text(10, 20, "Simulation Duration: ");
            TextField durationField = new TextField(Integer.toString(sim.getDuration()));
            durationField.setLayoutX(125);
            durationField.setLayoutY(10);

            pane.getChildren().addAll(durationTxt, durationField);

            durationField.setOnAction(event -> {
                    int newDuration = Integer.parseInt(durationField.getText());
                // Check for valid input in the text field
                if(newDuration >= 0) {
                 sim.setDuration(newDuration);
                    stage.close();
                } else {
                    // Alert the user to invalid input 
                    Alert alertPopup = new Alert(Alert.AlertType.INFORMATION, "Enter a value greater than or equal to 0.", ButtonType.OK);
                    alertPopup.setHeaderText("Invalid input");
                    alertPopup.setTitle("Invalid input");
                    alertPopup.showAndWait();
                }
            });

            stage.setScene(scene);
            stage.setTitle("Set Simulation Duration");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });

        changeCapacityBt.setOnAction(e -> {
            Pane pane = new Pane();
            Stage stage = new Stage();
            Scene scene = new Scene(pane, 300, 75);

            Text capacityText = new Text(10, 20, "Elevator Capacity:");
            TextField capacityTextField = new TextField(Integer.toString(elevator.getCapacity()));
            capacityTextField.setLayoutX(125);
            capacityTextField.setLayoutY(10);

            pane.getChildren().addAll(capacityText, capacityTextField);

            capacityTextField.setOnAction(event -> {
                int newValue = Integer.parseInt(capacityTextField.getText());
                if(newValue > 0) {
                    elevator.setCapacity(newValue);
                    stage.close();
                } else {
                    Alert alertPopup = new Alert(Alert.AlertType.WARNING, "Enter a value larger than 0", ButtonType.OK);
                    alertPopup.setHeaderText("Invalid input");
                    alertPopup.setTitle("Invalid input");
                    alertPopup.showAndWait();
                }
            });

            stage.setScene(scene);
            stage.setTitle("Change/View Elevator Capacity");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });

        changeWaitDurationBt.setOnAction(e -> {
            Pane pane = new Pane();
            Stage stage = new Stage();
            Scene scene = new Scene(pane, 300, 75);

            Text waitDurationText = new Text(10, 20, "Elevator Wait Duration:");
            TextField waitDurationField = new TextField(String.format("%.2f", elevator.getWaitDuration()));
            waitDurationField.setLayoutX(140);
            waitDurationField.setLayoutY(10);

            pane.getChildren().addAll(waitDurationText, waitDurationField);

            waitDurationField.setOnAction(event -> {
                double newValue = Double.parseDouble(waitDurationField.getText());
                if(newValue > 0) {
                    elevator.setWaitDuration(newValue);
                    stage.close();
                } else {
                    Alert alertPopup = new Alert(Alert.AlertType.WARNING, "Enter a value greater than 0", ButtonType.OK);
                    alertPopup.setHeaderText("Invalid input");
                    alertPopup.setTitle("Invalid input");
                    alertPopup.showAndWait();
                }
            });

            stage.setScene(scene);
            stage.setTitle("Change/View Elevator Wait Duration");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });
    }
}