package recruitment.repository;

import recruitment.models.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by Pavel on 24.05.2016.
 */
public interface BaseRepository {
    long save(Object o);
    List<?> getAll();
    Object getById(int id);
    default void remove(int id) { throw new NotImplementedException(); }
    default void remove(int id, int type) { throw new NotImplementedException(); }
    void update(Object o);
    default void update(Object o, int type) { throw new NotImplementedException(); };
}
