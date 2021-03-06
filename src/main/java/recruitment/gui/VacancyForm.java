package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.FunctionalityFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Mark;
import recruitment.models.Person;
import recruitment.models.Vacancy;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 09.06.2016.
 */
public class VacancyForm {
    private JPanel panel;
    private JList marksList;
    private JCheckBox statusCB;
    private JTextField positionTF;
    private JTextField salaryTF;
    private JTextArea requirementsTA;
    private JButton okBtn;
    private JButton deleteBtn;
    private JButton cancelBtn;
    private JButton saveBtn;
    private JLabel vacancyIdLabel;
    private JLabel companyNameLabel;
    private JLabel infoLabel;
    private JLabel companyNameLabel2;
    private JLabel averageMarkLabel;
    private JPanel marksPanel;

    private Person user;
    private static int personId;
    private static Vacancy vacancy;
    private static int vacancyId;
    
    private DefaultListModel<Mark> markModel = new DefaultListModel<>();
    private FilteringFacade filteringFacade;
    private LoginFacade loginFacade;
    private Facade facade;
    private FunctionalityFacade funcFacade = new FunctionalityFacade();

    public VacancyForm(JFrame frame) {
        loginFacade = new LoginFacade();
        filteringFacade = new FilteringFacade(user);
        facade = new Facade();
        
        user = loginFacade.getUser(personId);
        vacancy = facade.getVacancy(vacancyId);
        
        setupVacancy();
        setupMarks();
        setupVisibility();
        
        okBtn.addActionListener(e -> frame.dispose());
        cancelBtn.addActionListener(e -> frame.dispose());
        
        saveBtn.addActionListener(e -> {
            String errorMessage = "";
            if (positionTF.getText().equals("") || requirementsTA.getText().equals("")) {
                errorMessage = "Поля со * обязательны к заполнению. ";
            }
            if (!facade.isSalaryCorrect(salaryTF.getText())) {
                errorMessage += "Не верно указана зарплата.";
            }
            if (!errorMessage.equals("")) {
                JOptionPane.showMessageDialog(
                        frame,
                        errorMessage,
                        "Некорректные данные",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (filteringFacade.duplicatePosition(positionTF.getText(), vacancy.getEmployerId(), vacancy.getId())) {
                int confirm = JOptionPane.showOptionDialog(frame,
                        "У вас уже есть вакансия для такой должности. Вы уверены, что хотите продолжить?",
                        "Дублирующая вакансия", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            if (vacancy.getStatus() == Vacancy.STATUS_CLOSE && !statusCB.isSelected()) {
                facade.openVacancy(vacancy);
            }
            
            facade.updateVacancy(vacancy, 
                    positionTF.getText(), Double.parseDouble(salaryTF.getText()), requirementsTA.getText());
            frame.dispose();
        });
        
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showOptionDialog(frame,
                    "Вы уверены, что хотите удалиь вакансию?",
                    "Подтверждение", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                facade.deleteVacancy(vacancy.getId());
                frame.dispose();
            }
        });
    }

    private void setupVacancy() {
        vacancyIdLabel.setText(vacancy.getId()+"");
        companyNameLabel.setText(vacancy.getCompanyName());
        positionTF.setText(vacancy.getPosition());
        salaryTF.setText(String.valueOf(vacancy.getSalary()));
        requirementsTA.setText(vacancy.getRequirements());
        if (vacancy.getStatus() == Vacancy.STATUS_CLOSE) {
            statusCB.setSelected(true);
            infoLabel.setText(vacancy.getCloseDate() + "  Резюме: (" + vacancy.getApplicantId() + ")");
        }
    }
    
    private void setupMarks() {
        companyNameLabel2.setText(vacancy.getCompanyName());
        int employerPersonId = facade.getPersonIdByEmployerId(vacancy.getEmployerId());
        double averageMark = facade.getPerson(employerPersonId).getAverageMark();
        averageMarkLabel.setText(String.valueOf(averageMark));

        filteringFacade.setupMarksListModel(markModel, employerPersonId);
        marksList.setModel(markModel);
    }


    private void setupVisibility() {
        marksPanel.setVisible(funcFacade.isRoleManager());
        positionTF.setEditable(funcFacade.isCurrentEmployer(vacancy, personId));
        salaryTF.setEditable(funcFacade.isCurrentEmployer(vacancy, personId));
        requirementsTA.setEditable(funcFacade.isCurrentEmployer(vacancy, personId));
        statusCB.setEnabled(funcFacade.canEditVacancyStatus(vacancy, personId));
        saveBtn.setVisible(funcFacade.isCurrentEmployer(vacancy, personId));
        cancelBtn.setVisible(funcFacade.isCurrentEmployer(vacancy, personId));
        deleteBtn.setVisible(funcFacade.isCurrentEmployer(vacancy, personId));
    }

    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        vacancyId = Integer.parseInt(args[1]);
        JFrame frame = new JFrame("Вакансия");
        frame.setContentPane(new VacancyForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
