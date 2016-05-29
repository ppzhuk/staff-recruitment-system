package recruitment.repository;

import recruitment.models.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by Pavel on 24.05.2016.
 */
public interface BaseRepository {
    boolean save(Object o);
    List<?> getAll();
    Object getById(int id);
    default void remove(int id) { throw new NotImplementedException(); }
    default void remove(int id, int type) { throw new NotImplementedException(); }
}
