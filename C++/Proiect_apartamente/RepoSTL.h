#ifndef REPOSTL_H
#define REPOSTL_H
#include<stack>
#include "Apartament.h"
using namespace std;

class RepoSTL {
private:
    stack<Apartament> elems;
public:
    RepoSTL();
    ~RepoSTL();
    void add(Apartament &elem);
    bool find(Apartament &elem);
    bool remove(Apartament &elem);
    void update(stack<Apartament> elems1);
    stack<Apartament> getAll();
    int getSize();
};


#endif //REPOSTL_H
