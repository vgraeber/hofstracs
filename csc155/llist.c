#include <stdio.h>
#include <string.h>
#include "llistlib.h"

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
    if (uinToInt(uin, &unum)) {
      if ((0 < unum) && (unum < (numFuncs + 1))) {
        uiForFunc(mainFuncCalls, &lllen, &start, unum);
        printf("LinkedList running.\nType 'stop' to stop and exit.\nSeven functions available.\n");
        printf("1. addAtEnd\n2. deleteNode\n3. insertNode\n4. search\n5. printNode\n6. printList\n7. deleteList\n");
        printf("Type the number of a function to start it.\n");
      } else {
        printf("\nPlease enter a valid number.\n");
      }
    }
    fgets(uin, sizeof(uin), stdin);
  }
  deleteList(start);
  return 1;
}