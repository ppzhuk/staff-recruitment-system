package recruitment.models;

/**
 * Created by Nataly on 23.05.2016.
 */
public class Resume {
    private int id;
    private int applicantId;
    private String experience;
    private String skills;
    private String education;
    private String description;
    private boolean inSearch = true;
    private int employerVacancyId = -1;

    public Resume(int applicantId, String experience, String skills, String education) {
        this.applicantId = applicantId;
        this.experience = experience;
        this.skills = skills;
        this.education = education;
    }

    public Resume(int applicantId, String experience, String skills, String education, String description) {
        this.applicantId = applicantId;
        this.experience = experience;
        this.skills = skills;
        this.education = education;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInSearch() {
        return inSearch;
    }

    public void setInSearch(boolean inSearch, int employerVacancyId) {
        this.inSearch = inSearch;
        this.employerVacancyId = employerVacancyId;
    }

    public int getEmployerVacancyId() {
        return employerVacancyId;
    }


}
