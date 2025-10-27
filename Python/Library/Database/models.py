from sqlalchemy import Column, Integer, String, Boolean, ForeignKey, Enum as SqlEnum
from sqlalchemy.orm import declarative_base, relationship

from Domain.StateOfBook import StateOfBook

Base = declarative_base()

class Book(Base):
    __tablename__ = 'Book'

    id = Column(Integer, primary_key=True)
    title = Column(String)
    author = Column(String)
    genre = Column(String)
    nb_pag = Column(Integer)
    nb_of_books = Column(Integer)
    nb_of_books_borrowed = Column(Integer)
    state = Column(SqlEnum(StateOfBook))
    nb_of_boorows = Column(Integer)
    summary = Column(String)

    def __repr__(self):
        return f"<Book(id={self.id}, title='{self.title}', author={self.author}, genre={self.genre}, nb_pag={self.nb_pag}, nb_of_books={self.nb_of_books}, nb_of_books_borrowed= {self.nb_of_books_borrowed}, state={self.state}, nb_of_borrows={self.nb_of_boorows}, summary={self.summary})>"

class TypeOfCard(Base):
    __tablename__ = 'TypeOfCard'
    type = Column(String, primary_key=True)
    age_range = Column(String)
    price = Column(Integer)

    def __repr__(self):
        return f"<TypeOfCard(type={self.type}, age_range={self.age_range}, price={self.price})>"

class Card(Base):
    __tablename__ = 'Card'
    nb_card = Column(Integer, primary_key=True)
    type = Column(String, ForeignKey('TypeOfCard.type'))
    late_returns = Column(Integer)

    def __repr__(self):
        return f"<Card(nb_card={self.nb_card}, type={self.type}, returns={self.late_returns})>"


class Client(Base):
    __tablename__ = 'Client'
    id = Column(Integer, primary_key=True)
    lastname = Column(String)
    firstname = Column(String)
    age = Column(Integer)
    nb_card = Column(Integer, ForeignKey('Card.nb_card'))

    def __repr__(self):
        return f"<Client(id={self.id}, lastname={self.lastname}, firstname={self.firstname}, age={self.age}, nb_card={self.nb_card})>"

class Book_Card(Base):
    __tablename__ = 'Book_Card'
    id = Column(Integer, primary_key=True)
    id_nb_card = Column(Integer, ForeignKey('Card.nb_card'))
    id_book = Column(Integer, ForeignKey('Book.id'))
    starting_date = Column(String)
    ending_date = Column(String)
    returned = Column(String)

    def __repr__(self):
        return f"<Book_Client(id={self.id}, id_nb_card={self.id_nb_card}, id_book={self.id_book}, starting_date={self.starting_date}, ending_date={self.ending_date}, returned={self.returned})>"

class User(Base):
    __tablename__ = 'User'
    id = Column(Integer, primary_key=True)
    email = Column(String)
    password = Column(String)

    def __repr__(self):
        return f"<User(id={self.id}, email={self.email}, password={self.password})>"
