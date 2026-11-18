#include <stdio.h>

int main() {
	int randNum = 1094861568;
	unsigned char *p = (unsigned char*)&randNum;
	char temp;
	for (int i = 0; i < (sizeof(randNum) / 2); i++) {
		int j = sizeof(randNum) - (i + 1);
		temp = p[i];
		p[i] = p[j];
		p[j] = temp;
	}
	printf("p is: %s\n", p);
	printf("n is: %d\n", randNum);
}
