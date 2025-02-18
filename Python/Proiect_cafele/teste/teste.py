# import unittest
#
# from repository.cafele_repo import Repository
# from service.cafele_service import Service
# from validari.validator_cafea import Validator
#
#
# class VerificareTeste(unittest.TestCase):
#     def setUp(self):
#         self.__repo = Repository("D:\\FMI\\Sem 1 anul 1\\Algoritmi si programare\\Laborator\\model_test_cafele\\teste\\teste.txt")
#         self.__val = Validator()
#         self.__serv = Service(self.__repo, self.__val)
#
#     def verificare_test_adauga(self):
#         self.__serv.afisare_cafele()
#         l = len(self.__serv.afisare_cafele())
#         nume = "Caffe"
#         tara_de_origine = "Brazilia"
#         pret = 50
#         self.__serv.adauga_cafea(nume, tara_de_origine, pret)
#         self.assertEqual(len(self.__serv.afisare_cafele()), l+1)
#
#         # cafea = Cafea(7, "Nestle", "Brazilia", 50)
#         # l = len(self.__serv.afisare_cafele())
#         # self.__serv.adauga_cafea(cafea)
#         # self.assertEqual(len(self.__serv.afisare_cafele()), l + 2)
#
#     def tearDown(self):
#         pass
#
#
# if __name__ == '__main__':
#     unittest.main()
#
#
# teste = VerificareTeste()
# teste.verificare_test_adauga()