class Ceai:
    def __init__(self, id: int, nume:str, tip:str, pret:float):
        self.__id = id
        self.__nume = nume
        self.__tip = tip
        self.__pret = pret

    @property
    def id(self):
        return self.__id

    @property
    def nume(self):
        return self.__nume

    @property
    def tip(self):
        return self.__tip

    @property
    def pret(self):
        return self.__pret

    @id.setter
    def id(self, new_id):
        self.__id = new_id

    @nume.setter
    def nume(self, new_nume):
        self.__nume = new_nume

    @tip.setter
    def tip(self, new_tip):
        self.__tip = new_tip

    @pret.setter
    def pret(self, new_pret):
        self.__pret = new_pret

    def __str__(self):
        return f"Id ceai: {self.__id}, nume: {self.__nume}, tip: {self.__tip}, pret: {self.__pret}"