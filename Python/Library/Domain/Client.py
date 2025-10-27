from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Client(Base):
    __tablename__ = "Client"

    id = Column(Integer, primary_key=True, autoincrement=True)
    lastname = Column(String, nullable=False)
    firstname = Column(String, nullable=False)
    age = Column(Integer, nullable=False)
    nb_card = Column(Integer, ForeignKey("Card.nb_card"))

    def __repr__(self):
        return (f"<Client(id={self.id}, lastname='{self.lastname}', firstname='{self.firstname}', "
                f"age={self.age}, nb_card={self.nb_card})>")
