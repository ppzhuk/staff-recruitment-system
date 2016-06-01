package recruitment.mappers;

import org.sql2o.Connection;
import recruitment.models.Mark;
import recruitment.models.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nataly on 30.05.2016.
 */
public class ResumeMapper extends BaseMapper implements DataMapper<Resume> {

    ResumeMapper() {
        Map<String, String> colMaps = new HashMap<>();
        colMaps.put("applicant_id", "applicantId");
        colMaps.put("in_search", "inSearch");
        colMaps.put("vacancy_id", "employerVacancyId");

        sql2o.setDefaultColumnMappings(colMaps);
    }

    @Override
    public List<Resume> getAll() {
        String sql = "SELECT * FROM resume";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Resume.class);
        }
    }

    @Override
    public Resume getById(long id) {
        String sql =
                "SELECT * " +
                        "FROM resume " +
                        "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Resume.class);
        }
    }

    @Override
    public long save(Resume resume) {
        String sql =
                "INSERT INTO resume (applicant_id, experience, skills, education, description, in_search, vacancy_id) VALUES" +
                        "(:applicant_id, :experience, :skills, :education, :description, :in_search, :vacancy_id)";

        try (Connection con = sql2o.open()) {
            return (Long) con.createQuery(sql, true)
                    .addParameter("applicant_id", resume.getApplicantId())
                    .addParameter("experience", resume.getExperience())
                    .addParameter("skills", resume.getSkills())
                    .addParameter("education", resume.getEducation())
                    .addParameter("description", resume.getDescription())
                    .addParameter("in_search", resume.isInSearch())
                    .addParameter("vacancy_id", resume.getEmployerVacancyId())
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public void update(Resume resume) {
        String sql =
                "UPDATE resume SET applicant_id=:applicant_id, experience=:experience, skills=:skills, education=:education, " +
                       "description=:description, in_search=:in_search, vacancy_id=:vacancy_id" +
                        " WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("applicant_id", resume.getApplicantId())
                    .addParameter("experience", resume.getExperience())
                    .addParameter("skills", resume.getSkills())
                    .addParameter("education", resume.getEducation())
                    .addParameter("description", resume.getDescription())
                    .addParameter("in_search", resume.isInSearch())
                    .addParameter("vacancy_id", resume.getEmployerVacancyId())
                    .addParameter("id", resume.getId())
                    .executeUpdate();
        }
    }

    @Override
    public void delete(Resume o) {
        String sql =
                "DELETE FROM resume " +
                        "WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", o.getId())
                    .executeUpdate();
        }
    }
}
