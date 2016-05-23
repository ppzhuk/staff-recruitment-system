package recruitment.models;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Applicant extends Person {
    private boolean inJobSearch;
    private Resume resume;

    public Applicant(String name, String login, String email, String password) {
        super(name, login, email, password);
        inJobSearch = true;
        resume = null;
    }

    public boolean isInJobSearch() {
        return inJobSearch;
    }

    public void setInJobSearch(boolean inJobSearch) {
        this.inJobSearch = inJobSearch;
    }

    public Resume createResume() {
        return new Resume();
    }
}
