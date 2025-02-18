class Validator:
    def validare(self, pret):
        e = ''
        if pret <= 0:
            e += 'Pretul nu este mai mare ca 0'
        if len(e) > 0:
            raise ValueError("EROARE" + '\n' + e)