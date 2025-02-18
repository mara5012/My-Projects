from repository.cafele_repo import Repository
from service.cafele_service import Service
from validari.validator_cafea import Validator


class UI:
    def __init__(self, serv):
        self.__serv = serv

    def menu(self):
        print("[1] Adauga o cafea noua in lista")
        print("[2] Afisati toate cafelele sortate alfabetic")
        print("[3] Filtrati cafelele")
        print("[x] Exit")

    def run(self):
        while True:
            self.menu()
            n = input("Introduceti o comanda: ")
            if n == 'x':
                return
            n = int(n)
            if n == 1:
                self.adauga_cafea()
            elif n == 2:
                self.sortare()
            elif n == 3:
                self.filtrare()
            else:
                print("Comanda introdusa nu este valida!")

    def afisare_cafele(self):
        return self.__serv.afisare_cafele()

    def adauga_cafea(self):
        nume = input("Introduceti un nume: ")
        tara_de_origine = input("Introduceti tara de origine: ")
        pret = float(input("Introduceti un pret: "))
        self.__serv.adauga_cafea(nume, tara_de_origine, pret)
        for x in self.afisare_cafele():
            print(x)

    def sortare(self):
        self.__serv.sortare()
        for x in self.afisare_cafele():
            print(x)

    def filtrare(self):
        tara_de_origine = input("Introduceti tara de origine: ")
        pret = input("Introduceti pretul: ")
        self.__serv.filtrare(tara_de_origine, pret)
        lista_noua = self.__serv.filtrare(tara_de_origine, pret)
        for cafea in lista_noua:
            print(cafea)

repo = Repository("D:\\FMI\\Sem 1 anul 1\\Algoritmi si programare\\Laborator\\model_test_cafele\\cafele.txt")
validator = Validator()
serv = Service(repo, validator)
ui = UI(serv)
ui.run()