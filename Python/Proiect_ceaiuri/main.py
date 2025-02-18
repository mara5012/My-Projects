from UI.ui import UI
from repository.ceai_repo import Repository
from service.ceai_serv import Service
from validator.ceai_val import Validator

repo = Repository("D:\\FMI\\Sem 1 anul 1\\Algoritmi si programare\\Laborator\\model_test_ceaiuri\\ceai.txt")
val = Validator()
serv = Service(repo, val)
ui = UI(serv)
ui.run()