package Repo;

import Domain.Obiect;

import java.util.List;

public interface Repository<T extends Obiect> {
    void add(T entity);
    List<T> findAll();
    T findID(int id);
    void update(T entity);
    void delete(int id);

}

