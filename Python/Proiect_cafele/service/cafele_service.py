from domain.cafele import Cafea
from repository.cafele_repo import Repository


class Service:
    def __init__(self, repo, validator):
        self.__repo = repo
        self.__val = validator

    def afisare_cafele(self):
        return self.__repo.afisare_cafele()

    def find_next_id(self):
        return self.__repo.find_next_id()

    def adauga_cafea(self, nume, tara_de_origine, pret):
        self.afisare_cafele()
        id = self.find_next_id()
        cafea = Cafea(id, nume, tara_de_origine, pret)
        try:
            self.__val.validare(pret)
            self.__repo.adauga_cafea(cafea)
            self.afisare_cafele()
        except:
            print("Eroare bdcshjinvnds")

    def sortare(self):
        self.__repo.sortare()

    def filtrare(self, tara_de_origine, pret):
        lista_filtrata = []
        for cafea in self.afisare_cafele():
            if tara_de_origine == "":
                if cafea.pret <= pret:
                    lista_filtrata.append(cafea)
            elif pret == "":
                if cafea.tara_de_origine == tara_de_origine:
                    lista_filtrata.append(cafea)
            elif cafea.tara_de_origine == tara_de_origine and cafea.pret <= pret:
                lista_filtrata.append(cafea)
        return lista_filtrata



