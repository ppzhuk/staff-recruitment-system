package recruitment.facade;

import recruitment.models.Applicant;
import recruitment.models.Employer;
import recruitment.models.Interview;
import recruitment.models.Mark;
import recruitment.models.Person;
import recruitment.models.Resume;
import recruitment.models.Vacancy;
import recruitment.repository.EntityRepository;
import recruitment.repository.ManagerRepository;
import recruitment.repository.PersonRepository;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zhuk Pavel on 08.06.2016.
 */
public class FilteringFacade {
    private Person user;
    
    public FilteringFacade(Person user) {
        this.user = user;
    }
    
    public void setupListsModels(DefaultListModel<Vacancy> vacancyModel, 
                                 DefaultListModel<Resume> resumeModel, 
                                 DefaultListModel<Interview> interviewModel) {
        EntityRepository.getInstance().getAll(EntityRepository.VACANCY_TYPE).forEach(v -> {
            vacancyModel.addElement((Vacancy) v);
        });
        EntityRepository.getInstance().getAll(EntityRepository.RESUME_TYPE).forEach( r -> {
            resumeModel.addElement((Resume) r);
        });
        EntityRepository.getInstance().getAll(EntityRepository.INTERVIEW_TYPE).forEach( i -> {
            if (isShowInterview((Interview)i)) {
                interviewModel.addElement((Interview) i);
            }
        });
    }

    public void setupMarksListModel(DefaultListModel<Mark> markModel, int evaluatedPersonId) {
        EntityRepository.getInstance()
                .getPersonMarks(evaluatedPersonId)
                .forEach(markModel::addElement);
    }
    
    private boolean isShowInterview(Interview i) {
        boolean isAdd = true;
        if(LoginFacade.ROLE == LoginFacade.ROLE_APPLICANT && i.getApplicantId() != ((Applicant)user).getApplicantId()) {
            isAdd = false;
        }
        Employer employer = i.getEmployer();
        if (LoginFacade.ROLE == LoginFacade.ROLE_EMPLOYER && employer == null) {
            isAdd = false;
        } else {
            if (LoginFacade.ROLE == LoginFacade.ROLE_EMPLOYER && employer.getEmployerId() != ((Employer)user).getEmployerId()) {
                isAdd = false;
            }
        }
        return isAdd;
    }
    
    public void filterAllInterview(DefaultListModel<Interview> interviewModel) {
        interviewModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.INTERVIEW_TYPE).forEach( i -> {
            if (isShowInterview((Interview)i)) {
                interviewModel.addElement((Interview) i);
            }
        });
    }

    public void filterOpenInterview(DefaultListModel<Interview> interviewModel) {
        interviewModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.INTERVIEW_TYPE).forEach( i -> {
            if (isShowInterview((Interview)i) && !((Interview)i).isInterviewPassed()) {
                interviewModel.addElement((Interview) i);
            }
        });
    }

    public void filterPassedInterview(DefaultListModel<Interview> interviewModel) {
        interviewModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.INTERVIEW_TYPE).forEach( i -> {
            if (isShowInterview((Interview)i) && ((Interview)i).isInterviewPassed()) {
                interviewModel.addElement((Interview) i);
            }
        });
    }
    
    public void filterAllResume(DefaultListModel<Resume> resumeModel) {
        resumeModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.RESUME_TYPE).forEach( r -> {
            resumeModel.addElement((Resume) r);
        });
    }

    public void filterOpenResume(DefaultListModel<Resume> resumeModel) {
        resumeModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.RESUME_TYPE)
                .stream()
                .filter(r -> ((Resume)r).isInSearch())
                .forEach( r -> {
                    resumeModel.addElement((Resume) r);
                });
    }

    public static Resume[] getUnemployedResumes() {
        List<Resume> list =  EntityRepository.getInstance()
                .getAll(EntityRepository.RESUME_TYPE)
                .stream()
                .filter(r -> ((Resume)r).isInSearch())
                .map( r -> (Resume)r)
                .collect(Collectors.toList());
        Resume[] arr = new Resume[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static Vacancy[] getOpenVacancies() {
        List<Vacancy> list =  EntityRepository.getInstance()
                .getAll(EntityRepository.VACANCY_TYPE)
                .stream()
                .filter(r -> ((Vacancy)r).isOpen())
                .map( r -> (Vacancy)r)
                .collect(Collectors.toList());
        Vacancy[] arr = new Vacancy[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            arr[i] = list.get(i);
        }
        return arr;
    }
    
    public static Person[] getAllEvaluatedPersons() {
        List<Person> list = PersonRepository.getInstance().getAllNonManagers();
        Person[] arr = new Person[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            arr[i] = list.get(i);
        }
        return arr;
    }
    
    public void filterCloseResume(DefaultListModel<Resume> resumeModel) {
        resumeModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.RESUME_TYPE)
                .stream()
                .filter(r -> !((Resume)r).isInSearch())
                .forEach( r -> {
                    resumeModel.addElement((Resume) r);
                });
    }

    public void filterOwnResume(DefaultListModel<Resume> resumeModel) {
        resumeModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.RESUME_TYPE)
                .stream()
                .filter(r -> ((Resume)r).getApplicantId() == ((Applicant)user).getApplicantId())
                .forEach( r -> {
                    resumeModel.addElement((Resume) r);
                });
    }
    
    public void filterAllVacancy(DefaultListModel<Vacancy> vacancyModel) {
        vacancyModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.VACANCY_TYPE)
                .forEach( v -> {
                    vacancyModel.addElement((Vacancy) v);
                });
    }

    public void filterOpenVacancy(DefaultListModel<Vacancy> vacancyModel) {
        vacancyModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.VACANCY_TYPE)
                .stream()
                .filter( v -> ((Vacancy) v).getStatus() == Vacancy.STATUS_OPEN)
                .forEach( v -> {
                    vacancyModel.addElement((Vacancy) v);
                });
    }

    public void filterCloseVacancy(DefaultListModel<Vacancy> vacancyModel) {
        vacancyModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.VACANCY_TYPE)
                .stream()
                .filter( v -> ((Vacancy) v).getStatus() == Vacancy.STATUS_CLOSE)
                .forEach( v -> {
                    vacancyModel.addElement((Vacancy) v);
                });
    }

    public void filterOwnVacancy(DefaultListModel<Vacancy> vacancyModel) {
        vacancyModel.clear();
        EntityRepository.getInstance().getAll(EntityRepository.VACANCY_TYPE)
                .stream()
                .filter( v -> ((Vacancy) v).getEmployerId() == ((Employer)user).getEmployerId())
                .forEach( v -> {
                    vacancyModel.addElement((Vacancy) v);
                });
    }
    
    public boolean duplicatePosition(String position, int employerId, int vacancyId) {
        Object o = EntityRepository.getInstance().getAll(EntityRepository.VACANCY_TYPE)
                .stream()
                .filter( v -> 
                        ((Vacancy)v).getEmployerId() == employerId && 
                        ((Vacancy)v).getPosition().equals(position) &&
                        ((Vacancy)v).getId() != vacancyId)
                .findFirst().orElse(null);
        return o != null;
    }
        
    public void setupMarkModel(DefaultListModel<Mark> markModel, int managerId) {
        markModel.clear();
        EntityRepository.getInstance()
                .getAll(EntityRepository.MARK_TYPE)
                .stream()
                .filter( m -> ((Mark)m).getManagerId() == managerId)
                .map( m -> (Mark)m )
                .forEach(markModel::addElement);
    }
}
