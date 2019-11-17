// Class: LargeBuilding extends GenericBuilding
// Author: Aaron Van De Brook
// Last Modified: 11/29/2018
//
// Description: This class extends from the GenericBuilding class
//              to create a model of a "large" sized building that
//              will be used for the elevator study.
// 
// Methods: 
//
// Attributes:
//

public class LargeBuilding extends GenericBuilding {
    private int[] peoplePerFloor;

    // No-arg construcor
    public LargeBuilding() {
        super();
    }

    // Constructor to set the number of elevators
    public LargeBuilding(int elevators) {
        super();
        setElevators(elevators);
    }

    @Override
    public String getBuildingType() {
        return "Large";
    }
}