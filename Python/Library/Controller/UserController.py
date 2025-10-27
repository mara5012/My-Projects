from flask import Blueprint, jsonify, request
from flask_cors import CORS
from Service.UserService import UserService
from Repository.UserRepository import UserRepository
from Domain.User import User
from db_config import get_session
from werkzeug.security import generate_password_hash

user_bp = Blueprint('user_bp', __name__)
session = get_session()
user_service = UserService(UserRepository(session))



@user_bp.route('/users', methods=['GET'])
def get_all_users():
    users = user_service.get_all_users()
    return jsonify([user.to_dict() for user in users])


@user_bp.route('/users/<int:user_id>', methods=['GET'])
def get_user_by_id(user_id):
    try:
        user = user_service.get_user_by_id(user_id)
        return jsonify(user.__dict__)
    except Exception as e:
        return jsonify({'error': str(e)}), 404

@user_bp.route('/users', methods=['POST'])
def create_user():
    try:
        data = request.get_json()
        hashed_password = generate_password_hash(data.get('password'))  # 🔐

        new_user = User(
            email=data.get('email'),
            password=hashed_password  # ✅ Save hashed password
        )
        user_service.add_user(new_user)
        return jsonify({'message': 'User created successfully', 'user': new_user.to_dict()})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@user_bp.route('/users/<int:user_id>', methods=['PUT'])
def update_user(user_id):
    try:
        data = request.get_json()
        updated_user = User(
            id=user_id,
            email=data.get('email'),
            password=data.get('password')
        )
        user_service.update_user(updated_user)
        return jsonify({'message': 'User updated successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 400


@user_bp.route('/users/<int:user_id>', methods=['DELETE'])
def delete_user(user_id):
    try:
        user_service.delete_user(user_id)
        return jsonify({'message': 'User deleted successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 404


@user_bp.route('/users/login', methods=['POST'])
def login_user():
    try:
        data = request.get_json()
        email = data.get('email')
        password = data.get('password')
        user = user_service.login_user(email, password)
        return jsonify({'message': 'Login successful', 'user': user.to_dict()})
    except Exception as e:
        return jsonify({'error': str(e)}), 401
