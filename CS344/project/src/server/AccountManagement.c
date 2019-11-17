/*
* Responsible Member(s): Aaron Van De Brook, Joshua Gordon
* This code controls initial account management including
* creation and login. It is used as part of the authentication 
* process.
* Functions: createAccount(int client, struct loginInfoList *list)
* , login(int client, struct loginInfoList *list)
*/ 
#include"AccountManagement.h"
#include"ServerIO.h"
#include"ProjectList.h"

// Creates a new account for the user
// Returns the following:
// -1 - error sending data
//  0 - username already exists
//  1 - successfully created an account
int createAccount(int client, struct loginInfoList *list) {
	char *userNamePrompt = "Enter a username (e-mail): ";
	char *passwordPrompt = "Enter a password: ";
	char *fullNamePrompt = "Enter your full name: ";
	char userName[1024] = {0};
	char password[1024] = {0};
	char fullName[1024] = {0};

	printf("Sending username prompt...\n");
	send(client, userNamePrompt, strlen(userNamePrompt), 0);
	read(client, userName, MAX_USERLEN);

	if(findLogin(userName, list)) {
		return 0;
	}

	printf("Sending password prompt...\n");
	send(client, passwordPrompt, strlen(passwordPrompt), 0);
	read(client, password, 1024);

	printf("Sending full name prompt...\n");
	send(client, fullNamePrompt, strlen(fullNamePrompt), 0);
	read(client, fullName, MAX_NAMELEN);
	
	//Store the credentials
  	writeCredentials(userName, password, fullName);

	return 1;
}

// Function to log a user into an existing account
// Returns the following:
// -1 - Error communicating with client
//  0 - Wrong credentials
//  1 - Successfully logged the user in
int login(int client, struct loginInfoList *list, struct projList *projectList) {
	char userName[1024] = {0};
	char password[1024] = {0};
	int loginPos, i, exists;
	struct loginInfoListNode *node = list->front;

	printf("Getting username...\n");
	read(client, userName, 1024);
	
	// TODO: Verify user account exists
	loginPos = findLogin(userName, list);
	printf("username: %s\n", userName);
	if(loginPos < 0) {
		fprintf(stderr, "Username not found.\n");
		exists = 0;
		send(client, &exists, sizeof(exists), 0);
		return 0;
	}

	exists = 1;
	send(client, &exists, sizeof(exists), 0);

	for(i = 0; i < loginPos; i++) {
		node = node->next;
	}

	printf("Getting password...\n");
	read(client, password, 1024);

	if(strcmp(password, node->password) != 0) {
		fprintf(stderr, "Wrong password.\n");
		exists = 0;
		send(client, &exists, sizeof(exists), 0);
		return 0;
	}

	exists = 1;
	send(client, &exists, sizeof(exists), 0);

	printf("Loading projects...\n");
	readProjects(projectList, pathgen(userName));

	return 1;
}
