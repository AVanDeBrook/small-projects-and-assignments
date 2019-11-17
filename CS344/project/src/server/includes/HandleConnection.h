/*
* Responsible Member(s): Aaron Van De Brook
* This code impliments all required includes
* and prototypes for in relation to HandleConnection
*/ 
#ifndef __HANDLE_CLIENT_H
#define __HANDLE_CLIENT_H

#ifdef __cplusplus
extern "C" {
#endif

// Includes
#include<stdio.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<sys/types.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>

// Function prototypes
void HandleConnection(int client);

#ifdef __cplusplus
}
#endif

#endif
