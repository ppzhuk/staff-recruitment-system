package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.FunctionalityFacade;
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
    private FunctionalityFacade funcFacade = new FunctionalityFacade();
    
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
            if (interview.getApplicantId() < 1 || interview.getVacancyId() < 1) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Отсутсвует соискатель или работодатель. Сохранение не возможно.",
                        "Некорректные данные",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            } 
            
            if (!facade.isVacancyAndResumeFree(interview.getVacancyId(), interview.getApplicantId())) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Вакансия уже закрыта или соискатель уже струдоустроен.\n Результаты собеседование не будут применены.",
                        "Некорректные данные",
                        JOptionPane.WARNING_MESSAGE
                );
                facade.updateInterview(interview, applicantYesRB.isSelected(), applicantNoRB.isSelected(),
                        employerYesRB.isSelected(), employerNoRB.isSelected(), false);
            } else {
                facade.updateInterview(interview, applicantYesRB.isSelected(), applicantNoRB.isSelected(),
                        employerYesRB.isSelected(), employerNoRB.isSelected(), true);
            }
            frame.dispose();
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
        deleteBtn.setVisible(funcFacade.isRoleManager());
        applicantResLabel.setVisible(funcFacade.canSetInterviewResApplicant());
        applicantYesRB.setVisible(funcFacade.canSetInterviewResApplicantRBYES(interview));
        applicantNoRB.setVisible(funcFacade.canSetInterviewResApplicantRBNO(interview));
        employerResLabel.setVisible(funcFacade.canSetInterviewResEmployer());
        employerYesRB.setVisible(funcFacade.canSetInterviewResEmplloyerRBYES(interview));
        employerNoRB.setVisible(funcFacade.canSetInterviewResEmplloyerRBNO(interview));

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
