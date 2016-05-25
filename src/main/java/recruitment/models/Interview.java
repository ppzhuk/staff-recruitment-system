package recruitment.models;

import java.util.Date;

/**
 * Created by Nataly on 23.05.2016.
 */
public class Interview {
    public static final int RESULT_POSITIVE = 1;
    public static final int RESULT_UNDEFINED = 0;
    public static final int RESULT_NEGATIVE = -1;
    
    private int id;
    private int applicantId;
    private int resumeId;
    private Date interviewDate;
    private int result = RESULT_UNDEFINED;

    public Interview(int applicantId, int resumeId, Date interviewDate, int result) {
        this.applicantId = applicantId;
        this.resumeId = resumeId;
        this.interviewDate = interviewDate;
        this.result = result;
    }

    public Interview(int applicantId, int resumeId, Date interviewDate) {
        this.applicantId = applicantId;
        this.resumeId = resumeId;
        this.interviewDate = interviewDate;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public int getResult() {
        return result;
    }

    /**
     * 
     * @param result Interview.RESULT_POSITIVE, Interview.RESULT_NEGATIVE ,Interview.RESULT_UNDEFINED
     */
    public void setResult(int result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
