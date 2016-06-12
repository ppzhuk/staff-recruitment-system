package recruitment.mappers;

import org.sql2o.Connection;
import recruitment.models.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhuk Pavel on 29.05.2016.
 */
public class PersonMapper extends BaseMapper implements DataMapper<Person> {

    public PersonMapper() {
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("phone_number", "phoneNumber");

        sql2o.setDefaultColumnMappings(colMaps);
    }

    @Override
    public List<Person> getAll() {
        String sql = "SELECT * FROM person";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Person.class);
        }
    }

    @Override
    public Person getById(long id) {
        String sql =
                "SELECT * " +
                        "FROM person " +
                        "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Person.class);
        }
    }

    @Override
    public long save(Person p) {
        String sql =
                "INSERT INTO person (name, email, phone_number, login, password) VALUES" +
                        "(:name, :email, :phone_number, :login, :password)";

        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("name", p.getName())
                    .addParameter("email", p.getEmail())
                    .addParameter("phone_number", p.getPhoneNumber())
                    .addParameter("login", p.getLogin())
                    .addParameter("password", p.getPassword())
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Person p) {
        String sql =
                "UPDATE person SET name=:name, email=:email, phone_number=:phone_number, login=:login, password=:password" +
                        " WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", p.getName())
                    .addParameter("email", p.getEmail())
                    .addParameter("phone_number", p.getPhoneNumber())
                    .addParameter("login", p.getLogin())
                    .addParameter("password", p.getPassword())
                    .addParameter("id", p.getPersonId())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Person p) {

        new MarkMapper().deletePersonMarks(p.getPersonId());

        String sql =
                "DELETE FROM person " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", p.getPersonId())
                    .executeUpdate();
        }
    }
    
    public List<Person> getAllNonManagers() {
        String sql = "SELECT p.id, p.name, p.email, p.phone_number, p.login, p.password " +
                "FROM person AS p, employer AS e, applicant AS a " +
                "WHERE p.id = e.person_id OR p.id = a.person_id " +
                "GROUP BY p.id;";
        
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Person.class);
        }
    }
}
