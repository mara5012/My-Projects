from domain.ceai import Ceai


class Service:
    def __init__(self, repo, val):
        self.__repo = repo
        self.__val = val

    def afisare_ceaiuri(self):
        return self.__repo.afisare_ceaiuri()

    def find_next_id(self):
        return self.__repo.find_next_id()

    def adauga_ceai(self, nume, tip, pret):
        id = self.find_next_id()
        ceai = Ceai(id, nume, tip, pret)
        self.__val.validare(pret)
        self.__repo.adauga_ceai(ceai)

    def salvare_in_fisier(self):
        return self.__repo.salvare_in_fisier

    def sortare(self):
        return self.__repo.sortare()