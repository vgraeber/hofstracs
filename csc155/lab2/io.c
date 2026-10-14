#include<fcntl.h>
#include<unistd.h>
#include<string.h>
#include<stdio.h>

int print(char *str);
int readPwd(int fd);

int print(char *toPrint) {
  write(STDOUT_FILENO, toPrint, strlen(toPrint));
  return 0;
}

int parsePsswd(char *fc) {
  int numUsrs = 0;
  int amtInfo = 0;
  int curPos = 0;
  char psswdInfo[1000][7][100];
  for (int i = 0; i < strlen(fc); i++) {
    if (fc[i] == '\n') {
      numUsrs += 1;
      amtInfo = 0;
      curPos = 0;
    } else if (fc[i] == ':') {
      amtInfo += 1;
      curPos = 0;
    } else {
      psswdInfo[numUsrs][amtInfo][curPos] = fc[i];
      curPos += 1;
    }
  }
  for (int i = 0; i < numUsrs; i++) {
    print(psswdInfo[i][0]);
    print(":");
    print(psswdInfo[i][6]);
    print("\n");
  }
  return 0;
}

int readPwd(int fd) {
  if (fd > -1) {
    off_t fs = lseek(fd, 0, SEEK_END);
    lseek(fd, 0, SEEK_SET);
    char buffer[fs + 1];
    read(fd, buffer, sizeof(buffer));
    parsePsswd(buffer);
    return 0;
  }
  return 1;
}

int main() {
  int fileDesc = open("/etc/passwd", O_RDONLY | O_CLOEXEC);
  readPwd(fileDesc);
  return 0;
}