class UI:
    def __init__(self, serv):
        self.__serv = serv

    def menu(self):
        print("[1] Afisati toate ceaiurile")
        print("[2] Adaugati un ceai nou")
        print("[3] Salvati ceaiurile in fisier si afisati-le")
        print("[4] Sortati alfabetic dupa tip, iar in caz de egalitate, descrescator dupa pret")
        print("[x] Exit")

    def run(self):
        while True:
            self.menu()
            n = input("Introduceti comanda: ")
            if n == 'x':
                return
            n = int(n)
            if n == 1:
                self.afisare_ceaiuri()
            elif n == 2:
                self.adauga_ceai()
            elif n == 3:
                self.salvare_in_fisier()
            elif n == 4:
                self.sortare()
            else:
                print("Nu ati introdus o comanda valida!")

    def afisare_ceaiuri(self):
        lista_ceaiuri = self.__serv.afisare_ceaiuri()
        for ceai in lista_ceaiuri:
            print(ceai)

    def adauga_ceai(self):
        nume = input("Introduceti un nume: ")
        tip = input("Introduceti un tip: ")
        pret = float(input("Introduceti un pret: "))
        self.__serv.adauga_ceai(nume, tip, pret)

    def salvare_in_fisier(self):
        return self.__serv.salvare_in_fisier()

    def sortare(self):
        return self.__serv.sortare()