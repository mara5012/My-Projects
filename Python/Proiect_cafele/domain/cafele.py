class Cafea:
    def __init__(self, id:int, nume:str, tara_de_origine:str, pret:float):
        self.__id = id
        self.__nume = nume
        self.__tara_de_origine = tara_de_origine
        self.__pret = pret

    @property
    def id(self):
        return self.__id

    @property
    def nume(self):
        return self.__nume

    @property
    def tara_de_origine(self):
        return self.__tara_de_origine

    @property
    def pret(self):
        return self.__pret

    @id.setter
    def id(self, new_id):
        self.__id = new_id

    @nume.setter
    def nume(self, new_nume):
        self.__nume = new_nume

    @tara_de_origine.setter
    def tara_de_origine(self, new_tara_de_origine):
        self.__tara_de_origine = new_tara_de_origine

    @pret.setter
    def pret(self, new_pret):
        self.__pret = new_pret

    def __str__(self):
        return f"Id cafea: {self.__id}, nume: {self.__nume}, tara de origine: {self.__tara_de_origine}, pret: {self.__pret}"
