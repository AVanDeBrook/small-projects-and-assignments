// ***************************************************************
// Name: Aaron Van De Brook
// Date: 9/21/18
// 
// CS 225, Section 2, Homework 3
//
//  This file will:
//    -- provide student name and course section (either -01 or -02)
//    -- create a random point cloud (user inputs the number of points)
//    -- store a point cloud as an Nx3 array of doubles,
//    -- Find the center of mass of the point cloud
//	  -- Print the weight and x, y coordinates of the points in the cloud
// ***************************************************************

public class WeightedCloud extends Cloud {
	
	@Override
	public double[] findCenterOfMass() {
		double[] cm = new double[2];
		double totalMass = 0;
		
		//Sum points for x and y and multiply each point by its mass
		for(int i = 0; i < getCloud().length; i++) {
			cm[0] += getCloud()[i][0] * getCloud()[i][2];
			cm[1] += getCloud()[i][1] * getCloud()[i][2];
			totalMass += Math.abs(getCloud()[i][2]);
		}
		
		//Divide x and y by the total mass of the cloud to find its center of mass
		cm[0] /= totalMass;
		cm[1] /= totalMass; 
		
	    return cm;
	}
	
	@Override
	public void setCloudRandomly(int sizeOfCloud) {
		double[][] tempArray = new double[sizeOfCloud][3];
		//Not a test case, can/is being used to set the points array inherited from the Cloud class
		setCloudToTestCase(tempArray);
		
		for (int i = 0; i < getCloud().length; i++) {
			getCloud()[i][0] = 100 * Math.random();
			getCloud()[i][1] = 100 * Math.random();
			getCloud()[i][2] = 10 * Math.random(); //Creates a random weight from 0 to 10 in the 2nd index of the array
		}
	}
	
	@Override
	public void printCloud() {
		for (int i = 0; i < getCloud().length; i++) {
			System.out.printf("%3d : %3.2f, %3.2f Weight: %3.2f\n", i, getCloud()[i][0], getCloud()[i][1], getCloud()[i][2]);
		}
	}
	
}