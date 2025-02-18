//
// Created by Mara on 08/04/2024.
//

#include "RepoSTL.h"
#include <algorithm>

RepoSTL::RepoSTL() {

}

RepoSTL::~RepoSTL() {

}

void RepoSTL::add(Apartament &elem) {
    this->elems.push(elem);
}

bool RepoSTL::find(Apartament &elem) {
    stack <Apartament> elems1;
    elems1 = elems;
    while(!elems1.empty())
    {
        if(elems1.top() == elem)
            return true;
        elems1.pop();
    }
    return false;
}

bool RepoSTL::remove(Apartament &elem) {
    stack <Apartament> elems1;
    int verif = 0;
    elems1 = elems;
    while(! elems.empty())
    {
        if(elems.top() == elem)
        {
            elems.pop();
            verif = 1;
        }
        elems1.push(elems.top());
    }
    while(! elems1.empty())
    {
        elems.push(elems1.top());
        elems1.pop();
    }
    if(verif == 1) return true;
    return false;
}

stack<Apartament> RepoSTL::getAll() {
    return this->elems;
}

int RepoSTL::getSize(){
    return this->elems.size();
}

void RepoSTL::update(stack<Apartament> elems1) {
    while(!elems.empty())
        elems.pop();
    while(!elems1.empty())
    {
        elems.push(elems1.top());
        elems1.pop();
    }
}
