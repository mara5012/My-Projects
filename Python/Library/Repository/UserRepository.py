from typing import List

from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import Session

from Domain.User import User
from Exceptions.RepositoryExceptions import EntityNotFoundError, RepositoryError, DuplicateEntryError
from Repository.IBaseRepository import IBaseRepository, T


class UserRepository(IBaseRepository):
    def __init__(self, session: Session):
        self.session = session

    def get_all(self):
        return self.session.query(User).all()

    def get_by_id(self, entity_id: int):
        user = self.session.query(User).filter(User.id == entity_id).first()
        if not user:
            raise EntityNotFoundError(f"User with id {entity_id} not found!")
        return user

    def add(self, entity: User):
        try:
            self.session.add(entity)
            self.session.commit()
        except IntegrityError:
            self.session.rollback()
            raise DuplicateEntryError(f"User with id {entity.id} already exists!")

    def delete(self, entity_id:int):
        user = self.get_by_id(entity_id)
        if not user:
            raise EntityNotFoundError(f"User with id {entity_id} not found!")

        self.session.delete(user)
        self.session.commit()

    def update(self, entity: User):
        existing = self.get_by_id(entity.id)
        if not existing:
            raise EntityNotFoundError(f"User with id {entity.id} not found!")

        existing.email = entity.email
        existing.password = entity.password

        self.session.commit()
        return existing

    def find_by_email(self, email: str):
        return self.session.query(User).filter_by(email=email).first()

