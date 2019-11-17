/* Responsible Member(s): Aaron Van De Brook, Joshua Gordon
* This code controls project creation splash 
* pages and information forwarding
* between client and server
* 
* Functions: sendProjectMenu(int client, 
* struct projList *list), createProject(int 
* client, struct projList *list), 
* sendProjects(int client, struct projList 
* *list), editProject(int client, struct 
* projList *list), deleteProject(int client, 
* struct projList *list)
*/
#include"ProjectManagement.h"

// Function to send the project menu and get a response
// from the client.
void sendProjectMenu(int client, struct projList *list) {
	int choice;
	char *menu = "What would you like to do?\n\t1-Create a project\n\t2-View all projects\n\t3-Edit a project\n\t4-Delete a project\n\t5-Exit\n";

	printf("Sending project menu...\n");
	do {
		send(client, menu, sizeof(menu), 0); 
		read(client, &choice, sizeof(choice));

		switch(choice) {
			case 1:
				/* Create Project */
				createProject(client, list);
				break;
			case 2:
				/* View projects */
				printf("Sending projects.\n");
				sendProjects(client, list);
				break;
			case 3:
				/* Edit Project */
				editProject(client, list);
				break;
			case 4:
				/* Delete Project */
				deleteProject(client, list);
				break;
			case 5:
				printf("Exitting project menu...\n");
				break;
			default:
				break;
		}
	} while(choice != 5);
}

//This function is used to create projects
int createProject(int client, struct projList *list) {
	char projectName[PROJ_MAX_NAME];
	char projectDesc[PROJ_MAX_DESC];
	char dateCreated[PROJ_MAX_DATE];
	char dateDue[PROJ_MAX_DATE];
	int numMembers;
	char *temp1 = "Please enter the new project name: ";
	char *temp2 = "Please enter the project description: ";
	char *temp3 = "Please enter the date created: ";
	char *temp4 = "Please enter the date due: ";
	char *temp5 = "Please enter the number of members: ";

	send(client, temp1, sizeof(temp1), 0);
	read(client, projectName, strlen(projectName));
	
	send(client, temp2, sizeof(temp2), 0);
	read(client, projectDesc, strlen(projectDesc));
	
	send(client, temp3, sizeof(temp3), 0);
	read(client, dateCreated, strlen(dateCreated));
	
	send(client, temp4, sizeof(temp4), 0);
	read(client, dateDue, strlen(dateDue));
	
	send(client, temp5, sizeof(temp5), 0);
	read(client, &numMembers, sizeof(numMembers));

	printf("Creating new project...\n");
	appendProject(projectName, projectDesc, dateCreated, dateDue, numMembers, list);

	return 0;
}

//Sends all projects and their info
void sendProjects(int client, struct projList *list) {
	struct projListNode *node = list->front;
	int length = list->size, i;

	send(client, &length, sizeof(length), 0);
	
	for(i = 0; i < length; i++) {
		send(client, node->projectName, strlen(node->projectName), 0);
		send(client, node->projectDesc, strlen(node->projectDesc), 0);
		send(client, node->dateCreated, strlen(node->dateCreated), 0);
		send(client, node->dateDue, strlen(node->dateDue), 0);
		send(client, &(node->numMembers), sizeof(node->numMembers), 0);
		node = node->next;
	}
}

//This function finds the project requested and prompts the user to edit fields 
void editProject(int client, struct projList *list) {
	int choice, numMembers, pos, i;
	char *projectName, *projectDesc, *dateCreated, *dateDue;
	struct projListNode *node = list->front;
	char *menu1 = "\nPlease enter the project you wish to view: ";
	char *menu2 = "\nWhat would you like to edit?\n\t1-Project Name\n\t2-Project Description\n\t3-The Date Created\n\t4-The Date Due\n\t5-The Number of Members\n\t6-Exit this Menu\n";
	
	// Send menu to client
	send(client, menu1, strlen(menu1), 0);
	read(client, projectName, strlen(projectName));
	pos = findProject(projectName, list);

	printf("Finding project...\n");
	for(i = 0; i < pos; i++) {
		node = node->next;
	}

	memset(projectName, 0, strlen(projectName));
	
	do {
		send(client, menu2, strlen(menu2), 0);
		read(client, &choice, sizeof(choice));

		switch(choice) {
			case 1:
				// Project Name
				read(client, projectName, strlen(projectName));
				strcpy(node->projectName, projectName);
				break;
			case 2:
				// Project description 
				read(client, projectDesc, strlen(projectDesc));
				strcpy(node->projectDesc, projectDesc);
				break;
			case 3:
				// Date created
				read(client, dateCreated, strlen(dateCreated));
				strcpy(node->dateCreated, dateCreated);
				break;
			case 4:
				// Date due
				read(client, dateDue, strlen(dateDue));
				strcpy(node->dateDue, dateDue);
				break;
			case 5:
				// Number of members
				read(client, &numMembers, sizeof(numMembers));
				node->numMembers = numMembers;
				break;
			case 6:
				// Exit
				break;
			default:
				fprintf(stderr, "Enter a value from the menu.\n");
				break;
		}
	} while(choice != 6);
}
//This function prompts the user for a project name to be deleted
void deleteProject(int client, struct projList *list) {
	char projectName[PROJ_MAX_NAME];
	int confirmation, status;
	char *menu = "Please enter the project you wish to delete: ";
	
	send(client, menu, sizeof(menu), 0);
	read(client, projectName, PROJ_MAX_NAME);

	if(removeProject(projectName, list)) {
		confirmation = 1;
	} else {
		confirmation = 0;
	}

	send(client, &confirmation, sizeof(confirmation), 0);
}
