#include <iostream>
#include "testeApartamente.h"
#include "Apartament.h"
#include "RepoSTL.h"
#include "Service.h"
#include "UI.h"

int main() {
//    testeApartamente::testeAP();
    RepoSTL *r = new RepoSTL();
    Service s(r);
    UI console(s);
    console.comanda();
    delete r;
    return 0;
}
