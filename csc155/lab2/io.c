#include <fcntl.h>
#include "iolib.h"

int main() {
  int fileDesc = open("/etc/passwd", O_RDONLY | O_CLOEXEC);
  readPwd(fileDesc);
  return 0;
}