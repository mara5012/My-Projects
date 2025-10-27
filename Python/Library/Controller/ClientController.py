from flask import Blueprint, jsonify, request
from flask_cors import CORS
from Service.ClientService import ClientService
from Repository.ClientRepository import ClientRepository
from Domain.Client import Client
from db_config import get_session


client_bp = Blueprint('client', __name__)
session = get_session()
client_repo = ClientRepository(session)
client_service = ClientService(client_repo)


@client_bp.route('/clients', methods=['GET'])
def get_all_clients():
    clients = client_service.get_all_clients()
    return jsonify([client.__dict__ for client in clients])


@client_bp.route('/clients/<int:client_id>', methods=['GET'])
def get_client_by_id(client_id):
    try:
        client = client_service.get_client_by_id(client_id)
        return jsonify(client.__dict__)
    except Exception as e:
        return jsonify({'error': str(e)}), 404


@client_bp.route('/clients', methods=['POST'])
def create_client():
    try:
        data = request.get_json()
        client = Client(
            lastname=data.get('lastname', ''),
            firstname=data.get('firstname', ''),
            age=data.get('age'),
            nb_card=data.get('nb_card')
        )
        client_service.add_client(client)
        return jsonify({'message': 'Client created successfully', 'client': client.__dict__})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@client_bp.route('/clients/<int:client_id>', methods=['PUT'])
def update_client(client_id):
    try:
        data = request.get_json()
        updated = Client(
            id=client_id,
            lastname=data.get('lastname'),
            firstname=data.get('firstname'),
            age=data.get('age'),
            nb_card=data.get('nb_card')
        )
        client_service.update_client(updated)
        return jsonify({'message': 'Client updated successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@client_bp.route('/clients/<int:client_id>', methods=['DELETE'])
def delete_client(client_id):
    try:
        client_service.delete_client(client_id)
        return jsonify({'message': 'Client deleted successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 404
