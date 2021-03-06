package recruitment.repository;

import recruitment.mappers.ExternalMarkMapper;
import recruitment.mappers.InterviewMapper;
import recruitment.mappers.MarkMapper;
import recruitment.mappers.ResumeMapper;
import recruitment.mappers.VacancyMapper;
import recruitment.models.Interview;
import recruitment.models.Mark;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import java.util.List;

public class EntityRepository implements BaseRepository {
    private static EntityRepository instance;

    public static final int MARK_TYPE = 1;
    public static final int INTERVIEW_TYPE = 2;
    public static final int VACANCY_TYPE = 3;
    public static final int RESUME_TYPE = 4;

    private MarkMapper markMapper;
    private InterviewMapper interviewMapper;
    private VacancyMapper vacancyMapper;
    private ResumeMapper resumeMapper;

    public static EntityRepository getInstance() {
        if (instance == null) {
            instance = new EntityRepository();
        }
        return instance;
    }

    private EntityRepository() {
        markMapper = new MarkMapper();
        interviewMapper = new InterviewMapper();
        vacancyMapper = new VacancyMapper();
        resumeMapper = new ResumeMapper();
    }


    @Override
    public long save(Object o) {
        if (o instanceof Mark) {
            return markMapper.save((Mark) o);
        }
        if (o instanceof Interview) {
            return interviewMapper.save((Interview) o);
        }
        if (o instanceof Vacancy) {
            return vacancyMapper.save((Vacancy) o);
        }
        if (o instanceof Resume) {
            return resumeMapper.save((Resume) o);
        }
        throw new IllegalArgumentException();
    }
    
    public long saveResume(long applicantId, String experience, String skills, String education, String description) {
        Resume r = new Resume((int)applicantId, experience, skills, education, description);
        return save(r);
    }

    public List<?> getAll(int type) {
        switch (type) {
            case MARK_TYPE:
                return markMapper.getAll();
            case VACANCY_TYPE:
                return vacancyMapper.getAll();
            case INTERVIEW_TYPE:
                return interviewMapper.getAll();
            case RESUME_TYPE:
                return resumeMapper.getAll();
            default:
                throw new IllegalArgumentException();
        }
    }

    public List<Mark> getPersonMarks(int evaluatedPersonId) {
        List<Mark> storedMarks = markMapper.getPersonMarks(evaluatedPersonId);
        if (storedMarks.size() == 0) {
            String pathToSource = "src\\main\\resources\\marks.json";
            List<Mark> externalMarks = new ExternalMarkMapper(pathToSource).getPersonMarks(evaluatedPersonId);

            System.out.println("Get marks from external storage. Count: " + externalMarks.size());
            
            storedMarks.addAll(externalMarks);
        }
        return storedMarks;
    }

    public Object getById(int id, int type) {
        switch (type) {
            case MARK_TYPE:
                return markMapper.getById(id);
            case VACANCY_TYPE:
                return vacancyMapper.getById(id);
            case INTERVIEW_TYPE:
                return interviewMapper.getById(id);
            case RESUME_TYPE:
                return resumeMapper.getById(id);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void remove(int id, int type) {
        switch (type) {
            case MARK_TYPE:
                markMapper.delete(id);
                break;
            case VACANCY_TYPE:
                vacancyMapper.delete(id);
                break;
            case INTERVIEW_TYPE:
                interviewMapper.delete(id);
                break;
            case RESUME_TYPE:
                resumeMapper.delete(id);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void update(Object o, int type) {
        switch (type) {
            case MARK_TYPE:
                markMapper.update((Mark)o);
                break;
            case VACANCY_TYPE:
                vacancyMapper.update((Vacancy)o);
                break;
            case INTERVIEW_TYPE:
                interviewMapper.update((Interview)o);
                break;
            case RESUME_TYPE:
                resumeMapper.update((Resume)o);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Resume getResumeByApplicantId(int applicantId) {
        return resumeMapper.getByApplicantId(applicantId);
    }
    
    @Override
    public void update(Object o) {
        throw new RuntimeException("use update(object, entityType) instead");
    }

    @Override
    public List<Mark> getAll() {
        throw new RuntimeException("use getById(id, entityType) instead");
    }

    @Override
    public Object getById(int id) {
        throw new RuntimeException("use getById(id, entityType) instead");
    }
}
