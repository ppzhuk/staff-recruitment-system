package recruitment.gui;

import recruitment.facade.Facade;
import recruitment.facade.FilteringFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Applicant;
import recruitment.models.Employer;
import recruitment.models.Manager;
import recruitment.models.Mark;
import recruitment.models.Vacancy;

import javax.swing.*;

/**
 * Created by Zhuk Pavel on 12.06.2016.
 */
public class MarkForm {
    private JPanel panel;
    private JButton saveBtn;
    private JComboBox<Integer> markCB;
    private JTextField commentTF;
    private JLabel fioLabel;
    private JButton cancelBnt;

    private Manager manager;
    private static int personId;
    private static Mark mark;
    private static int markId;

    private FilteringFacade filteringFacade;
    private LoginFacade loginFacade;
    private Facade facade;

    public MarkForm(JFrame frame) {
        loginFacade = new LoginFacade();
        filteringFacade = new FilteringFacade(manager);
        facade = new Facade();

        manager = (Manager) loginFacade.getUser(personId);
        mark = facade.getMark(markId);
        
        setupMark();
        
        cancelBnt.addActionListener( e -> frame.dispose());
        saveBtn.addActionListener( e -> {
            facade.updateMark(
                    mark, 
                    commentTF.getText(),
                    markCB.getItemAt(markCB.getSelectedIndex())
            );
            frame.dispose();
        });
    }

    private void setupMark() {
        fioLabel.setText(
                facade.defineEvaluatedPersonName(mark.getEvaluatedPersonId())
        );
        markCB.setSelectedIndex(mark.getMark()-1);
        commentTF.setText(mark.getComment());
    }

    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        markId = Integer.parseInt(args[1]);
        JFrame frame = new JFrame("Отзыв");
        frame.setContentPane(new MarkForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        markCB = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    }
}
