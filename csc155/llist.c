#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct llnode {
  int data;
  struct llnode *next;
};

typedef struct llnode llnode;

int f1(int*, llnode**);
int f2(int*, llnode**);
int f3(int*, llnode**);
int f4(int*, llnode**);
int f5(int*, llnode**);
int f6(int*, llnode**);
int f7(int*, llnode**);
int addAtEnd(int, llnode*);
int deleteNode(int, llnode**);
int insertNode(int, int, llnode**);
llnode* search(int, llnode*);
int printNode(int, llnode*);
int printList(llnode*);
int deleteList(llnode*);
int uiForFunc(int (**)(int*, llnode**), int*, llnode**, int);

int uinToInt(char *uin, int *unum) {
  int base = 10;
  char *convRes = "";
  *unum = (int)strtol(uin, &convRes, base);
  if ((convRes == uin) && (*convRes != '\n')) {
    printf("\nPlease enter a number.\n");
    return 0;
  }
  return 1;
}

int main() {
  llnode *start = NULL;
  int lllen = 0;
  char uin[100];
  int unum = 0;
  int numFuncs = 7;
  int (*mainFuncCalls[numFuncs])(int*, llnode**);
  mainFuncCalls[0] = f1;
  mainFuncCalls[1] = f2;
  mainFuncCalls[2] = f3;
  mainFuncCalls[3] = f4;
  mainFuncCalls[4] = f5;
  mainFuncCalls[5] = f6;
  mainFuncCalls[6] = f7;
  printf("Running LinkedList.\nType 'stop' to stop and exit.\nSeven functions available.\n");
  printf("1. addAtEnd\n2. deleteNode\n3. insertNode\n4. search\n5. printNode\n6. printList\n7. deleteList\n");
  printf("Type the number of a function to start it.\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "stop\n") != 0) {
    if (uinToInt(uin, &unum) && ((0 < unum) && (unum < (numFuncs + 1)))) {
      uiForFunc(mainFuncCalls, &lllen, &start, unum);
      printf("LinkedList running.\nType 'stop' to stop and exit.\nSeven functions available.\n");
      printf("1. addAtEnd\n2. deleteNode\n3. insertNode\n4. search\n5. printNode\n6. printList\n7. deleteList\n");
      printf("Type the number of a function to start it.\n");
    }
    fgets(uin, sizeof(uin), stdin);
  }
  return 1;
}

int uiForFunc(int (**mainFuncCalls)(int*, llnode**), int *lllen, llnode **start, int unum) {
  ((int(**)(int*, llnode**))mainFuncCalls)[unum-1](lllen, start);
  return 1;
}

int f1(int *lllen, llnode **start) {
  char uin[100];
  int unum = 0;
  printf("\nRunning addAtEnd.\nType 'back' to return to the previous menu.\nEnter the number to add to the list:\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &unum)) {
      addAtEnd(unum, *start);
      *lllen = *lllen + 1;
      printf("\nRunning addAtEnd.\nType 'back' to return to the previous menu, or enter the next number to add to the list:\n");
    }
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n\n");
  return 1;
}

int addAtEnd(int newData, llnode *start) {
  if (start == NULL) {
    start= malloc(sizeof(llnode));
    start->data = newData;
    start->next = NULL;
  } else {
    llnode *end = malloc(sizeof(llnode));
    end->next = NULL;
    end->data = newData;
    llnode *curr = start;
    while(curr->next != NULL) {
      curr = curr->next;
    }
    curr->next = end;
  }
  return 1;
}

int f2(int *lllen, llnode **start) {
  char uin[100];
  int upos = 0;
  printf("\nRunning deleteNode.\nType 'back' to return to the previous menu.\nEnter the position of the node to delete (positions begin at 1):\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &upos)) {
      if ((-1 < (upos - 1)) && ((upos - 1) < *lllen)) {
        deleteNode(upos-1, start);
        *lllen = *lllen - 1;
        printf("\nRunning deleteNode.\nType 'back' to return to the previous menu, or enter the position of the next node to delete (positions begin at 1):\n");
      } else {
        printf("\nPlease enter a valid position.\n");
      }
    }
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n\n");
  return 1;
}

int deleteNode(int pos, llnode **start) {
  int i = 0;
  llnode *curr = *start;
  llnode *prev;
  while ((i < pos) && (curr->next != NULL)) {
    prev = curr;
    curr = curr->next;
    i = i + 1;
  }
  if (pos == 0) {
    *start = (*start)->next;
  } else {
    prev->next = curr->next;
  }
  free(curr);
  return 1;
}

