from Domain.Client import Client
from Repository.ClientRepository import ClientRepository
from Exceptions.RepositoryExceptions import EntityNotFoundError


class ClientService:
    def __init__(self, client_repository: ClientRepository):
        self.client_repository = client_repository

    def get_all_clients(self):
        return self.client_repository.get_all()

    def get_client_by_id(self, client_id: int):
        return self.client_repository.get_by_id(client_id)

    def add_client(self, client: Client):
        if not client.lastname:
            raise ValueError("Last name cannot be left empty.")
        if not client.firstname:
            raise ValueError("First name cannot be left empty.")
        if client.age is None or client.age <= 0:
            raise ValueError("Age must be a positive integer.")
        if client.nb_card is None:
            raise ValueError("Card number must be provided.")

        self.client_repository.add(client)

    def delete_client(self, client_id: int):
        self.client_repository.delete(client_id)

    def update_client(self, updated_client: Client):
        existing_client = self.client_repository.get_by_id(updated_client.id)

        if updated_client.lastname:
            existing_client.lastname = updated_client.lastname
        if updated_client.firstname:
            existing_client.firstname = updated_client.firstname
        if updated_client.age is not None:
            if updated_client.age <= 0:
                raise ValueError("Age must be a positive integer.")
            existing_client.age = updated_client.age
        if updated_client.nb_card is not None:
            existing_client.nb_card = updated_client.nb_card

        self.client_repository.update(existing_client)
