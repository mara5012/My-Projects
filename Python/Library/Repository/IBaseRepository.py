from abc import ABC, abstractmethod
from typing import Generic, TypeVar, List, Optional

T = TypeVar('T')

class IBaseRepository(ABC, Generic[T]):
    @abstractmethod
    def get_all(self) -> List[T]:
        pass

    @abstractmethod
    def get_by_id(self, entity_id:int) -> Optional[T]:
        pass

    @abstractmethod
    def add(self, entity: T) -> None:
        pass

    @abstractmethod
    def delete(self, entity_id:int) -> bool:
        pass

    @abstractmethod
    def update(self, entity: T) -> Optional[T]:
        pass