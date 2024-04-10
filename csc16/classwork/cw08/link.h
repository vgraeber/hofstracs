#ifndef LINK_H
#define LINK_H

typedef int dtype;

class Link {
  dtype data;
  Link *next;
  public:
    void setdata(dtype data) {
      this -> data = data;
    }
    void setnext(Link *next) {
      this -> next = next;
    }
    dtype getdata() {
      return data;
    }
    Link *getnext() {
      return next;
    }
    Link(dtype newdata, Link *newnext) {
      data = newdata;
      next = newnext;
    }
};

#endif
