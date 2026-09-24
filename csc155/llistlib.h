#ifndef LLISTLIB_H
#define LLISTLIB_H

struct llnode {
  int data;
  struct llnode *next;
};

typedef struct llnode llnode;

int uinToInt(char*, int*);
int uiForFunc(int (**)(int*, llnode**), int*, llnode**, int);
int f1(int*, llnode**);
int f2(int*, llnode**);
int f3(int*, llnode**);
int f4(int*, llnode**);
int f5(int*, llnode**);
int f6(int*, llnode**);
int f7(int*, llnode**);
int addAtEnd(int, llnode**);
int deleteNode(int, llnode**);
int insertNode(int, int, llnode**);
llnode* search(int, llnode*);
int printNode(int, llnode*);
int printList(llnode*);
int deleteList(llnode*);

#endif