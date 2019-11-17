// ***************************************************************
// Name: Aaron Van De Brook
// Date: 9/11/18
// Credit(s): 
// 		Distance Formula: https://www.mathplanet.com/education/algebra-2/conic-sections/distance-between-two-points-and-the-midpoint
//		Help with closest points algorithm: https://en.wikipedia.org/wiki/Closest_pair_of_points_problem
//		Center of mass help: http://www.batesville.k12.in.us/physics/APPhyNet/Dynamics/Center%20of%20Mass/2D_1.html
//
// CS 225, Section 2, Homework 1
//
//  For this homework, remove the dummy output messages, and make
//  each method provide a correct result. You may add attributes and
//  variables if you feel lyou need to. 
//
//  Do not run this file. There is no main method. Don't make one.
//  Run the Manager class instead. 
//
//  This file will:
//    -- provide student name and course section (either -01 or -02)
//    -- create a random point cloud (user inputs the number of points)
//    -- store a point cloud as an Nx2 array of doubles,
//    -- Find the two closet points,
//    -- Find the two points furthest apart, 
//    -- Find the center of mass of the point cloud, and
//    -- Find the point closet to the center of mass.
// ***************************************************************

public class Cloud {

  private double[][] points;

  public String getStudentInfo() {
    return "Aaron Van De Brook CS 225 Section 02\n\n";
  }
  
  public int[] findClosestPoints() {
	int[] closestPair = new int[2];
	//Set the closest distance to a distance that isn't possible with the current number generation
	double closestDist = 1000;
	double distance;
	
	//Loop through the points array and compare the distances between each point
	for(int i = 0; i < points.length; i++) {
		for(int j = i + 1; j < points.length; j++) {
			//See boilerplate for source of the distance formula
			distance = Math.sqrt( Math.pow(points[j][0] - points[i][0], 2) + Math.pow(points[j][1] - points[i][1], 2) );
			
			//If the current distance is greater than the closest distance then reset the closest distance to the current distance 
			if(distance < closestDist) {
				closestDist = distance;
				closestPair[0] = i;
				closestPair[1] = j;
			}
		}
	}
	
    return closestPair;
  }
  
  public int[] findFurthestPoints() {
	int[] furthestPair = new int[2];
	//Set furthest to 0 initially b/c two points will never be 0 distance apart
	double furthestPoint = 0;
	double currentPoint;
	
	//Loop through the points array and compare the distance between each point in the array
	for(int i = 0; i < points.length; i++) {
		for(int j = 1; j < points.length; j++) {
			//See boilerplate for source of the distance formula
			currentPoint = Math.sqrt( Math.pow(points[j][0] - points[i][0], 2) + Math.pow(points[j][1] - points[i][1], 2) );
			
			//If the current point is further than the current furthest reset the furthest point to the current point
			if(points.length > 2) {
				if(currentPoint > furthestPoint) {
					furthestPoint = currentPoint;
					furthestPair[0] = i;
					furthestPair[1] = j;
				}				
			} else {
				furthestPoint = currentPoint;
				furthestPair[0] = 0;
				furthestPair[1] = 1;
			}
		}
	}
	
    return furthestPair;
  }
  
  public double[] findCenterOfMass() {
	double[] cm = new double[2];
	
	//Sum points for x and y
	for(int i = 0; i < points.length; i++) {
		cm[0] += points[i][0];
		cm[1] += points[i][1];
	}
	
	//Divide x and y by the number of points in the cloud to find the center of mass
	cm[0] /= points.length;
	cm[1] /= points.length; 
	
    return cm;
  }
  
  public int findClosestToCenter() {
	int closestPoint = 0;
    double[] com = new double[2];
    com = findCenterOfMass();
    //Set closestDistance to some large number that will never actually be closest to ensure one of the points are found to be closer
    double closestDistance = 1000;
    double distance;
    
    //Loop through points array and compare distance between points and the center of mass of the cloud
    for(int i = 0; i < points.length; i++) {
    	//See boilerplate for source of the distance formula
    	distance = Math.sqrt( Math.pow(points[i][0] - com[0], 2) + Math.pow(points[i][1] - com[1], 2) );
    	
    	//If the distance is less than the current closest reset the closest distance to the current distance
    	if(distance < closestDistance) {
    		closestDistance = distance;
    		closestPoint = i;
    	}
    }
    
    return closestPoint;
  }

  
  
  // ******* You don't need to modify anything below this line. 
  // ******* This section contains methods to create and print the cloud. 

  // ****** These are the "setters." 
  public void setCloudRandomly(int sizeOfCloud) {
    points = new double[sizeOfCloud][2];
	
	for (int i = 0; i < points.length; i++) {
		points[i][0] = 100 * Math.random();
		points[i][1] = 100 * Math.random();
	}
  }

  public void setCloudToTestCase(double[][] testPoints) {
    points = testPoints;
  }

  public void printCloud() {
	  for (int i = 0; i < points.length; i++) {
		  System.out.printf("%3d : %3.2f, %3.2f \n", i,points[i][0], points[i][1]);
	  }
  }
  
   // ****** These are the "getters."
  public double[][] getCloud() {
	  return points;
  }
  
  public double[] getSinglePoint(int i) {
	  return points[i];
  }
 
}