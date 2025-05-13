package at.samegger.dataaccess;

import java.util.List;

public interface DAOInterface<T> {
    T findByID(Integer id);
    List<T> findAll();
    T insert(T o);
    void update(T o);
    void delete(Integer id);

}
