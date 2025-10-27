from flask import Blueprint, request, jsonify
from flask_cors import CORS
from Repository.Book_CardRepository import Book_CardRepository
from Repository.BookRepository import BookRepository
from Repository.CardRepository import CardRepository
from Domain.Book_Card import Book_Card
from Service.Book_CardService import Book_CardService
from db_config import get_session


book_card_bp = Blueprint('book_card', __name__)
session = get_session()
# Set up the repositories and service
book_repo = BookRepository(session)
card_repo = CardRepository(session)
book_card_repo = Book_CardRepository(session)
book_card_service = Book_CardService(book_card_repo, book_repo, card_repo)


@book_card_bp.route('/book_cards', methods=['GET'])
def get_all_book_cards():
    records = book_card_service.get_all_book_cards()
    return jsonify([record.__dict__ for record in records])


@book_card_bp.route('/book_cards/<int:book_card_id>', methods=['GET'])
def get_book_card(book_card_id):
    try:
        record = book_card_service.get_book_card_by_id(book_card_id)
        return jsonify(record.__dict__)
    except Exception as e:
        return jsonify({'error': str(e)}), 404


@book_card_bp.route('/book_cards/borrow', methods=['POST'])
def borrow_book():
    data = request.get_json()
    try:
        book_card_service.borrow_book(data['card_id'], data['book_id'])
        return jsonify({'message': 'Book borrowed successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@book_card_bp.route('/book_cards/return/<int:book_card_id>', methods=['PUT'])
def return_book(book_card_id):
    try:
        book_card_service.return_book(book_card_id)
        return jsonify({'message': 'Book returned successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@book_card_bp.route('/book_cards/by_card/<int:card_id>', methods=['GET'])
def borrowed_books_by_card(card_id):
    try:
        records = book_card_service.get_borrowed_books_by_card(card_id)
        return jsonify([r.__dict__ for r in records])
    except Exception as e:
        return jsonify({'error': str(e)}), 404
