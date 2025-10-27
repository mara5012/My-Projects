from sqlalchemy import Column, String, Integer
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class TypeOfCard(Base):
    __tablename__ = "TypeOfCard"

    type = Column(String, primary_key=True)
    age_range = Column(String, nullable=False)
    price = Column(Integer, nullable=False)

    def __repr__(self):
        return f"<TypeOfCard(type='{self.type}', age_range='{self.age_range}', price={self.price})>"
