from typing import List

from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import Session

from Domain.TypeOfCard import TypeOfCard
from Exceptions.RepositoryExceptions import EntityNotFoundError, RepositoryError, DuplicateEntryError
from Repository.IBaseRepository import IBaseRepository, T


class TypeOfCardRepository(IBaseRepository):
    def __init__(self, session: Session):
        self.session = session

    def get_all(self):
        return self.session.query(TypeOfCard).all()

    def get_by_id(self, entity_id: str):
        typeofcard = self.session.query(TypeOfCard).filter(TypeOfCard.type == entity_id).first()
        if not typeofcard:
            raise EntityNotFoundError(f"TypeOfCard with type {entity_id} not found!")
        return typeofcard

    def add(self, entity: TypeOfCard):
        try:
            self.session.add(entity)
            self.session.commit()
        except IntegrityError:
            self.session.rollback()
            raise DuplicateEntryError(f"TypeOfCard with type {entity.id} already exists!")

    def delete(self, entity_id:str):
        typeofcard = self.get_by_id(entity_id)
        if not typeofcard:
            raise EntityNotFoundError(f"TypeOfCard with type {entity_id} not found!")

        self.session.delete(typeofcard)
        self.session.commit()

    def update(self, entity: TypeOfCard):
        existing = self.get_by_id(entity.type)
        if not existing:
            raise EntityNotFoundError(f"TypeOfCard with type {entity.id} not found!")

        existing.age_range = entity.age_range
        existing.price = entity.price

        self.session.commit()
        return existing

