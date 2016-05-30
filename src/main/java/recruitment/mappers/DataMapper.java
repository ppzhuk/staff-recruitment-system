package recruitment.mappers;

import java.util.List;

interface DataMapper<T> {
    List<T> getAll();
    T getById(long id);
    long save(T o);
    void update(T o);
    void delete(T o);
}
