// Class: NormalBuilding extends GenericBuilding
// Author: Aaron Van De Brook
// Last Modified: 11/29/2018
//
// Description: This class extends from the GenericBuilding class
//              to create a model of a "normal" sized building that
//              will be used for the elevator study.
// 
// Methods: 
//
// Attributes:
//

public class NormalBuilding extends GenericBuilding {
    private int[] peoplePerFloor;

    // No-arg constructor
    public NormalBuilding() {
        super();
        setElevators(1);
    }

    @Override
    public String getBuildingType() {
        return "Normal";
    }
}