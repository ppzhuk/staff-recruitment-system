package recruitment.models;

import recruitment.repository.ApplicantRepository;
import recruitment.repository.EmployerRepository;
import recruitment.repository.EntityRepository;

/**
 * Created by Nataly on 23.05.2016.
 */
public class Interview {
    public static final int RESULT_POSITIVE = 1;
    public static final int RESULT_UNDEFINED = 0;
    public static final int RESULT_NEGATIVE = -1;

    private int id;
    private int applicantId;
    private int vacancyId;
    private String interviewDate;
    private int resultEmployer = RESULT_UNDEFINED;
    private int resultApplicant = RESULT_UNDEFINED;

    public Interview(int applicantId, int vacancyId, String interviewDate, int result) {
        this.applicantId = applicantId;
        this.vacancyId = vacancyId;
        this.interviewDate = interviewDate;
        this.resultEmployer = result;
    }

    public Interview(int applicantId, int vacancyId, String interviewDate) {
        this.applicantId = applicantId;
        this.vacancyId = vacancyId;
        this.interviewDate = interviewDate;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(int vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public int getResultEmployer() {
        return resultEmployer;
    }

    /**
     * @param resultEmployer Interview.RESULT_POSITIVE, Interview.RESULT_NEGATIVE ,Interview.RESULT_UNDEFINED
     */
    public void setResultEmployer(int resultEmployer) {
        if (isInterviewPassed()) {
            this.resultEmployer = resultEmployer;
        } else {
            this.resultEmployer = RESULT_UNDEFINED;
        }
    }

    public int getResultApplicant() {
        return resultApplicant;
    }

    /**
     * @param resultApplicant Interview.RESULT_POSITIVE, Interview.RESULT_NEGATIVE ,Interview.RESULT_UNDEFINED
     */
    public void setResultApplicant(int resultApplicant) {
        if (isInterviewPassed()) {
            this.resultApplicant = resultApplicant;
        } else {
            this.resultApplicant = RESULT_UNDEFINED;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employer getEmployer() {
        EmployerRepository employerRepository = EmployerRepository.getInstance();
        EntityRepository entityRepository = EntityRepository.getInstance();

        Vacancy v = (Vacancy) entityRepository.getById(vacancyId, EntityRepository.VACANCY_TYPE);
        return employerRepository.getById(v.getEmployerId());
    }

    public Applicant getApplicant() {
        ApplicantRepository applicantRepository = ApplicantRepository.getInstance();
        return applicantRepository.getById(applicantId);
    }

    public boolean isInterviewPassed() {
        return interviewDate.compareTo(Vacancy.getToday()) < 1;
    }

    public int getInterviewResult() {
        int res = RESULT_UNDEFINED;
        if (resultApplicant == RESULT_POSITIVE && resultEmployer == RESULT_POSITIVE) {
            res = RESULT_POSITIVE;
        } else if (resultApplicant == RESULT_NEGATIVE || resultEmployer == RESULT_NEGATIVE) {
            res = RESULT_NEGATIVE;
        }
        return res;
    }
}
