package recruitment.models;

/**
 * Created by Nataly on 23.05.2016.
 */
public class Vacancy {
    public static final int STATUS_OPEN = 0;
    public static final int STATUS_CLOSE = 1;


    private String position;
    private String requirements;
    private double salary;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Vacancy() {
        status = STATUS_OPEN;
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
}
