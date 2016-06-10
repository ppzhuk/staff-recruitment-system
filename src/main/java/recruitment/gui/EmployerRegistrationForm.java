package recruitment.gui;

import recruitment.facade.LoginFacade;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zhuk Pavel on 08.06.2016.
 */
public class EmployerRegistrationForm {

    public static final String html = "<html><body style='width:250px'>";

    private JPanel panel;
    private JTextField fioTF;
    private JPasswordField passTF;
    private JPasswordField submitPassTF;
    private JButton okBtn;
    private JButton cancelBtn;
    private JTextField loginTF;
    private JTextField emailTF;
    private JTextField companyNameTF;
    private JTextField descriptionTF;
    private JTextField siteTF;
    private JLabel errorMsgLabel;

    public EmployerRegistrationForm(JFrame frame) {
        fioTF.addActionListener(e -> loginTF.requestFocus());
        loginTF.addActionListener(e -> passTF.requestFocus());
        passTF.addActionListener(e -> submitPassTF.requestFocus());
        submitPassTF.addActionListener(e -> emailTF.requestFocus());
        emailTF.addActionListener(e -> companyNameTF.requestFocus());
        companyNameTF.addActionListener(e -> descriptionTF.requestFocus());
        descriptionTF.addActionListener(e -> siteTF.requestFocus());
        siteTF.addActionListener(e -> okBtn.requestFocus());
        cancelBtn.addActionListener(e -> frame.dispose());
        okBtn.addActionListener(e -> {
            LoginFacade loginFacade = new LoginFacade();
            String errorMessage = loginFacade.validateNewEmployer(fioTF.getText(),
                    loginTF.getText(),
                    new String(passTF.getPassword()),
                    new String(submitPassTF.getPassword()),
                    emailTF.getText(), 
                    companyNameTF.getText()
            );
            if (errorMessage.equals("")) {
                errorMessage = loginFacade.addNewEmployer(
                        fioTF.getText(),
                        loginTF.getText(),
                        new String(passTF.getPassword()),
                        emailTF.getText(),
                        companyNameTF.getText(),
                        descriptionTF.getText(),
                        siteTF.getText()
                );
                if (errorMessage.equals("")) {
                    frame.dispose();
                    return;
                }
            }
            errorMsgLabel.setText(html + errorMessage);
            errorMsgLabel.setForeground(new Color(255, 0, 0));
        });
    }
    

    public static void main(String[] args) {
        JFrame frame = new JFrame("Регистрация работодателя");
        frame.setContentPane(new EmployerRegistrationForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
