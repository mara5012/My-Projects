//
// Created by Mara on 08/04/2024.
//

#include "Service.h"
#include <cstring>
#include <iostream>

Service::Service() {

}

Service::Service(RepoSTL *r) {
    this->r = r;

}

void Service::addApartament(int nr_ap, int suma, const char *tip) {
    Apartament a(nr_ap, suma, tip);
    r->add(a);
}

Service::~Service() {

}

char **Service::getAll() {
    char **rez = new char *[r->getSize()];
    int i=0;
    stack <Apartament> elems1;
    elems1 = r->getAll();
    while(!elems1.empty())
    {
        rez[i] = elems1.top().toString();
        elems1.pop();
        i++;
    }
    return rez;
}

int Service::numberApartaments() {
    return r->getSize();
}

void Service::eliminare_suma(int nr_ap) {
    stack <Apartament> elems;
    stack <Apartament> elems1;
    elems = r->getAll();
    elems1 = r->getAll();
    int cond = false;
    while(cond == false && !elems.empty())
    {
        if(elems.top().getNr_ap() == nr_ap) {
            elems.top().setSuma(0);
            cond = true;
        }
        else
            elems.pop();
    }
    int n = elems1.size();
    if(elems1.size() != elems.size()) {
        while (elems.size() < n) {
            elems.push(elems1.top());
            elems1.pop();
        }
    }
    r->update(elems);
}

void Service::eliminare_tip(const char *tip) {
    stack<Apartament> elems;
    stack<Apartament> elems1;
    stack<Apartament> elems2;
    elems = r->getAll();
    elems1 = r->getAll();
    while(!elems.empty())
    {
        if(strcmp(elems.top().getTip(), tip)==0)
        {
            elems.top().setSuma(0);
            elems2.push(elems.top());
        }
        elems.pop();
    }
    int n=elems1.size();
    if(elems1.size() != elems2.size()){
        while(elems2.size()<n){
           elems = elems2;
           while(!elems.empty())
           {
               if(elems.top().getNr_ap() == elems1.top().getNr_ap() && strcmp(elems.top().getTip(), elems1.top().getTip())==0)
               {
                   elems1.pop();
               }
               else
                   break;
               elems.pop();
           }
           elems2.push(elems1.top());
        }
    }
    r->update(elems2);
}

void Service::inlocuire(int nr_ap, int suma, const char *tip) {
    stack<Apartament> elems;
    stack<Apartament> elems1;
    elems = r->getAll();
    elems1 = r->getAll();
    bool cond = false;
    while(cond == false && !elems.empty())
    {
        if(elems.top().getNr_ap() == nr_ap && strcmp(elems.top().getTip(), tip) == 0) {
            elems.top().setSuma(suma);
            cond = true;
        }
        else
            elems.pop();
    }
    int n=elems1.size();
    if(elems1.size() != elems.size()){
        while(elems.size()<n){
            elems.push(elems1.top());
            elems1.pop();
        }
    }
    r->update(elems);
}

void Service::listare(int a[256][2], int &n) {
    stack<Apartament> elems;
    n=0;
    elems = r->getAll();
    while(!elems.empty())
    {
        a[n][0] = elems.top().getNr_ap();
        a[n][1] = elems.top().getSuma();
        n++;
        elems.pop();
    }
}

void Service::listare_nr_ap(char (*a)[256], int &n, int nr_ap) {
    stack<Apartament> elems;
    n = 0;
    elems = r->getAll();
    while (!elems.empty())
    {
        if(elems.top().getNr_ap() == nr_ap)
        {
            strcpy(a[n], elems.top().getTip());
            strcat(a[n], " ");
            char suma[10];
            sprintf(suma, "%d", elems.top().getSuma());
            strcat(a[n], suma);
            n++;
        }
        elems.pop();
    }
}

void Service::listare_simbol(char (*a)[256], int &n, string simb, int suma) {
    stack<Apartament> elems;
    n = 0;
    elems = r->getAll();
    string sir1 = "<";
    string sir2 = ">";
    string sir3 = "=";
    if(simb == sir1)
    {
        while(!elems.empty())
        {
            if(elems.top().getSuma() < suma)
            {
                char ap[10];
                sprintf(ap, "%d", elems.top().getNr_ap());
                strcpy(a[n], ap);
                strcat(a[n], " ");
                strcat(a[n], elems.top().getTip());
                strcat(a[n], " ");
                char sum[10];
                sprintf(sum, "%d", elems.top().getSuma());
                strcat(a[n], sum);
                n++;
            }
            elems.pop();
        }
    }
    else if(simb == sir2)
    {
        while(!elems.empty())
        {
            if(elems.top().getSuma() > suma)
            {
                char ap[10];
                sprintf(ap, "%d", elems.top().getNr_ap());
                strcpy(a[n], ap);
                strcat(a[n], " ");
                strcat(a[n], elems.top().getTip());
                strcat(a[n], " ");
                char sum[10];
                sprintf(sum, "%d", elems.top().getSuma());
                strcat(a[n], sum);
                n++;
            }
            elems.pop();
        }
    }
    if(simb == sir3)
    {
        while(!elems.empty())
        {
            if(elems.top().getSuma() == suma)
            {
                char ap[10];
                sprintf(ap, "%d", elems.top().getNr_ap());
                strcpy(a[n], ap);
                strcat(a[n], " ");
                strcat(a[n], elems.top().getTip());
                strcat(a[n], " ");
                char sum[10];
                sprintf(sum, "%d", elems.top().getSuma());
                strcat(a[n], sum);
                n++;
            }
            elems.pop();
        }
    }
}

int Service::suma_factura(const char *tip) {
    stack<Apartament> elems;
    elems = r->getAll();
    int suma = 0;
    while(!elems.empty())
    {
        if(strcmp(elems.top().getTip(), tip) == 0)
        {
            suma = suma+ elems.top().getSuma();
        }
        elems.pop();
    }
    return suma;
}

void Service::cheltuiala_max(int nr_ap, int &suma) {
    stack<Apartament> elems;
    elems = r->getAll();
    suma = 0;
    while(!elems.empty())
    {
        if(elems.top().getNr_ap() == nr_ap)
        {
            if(elems.top().getSuma() > suma)
            {
                suma = elems.top().getSuma();
            }
        }
        elems.pop();
    }
}

void Service::filtru_suma(int suma) {
    stack<Apartament> elems;
    stack<Apartament> elems1;
    elems = r->getAll();
    while(!elems.empty())
    {
        if(elems.top().getSuma() < suma)
            elems1.push(elems.top());
        elems.pop();
    }
    r->update(elems1);
}

void Service::filtru_tip(const char *tip) {
    stack<Apartament> elems;
    stack<Apartament> elems1;
    elems = r->getAll();
    while(!elems.empty())
    {
        if(strcmp(elems.top().getTip(), tip) == 0)
            elems1.push(elems.top());
        elems.pop();
    }
    r->update(elems1);
}

stack<Apartament> Service::get_all() {
    return this->r->getAll();
}




