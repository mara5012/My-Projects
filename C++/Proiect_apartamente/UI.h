#ifndef UI_H
#define UI_H


#include "Service.h"
#include <string>

class UI {
private:
    Service service;
    void addApartament(int nr_ap, int suma, char tip[]);
    void afisareApartamente();
    void eliminare_suma(int nr_ap);
    void eliminare_tip(const char* tip);
    void inlocuire_suma(int nr_ap, int suma, const char* tip);
    void listare();
    void listare_nr_ap(int nr_ap);
    void listare_simbol(int suma, string simb);
    int suma_factura(const char* tip);
    void cheltuiala_max(int nr_ap, int &suma);
    void filtrare_suma(int suma);
    void filtare_tip(const char* tip);
public:
    UI();
    UI(Service &s);
    void showMenu();
    void comanda();
    ~UI();

};


#endif //UI_H
