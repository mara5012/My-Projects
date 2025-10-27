from flask import Blueprint, jsonify, request
from flask_cors import CORS
from Service.CardService import CardService
from Repository.CardRepository import CardRepository
from Domain.Card import Card
from db_config import get_session

card_bp = Blueprint('card', __name__)
session = get_session()
card_repo = CardRepository(session)
card_service = CardService(card_repo)


@card_bp.route('/cards', methods=['GET'])
def get_all_cards():
    cards = card_service.get_all_cards()
    return jsonify([card.__dict__ for card in cards])


@card_bp.route('/cards/<int:card_id>', methods=['GET'])
def get_card_by_id(card_id):
    try:
        card = card_service.get_card_by_id(card_id)
        return jsonify(card.__dict__)
    except Exception as e:
        return jsonify({'error': str(e)}), 404


@card_bp.route('/cards', methods=['POST'])
def create_card():
    try:
        data = request.get_json()
        new_card = Card(type=data.get('type', ''))
        card_service.add_card(new_card)
        return jsonify({'message': 'Card created successfully', 'card': new_card.__dict__})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@card_bp.route('/cards/<int:card_id>', methods=['PUT'])
def update_card(card_id):
    try:
        data = request.get_json()
        updated_card = Card(nb_card=card_id, type=data.get('type'), late_returns=data.get('late_returns'))
        card_service.update_card(updated_card)
        return jsonify({'message': 'Card updated successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@card_bp.route('/cards/<int:card_id>', methods=['DELETE'])
def delete_card(card_id):
    try:
        card_service.delete_card(card_id)
        return jsonify({'message': 'Card deleted successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 404
