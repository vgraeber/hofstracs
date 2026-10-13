#include<fcntl.h>
#include<unistd.h>
#include<string.h>

int readPwd(int fd);
void print(char *str);

int main() {
  int fd = open("/etc/passwd", O_RDONLY | O_CLOEXEC);
  char buffer[10000];
  int br = read(fd, buffer, sizeof(buffer));
  write(STDOUT_FILENO, buffer, strlen(buffer));
}