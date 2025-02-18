#ifndef APARTAMENT_H
#define APARTAMENT_H


class Apartament {
private:
    int nr_ap;
    int suma;
    char* tip;
public:
    Apartament();
    Apartament(int nr_ap, int suma, const char* tip);
    Apartament(const Apartament &a);
    int getNr_ap();
    int getSuma();
    char* getTip();
    void setNr_ap(int nr_ap);
    void setSuma(int suma);
    void setTip(const char* tip);
    Apartament& operator = (const Apartament &a);
    bool operator == (const Apartament &a);
    char* toString();
    ~Apartament();
};


#endif //APARTAMENT_H
