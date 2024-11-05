from domain.ceai import Ceai
from exceptii.erori import RepoException


class Repository:
    def __init__(self, file_path):
        self.__file_path = file_path
        self.__lista_ceaiuri = []

    def get_all_ceaiuri(self):
        return self.__lista_ceaiuri

    def afisare_ceaiuri(self):
        self.read_from_file()
        return self.__lista_ceaiuri

    def find_next_id(self):
        max = 0
        self.read_from_file()
        for ceai in self.get_all_ceaiuri():
            if ceai.id > max:
                max = ceai.id
        return max + 1

    def adauga_ceai(self, ceai):
        self.read_from_file()
        for x in self.get_all_ceaiuri():
            if x.id == ceai.id:
                raise RepoException("Exista deja un ceai cu acest id")
            if x.nume == ceai.nume:
                raise RepoException("Exista deja un ceai cu acest nume")
        self.get_all_ceaiuri().append(ceai)
        self.write_in_file()

    def salvare_in_fisier(self):
        self.read_from_file()
        self.write_in_file()

    def sortare(self):
        self.read_from_file()
        self.get_all_ceaiuri().sort(key = lambda x: (x.tip, -x.pret,))
        self.write_in_file()

    def read_from_file(self):
        try:
            with open(self.__file_path, mode='r') as file:
                self.get_all_ceaiuri().clear()
                all_lines = file.readlines()
                for line in all_lines:
                    line = line.strip()
                    if line != "":
                        elements = line.split(',')
                        elements = [element.strip() for element in elements]
                        id = int(elements[0])
                        nume = elements[1]
                        tip = elements[2]
                        pret = float(elements[3])
                        ceai = Ceai(id, nume, tip, pret)
                        self.get_all_ceaiuri().append(ceai)
            file.close()
        except EOFError:
            pass
        except FileNotFoundError:
            pass

    def write_in_file(self):
        with open(self.__file_path, mode='w') as file:
            for ceai in self.get_all_ceaiuri():
                elements = [ceai.id, ceai.nume, ceai.tip, ceai.pret]
                elements = [str(element) for element in elements]
                line = (',').join(elements) + '\n'
                file.write(line)
        file.close()

