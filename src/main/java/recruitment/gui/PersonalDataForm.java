package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.FunctionalityFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Employer;
import recruitment.models.Person;
import recruitment.repository.EmployerRepository;
import recruitment.repository.PersonRepository;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zhuk Pavel on 11.06.2016.
 */
public class PersonalDataForm {
    private JPanel panel;
    private JButton okBtn;
    private JButton cancelBtn;
    private JTextField fioTF;
    private JTextField emailTF;
    private JTextField companyNameTF;
    private JTextField descriptionTF;
    private JTextField siteTF;
    private JPanel emplPanel;

    private Person user;
    private static int personId;

    private FilteringFacade filteringFacade;
    private LoginFacade loginFacade;
    private Facade facade;
    private FunctionalityFacade funcFacade = new FunctionalityFacade();


    public PersonalDataForm(JFrame frame) {
        loginFacade = new LoginFacade();
        user = loginFacade.getUser(personId);
        facade = new Facade();
        filteringFacade = new FilteringFacade(user);
        
        setupData();
        setVisible();
        
        cancelBtn.addActionListener(e -> frame.dispose());
        okBtn.addActionListener(e -> {
            String errorMessage = "";
            if (fioTF.getText().equals("") || emailTF.getText().equals("") || (
                    funcFacade.isRoleEmployer() && companyNameTF.getText().equals(""))) {
                errorMessage = "Поля со * обязательны к заполнению. ";
            }
            if (!emailTF.getText().contains("@")) {
                errorMessage += "Не верно указана почта.";
            }
            if (!errorMessage.equals("")) {
                JOptionPane.showMessageDialog(
                        frame,
                        errorMessage,
                        "Некорректные данные",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            user.setName(fioTF.getText());
            user.setEmail(emailTF.getText());
            if (funcFacade.isRoleEmployer()) {
                Employer empl = (Employer) user;
                empl.setCompanyName(companyNameTF.getText());
                empl.setSite(siteTF.getText());
                empl.setDescription(descriptionTF.getText());

                EmployerRepository.getInstance().update(empl);
            } else {
                PersonRepository.getInstance().update(user);
            }
            frame.dispose();
        });
    }

    private void setupData() {
        fioTF.setText(user.getName());
        emailTF.setText(user.getEmail());
        if (funcFacade.isRoleEmployer()) {
            Employer empl = (Employer) user;
            companyNameTF.setText(empl.getCompanyName());
            descriptionTF.setText(empl.getDescription());
            siteTF.setText(empl.getSite());
        }
    }
    
    private void setVisible() {
        emplPanel.setVisible(funcFacade.isRoleEmployer());
    }
    
    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        JFrame frame = new JFrame("Изменение персональных данных");
        frame.setContentPane(new PersonalDataForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
