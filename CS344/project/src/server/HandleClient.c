/* 
* Responsible Member(s): Aaron Van De Brook
* This file handles the backbone of client connection 
* from the server side. This includes; loging in, 
* creating an account, closing connections and sending
* splash menus. 
* Functions: sendSplashMenu(int client), 
* HandleConnection(int client)
*/
#include"HandleConnection.h"
#include"AccountManagement.h"
#include"ProjectList.h"
#include"ProjectManagement.h"
#include"ServerIO.h"

// These prototypes will only be used in this file, so there is no point
// in putting them in the HandleConnection.h header file.
int sendSplashMenu(int client);

void HandleConnection(int client) {
	int clientResp = 0, accountResult;
	char *accountDNE = "Account does not exist.\n";
	char *exitMsg = "Closing connection...";

	struct projList projectList;
	struct loginInfoList loginInfo;

	projectList.size = loginInfo.size = 0;
	projectList.front = projectList.back = NULL;
	loginInfo.front = loginInfo.back = NULL;

	readCredentials(&loginInfo);

	printf("Handling client connection\n");

	do {
		printf("Sending login menu...\n");
		clientResp = sendSplashMenu(client);

		if(clientResp <= 0) {
			fprintf(stderr, "Failed to send login menu.\n");
			exit(EXIT_FAILURE);
		}

		switch(clientResp) {
			case 1: /* Login to existing account */
				if(login(client, &loginInfo, &projectList)) {
					sendProjectMenu(client, &projectList);
				}
				break;
			case 2: /* Create an account */
				accountResult = createAccount(client, &loginInfo);
				if(accountResult) {
					sendProjectMenu(client, &projectList);
				} else {
					fprintf(stderr, "Username already exists.\n");
				}
				break;
			case 3: /* Exit */
				printf("Closing connection with client.\n");
				send(client, exitMsg, strlen(exitMsg), 0);
				break;
		}
	} while(clientResp != 3);

	close(client);
	exit(0);
}

// Sends a login menu to client and waits for a response.
// returns the following:
// -1 - failed to send data
//  0 - unkown error
//  1 - login to an existing account
//  2 - create an account
//  3 - exit program, close connection
int sendSplashMenu(int client) {
	int response = 0;
	int status;
	char *buffer = "Login or create a profile.\n\t1 - Login\n\t2 - Create a profile\n\t3 - Exit\nEnter a value from the menu: ";

	// Send menu to client
	status = send(client, buffer, strlen(buffer), 0);
	printf("Sent login menu to client...\n");
	if(status < 0) { // Check for error sending data
		fprintf(stderr, "Error in sendSplashMenu()...\n");
		response = -1;
	} else {
		// Get response from client
		printf("Waiting for response from client...\n");
		read(client, &response, sizeof(response));
	}
	
	return response;
}
