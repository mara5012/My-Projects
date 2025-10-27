from Domain.Book import Book
from Repository.BookRepository import BookRepository
from Exceptions.RepositoryExceptions import RepositoryError, EntityNotFoundError

class BookService:
    def __init__(self, book_repository: BookRepository):
        self.book_repository = book_repository

    def get_all_books(self):
        return self.book_repository.get_all()

    def get_book_by_id(self, book_id: int):
        return self.book_repository.get_by_id(book_id)

    def add_book(self, book: Book):
        if not book.title:
            raise ValueError("Title cannot be left empty.")
        if not book.author:
            raise ValueError("Author cannot be left empty.")
        if not book.genre:
            raise ValueError("Genre cannot be left empty.")
        if not book.summary:
            raise ValueError("Summary cannot be left empty.")
        if book.nb_pag is None or book.nb_pag <= 0:
            raise ValueError("Number of pages must be greater than 0.")
        if book.nb_of_books is None or book.nb_of_books <= 0:
            raise ValueError("Number of books must be greater than 0.")
        if not book.state:
            raise ValueError("Book state cannot be left empty.")
        if book.year is None:
            raise ValueError("Year cannot be left empty.")

        if book.nb_of_books_borrowed is None:
            book.nb_of_books_borrowed = 0
        elif book.nb_of_books_borrowed > book.nb_of_books:
            raise ValueError("Number of borrowed books cannot exceed total number of books.")

        if book.nb_of_borrows is None:
            book.nb_of_borrows = 0


        self.book_repository.add(book)

    def delete_book(self, book_id: int):
        self.book_repository.delete(book_id)

    def update_book(self, updated_book: Book):
        existing_book = self.book_repository.get_by_id(updated_book.id)

        if updated_book.title:
            existing_book.title = updated_book.title
        if updated_book.author:
            existing_book.author = updated_book.author
        if updated_book.genre:
            existing_book.genre = updated_book.genre
        if updated_book.summary:
            existing_book.summary = updated_book.summary
        if updated_book.state:
            existing_book.state = updated_book.state

        if updated_book.nb_pag is not None:
            if updated_book.nb_pag <= 0:
                raise ValueError("Number of pages must be greater than 0.")
            existing_book.nb_pag = updated_book.nb_pag

        if updated_book.nb_of_books is not None:
            if updated_book.nb_of_books <= 0:
                raise ValueError("Number of books must be greater than 0.")
            existing_book.nb_of_books = updated_book.nb_of_books

        if updated_book.nb_of_books_borrowed is not None:
            if updated_book.nb_of_books is not None:
                max_books = updated_book.nb_of_books
            else:
                max_books = existing_book.nb_of_books

            if updated_book.nb_of_books_borrowed > max_books:
                raise ValueError("Number of borrowed books cannot exceed total number of books.")

            existing_book.nb_of_books_borrowed = updated_book.nb_of_books_borrowed

        if updated_book.nb_of_borrows is not None:
            if updated_book.nb_of_borrows < 0:
                raise ValueError("Number of borrows cannot be negative.")
            existing_book.nb_of_borrows = updated_book.nb_of_borrows

        self.book_repository.update(existing_book)

    def search_book(self, search_term: str):
        search_term = search_term.lower()
        all_books = self.book_repository.get_all()

        results = [
            book for book in all_books
            if search_term in book.title.lower()
               or search_term in book.author.lower()
        ]

        return results

    def search_genre(self, search_term: str):
        search_term = search_term.lower()
        all_books = self.book_repository.get_all()

        results = [
            book for book in all_books
            if search_term in book.genre.lower()
        ]
        return results

    def get_top_10_most_borrowed_books(self):
        books = self.book_repository.get_all()
        sorted_books = sorted(books, key=lambda book: book.nb_of_borrows, reverse=True)
        return sorted_books[:10]

    def get_most_borrowed_book_by_genre(self, genre: str):
        books = self.book_repository.get_all()
        genre_books = [book for book in books if book.genre.lower() == genre.lower()]

        if not genre_books:
            raise ValueError(f"No books found for genre: {genre}")

        most_borrowed = max(genre_books, key=lambda book: book.nb_of_borrows)
        return most_borrowed




