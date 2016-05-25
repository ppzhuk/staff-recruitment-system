package recruitment.models;

import com.sun.istack.internal.Nullable;

/**
 * Created by Nataly on 23.05.2016.
 */
public class Vacancy {
    public static final int STATUS_OPEN = 1;
    public static final int STATUS_CLOSE = -1;

    private int id;
    private int employerId;
    private String position;
    private String requirements;
    private double salary;
    private int status = STATUS_OPEN;
    private int applicantResumeId = -1; 
    
    public Vacancy(int employerId, String position, String requirements, double salary) {
        this.employerId = employerId;
        this.position = position;
        this.requirements = requirements;
        this.salary = salary;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 
     * @param status Vacancy.STATUS_OPEN, Vacancy.STATUS_CLOSE
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public int getApplicantResumeId() {
        return applicantResumeId;
    }

    public void setApplicantResumeId(int applicantResumeId) {
        this.applicantResumeId = applicantResumeId;
    }
}
