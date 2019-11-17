// Class: ElevatorStudy
// Author: Aaron Van De Brook
// Last Modified: 12/02/2018
//
// Description: Class responsible for running the simulation for the elevator study
// 
// Constructor: ElevatorStudy(void): sets duration var randomly
//              ElevatorStudy(int duration): sets duration var to a number specified by the user
//
// Methods: +runSimulatoin(void): void
//
// Attributes: -int duration
//

public class ElevatorStudy {
    private int duration;

    // No-arg constructor randomly sets the duration
    public ElevatorStudy() {
        duration = (int) (Math.random() * 1000);
    }

    // Sets the duration when an object of this class is created
    // to a value specified by the user.
    public ElevatorStudy(int duration) {
        this.duration = duration;
    }

    // Main math and logic behind the simulation, also the method that runs the simulation 
    // and produces usuable data for wait times, etc.
    public void runSimulation(GenericBuilding building, Elevator elevator) {
        int nextFloor = (int) (Math.random() * building.getFloors());
        // Set number of people in the elevator to a number determined by how long the elevator waits and the 
        // current flow rate of the floor it is on
        double numPeople = (building.getFlowRates()[elevator.getCurrentFloor()] * elevator.getWaitDuration());
        for(int currentCycle = 0; currentCycle <= duration; currentCycle++) {
            // Display current cycle
            System.out.printf("Cycle %d/%d\n", currentCycle, duration);
            // Check that the elevator isn't headed to the floor it's currently on
            while(nextFloor == elevator.getCurrentFloor()) {
                // loop until the next floor differs from the current floor
                nextFloor = (int) (Math.random() * building.getFloors());
            }

            // Check that number of people in the elevator does not exceed the 
            // capacity of the elevator
            if(numPeople > elevator.getCapacity()) {
                numPeople = elevator.getCapacity();
            }

            // Increment wait times based on which floor the elevator is on or going to
            for(int i = 0; i < building.getWaitTimes().length; i++) {
                if(i == nextFloor || i == elevator.getCurrentFloor()) {
                    building.setSingleWaitTime(i, elevator.getSpeed() * Math.abs(nextFloor - elevator.getCurrentFloor()));
                } else {
                    building.setSingleWaitTime(i, building.getWaitTimes()[i] + Math.abs(elevator.getSpeed() * (nextFloor - elevator.getCurrentFloor())));
                }
            }

            // Set current floor to the floor the elevator is headed to
            elevator.setCurrentFloor(nextFloor);
            nextFloor = (int) (Math.random() * building.getFloors());
            numPeople = (building.getFlowRates()[elevator.getCurrentFloor()] * elevator.getWaitDuration());
        }
    }

    // ----- Setter and Getter methods -----
    public void setDuration(int duration) {
        this.duration = duration;
        System.out.printf("Duration set to %d\n", duration);
    }

    public int getDuration() {
        return duration;
    }
}