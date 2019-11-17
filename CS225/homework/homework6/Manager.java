// ***************************************************************
// Name: Keith Garfield
// Date: 8/22/2018
//
// Modified By: Aaron Van De Brook
// Date: 9/21/2018
//
// This file will:
//    -- create an object of the Cloud class, 
//    -- prompt the user for the number of points in the cloud, 
//    -- use the methods in the cloud object to calculate the assignment requirements, and
//    -- perform a series of test cases.
//
//  Note: This file use very poor user input code in that it does not check
//        for bad user input. We'll fix that in Chapter 12 of the text.
//
// ***************************************************************

import java.util.Scanner;

public class Manager {
	int cloudSize;
	Cloud cloud = new Cloud();
	WeightedCloud wCloud = new WeightedCloud();
    

  public static void main(String[] args) {
    Manager mgr = new Manager();
 
    Scanner input = new Scanner(System.in);
    System.out.print("Input the number of points in the cloud (integer value greater than 0): " );
    mgr.cloudSize = input.nextInt();

    // Unweighted cloud results
	mgr.cloud.setCloudRandomly(mgr.cloudSize);
	System.out.println(mgr.cloud.getStudentInfo());
	mgr.cloud.printCloud();
    mgr.printResults();	
    mgr.runTestCases();
    
    // Weighted cloud results 
    mgr.wCloud.setCloudRandomly(mgr.cloudSize);
	mgr.wCloud.printCloud();
    mgr.printWeightedResults();	
    mgr.runWeightedTestCases();
    
    input.close();
  }

  public void printResults( ) {
	  int[] pointPair;
	  int singlePoint;
	  double[] centerOfMass;
	  
	  pointPair = cloud.findClosestPoints();
	  System.out.println("The closest points are "+ pointPair[0] +", and " + pointPair[1] + ".");
	  pointPair = cloud.findFurthestPoints();
	  System.out.println("The furthest points are "+ pointPair[0] +", and " + pointPair[1] + ".");	
	  centerOfMass = cloud.findCenterOfMass();
	  System.out.printf("The center of mass is at %3.2f, %3.2f. \n", centerOfMass[0], centerOfMass[1]);
	  singlePoint = cloud.findClosestToCenter();
	  System.out.println("The point closest to the center is " + singlePoint + ".");
	  System.out.println();
  }
  
  public void printWeightedResults( ) {
	  int[] pointPair;
	  int singlePoint;
	  double[] centerOfMass;
	  
	  pointPair = wCloud.findClosestPoints();
	  System.out.println("The closest points are "+ pointPair[0] +", and " + pointPair[1] + ".");
	  pointPair = wCloud.findFurthestPoints();
	  System.out.println("The furthest points are "+ pointPair[0] +", and " + pointPair[1] + ".");	
	  centerOfMass = wCloud.findCenterOfMass();
	  System.out.printf("The center of mass is at %3.2f, %3.2f. \n", centerOfMass[0], centerOfMass[1]);
	  singlePoint = wCloud.findClosestToCenter();
	  System.out.println("The point closest to the center is " + singlePoint + ".");
	  System.out.println();
  }
  
  public void runTestCases() {
	  double[][] testCase01 = {{5.0, 1.0}, {3.0, 3.0}, {8.0, 4.0}, {1.0, 8.0}, {5.0, 6.0}};
	  double[][] testCase02 = {{5.0, 0.0}, {-5.0, 0.0}, {4.0, 0.0}, {0.0, 0.0}};
	  double[][] testCase03 = {{1.0, 1.0}, {1.0, 1.0}};
	  
	  System.out.println("***** Test Case 1 **********");
	  cloud.setCloudToTestCase( testCase01 );
	  cloud.printCloud();
	  printResults();
	  
	  System.out.println("***** Test Case 2 **********");
	  cloud.setCloudToTestCase( testCase02 );
	  cloud.printCloud();
	  printResults();
	  
	  System.out.println("***** Test Case 3 **********");
	  cloud.setCloudToTestCase( testCase03 );
	  cloud.printCloud();
	  printResults();
	  
  }
  
  public void runWeightedTestCases() {
	  double[][] testCase01 = {{5.0, 1.0, 1.5}, {3.0, 3.0, 2.5}, {8.0, 4.0, 6.0}, {1.0, 8.0, 3.0}, {5.0, 6.0, 2.0}};
	  double[][] testCase02 = {{5.0, 0.0, 3.0}, {-5.0, 0.0, 4.0}, {4.0, 0.0, 1.0}, {0.0, 0.0, 5.0}};
	  double[][] testCase03 = {{1.0, 1.0, 5.0}, {1.0, 1.0, -5.0}};
	  
	  System.out.println("***** Test Case 1 **********");
	  wCloud.setCloudToTestCase( testCase01 );
	  wCloud.printCloud();
	  printWeightedResults();
	  
	  System.out.println("***** Test Case 2 **********");
	  wCloud.setCloudToTestCase( testCase02 );
	  wCloud.printCloud();
	  printWeightedResults();
	  
	  System.out.println("***** Test Case 3 **********");
	  wCloud.setCloudToTestCase( testCase03 );
	  wCloud.printCloud();
	  printWeightedResults();
	  
  }

}