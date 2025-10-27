from typing import List

from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import Session

from Domain.Book_Card import Book_Card
from Exceptions.RepositoryExceptions import EntityNotFoundError, RepositoryError, DuplicateEntryError
from Repository.IBaseRepository import IBaseRepository, T


class Book_CardRepository(IBaseRepository):
    def __init__(self, session: Session):
        self.session = session

    def get_all(self):
        return self.session.query(Book_Card).all()

    def get_by_id(self, entity_id: int):
        book_card = self.session.query(Book_Card).filter(Book_Card.id == entity_id).first()
        if not book_card:
            raise EntityNotFoundError(f"Book_Card with id {entity_id} not found!")
        return book_card

    def add(self, entity: Book_Card):
        try:
            self.session.add(entity)
            self.session.commit()
        except IntegrityError:
            self.session.rollback()
            raise DuplicateEntryError(f"Book_Card with id {entity.id} already exists!")

    def delete(self, entity_id:int):
        book_client = self.get_by_id(entity_id)
        if not book_client:
            raise EntityNotFoundError(f"Book_Card with id {entity_id} not found!")

        self.session.delete(book_client)
        self.session.commit()

    def update(self, entity: Book_Card):
        existing = self.get_by_id(entity.id)
        if not existing:
            raise EntityNotFoundError(f"Book_Card with id {entity.id} not found!")

        existing.id_nb_card = entity.id_nb_card
        existing.id_book = entity.id_book
        existing.starting_date = entity.starting_date
        existing.ending_date = entity.ending_date
        existing.returned = entity.returned

        self.session.commit()
        return existing

