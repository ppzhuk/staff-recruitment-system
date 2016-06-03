package recruitment.mappers;

import org.sql2o.Connection;
import recruitment.models.Interview;
import recruitment.models.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nataly on 30.05.2016.
 */
public class InterviewMapper extends BaseMapper implements DataMapper<Interview> {

    InterviewMapper() {
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("applicant_id", "applicantId");
        colMaps.put("vacancy_id", "vacancyId");
        colMaps.put("date", "interviewDate");
        colMaps.put("employer_result", "resultEmployer");
        colMaps.put("applicant_result", "resultApplicant");

        sql2o.setDefaultColumnMappings(colMaps);
    }

    @Override
    public List<Interview> getAll() {
        String sql = "SELECT * FROM interview";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Interview.class);
        }
    }

    @Override
    public Interview getById(long id) {
        String sql =
                "SELECT * " +
                        "FROM interview " +
                        "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Interview.class);
        }
    }

    @Override
    public long save(Interview p) {
        String sql =
                "INSERT INTO interview (applicant_id, vacancy_id, date, employer_result, applicant_result) VALUES" +
                        "(:applicant_id, :vacancy_id, :date, :employer_result, :applicant_result)";

        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("applicant_id", p.getApplicantId())
                    .addParameter("vacancy_id", p.getVacancyId())
                    .addParameter("date", p.getInterviewDate())
                    .addParameter("employer_result", p.getResultEmployer())
                    .addParameter("applicant_result", p.getResultApplicant())
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Interview p) {
        //TODO delete if vacId  && applId == null

        String sql =
                "UPDATE interview SET applicant_id=:applicant_id, vacancy_id=:vacancy_id, date=:date, "+
                        "employer_result=:employer_result, applicant_result=:applicant_result" +
                        " WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("applicant_id", p.getApplicantId())
                    .addParameter("vacancy_id", p.getVacancyId())
                    .addParameter("date", p.getInterviewDate())
                    .addParameter("employer_result", p.getResultEmployer())
                    .addParameter("applicant_result", p.getResultApplicant())
                    .addParameter("id", p.getId())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Interview o) {

        String sql =
                "DELETE FROM interview " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", o.getId())
                    .executeUpdate();
        }

    }



    // TODO сопоставить в этом маппере null поля из бд с -1 в классе (см vacancy mapper)
   /* public void clearApplicantId(int applicantId) {
        getAll().stream()
                .filter( i -> i.getApplicantId() == applicantId)
                .forEach( i -> {
                    i.setApplicantId(-1);
                });
    }*/

/*    public void clearVacancyId(int vacancyId) {
        getAll().stream()
                .filter( i -> i.getVacancyId() == vacancyId)
                .forEach( i -> {
                    i.setVacancyId(-1);
                });
    }*/
}
