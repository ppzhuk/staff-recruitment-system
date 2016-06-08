package recruitment.facade;

import recruitment.models.Person;
import recruitment.repository.ApplicantRepository;
import recruitment.repository.EmployerRepository;
import recruitment.repository.EntityRepository;
import recruitment.repository.ManagerRepository;
import recruitment.repository.PersonRepository;

/**
 * Created by Zhuk Pavel on 06.06.2016.
 */
public class LoginFacade {
    public static final int ROLE_UNDEFINED = -1;
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_EMPLOYER = 2;
    public static final int ROLE_APPLICANT = 3;
    public static int ROLE = ROLE_UNDEFINED;

    public static final String ERROR_EMPTY_FIELDS = "Поля со * не должны быть пустыми.<br>";
    public static final String ERROR_TOO_SHORT_LOGIN_OR_PASSWORD = "Логин и пароль должны быть не меньше 4 символовов.<br>";
    public static final String ERROR_LOGIN_IS_TAKEN = "Логин уже занят.<br>";
    public static final String ERROR_EMAIL = "Не верен формат почты.<br>";
    public static final String ERROR_PASSWORDS_NOT_EQUALS = "Пароли не совпадают.<br>";
    public static final String ERROR_EMPLOYER_SAVE_OBJECT = "Не удалось сохранить работодателя. Обратитесь к Администратору.<br>";
    public static final String ERROR_APPLICANT_SAVE_OBJECT = "Не удалось сохранить соискателя. Обратитесь к Администратору.<br>";
    public static final String ERROR_RESUME_SAVE_OBJECT = "Не удалось сохранить резюме. Обратитесь к Администратору.<br>";

    private ManagerRepository managerRepository = ManagerRepository.getInstance();
    private EmployerRepository employerRepository = EmployerRepository.getInstance();
    private ApplicantRepository applicantRepository = ApplicantRepository.getInstance();
    private PersonRepository personRepository = PersonRepository.getInstance();
    private EntityRepository entityRepository = EntityRepository.getInstance();
    
    
    public String validateNewEmployer(String fio, String login, String pass, String submitPass, String email, String companyName) {
        return checkIsEmptyFields(fio, login, pass, submitPass, email, companyName) +
                checkPersonData(login, pass, submitPass, email);
    }

    private String checkPersonData(String login, String pass, String submitPass, String email) {
        return checkIsLoginOrPassTooShort(login, pass) +
                checkIsLoginNotUnique(login) +
                checkEmail(email) +
                checkIsPasswordsEquals(pass, submitPass);
    }
    
    private String checkIsEmptyFields(String... params) {
        for (String p : params) {
            if (p.equals("")) {
                return ERROR_EMPTY_FIELDS;        
            }
        }
        return "";
    }

    private String checkIsLoginOrPassTooShort(String login, String pass) {
        return pass.length() < 4 ||
               login.length() < 4
               ? ERROR_TOO_SHORT_LOGIN_OR_PASSWORD : "";
    }

    private String checkIsLoginNotUnique(String login) {
        Person prsn = personRepository
                .getAll()
                .stream()
                .filter(p -> p.getLogin().equals(login))
                .findFirst()
                .orElse(null);
        return prsn != null ? ERROR_LOGIN_IS_TAKEN : "";
    }

    private String checkEmail(String email) {
        return !email.contains("@") ? ERROR_EMAIL : "";
    }

    private String checkIsPasswordsEquals(String pass, String submitPass) {
        return !pass.equals(submitPass)
                ? ERROR_PASSWORDS_NOT_EQUALS : "";

    }
    
    public String validateNewApplicant(String fio, String login, String pass, String submitPass, 
                                       String email, String experience, String skills, String education) {
        return checkIsEmptyFields(fio, login, pass, submitPass, email, experience, skills, education) +
                checkPersonData(login, pass, submitPass, email);
    }
    
    public String addNewApplicant(String fio, String login, String pass, String submitPass,
                                  String email, String experience, String skills, String education,String description) {
        long applicantId = applicantRepository.save(fio, login, pass, email);
        if (applicantId < 1) {
            return LoginFacade.ERROR_APPLICANT_SAVE_OBJECT;
        } else {
            long resumeId = entityRepository.saveResume( applicantId, experience, skills, education, description);
            if (resumeId < 1) {
                return LoginFacade.ERROR_RESUME_SAVE_OBJECT;
            }
        }
        return "";
    }
    
    public String addNewEmployer(String fio, String login, String pass, String email, 
                                 String companyName, String description, String site) {
        long id = employerRepository.save(fio, login, pass, email, companyName, description, site);
        if (id < 1) {
            return LoginFacade.ERROR_EMPLOYER_SAVE_OBJECT;
        }
        return "";
    }
    
    public Person getUser(String login, String password) {
        Person p = null;
        p = managerRepository.getByLoginAndPass(login, password);
        if (p != null) {
            LoginFacade.ROLE = LoginFacade.ROLE_MANAGER;
        } else {
            p = employerRepository.getByLoginAndPass(login, password);
            if (p != null) {
                LoginFacade.ROLE = LoginFacade.ROLE_EMPLOYER;
            } else {
                p = applicantRepository.getByLoginAndPass(login, password);
                if (p != null) {
                    LoginFacade.ROLE = LoginFacade.ROLE_APPLICANT;
                } 
            }
        }
        return p;
    }

    public Person getUser(int personId) {
        switch (LoginFacade.ROLE) {
            case LoginFacade.ROLE_MANAGER:
                return managerRepository.getByPersonId(personId);
            case LoginFacade.ROLE_EMPLOYER:
                return employerRepository.getByPersonId(personId);
            case LoginFacade.ROLE_APPLICANT:
                return applicantRepository.getByPersonId(personId);
            default:
                throw new IllegalArgumentException("Undefined client role.");
        }
    }
}
