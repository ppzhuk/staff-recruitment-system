package recruitment.repository;

import recruitment.models.Person;

import java.util.List;

/**
 * Created by Pavel on 24.05.2016.
 */
public interface BaseRepository {
    void save(Object o);
    List<?> getAll();
    Object getById(int id);
}
