/*
* Responsible Member(s): Aaron Van De Brook
* This code impliments all required includes
* and prototypes for in relation to Project Structures and Lists
*/ 
#ifndef __PROJECT_LIST_H
#define __PROJECT_LIST_H

#ifdef __cplusplus
extern "C" {
#endif

// Includes
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

// Constants
#define PROJ_MAX_NAME 100
#define PROJ_MAX_DESC 1000
#define PROJ_MAX_DATE 8

#define MEM_MAX_NAME 50

#define MAX_USERLEN 30
#define MAX_NAMELEN 50
#define MAX_PASSLEN 255

// Structures
struct projListNode {
    char projectName[PROJ_MAX_NAME];
    char projectDesc[PROJ_MAX_DESC];
    char dateCreated[PROJ_MAX_DATE+1];
    char dateDue[PROJ_MAX_DATE+1];
    int numMembers;
    struct memberList *members;

    struct projListNode *next;
    struct projListNode *prev;
};

struct projList {
    int size;

    struct projListNode *front;
    struct projListNode *back;
};

struct loginInfoList {
    int size;

    struct loginInfoListNode *front;
    struct loginInfoListNode *back;
};

struct loginInfoListNode {
    char username[MAX_USERLEN];
    char password[MAX_PASSLEN];
    char fullName[MAX_NAMELEN];

    struct loginInfoListNode *next;
    struct loginInfoListNode *prev;
};

struct memberListNode {
    char memberName[MEM_MAX_NAME];

    struct memberListNode *next;
    struct memberListNode *prev;
};

struct memberList {
    int size;

    struct memberListNode *front;
    struct memberListNode *back;
};

// Prototypes
int appendProject(char *name, char *desc, char *created, char *due, int members, struct projList *list);
int appendLoginInfo(char *username, char *password, char *fullName, struct loginInfoList *list);
int appendMember(char *name, struct memberList *list);
int findProject(char *project, struct projList *list);
int findLogin(char *username, struct loginInfoList *list);
int findMember(char *username, struct memberList *list);
int removeProject(char *project, struct projList *list);
int removeLoginInfo(char *username, struct loginInfoList *list);
int removeMember(char *username, struct memberList *list);

#ifdef __cplusplus
}
#endif

#endif
