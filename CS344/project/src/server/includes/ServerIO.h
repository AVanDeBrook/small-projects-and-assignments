/*
* Responsible Member(s): Aaron Van De Brook, Joshua Gordon
* This code impliments all required includes
* and prototypes for in relation to Server based file IO
*/ 
#ifndef __SERVER_IO_H
#define __SERVER_IO_H

#ifdef __cplusplus
extern "C" {
#endif

// Includes
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<libgen.h>
#include<sys/stat.h>

// Prototypes
void mkusrDir(char *input);
void delFile(char *input);
void writeFile(char *path, char *text);
void appendFile(char *path, char *text);
void readFromFile(struct projListNode *currProject, char *path);
char *pathgen(char *user);
void writeCredentials(char *currUsername, char *currPassword, char *currFullName);
void readCredentials(struct loginInfoList *list);
void readProjects(struct projList *list, char *path);
void writeProjects(struct projList *list, char *path);

#ifdef __cplusplus
}
#endif

#endif
