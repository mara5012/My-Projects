class Validator:
    def validare(self, pret):
        e = ''
        if pret <= 0:
            e = e + 'Pretul introdus nu este mai mare ca zero!'
        if len(e) > 0:
            raise ValueError("Eroare" + '\n' + e)
