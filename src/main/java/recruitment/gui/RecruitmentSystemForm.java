package recruitment.gui;

import recruitment.facade.FilteringFacade;
import recruitment.facade.FunctionalityFacade;
import recruitment.facade.LoginFacade;
import recruitment.models.Applicant;
import recruitment.models.Employer;
import recruitment.models.Interview;
import recruitment.models.Manager;
import recruitment.models.Mark;
import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;
import recruitment.repository.EntityRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Filter;

/**
 * Created by Zhuk Pavel on 07.06.2016.
 */
public class RecruitmentSystemForm {
    private JButton vacancyCreateButton;
    private JButton interviewCreateButton;
    private JButton markCreateButton;
    private JButton exitButton;
    private JPanel panel;
    private JLabel usernameL;
    private JButton logoutBtn;
    private JButton personDataUpdateButton;
    private JList vacancyList;
    private JList resumeList;
    private JList interviewList;
    private JRadioButton vacancyOpenRB;
    private JRadioButton vacancyAllRB;
    private JRadioButton vacancyCloseRB;
    private JRadioButton resumeOpenRB;
    private JRadioButton resumeCloseRB;
    private JRadioButton vacancyOwnRB;
    private JRadioButton resumeAllRB;
    private JRadioButton resumeOwnRB;
    private JRadioButton interviewOpenRB;
    private JRadioButton interviewPassedRB;
    private JRadioButton interviewAllRB;
    private JList marksList;
    private JButton refreshMarksBtn;
    private JPanel marksPanel;

    private Person user;
    private static int personId;
    private FilteringFacade filteringFacade;
    private FunctionalityFacade funcFacade = new FunctionalityFacade();

    private DefaultListModel<Vacancy> vacancyModel = new DefaultListModel<>();
    private DefaultListModel<Resume> resumeModel = new DefaultListModel<>();
    private DefaultListModel<Interview> interviewModel = new DefaultListModel<>();
    private DefaultListModel<Mark> markModel = new DefaultListModel<>();

    public RecruitmentSystemForm(JFrame frame) {
        user = new LoginFacade().getUser(personId);
        filteringFacade = new FilteringFacade(user);

        dropVisibility();
        
        filteringFacade.setupListsModels(vacancyModel, resumeModel, interviewModel);
        if (LoginFacade.ROLE == LoginFacade.ROLE_MANAGER) {
            filteringFacade.setupMarkModel(markModel, ((Manager)user).getManagerId());
            marksList.setModel(markModel);
        }
        vacancyList.setModel(vacancyModel);
        resumeList.setModel(resumeModel);
        interviewList.setModel(interviewModel);

        
        
        usernameL.setMaximumSize(new Dimension(200, 15));
        usernameL.setPreferredSize(new Dimension(200, 15));
        usernameL.setMinimumSize(new Dimension(200, 15));
        usernameL.setText(user.getName());
        
        vacancyAllRB.setSelected(true);
        resumeAllRB.setSelected(true);
        interviewAllRB.setSelected(true);

        vacancyAllRB.addActionListener(e -> filteringFacade.filterAllVacancy(vacancyModel));
        vacancyOpenRB.addActionListener(e -> filteringFacade.filterOpenVacancy(vacancyModel));
        vacancyCloseRB.addActionListener(e ->filteringFacade.filterCloseVacancy(vacancyModel));
        vacancyOwnRB.addActionListener(e -> filteringFacade.filterOwnVacancy(vacancyModel));
        
        resumeAllRB.addActionListener(e -> filteringFacade.filterAllResume(resumeModel));
        resumeOpenRB.addActionListener(e -> filteringFacade.filterOpenResume(resumeModel));
        resumeCloseRB.addActionListener(e -> filteringFacade.filterCloseResume(resumeModel));
        resumeOwnRB.addActionListener(e -> filteringFacade.filterOwnResume(resumeModel));
        
        interviewAllRB.addActionListener(e -> filteringFacade.filterAllInterview(interviewModel));
        interviewOpenRB.addActionListener(e -> filteringFacade.filterOpenInterview(interviewModel));
        interviewPassedRB.addActionListener(e -> filteringFacade.filterPassedInterview(interviewModel));

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showOptionDialog(frame,
                    "Выйти из приложения?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        logoutBtn.addActionListener(e -> {
            LoginForm.main(null);
            frame.dispose();
        });
        vacancyList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = vacancyList.locationToIndex(e.getPoint());
                    if (index > -1) {
                       VacancyForm.main(new String[]{personId+"", vacancyModel.get(index).getId()+""});
                    }
                } 
            }
        });
        resumeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = resumeList.locationToIndex(e.getPoint());
                    if (index > -1) {
                        ResumeForm.main(new String[]{personId+"", resumeModel.get(index).getId()+""});
                    }
                }
            }
        });
        
        interviewList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = interviewList.locationToIndex(e.getPoint());
                    if (index > -1) {
                        InterviewForm.main(new String[]{personId+"", interviewModel.get(index).getId()+""});
                    }
                }
            }
        });
        marksList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = marksList.locationToIndex(e.getPoint());
                    if (index > -1) {
                        MarkForm.main(new String[]{personId+"", markModel.get(index).getId()+""});
                    }
                }
            }
        });
        refreshMarksBtn.addActionListener( e -> {
            filteringFacade.setupMarkModel(markModel, ((Manager)user).getManagerId());
        });
        vacancyCreateButton.addActionListener(e -> CreateVacancyForm.main(new String[] {personId+""}));
        personDataUpdateButton.addActionListener(e -> PersonalDataForm.main(new String[] {personId+""}));
        usernameL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        interviewCreateButton.addActionListener(e -> CreateInterviewForm.main(new String[] {personId+""}));
        markCreateButton.addActionListener(e -> CreateMarkForm.main(new String[] {personId+""}));

    }
    
    private void dropVisibility() {
        vacancyCreateButton.setVisible(funcFacade.canCreateVacancy());
        vacancyOwnRB.setVisible(funcFacade.hasOwnVacancies());
        resumeOwnRB.setVisible(funcFacade.hasOwnResumes());
        interviewCreateButton.setVisible(funcFacade.canCreateInterview());
        markCreateButton.setVisible(funcFacade.canCreateMark());
        marksPanel.setVisible(funcFacade.hasOwnMarkss());
    }

    public static void main(String[] args) {
        personId = Integer.parseInt(args[0]);
        JFrame frame = new JFrame("Подбор персонала");
        frame.setContentPane(new RecruitmentSystemForm(frame).panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void createUIComponents() {
    }

}
