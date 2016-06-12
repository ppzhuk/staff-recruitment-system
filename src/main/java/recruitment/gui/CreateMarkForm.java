package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Manager;
import recruitment.models.Person;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 12.06.2016.
 */
public class CreateMarkForm {
    private JPanel panel;
    private JButton saveBtn;
    private JButton cancelBnt;
    private JComboBox<Integer> markCB;
    private JTextField commentTF;
    private JComboBox<Person> evaluatedPersonCB;

    private Manager manager;
    private static int personId;

    private LoginFacade loginFacade;
    private Facade facade;

    public CreateMarkForm(JFrame frame) {
        loginFacade = new LoginFacade();
        facade = new Facade();

        manager = (Manager) loginFacade.getUser(personId);
        
        cancelBnt.addActionListener( e -> frame.dispose());
        saveBtn.addActionListener( e -> {
            int evaluatedPersonIdx = evaluatedPersonCB.getSelectedIndex();
            int markIdx = markCB.getSelectedIndex();
            if (evaluatedPersonIdx  == -1 || markIdx == -1) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Не выбран пользователь или оценка.",
                        "Некорректные данные",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
           
            facade.saveMark(
                    manager.getManagerId(), 
                    evaluatedPersonCB.getItemAt(evaluatedPersonIdx).getPersonId(),
                    commentTF.getText(),
                    markCB.getItemAt(markIdx)
            );
            frame.dispose();
        });
    }
    private void createUIComponents() {
        markCB = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        evaluatedPersonCB = new JComboBox<>(FilteringFacade.getAllEvaluatedPersons());
    }

    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        JFrame frame = new JFrame("Добавить отзыв");
        frame.setContentPane(new CreateMarkForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
