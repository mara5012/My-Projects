from domain.cafele import Cafea
from exceptii.erori import RepoException


class Repository():
    def __init__(self, file_path):
        self.__file_path = file_path
        self.__lista_cafele = []

    def get_all_cafele(self):
        return self.__lista_cafele

    def afisare_cafele(self):
        self.read_from_file()
        return self.get_all_cafele()

    def adauga_cafea(self, cafea):
        for x in self.get_all_cafele():
            if x.id == cafea.id:
                raise RepoException("Exista deja o cafea cu acest id")
            if x.nume == cafea.nume:
                raise RepoException("Exista deja o cafea cu acest nume")
        self.get_all_cafele().append(cafea)
        self.write_in_file()

    def find_next_id(self):
        max = 0
        for cafea in self.get_all_cafele():
            if cafea.id > max:
                max = cafea.id
        return max + 1

    def sortare(self):
        self.afisare_cafele().sort(key=lambda x: (x.tara_de_origine, x.pret))
        self.write_in_file()

    def read_from_file(self):
        try:
            with open(self.__file_path, mode='r') as file:
                self.get_all_cafele().clear()
                all_lines = file.readlines()
                for line in all_lines:
                    line = line.strip()
                    if line != "":
                        parts = line.split(',')
                        parts = [part.strip() for part in parts]
                        id = int(parts[0])
                        nume = parts[1]
                        tara_de_origine = parts[2]
                        pret = float(parts[3])
                        cafea = Cafea(id, nume, tara_de_origine, pret)
                        self.get_all_cafele().append(cafea)
            file.close()
        except EOFError:
            pass
        except FileNotFoundError:
            pass

    def write_in_file(self):
        with open(self.__file_path, mode='w') as file:
            for cafea in self.get_all_cafele():
                elements = [cafea.id, cafea.nume, cafea.tara_de_origine, cafea.pret]
                elements = [str(element) for element in elements]
                line = (',').join(elements) + '\n'
                file.write(line)
        file.close()

