#include <stdio.h>
#include <unistd.h>
#include <math.h>
#include <bcm2835.h>
#include "I2C.h"
#include "PWM.h"

extern void setAllPWM(int on, int off) {
  write8(__ALL_LED_ON_L, on & 0xFF);
  write8(__ALL_LED_ON_H, on >> 8);
  write8(__ALL_LED_OFF_L, off & 0xFF);
  write8(__ALL_LED_OFF_H, off >> 8);
}

extern void PWMInit(unsigned int address) {
  i2cInit(address);
  setAllPWM(0, 0);
  write8(__MODE2, __OUTDRV);
  write8(__MODE1, __ALLCALL);
  sleep(0.005);
  char mode1 = readU8(__MODE1);
  mode1 = mode1 & ~__SLEEP;
  write8(__MODE1, mode1);
  sleep(0.005);
}

extern void setPWMFreq(int freq) {
  float prescaleval = 25000000.0;
  prescaleval /= 4096.0;
  prescaleval /= (float)freq;
  prescaleval -= 1.0;
  int prescale = floor(prescaleval + 0.5);

  int oldmode = readU8(__MODE1);
  int newmode = (oldmode & 0x7F) | 0x10;
  write8(__MODE1, newmode);
  write8(__PRESCALE, (int)(floor(prescale)));
  write8(__MODE1, oldmode);
  sleep(0.005);
  write8(__MODE1, oldmode | 0x80);
}

extern void setPWM(int channel, int on, int off) {
  write8(__LED0_ON_L+4*channel, on & 0xFF);
  write8(__LED0_ON_H+4*channel, on >> 8);
  write8(__LED0_OFF_L+4*channel, off & 0xFF);
  write8(__LED0_OFF_H+4*channel, off >> 8);
}