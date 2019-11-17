/*
 *  Responsible Members(s): Aaron Van De Brook
 *  This file contains the utility functions
 *  for the project and member linked lists.
 *
 *  Functions: appendProject(char *name, char *desc, 
 *  char *created, char *due, int members, struct 
 *  projList *list), appendLoginInfo(char  *username, 
 *  char *password, char *fullName, struct loginInfoList 
 *  *list), appendMember(char *name, struct memberList 
 *  *list), findLogin(char *username, struct loginInfoList 
 *  *list), findProject(char *project, struct projList 
 *  *list), findMember(char *name, struct memberList 
 *  *list), removeProject(char *name, struct projList 
 *  *list), removeLoginInfo(char *username, struct 
 *  loginInfoList *list), removeMember(char *username, 
 *  struct memberList *list)
 */

#include"ProjectList.h"

// Function to add a new project to a list
// Returns the following:
// -1 - either list or new project do not exist
//  0 - one or more of the new project's fields are incorrect or out of bounds
//  1 - successfully added the name to the list
int appendProject(char *name, char *desc, char *created, char *due, int members, struct projList *list) {
    if(list == NULL) { return -1; }

    struct projListNode *node = (struct projListNode *) malloc(sizeof(struct projListNode));

    strcpy(node->projectName, name);
    strcpy(node->projectDesc, desc);
    strcpy(node->dateCreated, created);
    strcpy(node->dateDue, due);
    node->numMembers = members;

    if(list->front == NULL && list->back == NULL) {
        list->front = list->back = node;
    } else {
        list->back->next = node;
        node->prev = list->back;
        node->next = NULL;
        list->back = node;
    }

    list->size++;

    return 1;
}

// Function to add new login info to the login
// info list.
// Returns the following:
// -1 - list is null
//  1 - successfully added new login info to list
int appendLoginInfo(char *username, char *password, char *fullName, struct loginInfoList *list) {
    if(list == NULL) { return -1; }

    struct loginInfoListNode *node = (struct loginInfoListNode *) malloc(sizeof(struct loginInfoListNode));

    strcpy(node->username, username);
    strcpy(node->password, password);
    strcpy(node->fullName, fullName);

    if(list->front == NULL && list->back == NULL) {
        list->front = list->back = node;
    } else {
        list->back->next = node;
        node->prev = list->back;
        node->next = NULL;
        list->back = node;
    }

    list->size++;

    return 1;
}

// Function to add a member to a member list
// Returns the following:
// -1 - list, name, or username are null
//  1 - successfully added member to list
int appendMember(char *name, struct memberList *list) {
    if(list == NULL || name == NULL) { return -1; }

    struct memberListNode *node = (struct memberListNode *) malloc(sizeof(struct memberListNode));

    strcpy(node->memberName, name);

    if(list->front == NULL && list->back == NULL) {
        list->front = list->back = node;
    } else {
        list->back->next = node;
        node->prev = list->back;
        node->next = NULL;
        list->back = node;
    }

    list->size++;

    return 1;
}

// Function to find the position of the login info of a 
// certain username
// Returns the following: 
// -2 - either username or list was null
// -1 - could not find username
// Returns the postion of the user info otherwise
int findLogin(char *username, struct loginInfoList *list) {
    if(username == NULL || list == NULL) { return -2; }

    int i;
    struct loginInfoListNode *node = list->front;

    for(i = 0; i < list->size; i++) {
        if(strcmp(node->username, username) == 0) {
            return i;
        }
        node = node->next;
    }

    return -1;
}

// Function to find the position of project in a list
// Returns the following:
// -2 - either the name or list are null
// -1 - member was not in the list
// Otherwise: returns the position of the project in the list
int findProject(char *project, struct projList *list) {
    if(project == NULL || list == NULL) { return -2; }

    int i;
    struct projListNode *node = list->front;

    for(i = 0; i < list->size; i++) {
        if(!strcmp(node->projectName, project)) {
            return i;
        } else {
            node = node->next;
        }
    }

    return -1;
}

// Function to find a member by username in the list
// Returns the following:
// -2 - either the username or list is null
// -1 - member was not in the list
// Otherwise: returns the postion of the member in the list
int findMember(char *name, struct memberList *list) {
    if(name == NULL || list == NULL) { return -2; }

    int i;
    struct memberListNode *node = list->front;

    for(i = 0; i < list->size; i++) {
        if(strcmp(node->memberName, name) == 0) {
            return i;
        }
        node = node->next;
    }

    return -1;
}

// Removes a project from the list
// Returns the following:
// -1 - name or list is null
//  0 - name is not in the list
//  1 - successfully removed item from list
int removeProject(char *name, struct projList *list) {
    if(list == NULL || name == NULL) { return -1; }

    int i, pos = findProject(name, list);
    struct projListNode *node = list->front;

    if(pos < 0) { return 0; }

    for(i = 0; i < pos; i++) {
        node = node->next;
    }

    if(node->next == NULL && node->prev == NULL) {
        list->front = list->back = NULL;
    } else if(node->next == NULL) {
        list->back = node->prev;
        node->prev->next = NULL;
    } else if(node->prev == NULL) {
        list->front = node->next;
        node->next->prev = NULL;
    } else {
        node->next->prev = node->prev;
        node->prev->next = node->next;
    }

    free(node);
    list->size--;

    return 1;
}

// Function to remove a login from the list.
// Returns the following:
// -2 - either list or username is null
//  0 - username is not in the list
//  1 - successfully removed the login info from the list
int removeLoginInfo(char *username, struct loginInfoList *list) {
    if(list == NULL || username == NULL) { return -2; }

    int i, pos = findLogin(username, list);
    struct loginInfoListNode *node = list->front;

    if(pos < 0) { return 0; }

    for(i = 0; i < pos; i++) {
        node = node->next;
    }

    if(node->next == NULL && node->prev == NULL) {
        list->front = list->back = NULL;
    } else if(node->next == NULL) {
        list->back = node->prev;
        node->prev->next = NULL;
    } else if(node->prev == NULL) {
        list->front = node->next;
        node->next->prev = NULL;
    } else {
        node->next->prev = node->prev;
        node->prev->next = node->next;
    }

    free(node);
    list->size--;

    return 1;
}

// Function to remove a member from the list.
// Returns the following:
// -2 - either list or username is null
//  0 - username is not in the list
//  1 - successfully removed member from the list
int removeMember(char *username, struct memberList *list) {
    if(list == NULL || username == NULL) { return -2; }

    int i, pos = findMember(username, list);
    struct memberListNode *node = list->front;

    if(pos < 0) { return 0; }

    for(i = 0; i < pos; i++) {
        node = node->next;
    }

    if(node->next == NULL && node->prev == NULL) {
        list->front = list->back = NULL;
    } else if(node->next == NULL) {
        list->back = node->prev;
        node->prev->next = NULL;
    } else if(node->prev == NULL) {
        list->front = node->next;
        node->next->prev = NULL;
    } else {
        node->next->prev = node->prev;
        node->prev->next = node->next;
    }

    free(node);
    list->size--;

    return 1;
}
