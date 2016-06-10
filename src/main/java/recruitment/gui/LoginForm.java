package recruitment.gui;

import recruitment.facade.LoginFacade;
import recruitment.models.Person;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Zhuk Pavel on 07.06.2016.
 */
public class LoginForm {
    private JFrame frame;
    private JTextField loginTF;
    private JPasswordField passwordTF;
    private JButton loginButton;
    private JButton employerRegistrationButton;
    private JButton applicantRegistrationButton;
    private JPanel panel;
    private JLabel incorrectDataLabel;

    public LoginForm(JFrame frame) {
        this.frame = frame;
        loginButton.addActionListener(loginlistener);
        passwordTF.addActionListener(loginlistener);
        loginTF.addActionListener(e -> passwordTF.requestFocus());
        employerRegistrationButton.addActionListener(e -> EmployerRegistrationForm.main(null));
        applicantRegistrationButton.addActionListener(e -> ApplicantRegistrationForm.main(null));
    }

    private ActionListener loginlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginFacade loginFacade = new LoginFacade();
            Person p = loginFacade.getUser(loginTF.getText(), new String(passwordTF.getPassword()));
            if (p == null) {
                incorrectDataLabel.setText("Неправильный логин или пароль.");
                return;
            }
            RecruitmentSystemForm.main(new String[]{p.getPersonId() + ""});
            frame.dispose();
        }
    };


    private void createUIComponents() {

    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Авторизация");
        frame.setContentPane(new LoginForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
