package recruitment.gui;

import recruitment.facade.LoginFacade;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zhuk Pavel on 08.06.2016.
 */
public class ApplicantRegistrationForm {
    private static JFrame frame;
    private JPanel panel;
    private JButton okBtn;
    private JButton cancelBtn;
    private JTextField fioTF;
    private JTextField loginTF;
    private JPasswordField passwordTF;
    private JPasswordField submitPasswordTF;
    private JTextField emailTF;
    private JTextArea experienceTA;
    private JTextArea skillsTA;
    private JLabel errorMessageLabel;
    private JTextArea educationTA;
    private JTextArea descriptionTA;

    public ApplicantRegistrationForm() {
        fioTF.addActionListener(e -> loginTF.requestFocus());
        loginTF.addActionListener(e -> passwordTF.requestFocus());
        passwordTF.addActionListener(e -> submitPasswordTF.requestFocus());
        submitPasswordTF.addActionListener(e -> emailTF.requestFocus());
        emailTF.addActionListener(e -> experienceTA.requestFocus());
        cancelBtn.addActionListener(e1 -> frame.dispose());
        okBtn.addActionListener(e -> {
            LoginFacade loginFacade = new LoginFacade();

            String errorMessage = loginFacade.validateNewApplicant(
                    fioTF.getText(),
                    loginTF.getText(),
                    new String(passwordTF.getPassword()),
                    new String(submitPasswordTF.getPassword()),
                    emailTF.getText(),
                    experienceTA.getText(),
                    skillsTA.getText(), 
                    educationTA.getText()
            );
            if (errorMessage.equals("")) {
                errorMessage = loginFacade.addNewApplicant(
                        fioTF.getText(),
                        loginTF.getText(),
                        new String(passwordTF.getPassword()),
                        new String(submitPasswordTF.getPassword()),
                        emailTF.getText(),
                        experienceTA.getText(),
                        skillsTA.getText(),
                        educationTA.getText(), 
                        descriptionTA.getText()
                );
                if (errorMessage.equals("")) {
                    frame.dispose();
                    return;
                }
            }
            errorMessageLabel.setText(EmployerRegistrationForm.html + errorMessage);
            errorMessageLabel.setForeground(new Color(255, 0, 0));
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Регистрация соискателя");
        frame.setContentPane(new ApplicantRegistrationForm().panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        experienceTA = new JTextArea(4, 20);
        skillsTA = new JTextArea(4, 20);
        educationTA = new JTextArea(4, 20);
        descriptionTA = new JTextArea(4, 20);
    }

}
