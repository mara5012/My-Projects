from typing import List

from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import Session

from Domain.Client import Client
from Exceptions.RepositoryExceptions import EntityNotFoundError, RepositoryError, DuplicateEntryError
from Repository.IBaseRepository import IBaseRepository, T


class ClientRepository(IBaseRepository):
    def __init__(self, session: Session):
        self.session = session

    def get_all(self):
        return self.session.query(Client).all()

    def get_by_id(self, entity_id: int):
        client = self.session.query(Client).filter(Client.id == entity_id).first()
        if not client:
            raise EntityNotFoundError(f"Client with id {entity_id} not found!")
        return client

    def add(self, entity: Client):
        try:
            self.session.add(entity)
            self.session.commit()
        except IntegrityError:
            self.session.rollback()
            raise DuplicateEntryError(f"Client with id {entity.id} already exists!")

    def delete(self, entity_id:int):
        client = self.get_by_id(entity_id)
        if not client:
            raise EntityNotFoundError(f"Client with id {entity_id} not found!")

        self.session.delete(client)
        self.session.commit()

    def update(self, entity: Client):
        existing = self.get_by_id(entity.id)
        if not existing:
            raise EntityNotFoundError(f"Client with id {entity.id} not found!")

        existing.lastname = entity.lastname
        existing.firstname = entity.firstname
        existing.age = entity.age

        self.session.commit()
        return existing

