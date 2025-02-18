#include "UI.h"
#include<iostream>
#include<cstring>
#include <cstdlib>
#include <vector>

using namespace std;


void UI::addApartament(int nr_ap, int suma, char tip[]) {
    service.addApartament(nr_ap, suma, tip);
}

UI::UI() {

}

UI::UI(Service &s) {
    this->service = s;
}

void UI::showMenu() {
    cout<<"Adauga o noua cheltuiala de tip:"<<endl;
    cout<<"            adauga nr_ap tip sum"<<endl;;
    cout<<"Modifica o cheltuiala deja existenta de tip:"<<endl;
    cout<<"            eliminare nr_ap"<<endl;;
    cout<<"            eliminare nr_ap la nr_ap"<<endl;;
    cout<<"            eliminare tip"<<endl;;
    cout<<"            inlocuire nr_ap tip suma"<<endl;;
    cout<<"Identificarea cheltuielilor dupa anumite proprietati de tip:"<<endl;
    cout<<"            listeaza"<<endl;;
    cout<<"            listeaza nr_ap"<<endl;;
    cout<<"            listeaza > sum"<<endl;;
    cout<<"            listeaza < sum"<<endl;;
    cout<<"            listeaza = sum"<<endl;;
    cout<<"Obtinerea unor caracteristici ale unor cheltuieli de tip:"<<endl;
    cout<<"            sum tip"<<endl;;
    cout<<"            max nr_ap"<<endl;;
    cout<<"            sorteaza tip"<<endl;;
    cout<<"Filtrari de tip:"<<endl;
    cout<<"            filtru tip"<<endl;;
    cout<<"            filtru sum"<<endl;;
    cout<<"Undo:"<<endl;
    cout<<"            undo"<<endl;;
    cout<<"Exit:"<<endl;
    cout<<"            EXIT"<<endl;;
}

UI::~UI() {

}


void UI::afisareApartamente() {
    char **rez = service.getAll();
    for(int i = 0; i<service.numberApartaments(); i++) {
        cout << rez[i] << endl;
    }
}

void UI::comanda() {
    int c = 1;
    showMenu();
    while(c == 1) {
        int n = 0, nr_ap, suma;
        char s[256], *p, v[256][32], tip[256];
        cin.getline(s, 256);
        if(strcmp(s, "EXIT")==0)
            c = 0;
        p = strtok(s, " ");
        while (p != nullptr) {
            strcpy(v[n], p);
            n++;
            p = strtok(nullptr, " ");
        }
        if (strcmp(v[0], "adauga") == 0) {
            nr_ap = stoi(v[1]);
            strcpy(tip, v[2]);
            suma = stoi(v[3]);
            addApartament(nr_ap, suma, tip);
        }
        else if(strcmp(v[0], "afisare")==0)
            afisareApartamente();
        else if(strcmp(v[0], "eliminare") == 0)
        {
            if(n == 2){
                if(strcmp(v[1], "apa") == 0 || strcmp(v[1], "gaz") == 0 || strcmp(v[1], "electricitate") == 0 || strcmp(v[1], "caldura") == 0 || strcmp(v[1], "altele") == 0)
                {
                    strcpy(tip, v[1]);
                    eliminare_tip(tip);
                }
                else{
                    nr_ap = stoi(v[1]);
                    eliminare_suma(nr_ap);
                }
            }
            else if(n == 4){
                int nr_in = stoi(v[1]);
                int nr_fin = stoi(v[3]);
                for(int i=nr_in; i<=nr_fin; i++)
                    eliminare_suma(i);
            }
        }
        else if(strcmp(v[0], "inlocuire") == 0)
        {
            nr_ap = stoi(v[1]);
            strcpy(tip, v[2]);
            suma = stoi(v[3]);
            inlocuire_suma(nr_ap, suma, tip);
        }
        else if(strcmp(v[0], "listeaza") == 0)
        {
            if(n == 1)
                listare();
            else if(n == 2) {
                nr_ap = stoi(v[1]);
                listare_nr_ap(nr_ap);
            }
            else
            {
                string simb;
                simb = v[1];
                suma = stoi(v[2]);
                listare_simbol(suma, simb);
            }
        }
        else if(strcmp(v[0], "sum") == 0)
        {
            strcpy(tip, v[1]);
            suma = suma_factura(tip);
            cout<<tip<<" "<<suma<<endl;
        }
        else if(strcmp(v[0], "max") == 0)
        {
            nr_ap = stoi(v[1]);
            cheltuiala_max(nr_ap, suma);
            cout<<nr_ap<<" "<<suma<<endl;
        }
        else if(strcmp(v[0], "filtru") == 0)
        {
            if(strcmp(v[1], "apa") == 0 || strcmp(v[1], "gaz") == 0 || strcmp(v[1], "electricitate") == 0 || strcmp(v[1], "caldura") == 0 || strcmp(v[1], "altele") == 0)
            {
                filtare_tip(v[1]);
                afisareApartamente();
            }
            else
            {
                suma = stoi(v[1]);
                filtrare_suma(suma);
                afisareApartamente();
            }
        }
        else if(strcmp(v[0], "undo") == 0)
        {


        }
        else if((strcmp(s, "EXIT")!=0))
            cout<<"Nu exista comanda adaugata!"<<endl;


    }
}

void UI::eliminare_suma(int nr_ap) {
    service.eliminare_suma(nr_ap);
}

void UI::eliminare_tip(const char *tip) {
    service.eliminare_tip(tip);
}

void UI::inlocuire_suma(int nr_ap, int suma, const char *tip) {
    service.inlocuire(nr_ap, suma, tip);
}

void UI::listare() {
    int n;
    int a[256][2];
    service.listare(a, n);
    for(int i=0; i<n; i++)
    {
        cout<<a[i][0]<<" "<<a[i][1]<<'\n';
    }
}

void UI::listare_nr_ap(int nr_ap) {
    int n;
    char a[256][256];
    service.listare_nr_ap(a, n, nr_ap);
    for(int i=0; i<n; i++)
        cout<<a[i]<<endl;
}

void UI::listare_simbol(int suma, string simb) {
    int n = 0;
    char a[256][256];
    service.listare_simbol(a, n, simb, suma);
    for(int i=0; i<n; i++)
        cout<<a[i]<<endl;
}

int UI::suma_factura(const char *tip) {
    return service.suma_factura(tip);
}

void UI::cheltuiala_max(int nr_ap, int &suma) {
    service.cheltuiala_max(nr_ap, suma);
}

void UI::filtrare_suma(int suma) {
    service.filtru_suma(suma);
}

void UI::filtare_tip(const char *tip) {
    service.filtru_tip(tip);
}
