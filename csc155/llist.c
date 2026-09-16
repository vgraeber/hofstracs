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
  if (convRes == uin) {
    printf("Please enter a number.\n");
    return 0;
  } else if (*convRes != '\n') {
    printf("Please enter only a number.\n");
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
  printf("LinkedList running. Type 'stop' to stop and exit.\n");
  printf("Seven functions available.\n");
  printf("1. addAtEnd\n2. deleteNode\n3. insertNode\n4. search\n5. printNode\n6. printList\n7. deleteList\n");
  printf("Type the number of a function to start it.\n");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "stop\n") != 0) {
    if (uinToInt(uin, &unum) && ((0 < unum) && (unum < (numFuncs + 1)))) {
      uiForFunc(mainFuncCalls, &lllen, &start, unum);
    }
    fgets(uin, sizeof(uin), stdin);
  }
  return 0;
}

int uiForFunc(int (**mainFuncCalls)(int*, llnode**), int *lllen, llnode **start, int unum) {
  ((int(**)(int*, llnode**))mainFuncCalls)[unum-1](lllen, start);
  return 0;
}

int f1(int *lllen, llnode **start) {
  char uin[100];
  int unum = 0;
  printf("Running addAtEnd. Type 'back' to return to the previous menu.\n");
  printf("Enter the number to add to the list: ");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &unum)) {
      addAtEnd(unum, *start);
      *lllen = *lllen + 1;
    } else {
      printf("Please enter a number.\n");
    }
    printf("Running addAtEnd. Type 'back' to return to the previous menu, or enter the next number to add to the list: ");
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n");
  return 1;
}

int addAtEnd(int newData, llnode *start) {
  llnode *end = malloc(sizeof(llnode));
  end->next = NULL;
  if (start == NULL) {
    start->data = newData;
    start->next = NULL;
  } else {
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
  printf("Running deleteNode. Type 'back' to return to the previous menu.\n");
  printf("Enter the position of the node to delete (positions begin at 1): ");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &upos) && ((-1 < (upos - 1)) && ((upos - 1) < *lllen))) {
      deleteNode(upos-1, start);
      *lllen = *lllen - 1;
    } else {
      printf("Please enter a valid position.\n");
    }
    printf("Running deleteNode. Type 'back' to return to the previous menu, or enter the position of the next node to delete (positions begin at 0): ");
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n");
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
  printf("Running insertNode. Type 'back' to return to the previous menu.\n");
  printf("Enter the position you wish to insert the node at (positions begin at 1): ");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &upos) && ((-1 < (upos - 1)) && ((upos - 1) < *lllen))) {
      printf("Enter the number to add to the list: ");
      fgets(uin, sizeof(uin), stdin);
      int ins = 1;
      while ((ins) && (strcmp(uin, "back\n") != 0)) {
        if (uinToInt(uin, &unum)) {
          fgets(uin, sizeof(uin), stdin);
          uinToInt(uin, &unum)
          insertNode(unum, upos-1, start);
          *lllen = *lllen + 1;
          ins = 0;
        }
      }
    } else {
      printf("Please enter a valid position.\n");
    }
    printf("Running insertNode. Type 'back' to return to the previous menu, or enter the position of the next node to insert (positions begin at 1): ");
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n");
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
  if (i < pos) {
    return -1;
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
  return 0;
}

int f4(int *lllen, llnode **start) {
  char uin[100];
  int unum = 0;
  printf("Running search. Type 'back' to return to the previous menu.\n");
  printf("Enter the number to search for: ");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &unum)) {
      search(unum, *start);
    } else {
      printf("Please enter a number.\n");
    }
    printf("Running search. Type 'back' to return to the previous menu, or enter the next number to search for: ");
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n");
  return 0;
}

llnode* search(int check, llnode *start) {
  llnode *curr = start;
  while ((curr->next != NULL) && (curr->data != check)) {
    curr = curr->next;
  }
  return curr;
}

int f5(int *lllen, llnode **start) {
  char uin[100];
  int upos = 0;
  printf("Running printNode. Type 'back' to return to the previous menu.\n");
  printf("Enter the position of the node to print (positions begin at 1): ");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (uinToInt(uin, &upos) && ((-1 < (upos - 1)) && ((upos - 1) < *lllen)))) {
      printNode(upos-1, *start);
    } else {
      printf("Please enter a valid position.\n");
    }
    printf("Running printNode. Type 'back' to return to the previous menu, or enter the position of the next node to print (positions begin at 1): ");
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n");
  return 0;
}

int printNode(int pos, llnode *start) {
  int i = 0;
  llnode *curr = start;
  while ((i < pos) && (curr->next != NULL)) {
    curr = curr->next;
    i = i + 1;
  }
  if (i < pos) {
    return -1;
  }
  printf("Data: %d\n", curr->data);
  return 0;
}

int f6(int *lllen, llnode **start) {
  char uin[100];
  printf("Running printList. Type 'back' to return to the previous menu.\n");
  printf("Type 'print' to print the list: ");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (strcmp(uin, "print\n") == 0) {
      printList(*start);
    } else {
      printf("Please enter a valid command.\n");
    }
    printf("Running printList. Type 'back' to return to the previous menu, or enter 'print' to print the list again: ");
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n");
  return 0;
}

int printList(llnode *start) {
  llnode *curr = start;
  while (curr->next != NULL) {
    printf("Data: %d\n", curr->data);
    curr = curr->next;
  }
  printf("Data: %d\n", curr->data);
  return 0;
}

int f7(int *lllen, llnode **start) {
  char uin[100];
  printf("Running deleteList. Type 'back' to return to the previous menu.\n");
  printf("Type 'delete' to delete the list: ");
  fgets(uin, sizeof(uin), stdin);
  while (strcmp(uin, "back\n") != 0) {
    if (strcmp(uin, "delete\n") == 0) {
      deleteList(*start);
    } else {
      printf("Please enter a valid command.\n");
    }
    printf("Running deleteList. Type 'back' to return to the previous menu, or enter 'delete' to delete the list again: ");
    fgets(uin, sizeof(uin), stdin);
  }
  printf("Returning to previous menu.\n");
  return 0;
}

int deleteList(llnode *start) {
  llnode *prev = start;
  while (start->next != NULL) {
    start = start->next;
    free(prev);
    prev = start;
  }
  free(prev);
  return 0;
}
