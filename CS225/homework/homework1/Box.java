// ***************************************************************
// Name: Aaron Van De Brook
// Date: 9/4/18
//
// CS 225, Section Section 02, Homework 1
//
//  This file has all of the methods needed for the homework, but
//  they don't currently work correctly. This is typical for software
//  development. You make dummy methods that return dummy results, and
//  then flesh them out later to create correct results. 
//
//  For this homework, remove the dummy output messages, and make
//  each method provide a correct result. 
//
//  Do not run this file. There is no main method. Don't make one.
//  Run the Manager class instead. 
//
//  This file will:
//    -- store the length, width, and height parameters for a box,
//    -- provide student name and course section (either -01 or -02)
//    -- calculate the box parameters (volume, surface area, edge length), and
//    -- calculate the largest parameter.
// ***************************************************************

public class Box {

  private double length, height, width;

  public String getStudentInfo() {
    return "Aaron Van De Brook CS 225 Section 02\n\n";
  }
  
  public double calculateVolume() {
    double volume = length * height * width;
    return volume;
  }

  public double calculateSurfaceArea() {
    double surfaceArea = 2 * (length * height + width * height + length * width);
    return surfaceArea;
  }

  public double calculateEdgeLength() {
    double edgeLength = 4 * (length + height + width);
    return edgeLength;
  }

  public double calculateLargestParameter() {
    double largest = 0;
	double volume = calculateVolume();
    double surfaceArea = calculateSurfaceArea();
    double edgeLength = calculateEdgeLength();
    
    if(edgeLength > surfaceArea && edgeLength > volume) {
    	largest = edgeLength;
    }else if(surfaceArea > edgeLength && surfaceArea > volume) {
    	largest = surfaceArea;
    }else{
    	largest = volume;
    }
    
    return largest;
  }
  
  // ******* You don't need to modify anything below this line. 
  // ******* This section contains the mundane "setter" and "getter" methods.
  // ******* Eclipse has a tool to create these very easily. 

  // ****** These are the "setters." 
  public void setLength(double newLength) {
    length = newLength;
  }

  public void setHeight(double newHeight) {
    height = newHeight;
  }

  public void setWidth(double newWidth) {
    width = newWidth;
  }

  // ****** These are the "getters."
  public double getLength() {
    return length;
  }

  public double getHeight() {
    return height;
  }

  public double getWidth() {
    return width;
  }
}