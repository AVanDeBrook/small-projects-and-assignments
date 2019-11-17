// Class: CloudPane (extends Pane)
// Author: Aaron Van De Brook
// Last Modified: 11/05/18
//
// Description: Provides data to create the graphical representation
//				of the points, closest points, furthest points and
//				center of mass from the Cloud and WeightedCloud classes.
//				
//
// Attributes: Circle[] pointMap;
//			   Line closestConnectr;
//			   Line furthestConnector;
//			   Line[] plusSign;
//			   Line[] axes;
//			   int pointSize;
//			   Button pointCloudBt, closestBt, furthestBt, comBt, saveDataBt, loadDataBt;
//			   File file;
//
// Methods: Constructor
//			void drawPoints(double[][] points);
//			void drawAxes();
//			void createButtons();
//			void writeDataToFile(double[][] points);
//			double[][] loadDataFromFile();
//			void connectClosestPoints(double[] p1, double[] p2);
//			void connectFurthestPoints(double[] p1, double[] p2);
//			void markCenterOfMass(double[] com);
//

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class CloudPane extends Pane {
	private Circle[] pointMap;
	private Line closestConnector;
	private Line furthestConnector;
	private Line[] plusSign;
	private Line[] axes;
	private int pointSize = 2;
	private Button pointCloudBt, closestBt, furthestBt, comBt, saveDataBt, loadDataBt;
	private File file = new File("cloud.dat");
	
	public CloudPane(Cloud cloud) {
		// Randomly set the point cloud from the Cloud class
		cloud.setCloudRandomly(10);
		// Initialize relevant arrays
		pointMap = new Circle[10];
		plusSign = new Line[2];
		axes = new Line[3];

		// Draw x and y axis
		drawAxes();
		// Create buttons to toggle GUI elements
		createButtons();
		// Draw points, connect closest, furthest and mark center of mass
		drawPoints(cloud.getCloud());
		// Connect furthest points
		connectFurthestPoints(cloud.getSinglePoint(cloud.findFurthestPoints()[0]), cloud.getSinglePoint(cloud.findFurthestPoints()[1]));
		furthestConnector.setVisible(false);
		// Connect closest points
		connectClosestPoints(cloud.getSinglePoint(cloud.findClosestPoints()[0]), cloud.getSinglePoint(cloud.findClosestPoints()[1]));
		closestConnector.setVisible(false);
		// Mark center of mass
		markCenterOfMass(cloud.findCenterOfMass());
		plusSign[0].setVisible(false);
		plusSign[1].setVisible(false);

		// Set point cloud button to toggle points visible or invisible
		pointCloudBt.setOnAction(e -> {
			for(int i = 0; i < pointMap.length; i++) {
				pointMap[i].setVisible(!pointMap[i].isVisible());
			}
		});

		// Set closest connector button to toggle visibility of the line connecting 
		// the closest points
		closestBt.setOnAction(e -> {
			closestConnector.setVisible(!closestConnector.isVisible());
		});

		// Set furthest connector button to toggle visibility of the line connecing
		// the furthest points
		furthestBt.setOnAction(e -> {
			furthestConnector.setVisible(!furthestConnector.isVisible());
		});

		// Set center of mass button to toggle visibility of the center of mass marker
		comBt.setOnAction(e -> {
			for(int i = 0; i < plusSign.length; i++) {
				plusSign[i].setVisible(!plusSign[i].isVisible());
			}
		});
		
		// Set save button data
		saveDataBt.setOnAction(e -> {
			writeDataToFile(cloud.getCloud());
		});
		
		
		// Set load button data
		loadDataBt.setOnAction(e -> {
			for(int i = 0; i < pointMap.length; i++) {
				pointMap[i].setVisible(false);
			}
			
			drawPoints(loadDataFromFile());
		});
		
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
			pointMap[i] = new Circle(200 + points[i][0] * 4, 400 - points[i][1] * 4, pointSize);
			getChildren().add(pointMap[i]);
		}
	}

	public void drawAxes() {
		axes[0] = new Line(200, 0, 200, 530);
		axes[1] = new Line(0, 400, 600, 400);
		axes[2] = new Line(0, 530, 600, 530);
		getChildren().addAll(axes[0], axes[1], axes[2]);
	}

	public void createButtons() {
		// Create button to toggle point visibility
		pointCloudBt = new Button("Toggle Points");
		pointCloudBt.setLayoutX(10);
		pointCloudBt.setLayoutY(550);
		// Create button to toggle closest points line visibility
		closestBt = new Button("Toggle Closest Points");
		closestBt.setLayoutX(110);
		closestBt.setLayoutY(550);
		// Create button to toggle furthest points line visibility
		furthestBt = new Button("Toggle Furthest Points");
		furthestBt.setLayoutX(250);
		furthestBt.setLayoutY(550);
		// Create button to toggle visibility of the center of mass marker
		comBt = new Button("Toggle Center of Mass");
		comBt.setLayoutX(395);
		comBt.setLayoutY(550);
		// Create button to write data to a file
		saveDataBt = new Button("Save Cloud");
		saveDataBt.setLayoutX(10);
		saveDataBt.setLayoutY(585);
		// Create button to load data from a file
		loadDataBt = new Button("Load Cloud");
		loadDataBt.setLayoutX(110);
		loadDataBt.setLayoutY(585);
		// Add all buttons to the pane
		getChildren().addAll(pointCloudBt, closestBt, furthestBt, comBt, saveDataBt, loadDataBt);
	}
	
	public void writeDataToFile(double[][] points) {
		try {	
			PrintWriter output = new PrintWriter(file);
			
			for(int i = 0; i < points.length; i++) {
				output.printf("%.2f %.2f\n", points[i][0], points[i][1]);
			}
			
			output.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public double[][] loadDataFromFile() {
		double[][] points = new double[10][2];
		try {
			Scanner reader = new Scanner(file);
			
			for(int i = 0; i < points.length; i++) {
				points[i][0] = reader.nextDouble();
				points[i][1] = reader.nextDouble();
			}
			
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return points;
	}

	public void connectClosestPoints(double[] p1, double[] p2) {
		// Create and show a line based off the input points
		// Multiply by 4 to scale to the window size, add 400 and 200
		// to keep points within a region of the screen
		closestConnector = new Line(200 + p1[0] * 4, 400 - p1[1] * 4, 200 + p2[0] * 4, 400 - p2[1] * 4);
		getChildren().add(closestConnector);
	}

	public void connectFurthestPoints(double[] p1, double[] p2) {
		// Create and show a line based off the input points
		// Multiply by 4 to scale to the window size, add 400 and 200
		// to keep points within a region of the screen
		furthestConnector = new Line(200 + p1[0] * 4, 400 - p1[1] * 4, 200 + p2[0] * 4, 400 - p2[1] * 4);
		getChildren().add(furthestConnector);
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