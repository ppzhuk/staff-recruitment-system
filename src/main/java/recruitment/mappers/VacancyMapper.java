package recruitment.mappers;

import org.sql2o.Connection;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nataly on 30.05.2016.
 */
public class VacancyMapper extends BaseMapper implements DataMapper<Vacancy> {

    VacancyMapper() {
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("employer_id", "employerId");
        colMaps.put("applicant_id", "applicantId");

        sql2o.setDefaultColumnMappings(colMaps);
    }

    @Override
    public List<Vacancy> getAll() {
        String sql = "SELECT * FROM vacancy";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Vacancy.class);
        }
    }

    @Override
    public Vacancy getById(long id) {
        String sql =
                "SELECT * " +
                        "FROM vacancy " +
                        "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Vacancy.class);
        }
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
