package recruitment.models;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Mark {
    private int mark;
    private String comment;
    private Person evaluatedPerson;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Person getEvaluatedPerson() {
        return evaluatedPerson;
    }

    public void setEvaluatedPerson(Person evaluatedPerson) {
        this.evaluatedPerson = evaluatedPerson;
    }

}
