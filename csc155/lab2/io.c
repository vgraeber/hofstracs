#include<fcntl.h>
#include<unistd.h>
#include<string.h>

int readPwd(int fd);
void print(char *str);

void print(char *toPrint) {
  write(STDOUT_FILENO, toPrint, strlen(toPrint));
}

int readPwd(int fd) {
  char buffer[10000];
  read(fd, buffer, sizeof(buffer));
  print(buffer);
}

int main() {
  int fileDesc = open("/etc/passwd", O_RDONLY | O_CLOEXEC);
  readPwd(fileDesc);
}