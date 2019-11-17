/*
* Responsible Member(s): Aaron Van De Brook
* This code impliments all required includes
* and prototypes for in relation to AccountManagment
*/ 

#ifndef __ACCOUNT_MANAGEMENT_H
#define __ACCOUNT_MANAGEMENT_H

#ifdef __cplusplus
extern "C" {
#endif

// Includes
#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<string.h>

#include"ProjectList.h"

// Prototypes
int createAccount(int client, struct loginInfoList *list);
int login(int client, struct loginInfoList *list, struct projList *projectList);

// Defines
#define MIN_PASSLEN 8
#define MAX_USERLEN 30
#define MAX_NAMELEN 50

#ifdef __cplusplus
}
#endif

#endif
