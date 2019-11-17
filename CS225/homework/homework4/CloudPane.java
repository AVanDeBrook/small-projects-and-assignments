// Class: CloudPane (extends Pane)
// Author: Aaron Van De Brook
// Last Modified: 10/07/18
//
// Description: Provides data to create the graphical representation
//				of the points, closest points, furthest points and
//				center of mass from the Cloud and WeightedCloud classes.
//				
//
// Attributes: Circle[] pointMap;
//			   Line line;
//			   Line[] plusSign;
//			   int pointSize;
//
// Methods: Constructor
//			drawPoints(double[][] points)
//			connectPoints(Cloud cloud, int[] points)
//			markCenterOfMass(double[] com)
//

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

public class CloudPane extends Pane {
	private Circle[] pointMap;
	private Line connector;
	private Line[] plusSign;
	private Line[] axes;
	private int pointSize = 2;
	
	public CloudPane(Cloud cloud) {
		// Randomly set the point cloud from the Cloud class
		cloud.setCloudRandomly(10);
		// Initialize relevant arrays
		pointMap = new Circle[10];
		plusSign = new Line[2];
		axes = new Line[2];
		
		// Draw x and y axis
		drawAxes();
		// Draw points, connect closest, furthest and mark center of mass
		drawPoints(cloud.getCloud());
		// Connect furthest points
		connectPoints(cloud.getSinglePoint(cloud.findFurthestPoints()[0]), cloud.getSinglePoint(cloud.findFurthestPoints()[1]));
		// Connect closest points
		connectPoints(cloud.getSinglePoint(cloud.findClosestPoints()[0]), cloud.getSinglePoint(cloud.findClosestPoints()[1]));
		// Mark center of mass
		markCenterOfMass(cloud.findCenterOfMass());
		
		// Print relevant point cloud info
		System.out.println(cloud.getStudentInfo());
		cloud.printCloud();
		System.out.printf("Closest Points: %d, %d\n", cloud.findClosestPoints()[0], cloud.findClosestPoints()[1]);
		System.out.printf("Furthest Points: %d, %d\n", cloud.findFurthestPoints()[0], cloud.findFurthestPoints()[1]);
		System.out.printf("Center of Mass: %.2f, %.2f\n\n", cloud.findCenterOfMass()[0], cloud.findCenterOfMass()[1]);
	}
	
	/* Methods */
	public void drawPoints(double[][] points) {
		// Create and show a point for each point in the cloud in a 400x400 region
		// inside the 600x600 window
		for(int i = 0; i < points.length; i++) {
			// new Cloud(startX, startY, radius);
			pointMap[i] = new Circle(200 + points[i][0] * 4, 400 - points[i][1] * 4, pointSize);
			getChildren().add(pointMap[i]);
		}
	}

	public void drawAxes() {
		axes[0] = new Line(200, 0, 200, 600);
		axes[1] = new Line(0, 400, 600, 400);
		getChildren().add(axes[0]);
		getChildren().add(axes[1]);
	}

	public void connectPoints(double[] p1, double[] p2) {
		// Create and show a line based off the input points
		// Multiply by 4 to scale to the window size, add 400 and 200
		// to keep points within a region of the screen
		connector = new Line(200 + p1[0] * 4, 400 - p1[1] * 4, 200 + p2[0] * 4, 400 - p2[1] * 4);
		getChildren().add(connector);
	}
	
	public void markCenterOfMass(double[] com) {
		// Draw 2 lines to create plus sign centered on the
		// specified center of mass
		plusSign[0] = new Line(200 + (com[0] * 4 - 5), 400 - com[1] * 4, 200 + (com[0] * 4 + 5), 400 - com[1] * 4); // Horizontal Line
		plusSign[1] = new Line(200 + com[0] * 4, 400 - (com[1] * 4 - 5), 200 + com[0] * 4, 400 - (com[1] * 4 + 5)); // Vertical line
		getChildren().add(plusSign[0]);
		getChildren().add(plusSign[1]);
	}
}