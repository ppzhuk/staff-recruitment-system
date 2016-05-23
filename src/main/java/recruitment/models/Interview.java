package recruitment.models;

import java.util.Date;

/**
 * Created by Nataly on 23.05.2016.
 */
public class Interview {
    private Applicant applicant;
    private Vacancy vacancy;
    private Date interviewDate;
    private boolean result;

    public Interview(Applicant applicant, Vacancy vacancy, Date interviewDate) {
        this.applicant = applicant;
        this.vacancy = vacancy;
        this.interviewDate = interviewDate;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
