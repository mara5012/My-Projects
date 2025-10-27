from sqlalchemy import Column, Integer, String, Date, Boolean, ForeignKey
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Book_Card(Base):
    __tablename__ = "Book_Card"

    id = Column(Integer, primary_key=True, autoincrement=True)
    id_nb_card = Column(Integer, ForeignKey("Card.nb_card"), nullable=False)
    id_book = Column(Integer, ForeignKey("Book.id"), nullable=False)
    starting_date = Column(String, nullable=False)
    ending_date = Column(String, nullable=False)
    returned = Column(String, default=False, nullable=False)

    def __repr__(self):
        return (f"<BookClient(id={self.id}, id_nb_card={self.id_nb_card}, id_book={self.id_book}, "
                f"starting_date={self.starting_date}, ending_date={self.ending_date}, returned={self.returned})>")
