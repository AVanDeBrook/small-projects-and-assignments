/*
* Responsible Member(s): Aaron Van De Brook
* Code responsible for setting up the server 
* and spawning child processes for new connections.
* Function(s): int main(int argc, char *argv[])
*/

#include "HandleConnection.h"
#include<signal.h>

#define MAXCLIENTS 3

int main(int argc, char *argv[])
{
	int port, serverSocketDesc, clientSocketDesc, clientLength, childPID, childProcesses = 0, waitStatus;
	struct sockaddr_in server;
	struct sockaddr_in client;

	if (argc != 2) {
		printf("Usage: %s <port>\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	// Set up a socket on specified port
	port = atoi(argv[1]);
	serverSocketDesc = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (serverSocketDesc < 0) {
		fprintf(stderr, "socket() failed\n");
		exit(EXIT_FAILURE);
	}

	// Bind structure to socket
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = htonl(INADDR_ANY);
	server.sin_port = htons(port);

	if (bind(serverSocketDesc, (struct sockaddr *)&server, sizeof(server)) < 0) {
		fprintf(stderr, "bind() failed\n");
		exit(EXIT_FAILURE);
	} else {
		printf("Socket bound to server struct.\n");
	}

	// Setup the socket to listen for a max of MAXCLIENTS
	if (listen(serverSocketDesc, MAXCLIENTS) < 0) {
		fprintf(stderr, "listen() failed\n");
		exit(EXIT_FAILURE);
	} else {
		printf("Listening for connection...\n");
	}

	// Accept and handle a client connection
	while (1) {
		clientLength = sizeof(client);
		clientSocketDesc = accept(serverSocketDesc, (struct sockaddr *)&client, &clientLength);
		if (clientSocketDesc < 0) {
			fprintf(stderr, "accept() failed\n");
			exit(EXIT_FAILURE);
		} else {
			childPID = fork();
			childProcesses++;
			if(childPID != 0) {
				printf("Spawned process with ID %d to handle connection\n", childPID);
			}

			if(childPID == 0) {
				HandleConnection(clientSocketDesc);
			} else if(childPID < 0) {
				fprintf(stderr, "fork() failed\n");
				exit(EXIT_FAILURE);
			}
		}
		signal(SIGCHLD, SIG_IGN);
	}
}
