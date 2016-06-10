package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Mark;
import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 09.06.2016.
 */
public class ResumeForm {
    private static JFrame frame;
    private JPanel panel;
    private JList marksList;
    private JCheckBox statusCB;
    private JTextArea educationTA;
    private JButton okBtn;
    private JButton deleteBtn;
    private JButton cancelBtn;
    private JButton saveBtn;
    private JLabel resumeIdLabel;
    private JLabel fioLabel;
    private JLabel infoLabel;
    private JLabel fioLabel2;
    private JLabel averageMarkLabel;
    private JPanel marksPanel;
    private JTextArea experienceTA;
    private JTextArea skillsTA;
    private JTextArea descriptionTA;

    private Person user;
    private static int personId;
    private static Resume resume;
    private static int resumeId;

    private DefaultListModel<Mark> markModel = new DefaultListModel<>();
    private FilteringFacade filteringFacade;
    private LoginFacade loginFacade;
    private Facade facade;

    ResumeForm() {
        loginFacade = new LoginFacade();
        filteringFacade = new FilteringFacade(user);
        facade = new Facade();

        user = loginFacade.getUser(personId);
        resume = facade.getResume(resumeId);

        setupResume();
        setupMarks();
        setupVisibility();

        okBtn.addActionListener(e -> frame.dispose());
        cancelBtn.addActionListener(e -> frame.dispose());

        saveBtn.addActionListener(e -> {
            String errorMessage = "";
            if (experienceTA.getText().equals("") || skillsTA.getText().equals("") || educationTA.getText().equals("")) {
                errorMessage = "Поля со * обязательны к заполнению. ";
            }
            if (!errorMessage.equals("")) {
                JOptionPane.showMessageDialog(
                        frame,
                        errorMessage,
                        "Некорректные данные",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!resume.isInSearch() && statusCB.isSelected()) {
                facade.openResume(resume);
            }

            facade.updateResume(resume,
                    experienceTA.getText(), skillsTA.getText(), educationTA.getText(), descriptionTA.getText());
            frame.dispose();
        });

        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showOptionDialog(frame,
                    "Вы уверены, что хотите удалиь резюме?",
                    "Подтверждение", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                facade.deleteResume(resumeId);
                frame.dispose();
            }
        });
    }
    
    private void setupResume() {
        resumeIdLabel.setText(resumeId+"");
        fioLabel.setText(facade.getApplicantFIO(resume));
        experienceTA.setText(resume.getExperience());
        skillsTA.setText(resume.getSkills());
        educationTA.setText(resume.getEducation());
        descriptionTA.setText(resume.getDescription());
        if (resume.isInSearch()) {
            statusCB.setSelected(true);
        } else {
            infoLabel.setText("Вакансия: (" + resume.getEmployerVacancyId() + ")");
        }
    }

    private void setupMarks() {
        fioLabel2.setText(facade.getApplicantFIO(resume));
        int applicantPersonId = facade.getPersonIdByApplicantId(resume.getApplicantId());
        double averageMark = facade.getPerson(applicantPersonId).getAverageMark();
        averageMarkLabel.setText(String.valueOf(averageMark));

        filteringFacade.setupMarksListModel(markModel, applicantPersonId);
        marksList.setModel(markModel);
    }


    private void setupVisibility() {
        if (LoginFacade.ROLE == LoginFacade.ROLE_MANAGER) {
            marksPanel.setVisible(true);
        }
        if (LoginFacade.ROLE == LoginFacade.ROLE_APPLICANT &&
                resume.getApplicantId() == facade.getApplicantIdByPersonId(personId)) {
            educationTA.setEditable(true);
            experienceTA.setEditable(true);
            skillsTA.setEditable(true);
            descriptionTA.setEditable(true);
            statusCB.setEnabled(!resume.isInSearch());
            saveBtn.setVisible(true);
            cancelBtn.setVisible(true);
            deleteBtn.setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        resumeId = Integer.parseInt(args[1]);
        frame = new JFrame("Резюме");
        frame.setContentPane(new ResumeForm().panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
