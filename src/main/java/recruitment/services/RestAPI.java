package recruitment.services;

import recruitment.models.Mark;
import recruitment.repository.EntityRepository;

import java.util.stream.Collectors;

import static recruitment.services.JsonUtil.json;
import static spark.Spark.*;

public class RestAPI {
    public static void start() {
        get("/marks", (req, res) -> 
                EntityRepository.getInstance().getAll(EntityRepository.MARK_TYPE), json()
        );

        get("/marks/:personId", (req, res) -> {
            String param = req.params(":personId");
            try{
                int evaluatedPersonId = Integer.parseInt(param);
                return EntityRepository.getInstance()
                        .getAll(EntityRepository.MARK_TYPE)
                        .stream()
                        .filter( m -> ((Mark)m).getEvaluatedPersonId() == evaluatedPersonId)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                res.status(400);
                return "No user with id "+param+" found";
            }
        }, json());

        post("/marks", (req, res) -> {
            long id = EntityRepository.getInstance().save(new Mark(
                    Integer.parseInt(req.queryParams("managerId")),
                    Integer.parseInt(req.queryParams("evaluatedPersonId")),
                    Integer.parseInt(req.queryParams("mark")),
                    req.queryParams("comment")
            ));
            
            return "add mark with id: " + id;
        }, json());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(e.getLocalizedMessage());
        });
    }

}
