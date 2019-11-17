// Class: FileManager
// Author: Aaron Van De Brook
// Last Modified: 12/03/2018
//
// Description: Class responsible for creating and importing files for the simulation
//
//
// Constructor(s): FileMangaer()
//
// Methods: 
//
// Attributes: 
//

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManager {
    private File outFile;

    public FileManager() {
        outFile = new File("output_data.txt");
    }

    public FileManager(String fileName) {
        outFile = new File(fileName);
    }

    // Uses a building and elevator classes to print relevant info about the simulation
    // to an output file
    public void exportData(GenericBuilding building, Elevator elevator, ElevatorStudy study) {
        try {
            System.out.println("Exporting data to output file...");
            PrintWriter output = new PrintWriter(outFile);

            // Print relevant info from building and elevator classes
            output.printf("Floors: %d\n", building.getFloors());
            output.printf("Elevators: %d\n", building.getElevators());
            output.printf("Speed: %.2f\n", elevator.getSpeed());
            output.printf("Capacity: %d\n", elevator.getCapacity());
            output.printf("Duration: %d\n", study.getDuration());

            // Print flow rates for each floor
            output.println("Flow rates for each floor:");
            for(int i = 0; i < building.getFlowRates().length; i++) {
                output.printf("\t%d: %.2f\n", i+1, building.getFlowRates()[i]);
            }

            output.println("Wait times for each floor:");
            for(int i = 0; i < building.getWaitTimes().length; i++) {
                output.printf("\t%d: %.2f\n", i+1, building.getWaitTimes()[i]);
            }

            output.printf("Floor w/ highest flow rate: %d\n", building.getHighestFlowRateFloor());
            output.printf("Optimal floor: %d\n", building.findOptimalFloor());
            output.printf("Min wait time: %.2f\n", building.findMinWaitTime());
            output.printf("Max wait time: %.2f\n", building.findMaxWaitTime());
            output.printf("Mean wait time: %.2f\n", building.findAvgWaitTime());
            output.printf("Building type: %s\n", building.getBuildingType());
            
            // Close file buffer
            output.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Imports data from an output file and sets relevant variables in the
    // building and elevator classes
    public void importData(GenericBuilding building, Elevator elevator, ElevatorStudy study) throws FileNotFoundException {
        try{
            System.out.println("Importing data...");
            // Read in values with a scanner object
            Scanner reader = new Scanner(outFile);

            // skip the descriptive text before the actual value
            reader.next();
            // set number of floors based on data read
            building.setFloors(reader.nextInt());
            building.setFlowRateSize(building.getFloors());
            reader.next();
            building.setElevators(reader.nextInt());
            reader.next();
            elevator.setSpeed(reader.nextDouble());
            reader.next();
            elevator.setCapacity(reader.nextInt());
            reader.next();
            study.setDuration(reader.nextInt());

            // skip the descriptive text and extra spaces
            reader.nextLine();
            reader.nextLine();
            for(int i = 0; i < building.getFloors(); i++) {
                // Skips the floor number 
                reader.next();
                // Finally read the actual value for the relevant floor
                building.setSingleFlowRate(i, reader.nextDouble());
            }

            // Close file buffer
            reader.close();
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
            throw new FileNotFoundException("File not found, make sure the input file is in the same directry as the program.");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}