package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Employer;
import recruitment.models.Person;
import recruitment.models.Vacancy;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 10.06.2016.
 */
public class CreateVacancyForm {
    private JPanel panel;
    private JButton cancelBtn;
    private JButton saveBtn;
    private JTextField positionTF;
    private JTextField salaryTF;
    private JTextArea requirementsTA;

    private Employer user;
    private static int personId;

    private FilteringFacade filteringFacade;
    private LoginFacade loginFacade;
    private Facade facade;
    
    public CreateVacancyForm(JFrame frame) {
        loginFacade = new LoginFacade();
        user = (Employer) loginFacade.getUser(personId);
        facade = new Facade();
        filteringFacade = new FilteringFacade(user);
        
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

            if (filteringFacade.duplicatePosition(positionTF.getText(), user.getEmployerId(), -1)) {
                int confirm = JOptionPane.showOptionDialog(frame,
                        "У вас уже есть вакансия для такой должности. Вы уверены, что хотите продолжить?",
                        "Дублирующая вакансия", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            facade.addNewVacancy(positionTF.getText(), Double.parseDouble(salaryTF.getText()), 
                    requirementsTA.getText(), user.getEmployerId());
            frame.dispose();
        });
    }
    
    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        JFrame frame = new JFrame("Создать вакансию");
        frame.setContentPane(new CreateVacancyForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
