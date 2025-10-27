class RepositoryError(Exception):
    """Base class for repository-related exceptions."""
    pass

class EntityNotFoundError(RepositoryError):
    """Raised when an entity is not found in the DB."""
    pass

class DuplicateEntryError(RepositoryError):
    """Raised when attempting to insert a duplicate."""
    pass
