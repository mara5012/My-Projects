import random
from Domain.Card import Card
from Exceptions.RepositoryExceptions import EntityNotFoundError, DuplicateEntryError
from Repository.CardRepository import CardRepository


class CardService:
    def __init__(self, card_repository: CardRepository):
        self.card_repository = card_repository

    def get_all_cards(self):
        return self.card_repository.get_all()

    def get_card_by_id(self, card_id: int):
        return self.card_repository.get_by_id(card_id)

    def generate_unique_card_number(self):
        existing_ids = {card.nb_card for card in self.get_all_cards()}
        while True:
            new_id = random.randint(1000, 9999)
            if new_id not in existing_ids:
                return new_id

    def add_card(self, card: Card):
        if not card.type:
            raise ValueError("Card type must not be empty.")

        card.late_returns = 0
        card.nb_card = self.generate_unique_card_number()

        self.card_repository.add(card)

    def delete_card(self, card_id: int):
        self.card_repository.delete(card_id)

    def update_card(self, updated_card: Card):
        existing_card = self.card_repository.get_by_id(updated_card.nb_card)

        if updated_card.type:
            existing_card.type = updated_card.type

        if updated_card.late_returns:
            existing_card.returns = updated_card.late_returns

        self.card_repository.update(existing_card)
