from typing import List

from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import Session

from Domain.Book import Book
from Exceptions.RepositoryExceptions import EntityNotFoundError, RepositoryError, DuplicateEntryError
from Repository.IBaseRepository import IBaseRepository, T


class BookRepository(IBaseRepository):
    def __init__(self, session: Session):
        self.session = session

    def get_all(self):
        return self.session.query(Book).all()

    def get_by_id(self, entity_id: int):
        book = self.session.query(Book).filter(Book.id == entity_id).first()
        if not book:
            raise EntityNotFoundError(f"Book with id {entity_id} not found!")
        return book

    def add(self, entity: Book):
        try:
            self.session.add(entity)
            self.session.commit()
        except IntegrityError:
            self.session.rollback()
            raise DuplicateEntryError(f"Book with id {entity.id} already exists!")

    def delete(self, entity_id:int):
        book = self.get_by_id(entity_id)
        if not book:
            raise EntityNotFoundError(f"Book with id {entity_id} not found!")

        self.session.delete(book)
        self.session.commit()

    def update(self, entity: Book):
        existing = self.get_by_id(entity.id)
        if not existing:
            raise EntityNotFoundError(f"Book with id {entity.id} not found!")

        existing.title = entity.title
        existing.author = entity.author
        existing.nb_pag = entity.nb_pag
        existing.nb_of_books = entity.nb_of_books
        existing.nb_of_books_borrowed = entity.nb_of_books_borrowed
        existing.state = entity.state
        existing.nb_of_borrows = entity.nb_of_borrows
        existing.summary = entity.summary
        existing.genre = entity.genre
        existing.year = entity.year

        self.session.commit()
        return existing

