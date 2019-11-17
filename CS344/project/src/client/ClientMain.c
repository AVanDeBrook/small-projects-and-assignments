/*
* Members Responsible: Justin Andrews, Aaron Van De Brook
* This code is used for client interaction with the 
* server before a full connection is established
* Functions: main, login(int server), createAccount(int server), 
* projectMenu(int server),  editProject(int server), deleteProject(int server)
*/
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>

int login(int server);
void createAccount(int server);
void projectMenu(int server);
void editProject(int server);
void deleteProject(int server);

int main(int argc, char *argv[])
{
	int port, socketNum, choice;
	char *serverIP;
	struct sockaddr_in serverAddr;

	//create variables to hold user input
	char buffer[1024] = {0};
	char userName[100];
	char password[100];
	char fullName[100];

	//check for correct number of arguements
	if (argc != 3)
	{
		printf("Usage: %s <server IP> <port>\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	//set arguements to IP and port number
	serverIP = argv[1];
	port = atoi(argv[2]);

	//set socket
	socketNum = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (socketNum < 0)
	{
		fprintf(stderr, "socket() failed somehow\n");
	}

	//format IP address for network
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr(serverIP);
	serverAddr.sin_port = htons(port);

	//connect to server
	if (connect(socketNum, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0)
	{
		fprintf(stderr, "Failed to connect to server\n");
		exit(EXIT_FAILURE);
	}


	while (1)
	{
		//Login menu
		read(socketNum, buffer, 1024);
		printf("%s", buffer);
		scanf("%d", &choice);

		switch (choice)
		{

		//user chooses to login
		case 1:

			//send choice
			send(socketNum, &choice, sizeof(choice), 0);
			//Log user in
			if(login(socketNum)) {
				projectMenu(socketNum);
			}
			break;


		//user chooses to create an account
		case 2:
			//send choice to server
			send(socketNum, &choice, sizeof(choice), 0);
			//Account creation
			createAccount(socketNum);
			projectMenu(socketNum);
			break;

		//user chooses to exit
		case 3:
			printf("Exiting...\n");
			send(socketNum, &choice, sizeof(choice), 0);
			send(socketNum, userName, strlen(userName), 0);
			close(socketNum);
			exit(0);
			break;

		//user inputs invalid response
		default:
			printf("ERROR: Invalid input. Exiting.\n");
			choice = 3;
			send(socketNum, &choice, sizeof(choice), 0);
			close(socketNum);
			exit(0);
			break;
		}
	}
}

int login(int server) {
	char userName[100], password[100];
	int exists;

	printf("Enter username: ");
	scanf("%s", userName);

	send(server, userName, strlen(userName), 0);

	read(server, &exists, sizeof(exists));
	if(exists == 0) {
		fprintf(stderr, "Username does not exist.\n");
		return 0;
	}

	printf("Enter password: ");
	scanf("%s", password);

	send(server, password, strlen(password), 0);

	read(server, &exists, sizeof(exists));
	if(exists == 0) {
		fprintf(stderr, "Incorrect password.\n");
		return 0;
	}

	return 1;
}

void createAccount(int server) {
	char buffer[1024];
	char userName[100], password[100], fullName[100];

	// Username
	memset(buffer, 0, 1024);
	read(server, buffer, 1024);
	do {
		printf("%s", buffer);
		scanf("%s", userName);
		if(strlen(userName) > 30) {
			fprintf(stderr, "Enter a username 50 characters or less in length.\n");
		} else {
			send(server, userName, strlen(userName), 0);
		}
	} while(strlen(userName) <= 0 || strlen(userName) > 30);

	// Password
	memset(buffer, 0, strlen(buffer));
	read(server, buffer, 1024);
	do {
		printf("%s", buffer);
		scanf("%s", password);
		if(strlen(password) < 8) {
			fprintf(stderr, "Enter a password at least 8 characters long.\n");
		} else {
			send(server, password, strlen(password), 0);
		}
	} while(strlen(password) <= 0 || strlen(password) < 8);

	// Full name
	memset(buffer, 0, strlen(buffer));
	read(server, buffer, 1024);
	do {
		printf("%s", buffer);
		scanf("%s", fullName);
		if(strlen(fullName) > 50) {
			fprintf(stderr, "Name is too long, must be less than 50 characters.\n");
		} else {
			send(server, fullName, strlen(fullName), 0);
		}
	} while(strlen(fullName) <= 0 || strlen(fullName) > 50);
}

void projectMenu(int server) {
	char projectName[100], projectDesc[100], dateCreated[100], dateDue[100];
	int numMembers, choice, length, i;
	
	do {
		printf("1-Create a Project\n2-View Projects\n3-Edit Project\n4-Delete Project\n5-Exit\nOption: ");
		scanf("%d", &choice);
		send(server, &choice, sizeof(choice), 0);

		switch(choice) {
			case 1:
				// Create Project
				printf("Enter project name: ");
				scanf("%s", projectName);
				printf("Enter project description: ");
				scanf("%s", projectDesc);
				printf("Enter date created: ");
				scanf("%s", dateCreated);
				printf("Enter date due: ");
				scanf("%s", dateDue);
				printf("Enter number of members: ");
				scanf("%d", &numMembers);

				send(server, projectName, strlen(projectName), 0);
				send(server, projectDesc, strlen(projectDesc), 0);
				send(server, dateCreated, strlen(dateCreated), 0);
				send(server, dateDue, strlen(dateDue), 0);
				send(server, &numMembers, sizeof(numMembers), 0);

				printf("Created new project.\n");
				break;
			case 2:
				//Display projects
				read(server, &length, sizeof(length));
				
				if(length <= 0) {
					printf("No projects to display.\n");
				}

				for(i = 0; i < length; i++) {
					read(server, projectName, 100);
					read(server, projectDesc, 100);
					read(server, dateCreated, 100);
					read(server, dateDue, 100);
					read(server, &numMembers, sizeof(numMembers));

					printf("\nProject Name: %s\n", projectName);
					printf("Project Description: %s\n", projectDesc);
					printf("Date Created: %s\n", dateCreated);
					printf("Date due: %s\n", dateDue);
					printf("Number of Members: %d\n\n", numMembers);
				}
				break;
			case 3:
				// Edit project
				editProject(server);
				break;
			case 4:
				// Delete project
				deleteProject(server);
				break;
			case 5:
				// Exit
				printf("Exitting...\n");
				break;
			default:
				fprintf(stderr, "Enter an option from the list");
				break;
		}
	} while(choice != 5);
}

void editProject(int server) {
	int choice, numMembers;
	char name[100], buffer[1024];

	printf("Enter the name of the project to edit: ");
	scanf("%s", name);
	send(server, name, strlen(name), 0);

	do {
		memset(buffer, 0, 1024);
		printf("Edit:\n1 - Project Name\n2 - Project Description\n3 - Date created\n4 - Date due\n5 - Number of members\n6 - Exit\nOption: ");
		scanf("%d", &choice);
		send(server, &choice, sizeof(choice), 0);

		switch(choice) {
			case 1:
				// Project Name
				printf("New name: ");
				scanf("%s", buffer);
				send(server, buffer, strlen(buffer), 0);
				break;
			case 2:
				// Project description 
				printf("New description: ");
				scanf("%s", buffer);
				send(server, buffer, strlen(buffer), 0);
				break;
			case 3:
				// Date created
				printf("New date created: ");
				scanf("%s", buffer);
				send(server, buffer, strlen(buffer), 0);
				break;
			case 4:
				// Date due
				printf("New date due: ");
				scanf("%s", buffer);
				send(server, buffer, strlen(buffer), 0);
				break;
			case 5:
				// Number of members
				printf("New number of members: ");
				scanf("%d", &numMembers);
				send(server, &numMembers, sizeof(numMembers), 0);
				break;
			case 6:
				// Save
				printf("Saving project...\n");
				break;
			default:
				fprintf(stderr, "Enter a value from the menu.\n");
				break;
		}
	} while(choice != 6);
}

void deleteProject(int server) {
	char projectName[100];
	int confirmation;

	printf("Enter the name of the project to delete: ");
	scanf("%s", projectName);

	send(server, projectName, strlen(projectName), 0);

	read(server, &confirmation, sizeof(confirmation));

	if(confirmation) {
		printf("Deleted project %s\n", projectName);
	}
}