#include "testeApartamente.h"
#include "Apartament.h"
#include <cassert>
#include <cstring>

void testeApartamente::testeAP() {
    Apartament a1(14, 845, "gaz");
    Apartament a2(10, 45, "electricitate");
    Apartament a3(5, 147, "apa");
    Apartament a4(2, 236, "caldura");
    Apartament a5(10, 45, "electricitate");
    assert(a1.getSuma() == 845);
    assert(a2.getNr_ap() == 10);
    assert(strcmp(a3.getTip(), "apa") == 0);
    a4.setNr_ap(27);
    a5.setSuma(784);
    a2.setTip("altele");
    assert(a4.getNr_ap() == 27);
    assert(a5.getSuma() == 784);
    assert(strcmp(a2.getTip(), "altele") == 0);
    a3 = a1;
    assert(a3.getNr_ap() == 14);
    assert(a3.getSuma() == 845);
    assert(strcmp(a3.getTip(), "gaz") == 0);
}
