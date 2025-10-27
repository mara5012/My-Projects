from sqlalchemy import Column, Integer, String, Enum
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Book(Base):
    __tablename__ = "Book"

    id = Column(Integer, primary_key=True, autoincrement=True)
    title = Column(String, nullable=False)
    author = Column(String, nullable=False)
    genre = Column(String, nullable=False)
    nb_pag = Column(Integer, nullable=False)
    nb_of_books = Column(Integer, nullable=False)
    nb_of_books_borrowed = Column(Integer, nullable=False)
    state = Column(String, nullable=False)
    nb_of_borrows = Column(Integer, nullable=False)
    summary = Column(String, nullable=True)
    year = Column(Integer, nullable=False)

    def __repr__(self):
        return (f"<Book(id={self.id}, title='{self.title}', author='{self.author}', "
                f"genre='{self.genre}', pages={self.nb_pag}, total={self.nb_of_books}, "
                f"borrowed={self.nb_of_books_borrowed}, state={self.state}, "
                f"borrows={self.nb_of_borrows}, summary={self.summary}, year={self.year})>")


    def to_dict(self):
        return {
            "id": self.id,
            "title": self.title,
            "author": self.author,
            "genre": self.genre,
            "nb_pag": self.nb_pag,
            "nb_of_books": self.nb_of_books,
            "nb_of_books_borrowed": self.nb_of_books_borrowed,
            "state": self.state,
            "nb_of_borrows": self.nb_of_borrows,
            "summary": self.summary,
            "year": self.year
        }
