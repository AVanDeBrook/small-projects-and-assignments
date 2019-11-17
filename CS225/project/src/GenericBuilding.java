// Class: GenericBuilding
// Author: Aaron Van De Brook
// Last Modified: 12/02/2018
//
// Description: This class will never be made as an object, only extended
//              to create different sized buildings with different numbers 
//              of elevators, floors, and rates of flow of people.
// 
// Methods: +findOptimalFloor(void): int
//          +findMaxWaitTime(void): double
//          +findMinWaitTime(void): double
//          +findAvgWaitTime(void): double
//          
//
// Attributes: -int floors
//             -int elevators
//             -double[] flowRate
//             -double[] waitTimes
//

import java.util.Arrays;

public class GenericBuilding {
    private int floors;
    private int elevators;
    private double[] flowRate;
    private double[] waitTimes;

    // Constructor to randomly generate initial values for the simulation to run.
    // This constructor is made primarily for the NormalBuilding and LargeBuilding classes to invoke
    // when their respective objects are created.
    public GenericBuilding() {
        floors = (int) ((Math.random() * 20) + 2);
        elevators = (int) ((Math.random() * 5) + 1);
        flowRate = new double[floors];
        waitTimes = new double[floors];
        setFlowRates();
    }

    // Finds the most optimal floor for the elevator to wait on during idle periods
    // based on the wait times from the simulation.
    public int findOptimalFloor() {
        // Basically just finds the mid point between the 2 floors
        // with the highest wait times.
        int highest = 0;
        int second = 0;
        for (int i = 0; i < waitTimes.length; i++) {
            // Check each value until the against the previous highest value
            // until the highest in the array is found
            if(waitTimes[i] > highest) {
                highest = i;       
            }
            for (int j = 0; j < waitTimes.length; j++) {
                // Find the second highest value in the array
                if(waitTimes[j] > second && waitTimes[j] < highest) {
                    second = j;
                }
            }
        }
        return (highest-second)/2;
    }

    public double findMinWaitTime() {
        double output = Double.MAX_VALUE;
        for (int i = 0; i < waitTimes.length; i++) {
            if(waitTimes[i] < output) {
                output = waitTimes[i];
            }
        }
        return output;
    }

    public double findMaxWaitTime() {
        double output = 0;
        for (int i = 0; i < waitTimes.length; i++) {
            if(waitTimes[i] > output) {
                output = waitTimes[i];
            }
        }
        return output;
    }

    public double findAvgWaitTime() {
        int sum = 0;
        for (int i = 0; i < waitTimes.length; i++) {
            sum += waitTimes[i];
        }
        return sum/waitTimes.length;
    }

    // ------ Setter and Getter methods ------    
    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
        System.out.printf("Number of floors changed to %d\n", floors);
    }

    public int getElevators() {
        return elevators;
    }

    public void setElevators(int elevators) {
        this.elevators = elevators;
        System.out.printf("Number of elevators: %d\n", elevators);
    }

    public double[] getFlowRates() {
        return flowRate;
    }

    // Randomly sets the flowRate of each floor to a random value
    public void setFlowRates() {
        for(int i = 0; i < flowRate.length; i++) {
            flowRate[i] = Math.random() * 5;
        }
    }

    public void setSingleFlowRate(int floor, double rate) {
        flowRate[floor] = rate;
        System.out.printf("Floor %d changed to %.2f\n", floor+1, rate);
    }

    public String getBuildingType() {
        return "Generic";
    }

    public void setFlowRateSize(int size) {
        // Makes a copy of the flowRate and waitTImes arrays with a new size specified by the method 
        flowRate = Arrays.copyOf(flowRate, size);
        waitTimes = Arrays.copyOf(waitTimes, size);
        System.out.printf("Array size changed to %d\n", size);
    }

    // returns the floor number with the highest flow rate 
    public int getHighestFlowRateFloor() {
        int output = 0;
        double tmp = 0;
        for(int i = 0; i < floors; i++) {
            if(flowRate[i] > tmp) {
                tmp = flowRate[i];
                output = i;
            }
        }
        return output+1;
    }

    public void setWaitTimes(double[] waitTimes) {
        this.waitTimes = waitTimes;
    }

    public double[] getWaitTimes() {
        return waitTimes;
    }

    public void setSingleWaitTime(int location, double waitTime) {
        waitTimes[location] = waitTime;
    }
}