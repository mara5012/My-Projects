from sqlalchemy import Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class User(Base):
    __tablename__ = "User"

    id = Column(Integer, primary_key=True, autoincrement=True)
    email = Column(String, unique=True, nullable=False)
    password = Column(String, nullable=False)

    def __repr__(self):
        return f"<User(id={self.id}, email='{self.email}', password='{self.password}')>"

    def to_dict(self):
        return {
            "id": self.id,
            "email": self.email,
        }