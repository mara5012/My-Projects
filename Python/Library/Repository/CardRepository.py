from typing import List

from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import Session

from Domain.Card import Card
from Exceptions.RepositoryExceptions import EntityNotFoundError, RepositoryError, DuplicateEntryError
from Repository.IBaseRepository import IBaseRepository, T


class CardRepository(IBaseRepository):
    def __init__(self, session: Session):
        self.session = session

    def get_all(self):
        return self.session.query(Card).all()

    def get_by_id(self, entity_id: int):
        card = self.session.query(Card).filter(Card.nb_card == entity_id).first()
        if not card:
            raise EntityNotFoundError(f"Card with id {entity_id} not found!")
        return card

    def add(self, entity: Card):
        try:
            self.session.add(entity)
            self.session.commit()
        except IntegrityError:
            self.session.rollback()
            raise DuplicateEntryError(f"Card with id {entity.id} already exists!")

    def delete(self, entity_id:int):
        card = self.get_by_id(entity_id)
        if not card:
            raise EntityNotFoundError(f"Card with id {entity_id} not found!")

        self.session.delete(card)
        self.session.commit()

    def update(self, entity: Card):
        existing = self.get_by_id(entity.id)
        if not existing:
            raise EntityNotFoundError(f"Card with id {entity.id} not found!")

        existing.type = entity.type
        existing.returns = entity.returns

        self.session.commit()
        return existing

