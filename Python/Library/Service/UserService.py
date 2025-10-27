from Domain.User import User
from Repository.UserRepository import UserRepository
from Exceptions.RepositoryExceptions import EntityNotFoundError
from werkzeug.security import check_password_hash



class UserService:
    def __init__(self, user_repository: UserRepository):
        self.user_repository = user_repository

    def get_all_users(self):
        return self.user_repository.get_all()

    def get_user_by_id(self, user_id: int):
        return self.user_repository.get_by_id(user_id)

    def add_user(self, user: User):
        if not user.email:
            raise ValueError("Email cannot be empty.")
        if not user.password or len(user.password) < 6:
            raise ValueError("Password must be at least 6 characters long.")

        self.user_repository.add(user)

    def delete_user(self, user_id: int):
        self.user_repository.delete(user_id)

    def update_user(self, updated_user: User):
        existing_user = self.user_repository.get_by_id(updated_user.id)

        if updated_user.email:
            existing_user.email = updated_user.email
        if updated_user.password:
            if len(updated_user.password) < 6:
                raise ValueError("Password must be at least 6 characters long.")
            existing_user.password = updated_user.password

        self.user_repository.update(existing_user)

    def login_user(self, email, password):
        user = self.user_repository.find_by_email(email)
        if not user or not check_password_hash(user.password, password):
            raise Exception("Invalid email or password")
        return user


