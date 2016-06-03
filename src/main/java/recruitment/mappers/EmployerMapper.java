package recruitment.mappers;

import org.sql2o.Connection;
import org.sql2o.data.Row;
import recruitment.models.Employer;
import recruitment.models.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployerMapper extends BaseMapper implements DataMapper<Employer> {


    @Override
    public List<Employer> getAll() {
        List<Row> employers;
        List<Row> persons;
        
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM employer";
            employers = con.createQuery(sql).executeAndFetchTable().rows();
            sql = "SELECT * FROM person";
            persons = con.createQuery(sql).executeAndFetchTable().rows();
        }
        List<Employer> list = new ArrayList<>();
        for (Row employer: employers) {
            Row person = persons.stream()
                    .filter(p -> p.getInteger("id").equals(employer.getInteger("person_id")))
                    .findFirst().orElse(null);
            list.add(formEmployer(employer, person));
        }
        return list;
    }

    private Employer formEmployer(Row e, Row p) {
        Person prsn = new Person(
                p.getInteger("id"), 
                p.getString("name"), 
                p.getString("email"), 
                p.getString("login"),
                p.getString("password")
        );
        prsn.setPhoneNumber(p.getString("phone_number"));

        Employer empl = new Employer(
                e.getInteger("id"),
                prsn
        );
        empl.setCompanyName(e.getString("company_name"));
        empl.setSite(e.getString("site"));
        empl.setDescription(e.getString("description"));
        return empl;
    }

    @Override
    public Employer getById(long id) {
        List<Row> employers;
        List<Row> persons;
        
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM employer WHERE id = :id";
            employers = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchTable().rows();
            sql = "SELECT * FROM person WHERE id = :person_id";
            persons = con.createQuery(sql)
                    .addParameter("person_id", employers.get(0).getInteger("person_id"))
                    .executeAndFetchTable().rows();
        }
        return formEmployer(employers.get(0), persons.get(0));
    }

    @Override
    public long save(Employer empl) {
        PersonMapper pm = new PersonMapper();
        long person_id = pm.save(empl);
        String sql =
                "INSERT INTO employer (person_id, company_name, description, site) VALUES " +
                        "(:pid, :cn, :d, :s)";
        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("pid", person_id)
                    .addParameter("cn", empl.getCompanyName())
                    .addParameter("d", empl.getDescription())
                    .addParameter("s", empl.getSite())
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Employer empl) {
        PersonMapper pm = new PersonMapper();
        pm.update(empl);
        String sql =
                "UPDATE employer SET company_name=:company_name, description=:description, site=:site" +
                        " WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("company_name", empl.getCompanyName())
                    .addParameter("description", empl.getDescription())
                    .addParameter("site", empl.getSite())
                    .addParameter("id", empl.getEmployerId())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Employer empl) {
        new VacancyMapper().deleteAllEmployersVacancies(empl.getEmployerId());

        String sql =
                "DELETE FROM employer " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", empl.getEmployerId())
                    .executeUpdate();
        }
        PersonMapper pm = new PersonMapper();
        pm.delete(empl);
    }

    public void delete(int id) {
        Person p = new Person("", "", "", "");
        Employer e = new Employer(id, p);
        delete(e);
    }

}
