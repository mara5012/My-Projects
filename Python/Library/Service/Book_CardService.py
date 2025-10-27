from datetime import date, timedelta, datetime

from Domain.Book_Card import Book_Card
from Repository.BookRepository import BookRepository
from Repository.Book_CardRepository import Book_CardRepository
from Repository.CardRepository import CardRepository


class Book_CardService:
    def __init__(self, book_card_repository: Book_CardRepository, book_repository: BookRepository, card_repository: CardRepository):
        self.book_repository = book_repository
        self.book_card_repository = book_card_repository
        self.card_repository = card_repository

    def get_all_book_cards(self):
        return self.book_card_repository.get_all()

    def get_book_card_by_id(self, book_card_id: int):
        return self.book_card_repository.get_by_id(book_card_id)

    def add_book_card(self, book_card: Book_Card):
        if not book_card.id_nb_card:
            raise ValueError("Card ID must not be empty.")
        if not book_card.id_book:
            raise ValueError("Book ID must not be empty.")
        if book_card.returned not in ['YES', 'NO']:
            raise ValueError("Returned must be a between YES and NO.")

        today = date.today()
        end_date = today + timedelta(days=14)
        book_card.starting_date = today.strftime("%d/%m/%Y")
        book_card.ending_date = end_date.strftime("%d/%m/%Y")

        self.book_card_repository.add(book_card)

    def delete_book_card(self, book_card_id: int):
        self.book_card_repository.delete(book_card_id)

    def update_book_card(self, updated: Book_Card):
        existing = self.book_card_repository.get_by_id(updated.id)

        if updated.returned is not None:
            if updated.returned not in ['YES', 'NO']:
                raise ValueError("Returned must be YES or NO.")
            existing.returned = updated.returned

        self.book_card_repository.update(existing)

    def borrow_book(self, card_id: int, book_id: int):
        existing_borrowed = [
            bc for bc in self.book_card_repository.get_all()
            if bc.id_nb_card == card_id and bc.returned == "NO"
        ]

        if existing_borrowed:
            raise PermissionError("You can only borrow one book at a time. Please return the current one first.")

        self.can_borrow(card_id)

        from datetime import datetime, timedelta

        today = datetime.today().date()
        end_date = today + timedelta(days=14)

        book = self.book_repository.get_by_id(book_id)

        if book.nb_of_books_borrowed <= 0:
            raise ValueError("No available copies to borrow.")

        book.nb_of_books_borrowed -= 1
        book.nb_of_borrows += 1
        if book.nb_of_books_borrowed == 0:
            book.state = "BORROWED"
        self.book_repository.update(book)

        new_borrow = Book_Card(
            id_nb_card=card_id,
            id_book=book_id,
            starting_date=today.strftime("%d/%m/%Y"),
            ending_date=end_date.strftime("%d/%m/%Y"),
            returned="NO"
        )
        self.book_card_repository.add(new_borrow)

    def return_book(self, book_card_id: int):
        book_card = self.book_card_repository.get_by_id(book_card_id)

        if book_card.returned == "YES":
            raise ValueError("This book has already been returned.")

        book_card.returned = "YES"

        ending_date = datetime.strptime(book_card.ending_date, "%d/%m/%Y").date()
        today = datetime.today().date()

        if today > ending_date:
            card = self.card_repository.get_by_id(book_card.id_nb_card)
            card.late_returns += 1
            self.card_repository.update(card)

        book = self.book_repository.get_by_id(book_card.id_book)
        book.nb_of_books_borrowed -= 1

        if book.nb_of_books_borrowed > 0 and book.state == "BORROWED":
            book.state = "NOT BORROWED"

        self.book_card_repository.update(book_card)
        self.book_repository.update(book)

    def can_borrow(self, card_id: int):
        card = self.card_repository.get_by_id(card_id)

        if card.late_returns >= 3:
            raise PermissionError("You cannot borrow books due to multiple late returns.")

        borrowed_books = self.book_card_repository.get_all()
        for bc in borrowed_books:
            if bc.id_nb_card == card_id and bc.returned == "NO":
                book = self.book_repository.get_by_id(bc.id_book)
                if book.state == "LOST":
                    raise PermissionError("You cannot borrow books due to a lost book.")
        return True

    def get_borrowed_books_by_card(self, card_id: int):
        all_records = [
            bc for bc in self.book_card_repository.get_all()
            if bc.id_nb_card == card_id
        ]

        if not all_records:
            raise ValueError(f"No borrowed books found for card ID {card_id}.")

        sorted_records = sorted(
            all_records,
            key=lambda bc: datetime.strptime(bc.starting_date, "%d/%m/%Y")
        )

        return sorted_records
