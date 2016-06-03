package recruitment.repository;

import recruitment.models.Interview;
import recruitment.models.Mark;
import recruitment.models.Resume;
import recruitment.models.Vacancy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityRepository implements BaseRepository {
    private static EntityRepository instance;
    
    public static final int MARK_TYPE = 1;
    public static final int INTERVIEW_TYPE = 2;
    public static final int VACANCY_TYPE = 3;
    public static final int RESUME_TYPE = 4;
    
    private List<Mark> marks;
    private List<Interview> interviews;
    private List<Vacancy> vacancies;
    private List<Resume> resumes;

    public static EntityRepository getInstance() {
        if (instance == null) {
            instance = new EntityRepository();
        }
        return instance;
    }
    
    private EntityRepository() {
        addMarks();
        addInterviews();
        addVacancies();
        addResumes();
    }

    private void addMarks() {
        marks = new ArrayList<>(3);
        Mark m = new Mark(1, 5, 5);
        m.setId(1);
        m.setComment("Очень хорошо!");
        marks.add(m);
        m = new Mark(2, 7, 3);
        m.setId(2);
        m.setComment("Плохо.");
        marks.add(m);
        m = new Mark(3, 6, 1);
        m.setId(3);
        m.setComment("Ужасно.");
        marks.add(m);
        m = new Mark(3, 7, 4);
        m.setId(4);
        marks.add(m);
    }

    private void addInterviews() {
        interviews = new ArrayList<>(3);
        Interview i = new Interview(1, 1, Vacancy.getToday());
        i.setId(1);
        interviews.add(i);
        i = new Interview(2, 2, Vacancy.getToday());
        i.setId(2);
        interviews.add(i);
        i = new Interview(3, 3, Vacancy.getToday());
        i.setId(3);
        i.setResultEmployer(Interview.RESULT_POSITIVE);
        interviews.add(i);
    }
    
    private void addVacancies() {
        vacancies = new ArrayList<>(3);
        Vacancy v = new Vacancy(1, "Должность1", "Какие-то требования", 100000);
        v.setId(1);
        vacancies.add(v);
        v = new Vacancy(2, "Должность2", "Какие-то очень жесткие требования", 999999);
        v.setId(2);
        vacancies.add(v);
        v = new Vacancy(3, "Должность3", "Нет требований", 3.99);
        v.setId(3);
        v.setStatus(Vacancy.STATUS_CLOSE, 3);
        vacancies.add(v);
    }

    private void addResumes() {
        resumes = new ArrayList<>();
        Resume r = new Resume(
                1,
                "10 лет опыта в конторе рога и копыта", 
                "скилл1, скилл2, скилл3", 
                "3 Высших образования"
        );
        r.setId(1);
        r.setDescription("дополнительная информация 1");
        resumes.add(r);
        r = new Resume(
                2,
                "1 год опыта работы дворником",
                "скилл - подметать", 
                "среднее дворниковое"
        );
        r.setId(2);
        r.setDescription("я очень хороший дворник");
        resumes.add(r);
        r = new Resume(
                3,
                "опыта нет",
                "навыков нет", 
                "еще в школе учусь"
        );
        r.setId(3);
        r.setDescription("просто хочу работать");
        r.setInSearch(false, 3);
        resumes.add(r);
    }

    @Override
    public boolean save(Object o) {
        if (o instanceof Mark) {
            Mark m = (Mark) o;
            m.setId(marks.size()+1);
            return marks.add(m); 
        }
        if (o instanceof Interview) {
            Interview i = (Interview)o;
            i.setId(interviews.size()+1);
            return interviews.add(i);
        }
        if (o instanceof Vacancy) {
            Vacancy v = (Vacancy) o;
            v.setId(vacancies.size()+1);
            return vacancies.add(v);
        }
        if (o instanceof Resume) {
            Resume r = (Resume)o;
            r.setId(resumes.size()+1);
            return resumes.add(r);
        }
        throw new IllegalArgumentException();
    }

    public List<?> getAll(int type) {
        switch (type) {
            case MARK_TYPE:
                return marks;
            case VACANCY_TYPE:
                return vacancies;
            case INTERVIEW_TYPE:
                return interviews;
            case RESUME_TYPE:
                return resumes;
            default:
                throw new IllegalArgumentException();
        }
    }


    public Object getById(int id, int type) {
        switch (type) {
            case MARK_TYPE:
                return marks.get(id-1);
            case VACANCY_TYPE:
                return vacancies.get(id-1);
            case INTERVIEW_TYPE:
                return interviews.get(id-1);
            case RESUME_TYPE:
                return resumes.get(id-1);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void remove(int id, int type) {
        switch (type) {
            case MARK_TYPE:
                marks.remove(id-1);
                break;
            case VACANCY_TYPE:
                vacancies.remove(id-1);
                break;
            case INTERVIEW_TYPE:
                interviews.remove(id-1);
                break;
            case RESUME_TYPE:
                resumes.remove(id-1);
                break;
            default:
                throw new IllegalArgumentException();
        }
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
