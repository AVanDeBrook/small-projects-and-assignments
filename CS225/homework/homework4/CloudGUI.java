// Class: CloudGUI (extends Application)
// Author: Aaron Van De Brook
// Last Modified: 10/07/18
//
// Description: Displays a randomly created point cloud
//				and marks the closest and furthest points
//				in the cloud as well as the center of mass
//				of the cloud.
//

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CloudGUI extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Cloud cloud = new Cloud();
		WeightedCloud wCloud = new WeightedCloud();
		// Pass objects to the CloudPane in order to create a new point cloud
		// and do necessary calculations
		CloudPane cloudPane = new CloudPane(cloud);
		CloudPane weightedPane = new CloudPane(wCloud);
		// Create 2 new stages for the point cloud and weighted point cloud
		Stage cloudStage = new Stage();
		Stage weightedStage = new Stage();
		// Create 2 new scenes for the point clouds 
		Scene cloudScene = new Scene(cloudPane, 600, 600);
		Scene weightedScene = new Scene(weightedPane, 600, 600);
		
		// Set the titles of each stage
		cloudStage.setTitle("Cloud - Aaron Van De Brook");
		weightedStage.setTitle("Weighted Cloud - Aaron Van De Brook");
		// Add each scene to the stage
		cloudStage.setScene(cloudScene);
		weightedStage.setScene(weightedScene);
		// Show the stages 
		cloudStage.show();
		weightedStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}