#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include "iolib.h"

int readPwd(int fd);
int parsePsswd(char *fc);
int print(char *str);

int print(char *toPrint) {
  write(STDOUT_FILENO, toPrint, strlen(toPrint));
  return 0;
}

int printPsswd(char psswdInfo[][7][100], int numUsrs) {
  /*
  0 -> username
  1 -> password
  2 -> user id (uid)
  3 -> group id (gid)
  4 -> user id info (gecos)
  5 -> home directory
  6 -> command/shell
  */
  for (int i = 0; i < numUsrs; i++) {
    print(psswdInfo[i][0]);
    print(":");
    print(psswdInfo[i][6]);
    print("\n");
  }
  return 0;
}

int parsePsswd(char *fc) {
  int numUsrs = 0;
  int amtInfo = 0;
  int curPos = 0;
  int isComment = 0;
  char psswdInfo[1000][7][100];
  for (int i = 0; i < strlen(fc); i++) {
    if (fc[i] == '#') {
      isComment = 1;
    } else if (fc[i] == '\n') {
      numUsrs += 1;
      amtInfo = 0;
      curPos = 0;
      isComment = 0;
    } else if (isComment == 0) {
      if (fc[i] == ':') {
        amtInfo += 1;
        curPos = 0;
      } else {
        psswdInfo[numUsrs][amtInfo][curPos] = fc[i];
        curPos += 1;
      }
    }
  }
  printPsswd(psswdInfo, numUsrs);
  return 0;
}

int readPwd(int fd) {
  if (fd > -1) {
    off_t fs = lseek(fd, 0, SEEK_END);
    lseek(fd, 0, SEEK_SET);
    char buffer[fs];
    read(fd, buffer, sizeof(buffer));
    parsePsswd(buffer);
    return 0;
  }
  return 1;
}