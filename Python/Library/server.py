from flask import Flask
from flask_cors import CORS

from Controller.BookController import book_bp
from Controller.Book_CardController import book_card_bp
from Controller.CardController import card_bp
from Controller.ClientController import client_bp
from Controller.UserController import user_bp


def create_app():
    app = Flask(__name__)
    CORS(app, origins=['http://localhost:3000'])

    # Register your blueprints
    app.register_blueprint(book_bp)
    app.register_blueprint(card_bp)
    app.register_blueprint(book_card_bp)
    app.register_blueprint(client_bp)
    app.register_blueprint(user_bp)

    return app

if __name__ == "__main__":
    app = create_app()
    app.run(debug=True, port=5000)
