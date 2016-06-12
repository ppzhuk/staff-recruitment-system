package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * Created by Zhuk Pavel on 11.06.2016.
 */
public class CreateInterviewForm {
    private JPanel panel;
    private JButton saveBtn;
    private JButton cancelBtn;
    private JTextField dateTF;
    private JComboBox<Vacancy> vacancyCB;
    private JComboBox<Resume> resumeCB;
    
    private Person user;
    private static int personId;

    private LoginFacade loginFacade;
    private Facade facade;
    
    public CreateInterviewForm(JFrame frame) {
        loginFacade = new LoginFacade();
        user = loginFacade.getUser(personId);
        facade = new Facade();
        
        cancelBtn.addActionListener(e -> frame.dispose());
        saveBtn.addActionListener(e -> {
            String errorMessage = "";
            int resumeIdx = resumeCB.getSelectedIndex();
            int vacancyIdx = vacancyCB.getSelectedIndex();
            if (resumeIdx  == -1 || vacancyIdx == -1) {
                errorMessage = "Не выбрано резюме или вакансия.\n";
            }
            if (!facade.isDateCorrect(dateTF.getText())) {
                errorMessage += "Не верно указана дата. Формат: ГГГГ.ММ.ДД";
            }
            if (!errorMessage.equals("")) {
                JOptionPane.showMessageDialog(
                        frame,
                        errorMessage,
                        "Некорректные данные",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            facade.addNewInterview(
                    resumeCB.getItemAt(resumeIdx).getApplicantId(),
                    vacancyCB.getItemAt(vacancyIdx).getId(),
                    dateTF.getText()
            );
            frame.dispose();
        });
    }
    
    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        JFrame frame = new JFrame("Создание интервью");
        frame.setContentPane(new CreateInterviewForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        resumeCB = new JComboBox<>(FilteringFacade.getUnemployedResumes());
        vacancyCB = new JComboBox<>(FilteringFacade.getOpenVacancies());
    }
}

