package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Applicant;
import recruitment.models.Employer;
import recruitment.models.Interview;
import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 10.06.2016.
 */
public class InterviewForm {
    private JPanel panel;
    private JButton OkBtn;
    private JRadioButton employerNoRB;
    private JRadioButton employerYesRB;
    private JLabel applicantLabel;
    private JLabel employerLabel;
    private JLabel positionLabel;
    private JLabel interviewDateLabel;
    private JButton saveBtn;
    private JButton cancelBtn;
    private JRadioButton applicantYesRB;
    private JRadioButton applicantNoRB;
    private JButton deleteBtn;
    private JLabel employerResLabel;
    private JLabel applicantResLabel;

    private static int personId;
    private static int interviewId;

    private Person user;
    private Interview interview;
    private Applicant applicant;
    private Employer employer;
    private Vacancy vacancy;
    private Resume resume;
    
    private FilteringFacade filteringFacade;
    private LoginFacade loginFacade;
    private Facade facade;
    
    InterviewForm(JFrame frame) {
        loginFacade = new LoginFacade();
        filteringFacade = new FilteringFacade(user);
        facade = new Facade();

        getEntities();
        setupInterview();
        setupVisibility();
        
        OkBtn.addActionListener(e -> frame.dispose());
        cancelBtn.addActionListener(e -> frame.dispose());
        saveBtn.addActionListener(e -> {
            
        });
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showOptionDialog(frame,
                    "Вы уверены, что хотите удалиь собеседование?",
                    "Подтверждение", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                facade.deleteInterview(interviewId);
                frame.dispose();
            }
        });
    }

    private void getEntities() {
        user = loginFacade.getUser(personId);
        interview = facade.getInterview(interviewId);
        applicant = interview.getApplicant();
        employer = interview.getEmployer();
        vacancy = facade.getVacancy(interview.getVacancyId());
        resume = facade.getResumeByApplicantId(interview.getApplicantId());
    }

    private void setupInterview() {
        employerLabel.setText(
                (employer == null ? "null" : employer.getCompanyName()) + " (" + 
                (vacancy == null ? "null" : vacancy.getId()) + ")"
        );
        applicantLabel.setText(
                (applicant == null ? "null" :applicant.getName()) + " (" + 
                (resume == null ? "null" : resume.getId()) +  ")"
        );
        positionLabel.setText(vacancy == null ? "null" : vacancy.getPosition());
        interviewDateLabel.setText(interview.getInterviewDate() + 
                (!interview.isInterviewPassed() ? "  -  собеседование еще не прошло" : ""));
        if (interview.getResultApplicant() == Interview.RESULT_POSITIVE) {
            applicantYesRB.setSelected(true);
        }
        if (interview.getResultApplicant() == Interview.RESULT_NEGATIVE) {
            applicantNoRB.setSelected(true);
        }
        if (interview.getResultEmployer() == Interview.RESULT_POSITIVE) {
            employerYesRB.setSelected(true);
        }
        if (interview.getResultEmployer() == Interview.RESULT_NEGATIVE) {
            employerNoRB.setSelected(true);
        }
    }
    
    private void setupVisibility() {
        if (LoginFacade.ROLE == LoginFacade.ROLE_MANAGER) {
            deleteBtn.setVisible(true);
            setVisibleApplicantRes();
            setVisibleEmployerRes();
        }
        if (LoginFacade.ROLE == LoginFacade.ROLE_EMPLOYER) {
            setVisibleEmployerRes();
        }
        if (LoginFacade.ROLE == LoginFacade.ROLE_APPLICANT) {
            setVisibleApplicantRes();
        }
        if (interview.isInterviewPassed() && interview.getResultEmployer() == Interview.RESULT_UNDEFINED) {
            enableEmployerRes();
            saveBtn.setVisible(true);
            cancelBtn.setVisible(true);
        }
        if (interview.isInterviewPassed() && interview.getResultApplicant() == Interview.RESULT_UNDEFINED) {
            enableApplicantRes();
            saveBtn.setVisible(true);
            cancelBtn.setVisible(true);
        }
    }
    
    private void setVisibleApplicantRes() {
        applicantResLabel.setVisible(true);
        applicantYesRB.setVisible(true);
        applicantNoRB.setVisible(true);
    }

    private void setVisibleEmployerRes() {
        employerResLabel.setVisible(true);
        employerYesRB.setVisible(true);
        employerNoRB.setVisible(true);
    }
    
    private void enableApplicantRes() {
        applicantNoRB.setEnabled(true);
        applicantYesRB.setEnabled(true);
    }
    
    private void enableEmployerRes() {
        employerNoRB.setEnabled(true);
        employerYesRB.setEnabled(true);
    }
    
    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        interviewId = Integer.parseInt(args[1]);
        JFrame frame = new JFrame("Собеседование");
        frame.setContentPane(new InterviewForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
