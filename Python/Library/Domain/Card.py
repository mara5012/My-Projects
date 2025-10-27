from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Card(Base):
    __tablename__ = "Card"

    nb_card = Column(Integer, primary_key=True)
    type = Column(String, ForeignKey("Card.type"), nullable=False)
    late_returns = Column(Integer, nullable=False)

    def __repr__(self):
        return f"<Card(nb_card={self.nb_card}, type='{self.type}', late_returns={self.late_returns})>"
