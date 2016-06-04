package recruitment.mappers;

import org.sql2o.Connection;
import org.sql2o.data.Row;
import recruitment.models.Vacancy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nataly on 30.05.2016.
 */
public class VacancyMapper extends BaseMapper implements DataMapper<Vacancy> {

    @Override
    public List<Vacancy> getAll() {
        List<Row> vacancies;

        String sql = "SELECT * FROM vacancy";
        try (Connection con = sql2o.open()) {
            vacancies = con.createQuery(sql).executeAndFetchTable().rows();
        }
        return vacancies
                .stream()
                .map(this::formVacancy)
                .collect(Collectors.toList());

    }

    private Vacancy formVacancy(Row v) {
        Vacancy vacancy = new Vacancy(
                v.getInteger("employer_id"),
                v.getString("position"),
                v.getString("requirments"),
                v.getDouble("salary") == null ? 0.0 : v.getDouble("salary")
        );
        vacancy.setId(v.getInteger("id"));
        vacancy.setStatus(
                v.getInteger("status"),
                v.getInteger("applicant_id") == null ? -1 : v.getInteger("applicant_id")
        );
        vacancy.setCloseDate(v.getString("closeDate"));

        return vacancy;
    }

    @Override
    public Vacancy getById(long id) {
        List<Row> vacancies;

        String sql =
                "SELECT * " +
                        "FROM vacancy " +
                        "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            vacancies = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchTable().rows();
        }

        return formVacancy(vacancies.get(0));
    }

    @Override
    public long save(Vacancy vacancy) {
        String sql =
                "INSERT INTO vacancy (employer_id, position, requirments, salary, " +
                        "status, applicant_id, closeDate) VALUES " +
                        "(:employer_id, :position, :requirments, :salary, " +
                        ":status, :applicant_id, :closeDate)";
        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("employer_id", vacancy.getEmployerId())
                    .addParameter("position", vacancy.getPosition())
                    .addParameter("requirments", vacancy.getRequirements())
                    .addParameter("salary", vacancy.getSalary())
                    .addParameter("status", vacancy.getStatus())
                    .addParameter("applicant_id", vacancy.getApplicantId() < 1 ? null : vacancy.getApplicantId())
                    .addParameter("closeDate", vacancy.getCloseDate())
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Vacancy vacancy) {
        String sql =
                "UPDATE vacancy SET employer_id=:employer_id, position=:position, requirments=:requirments, " +
                        "salary=:salary, status=:status, applicant_id=:applicant_id, closeDate=:closeDate" +
                        " WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("employer_id", vacancy.getEmployerId())
                    .addParameter("position", vacancy.getPosition())
                    .addParameter("requirments", vacancy.getRequirements())
                    .addParameter("salary", vacancy.getSalary())
                    .addParameter("status", vacancy.getStatus())
                    .addParameter("applicant_id", vacancy.getApplicantId() < 1 ? null : vacancy.getApplicantId())
                    .addParameter("closeDate", vacancy.getCloseDate())
                    .addParameter("id", vacancy.getId())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Vacancy o) {
        InterviewMapper im = new InterviewMapper();
        im.getAll()
                .stream()
                .filter(i -> i.getVacancyId() == o.getId())
                .forEach(i -> im.clearVacancyId(i.getId()));

        ResumeMapper rm = new ResumeMapper();
        rm.getAll()
                .stream()
                .filter(r -> r.getEmployerVacancyId() == o.getId())
                .forEach(rm::clearVacancy);

        String sql =
                "DELETE FROM vacancy " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", o.getId())
                    .executeUpdate();
        }
    }


    public void deleteAllEmployersVacancies(int employerId) {
        getAll().stream()
                .filter(v -> v.getEmployerId() == employerId)
                .forEach(this::delete);
    }

    public void clearApplicant(Vacancy v) {
        v.setStatus(Vacancy.STATUS_OPEN, -1);
        update(v);
    }
}
