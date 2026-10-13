#include<fcntl.h>
#include<stdio.h>

int readPwd(int fd);
void print(char *str);

int main() {
  int fd = open("/etc/passwd", O_RDONLY | O_CLOEXEC);
  char buffer[100];
  int passwd = read(fd, buffer ,sizeof(buffer))

  size = strlen(buff); size = size + 1;
  write(STDOUT_FILENO,buff,size);
}