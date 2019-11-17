/*
* Responsible Member(s): Aaron Van De Brook
* This code impliments all required includes
* and prototypes for in relation to ProjectManagment
*/ 

#ifndef __PROJECT_MANAGEMENT_H
#define __PROJECT_MANAGEMENT_H

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
#include"ProjectList.h"

// Prototypes
int createProject(int client, struct projList *list);
void sendProjectMenu(int client, struct projList *list);
void sendProjects(int client, struct projList *list);
void editProject(int client, struct projList *list);
void deleteProject(int client, struct projList *list);

#ifdef __cplusplus
}
#endif

#endif
