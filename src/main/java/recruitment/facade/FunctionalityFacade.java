package recruitment.facade;

import recruitment.models.Interview;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

/**
 * Created by Nataly on 16.06.2016.
 */
public class FunctionalityFacade {
    private Facade facade = new Facade();

    public boolean isRoleManager() {
        return LoginFacade.ROLE == LoginFacade.ROLE_MANAGER;
    }

    public boolean isRoleEmployer() {
        return LoginFacade.ROLE == LoginFacade.ROLE_EMPLOYER;
    }

    public boolean isCurrentEmployer(Vacancy vacancy, int personId) {
        return LoginFacade.ROLE == LoginFacade.ROLE_EMPLOYER &&
                vacancy.getEmployerId() == facade.getEmployerIdByPersonId(personId);
    }

    public boolean isCurrentApplicant(Resume resume, int personId) {
        return LoginFacade.ROLE == LoginFacade.ROLE_APPLICANT &&
                resume.getApplicantId() == facade.getApplicantIdByPersonId(personId);
    }

    public boolean canEditVacancyStatus(Vacancy vacancy, int personId) {
        return isCurrentEmployer(vacancy, personId) && vacancy.getStatus() == Vacancy.STATUS_CLOSE;
    }

    public boolean canEditResumeStatus(Resume resume, int personId) {
        return isCurrentApplicant(resume, personId) && !resume.isInSearch();
    }

    public boolean isRoleApplicant() {
        return LoginFacade.ROLE == LoginFacade.ROLE_APPLICANT;
    }

    public boolean canCreateVacancy() {
        return !(isRoleManager() || isRoleApplicant());
    }

    public boolean canCreateInterview() {
        return !(isRoleEmployer() || isRoleApplicant());
    }

    public boolean canCreateMark() {
        return !(isRoleEmployer() || isRoleApplicant());
    }

    public boolean hasOwnVacancies() {
        return !(isRoleManager() || isRoleApplicant());
    }

    public boolean hasOwnResumes() {
        return !(isRoleManager() || isRoleEmployer());
    }

    public boolean hasOwnMarkss() {
        return !(isRoleEmployer() || isRoleApplicant());
    }

    public boolean interviewPassedEmployerUndefined(Interview interview) {
        return interview.isInterviewPassed() && interview.getResultEmployer() == Interview.RESULT_UNDEFINED;
    }

    public boolean interviewPassedApplicantUndefined(Interview interview) {
        return interview.isInterviewPassed() && interview.getResultApplicant() == Interview.RESULT_UNDEFINED;
    }
    public boolean canSetInterviewResApplicant() {
        return isRoleManager() || isRoleApplicant();
    }

    public boolean canSetInterviewResEmployer() {
        return isRoleManager() || isRoleEmployer();
    }

    public boolean canSetInterviewResApplicantRBYES(Interview interview) {
        return isRoleManager() || isRoleApplicant() || interviewPassedApplicantUndefined(interview);
    }

    public boolean canSetInterviewResApplicantRBNO(Interview interview) {
        return isRoleManager() || isRoleApplicant() || interviewPassedApplicantUndefined(interview);
    }

    public boolean canSetInterviewResEmplloyerRBYES(Interview interview) {
        return isRoleManager() || isRoleEmployer() || interviewPassedEmployerUndefined(interview);
    }

    public boolean canSetInterviewResEmplloyerRBNO(Interview interview) {
        return isRoleManager() || isRoleEmployer() || interviewPassedEmployerUndefined(interview);
    }
}
