#ifndef SERVICE_H
#define SERVICE_H


#include "Apartament.h"
#include "RepoSTL.h"
#include <string>

class Service {
private:
    RepoSTL *r;
public:
    Service();
    Service(RepoSTL*r);
    void addApartament(int nr_ap, int suma, const char* tip);
    char** getAll();
    int numberApartaments();
    void eliminare_suma(int nr_ap);
    void eliminare_tip(const char* tip);
    void inlocuire(int nr_ap, int suma, const char* tip);
    void listare(int a[256][2], int &n);
    void listare_nr_ap(char a[256][256], int &n, int nr_ap);
    void listare_simbol(char a[256][256], int &n, string simb, int suma);
    int suma_factura(const char* tip);
    void cheltuiala_max(int nr_ap, int &suma);
    void filtru_suma(int suma);
    void filtru_tip(const char* tip);
    stack <Apartament> get_all();
    ~Service();
};


#endif SERVICE_H