int f3(int *lllen, llnode **start) {
  char uin[100];
  int upos = 0;
  int unum = 0;
  printf("\nRunning insertNode.\nType 'back' to return to the previous menu.\nEnter the position you wish to insert the node at (positions begin at 1):\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &upos)) {
      if ((-1 < (upos - 1)) && ((upos - 1) < *lllen)) {
        printf("\nEnter the number to add to the list:\n");
        fgets(uin, sizeof(uin), stdin);
        int ins = 1;
        while ((ins) && (strcmp(uin, "back\n") != 0)) {
          if (uinToInt(uin, &unum)) {
            insertNode(unum, upos-1, start);
            *lllen = *lllen + 1;
            ins = 0;
            printf("\nRunning insertNode.\nType 'back' to return to the previous menu, or enter the position of the next node to insert (positions begin at 1):\n");
          } else {
            fgets(uin, sizeof(uin), stdin);
          }
        }
      } else {
        printf("\nPlease enter a valid position.\n");
      }
    }
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n\n");
  return 1;
}

int insertNode(int newData, int pos, llnode **start) {
  int i = 0;
  llnode *curr = *start;
  llnode *prev;
  while ((i < pos) && (curr->next != NULL)) {
    prev = curr;
    curr = curr->next;
    i = i + 1;
  }
  llnode *newNode = malloc(sizeof(llnode));
  newNode->data = newData;
  if (pos == 0) {
    newNode->next = curr;
    *start = newNode;
  } else {
    prev->next = newNode;
    newNode->next = curr;
  }
  return 1;
}

int f4(int *lllen, llnode **start) {
  char uin[100];
  int unum = 0;
  printf("\nRunning search.\nType 'back' to return to the previous menu.\nEnter the number to search for:\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &unum)) {
      llnode* temp = search(unum, *start);
      if (temp != NULL) {
        printf("Node found. Data: %d\n", temp->data);
      } else {
        printf("No node with that data found.");
      }
      printf("\n\nRunning search.\nType 'back' to return to the previous menu, or enter the next number to search for:\n");
    }
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n\n");
  return 1;
}

llnode* search(int check, llnode *start) {
  llnode *curr = start;
  while ((curr != NULL) && (curr->data != check)) {
    curr = curr->next;
  }
  return curr;
}

int f5(int *lllen, llnode **start) {
  char uin[100];
  int upos = 0;
  printf("\nRunning printNode.\nType 'back' to return to the previous menu.\nEnter the position of the node to print (positions begin at 1):\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &upos)) {
      if ((-1 < (upos - 1)) && ((upos - 1) < *lllen)) {
        printNode(upos-1, *start);
        printf("\nRunning printNode.\nType 'back' to return to the previous menu, or enter the position of the next node to print (positions begin at 1):\n");
      } else {
        printf("\nPlease enter a valid position.\n");
      }
    }
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n\n");
  return 1;
}

int printNode(int pos, llnode *start) {
  int i = 0;
  llnode *curr = start;
  while ((i < pos) && (curr->next != NULL)) {
    curr = curr->next;
    i = i + 1;
  }
  printf("Data: %d\n", curr->data);
  return 1;
}

int f6(int *lllen, llnode **start) {
  char uin[100];
  printf("\nRunning printList.\nType 'back' to return to the previous menu.\nType 'print' to print the list:\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (strcmp(uin, "print\n") == 0) {
      printList(*start);
      printf("List printed.\n\nRunning printList.\nType 'back' to return to the previous menu, or enter 'print' to print the list again:\n");
    } else {
      printf("\nPlease enter a valid command.\n");
    }
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n\n");
  return 1;
}

int printList(llnode *start) {
  llnode *curr = start;
  while (curr != NULL) {
    printf("Data: %d\n", curr->data);
    curr = curr->next;
  }
  return 1;
}

int f7(int *lllen, llnode **start) {
  char uin[100];
  printf("\nRunning deleteList.\nType 'back' to return to the previous menu.\nType 'delete' to delete the list:\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (strcmp(uin, "delete\n") == 0) {
      deleteList(*start);
      printf("\nList deleted. Running deleteList.\nType 'back' to return to the previous menu, or enter 'delete' to delete the list again:\n");
    } else {
      printf("\nPlease enter a valid command.\n");
    }
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n\n");
  return 1;
}

int deleteList(llnode *start) {
  llnode *prev = start;
  while ((start != NULL) && (start->next != NULL)) {
    start = start->next;
    free(prev);
    prev = start;
  }
  free(prev);
  return 1;
}
