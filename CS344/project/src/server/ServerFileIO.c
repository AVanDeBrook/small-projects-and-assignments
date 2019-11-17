/*
* Responsible Member(s): Aaron Van De Brook, Joshua Gordon
* This file controls all File IO required for the server
* Functions: mkUsrDir(char *input), delFile(char *input), 
* writeFile(char *path, char *text), appendFile(char *path, 
* char *text), writeCredentials(char *currUsername, char 
* *currPassword, char *currFullName), 
* readCredentials(struct loginInfoList *list), 
* readFromFile(struct projListNode *currProject, char *path), 
* *pathgen(char *user)
*/

#include"ProjectList.h"
#include"ServerIO.h"

//Method to create directories given a path
void mkUsrDir(char *input){
  //Setup
  char buffer[100] = "";
  snprintf(buffer, sizeof(buffer), "mkdir -p %s", input);
  //Linux command execute ex. mkdir ./test/testing.txt
  system(buffer);
}

//Method used to delete file given its path (Will delete recurisvely through entire hierarchy)
void delFile(char *input){
  //Setup
  char buffer[100] = "";
  snprintf(buffer, sizeof(buffer), "rm -r %s", input);
  //Linux command execute ex. rm -r ./test/testing.txt
  system(buffer);
}

//Method to write to a file given a path and message 
void writeFile(char *path, char *text){
  //Set up
  char *dirPath = dirname(path);
  FILE *currFile = fopen(path, "w");

  //Currfile will stay null if the directory does not exist
  if(currFile == NULL){
    //Make the directory
    mkUsrDir(dirPath);

    //Refresh status to write the new file
    currFile = fopen(path, "w");
    fputs(text, currFile);
    //close the writers
    fclose(currFile);
    chmod(path, 0600);
  }//Directory exists write like normal
  else{
    //Write the file
    fputs(text, currFile);
    //Close the writers
    fclose(currFile);
  }
}

//Method to append to a file given a path and message 
void appendFile(char *path, char *text){
  //Set up
  char *dirPath = dirname(path);
  FILE *currFile = fopen(path, "a");

  //Currfile will stay null if the directory does not exist
  if(currFile == NULL){
    //Make the directory
    mkUsrDir(dirPath);

    //Since file didn't exists just write it like normal rite the new file
    currFile = fopen(path, "w");
    fputs(text, currFile);
    fputs("\n", currFile);
    //Close the writers
    fclose(currFile);
  //  chmod(path, 0600);
  }//File exists write like normal
  else{
    //Append the file
    fputs(text, currFile);
    fputs("\n", currFile);
    //Close the writers
    fclose(currFile);
  }
}

//Method used to append credential file
void writeCredentials(char *currUsername, char *currPassword, char *currFullName) {
	FILE *credFile = fopen("./data/credentials.txt", "w");
  fputs(currUsername, credFile);
  fputs("\n", credFile);
  fputs(currPassword, credFile);
  fputs("\n", credFile);
  fputs(currFullName, credFile);
  fputs("\n", credFile);
  fclose(credFile);
}

void readCredentials(struct loginInfoList *list) {
  FILE *credFile = fopen("./data/credentials.txt", "r");
  char username[MAX_USERLEN] = {0}, password[MAX_PASSLEN] = {0}, fullName[MAX_NAMELEN] = {0};
  while(!feof(credFile)) {
    fscanf(credFile, "%s", username);
    fscanf(credFile, "%s", password);
    fscanf(credFile, "%s", fullName);
    appendLoginInfo(username, password, fullName, list);
  }
  fclose(credFile);
}

//Method used to read from a file given a project to be loaded into and the path
void readFromFile(struct projListNode *currProject, char *path){
  //Setup
  FILE *currFile = fopen(path, "r");
  char *temp1;
  struct memberList *membersList = (struct memberList *) malloc(sizeof(struct memberList));
  currProject->members = membersList;
  
  //Initial Reads and populations
  fgets(currProject->projectName, PROJ_MAX_NAME+1, currFile);
  fgets(currProject->projectDesc, PROJ_MAX_DESC+1, currFile);
  fgets(currProject->dateCreated, PROJ_MAX_DATE+1, currFile);
  //Get rid of junk character
  fgetc(currFile);	
  //Next Read
  fgets(currProject->dateDue, PROJ_MAX_DATE+1, currFile);
  //Get rid of junk character
  fgetc(currFile);
  //Final project information reads and populations
  fgets(temp1, 10, currFile);
  currProject->numMembers = atoi(temp1);
  membersList->size = atoi(temp1); // Might not need this, appendMember adds 1 to the list size each time
  
  //Reads members	
  while(!feof(currFile)) {
     fgets(temp1, MAX_NAMELEN+1, currFile);
     appendMember(temp1, currProject->members);
  }

	fclose(currFile);
}

void readProjects(struct projList *list, char *path) {
  printf("path: %s\n", path);
  
  FILE *projectFile = fopen(path, "r");
  char *projectName, *projectDesc, *dateCreated, *dateDue, *numMembers, *temp;
  int i;
  struct memberList *membersList;
  struct projListNode *node;

  while(!feof(projectFile)) {
    membersList = (struct memberList *) malloc(sizeof(struct memberList));
    fgets(projectName, PROJ_MAX_NAME+1, projectFile);
    printf("projectName: %s\n", projectName);
    fgets(projectDesc, PROJ_MAX_DESC+1, projectFile);
    printf("projectDesc: %s\n", projectDesc);
    fgets(dateCreated, PROJ_MAX_DATE+1, projectFile);
    printf("Date created: %s\n", dateCreated);
    fgetc(projectFile);
    fgets(dateDue, PROJ_MAX_DATE+1, projectFile);
    printf("dateDue: %s\n", dateDue);
    fgetc(projectFile);
    fgets(numMembers, 5, projectFile);
    printf("numMembers: %s\n", numMembers);

    appendProject(projectName, projectDesc, dateCreated, dateDue, atoi(numMembers), list);
    node = list->front;
    node->members = membersList;

    for(i = 0; i < node->numMembers; i++) {
      fgets(temp, MAX_NAMELEN, projectFile);
      appendMember(temp, membersList);
    }
  }

  fclose(projectFile);
}

void writeProjects(struct projList *list, char *path) {
  FILE *projectFile = fopen(path, "w");
  int i;
  struct projListNode *node = list->front;
  struct memberListNode *memberNode = node->members->front;

  while(node) {
    fprintf(projectFile, "%s\n", node->projectName);
    fprintf(projectFile, "%s\n", node->projectDesc);
    fprintf(projectFile, "%s\n", node->dateCreated);
    fprintf(projectFile, "%s\n", node->dateDue);
    fprintf(projectFile, "%d\n", node->numMembers);

    for(i = 0; i < node->numMembers; i++) {
      fprintf(projectFile, "%s\n", memberNode->memberName);
      memberNode = memberNode->next;
    }
    node = node->next;
  }
  fclose(projectFile);
}

//Method used to generate the unique storage path given a user
char *pathgen(char *user){
  //Setup 
  char *finalPath = (char *) malloc((sizeof(user)*2)+8);
  //Path generation storing to finalPath
  sprintf(finalPath, "./data/%s.proj", user);
  return finalPath;
}
