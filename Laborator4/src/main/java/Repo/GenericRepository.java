package Repo;
import Domain.Obiect;
import Exceptii.EntityAlreadyExistsException;
import Exceptii.EntityNotFoundException;
import Exceptii.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericRepository<T extends Obiect> implements Repository<T>{
    protected Map<Integer, T> baza_de_date = new HashMap<>();

    @Override
    public void add(T entity) {
        if (entity != null) {
            Obiect entitate = (Obiect) entity;
            if (baza_de_date.containsKey(entitate.getId())) {
                throw new EntityAlreadyExistsException(entitate.getId());
            }
            baza_de_date.put(entitate.getId(), (T) entity);
        } else {
            throw new InvalidEntityException("Entitatea trebuie să fie de tip Entitate!");
        }
    }

    @Override
    public T findID(int id) {
        T obiect = baza_de_date.get(id);
        if (obiect == null) {
            throw new EntityNotFoundException(id);
        }
        return obiect;
    }

    @Override
    public List<T> findAll(){
        return new ArrayList<>(baza_de_date.values());
    }

    @Override
    public void update(T entity) {
        if (entity != null) {
            Obiect entitate = (Obiect) entity;
            if (!baza_de_date.containsKey(entitate.getId())) {
                throw new EntityNotFoundException(entitate.getId());
            }
            baza_de_date.put(entitate.getId(), entity);
        } else {
            throw new InvalidEntityException("Entitatea trebuie să fie de tip Entitate!");
        }
    }

    @Override
    public void delete(int id) {
        if (!baza_de_date.containsKey(id)) {
            throw new EntityNotFoundException(id);
        }
        baza_de_date.remove(id);
    }
}
