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

char* test(char *temp) {
  return temp;
}

int parsePsswd(char *fc) {
  int numUsrs = 100;
  int amtInfo = 7;
  char *delims = ":\n";
  char psswdInfo[numUsrs][amtInfo][100];
  char *rst = strpbrk(fc, delims);
  int pos = 0;
  for (int i = 0; i < numUsrs; i++) {
    for (int j = 0; j < amtInfo; j++) {
      int infoLen = (int)(rst - fc) - pos;
      strncpy(psswdInfo[i][j], fc + pos, infoLen);
      psswdInfo[i][j][infoLen] = '\0';
      pos += infoLen + 1;
      rst = strpbrk(rst+1, delims);
    }
    if (rst == NULL) {
      numUsrs = i + 1;
      break;
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