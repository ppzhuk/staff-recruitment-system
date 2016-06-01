package recruitment.mappers;

import org.sql2o.Connection;
import org.sql2o.data.Row;
import recruitment.models.Applicant;
import recruitment.models.Person;

import java.util.ArrayList;
import java.util.List;

public class ApplicantMapper extends BaseMapper implements DataMapper<Applicant> {
    @Override
    public List<Applicant> getAll() {
        List<Row> applicants;
        List<Row> persons;

        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM applicant";
            applicants = con.createQuery(sql).executeAndFetchTable().rows();
            sql = "SELECT * FROM person";
            persons = con.createQuery(sql).executeAndFetchTable().rows();
        }
        List<Applicant> list = new ArrayList<>();
        for (Row applicant: applicants) {
            Row person = persons.stream()
                    .filter(p -> p.getInteger("id").equals(applicant.getInteger("person_id")))
                    .findFirst().orElse(null);
            list.add(formApplicant(applicant, person));
        }
        return list;
    }

    private Applicant formApplicant(Row a, Row p) {
        Person prsn = new Person(
                p.getInteger("id"),
                p.getString("name"),
                p.getString("email"),
                p.getString("login"),
                p.getString("password")
        );
        prsn.setPhoneNumber(p.getString("phone_number"));

        return new Applicant(
                a.getInteger("id"),
                prsn
        );
    }

    @Override
    public Applicant getById(long id) {
        List<Row> applicants;
        List<Row> persons;

        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM applicant WHERE id = :id";
            applicants = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchTable().rows();
            sql = "SELECT * FROM person WHERE id = :person_id";
            persons = con.createQuery(sql)
                    .addParameter("person_id", applicants.get(0).getInteger("person_id"))
                    .executeAndFetchTable().rows();
        }
        return formApplicant(applicants.get(0), persons.get(0));
    }

    @Override
    public long save(Applicant prsn) {
        PersonMapper pm = new PersonMapper();
        long person_id = pm.save(prsn);
        String sql =
                "INSERT INTO applicant (person_id) VALUES " +
                        "(:pid)";
        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("pid", person_id)
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Applicant prsn) {
        PersonMapper pm = new PersonMapper();
        pm.update(prsn);
    }

    @Override
    public void delete(Applicant prsn) {
        // TODO delete resume before that
        // + modify interview table (appl_id & vac_id should be nullable)
        
        String sql =
                "DELETE FROM applicant " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", prsn.getApplicantId())
                    .executeUpdate();
        }
        PersonMapper pm = new PersonMapper();
        pm.delete(prsn);
    }
}
