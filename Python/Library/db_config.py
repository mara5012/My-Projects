from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

# Correct SQLAlchemy SQLite path
DATABASE_URL = "sqlite:///D:/FMI/Proiect personal/Library/Database/LibraryDB.db"

# Create SQLAlchemy engine
engine = create_engine(DATABASE_URL, echo=True)

# Create a configured "Session" class
SessionLocal = sessionmaker(bind=engine)

# Dependency to get a new session
def get_session():
    return SessionLocal()
