from flask import Flask
from Controller.BookController import book_bp
from Controller.Book_CardController import book_card_bp
from Controller.CardController import card_bp
from Controller.ClientController import client_bp

app = Flask(__name__)

# Register Blueprints
app.register_blueprint(book_bp)
app.register_blueprint(book_card_bp)
app.register_blueprint(card_bp)
app.register_blueprint(client_bp)

if __name__ == '__main__':
    app.run(debug=True, port=5000)
