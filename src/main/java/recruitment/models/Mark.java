package recruitment.models;

/**
 * Created by Pavel on 23.05.2016.
 */
public class Mark {
    public static final double SATISFACTORY_MARK = 3.0;

    private int id;
    private int managerId;
    private int evaluatedPersonId;
    private int mark;
    private String comment;

    public Mark(int managerId, int evaluatedPersonId, int mark) {
        this.managerId = managerId;
        this.evaluatedPersonId = evaluatedPersonId;
        this.mark = mark;
    }

    public Mark(int managerId, int evaluatedPersonId, int mark, String comment) {
        this.managerId = managerId;
        this.evaluatedPersonId = evaluatedPersonId;
        if (mark < 0 || mark > 5) {
            throw new IllegalArgumentException();
        }
        this.mark = mark;
        this.comment = comment;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        if (mark < 0 || mark > 5) {
            throw new IllegalArgumentException();
        }
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getEvaluatedPersonId() {
        return evaluatedPersonId;
    }

    public void setEvaluatedPersonId(int evaluatedPersonId) {
        this.evaluatedPersonId = evaluatedPersonId;
    }


}
