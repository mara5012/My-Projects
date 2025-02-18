#include "Apartament.h"
#include <cstring>
#include <cstdlib>

Apartament::Apartament() {
     nr_ap = 0;
     suma = 0;
     tip = nullptr;

}

Apartament::Apartament(int nr_ap, int suma, const char *tip) {
    this->nr_ap = nr_ap;
    this->suma = suma;
    this->tip = new char[strlen(tip)+1];
    strcpy(this->tip, tip);
}

Apartament::Apartament(const Apartament &a) {
    this->nr_ap = a.nr_ap;
    this->suma = a.suma;
    if(a.tip)
    {
        this->tip = new char[strlen(a.tip)+1];
        strcpy(this->tip, a.tip);
    }
}

int Apartament::getNr_ap() {
    return nr_ap;
}

int Apartament::getSuma() {
    return suma;
}

char *Apartament::getTip() {
    return tip;
}

void Apartament::setNr_ap(int nr_ap) {
    this->nr_ap = nr_ap;
}

void Apartament::setSuma(int suma) {
    this->suma = suma;
}

void Apartament::setTip(const char *tip) {
    if(this->tip)
        delete[] tip;
    this->tip = new char[strlen(tip)+1];
    strcpy(this->tip, tip);
}

Apartament &Apartament::operator=(const Apartament &a) {
    if(this != &a)
    {
        this->nr_ap = a.nr_ap;
        this->suma = a.suma;
        if(this->tip)
            delete[] tip;
        this->tip = new char[strlen(tip)+1];
        strcpy(this->tip, tip);
    }
    return *this;
}

bool Apartament::operator==(const Apartament &a) {
    if(strcmp(this->tip, a.tip) == 0 && this->nr_ap == a.nr_ap && this->suma == a.suma)
        return true;
    return false;
}

char *Apartament::toString() {
    char *rez = new char[strlen(tip)+6];
    char *nr = new char[2];
    itoa(nr_ap, nr, 10);
    strcpy(rez, nr);
    strcat(rez, " ");
    char *sum = new char[4];
    itoa(suma, sum, 10);
    strcat(rez, sum);
    strcat(rez, " ");
    strcat(rez, tip);
    delete [] nr;
    delete [] sum;
    return rez;
}

Apartament::~Apartament() {
    this->nr_ap = 0;
    this->suma = 0;
    if(this->tip)
        delete[] tip;
}
