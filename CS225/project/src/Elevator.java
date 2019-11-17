// Class: Elevator
// Author: Aaron Van De Brook
// Last Modified: 12/04/2018
//
// Description: Will never be created as an object, only extended 
//              to create different types of elevator models
//              (different speeds, waiting spots, capacity, etc.)
// 
// Methods: 
//
// Attributes: double speed
//             int capacity
//             double maxTime
//             double minTime
//             meanTime
//

public class Elevator {
    private double speed;
    private double waitDuration;
    private int capacity;
    private int currentFloor;
    private int people; // Number of people currently in the elevator

    // No-arg constructor
    // Speed and capacity will initially be set to random values
    public Elevator() {
        // Set relevant attributes randomly 
        speed = (Math.random() * 10) + 1;
        capacity = (int) (Math.random() * 20) + 1; // Cast to an int for compatable data types
        waitDuration = (Math.random() * 7) + 1;
        currentFloor = 0;
    }

    // Constructor for creating an object with an initial speed and capacity
    public Elevator(double speed, int capacity) {
        // Set relevant attributes from input args
        this.speed = speed;
        this.capacity = capacity;
    }

    // ----- Setter and Getter methods -----
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        System.out.printf("Speed changed to %.2f\n", speed);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        System.out.printf("Capacity changed to %d\n", capacity);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        currentFloor = floor;
    }

    public double getWaitDuration() {
        return waitDuration;
    }

    public void setWaitDuration(double waitDuration) {
        this.waitDuration = waitDuration;
        System.out.printf("Wait Duration changed to %.2f\n", waitDuration);
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }
}