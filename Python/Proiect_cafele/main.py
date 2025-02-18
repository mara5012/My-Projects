from UI.ui import UI
from repository.cafele_repo import Repository
from service.cafele_service import Service
# from teste.teste import VerificareTeste
from validari.validator_cafea import Validator


repo = Repository("D:\\FMI\\Sem 1 anul 1\\Algoritmi si programare\\Laborator\\model_test_cafele\\cafele.txt")
validator = Validator()
serv = Service(repo, validator)
ui = UI(serv)
ui.run()