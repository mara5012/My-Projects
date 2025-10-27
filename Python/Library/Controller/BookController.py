from flask import Blueprint, request, jsonify
from flask_cors import CORS

from Domain.Book import Book
from Repository.BookRepository import BookRepository
from Service.BookService import BookService
from db_config import get_session

book_bp = Blueprint("book_bp", __name__)
session = get_session()

book_service = BookService(BookRepository(session))

@book_bp.route("/books", methods=["GET"])
def get_all_books():
    books = book_service.get_all_books()
    return jsonify([book.to_dict() for book in books])

@book_bp.route("/books/<int:book_id>", methods=["GET"])
def get_book(book_id):
    try:
        book = book_service.get_book_by_id(book_id)
        return jsonify(book.to_dict())
    except Exception as e:
        return jsonify({"error": str(e)}), 404

@book_bp.route("/books", methods=["POST"])
def add_book():
    try:
        data = request.json
        book = Book(**data)
        book_service.add_book(book)
        return jsonify({"message": "Book added successfully."}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@book_bp.route("/books/<int:book_id>", methods=["PUT"])
def update_book(book_id):
    try:
        data = request.json
        data["id"] = book_id
        updated_book = Book(**data)
        book_service.update_book(updated_book)
        return jsonify({"message": "Book updated successfully."})
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@book_bp.route("/books/<int:book_id>", methods=["DELETE"])
def delete_book(book_id):
    try:
        book_service.delete_book(book_id)
        return jsonify({"message": "Book deleted successfully."})
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@book_bp.route("/books/search", methods=["GET"])
def search_books():
    term = request.args.get("q", "")
    results = book_service.search_book(term)
    return jsonify([book.to_dict() for book in results])

@book_bp.route("/books/top-borrowed", methods=["GET"])
def top_borrowed():
    books = book_service.get_top_10_most_borrowed_books()
    return jsonify([book.to_dict() for book in books])

@book_bp.route("/books/top-by-genre", methods=["GET"])
def top_by_genre():
    genre = request.args.get("genre", "")
    try:
        book = book_service.get_most_borrowed_book_by_genre(genre)
        return jsonify(book.to_dict())
    except Exception as e:
        return jsonify({"error": str(e)}), 404

@book_bp.route("/books/top-by-all-genres", methods=["GET"])
def top_by_all_genres():
    try:
        result = {}
        genres = [
            "Action", "Adventure", "Biography", "Children", "Comedy",
            "Fantasy", "Horror", "Mystery and Crime", "Non-Fiction",
            "Philosophical", "Romance", "Science-Fiction", "Thriller"
        ]
        for genre in genres:
            book = book_service.get_most_borrowed_book_by_genre(genre)
            if book:
                result[genre] = book.to_dict()
        return jsonify(result)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@book_bp.route("/books/search-by-genre", methods=["GET"])
def search_by_genre():
    try:
        genre = request.args.get("genre")
        if not genre:
            return jsonify({"error": "Genre parameter is required"}), 400

        books = book_service.search_genre(genre)
        return jsonify([book.to_dict() for book in books])
    except Exception as e:
        return jsonify({"error": str(e)}), 500
