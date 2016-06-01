package recruitment.mappers;

import recruitment.models.Vacancy;

import java.util.List;

/**
 * Created by Nataly on 30.05.2016.
 */
public class VacancyMapper extends BaseMapper implements DataMapper<Vacancy> {
    @Override
    public List<Vacancy> getAll() {
        return null;
    }

    @Override
    public Vacancy getById(long id) {
        return null;
    }

    @Override
    public long save(Vacancy o) {
        return 0;
    }

    @Override
    public void update(Vacancy o) {

    }

    @Override
    public void delete(Vacancy o) {

    }
}
