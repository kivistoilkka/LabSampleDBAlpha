package tikape.labsampledatabase;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.dao.Database;
import tikape.dao.HostDao;

public class Main {
    public static void main(String[] args) throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (!(dbUrl != null && dbUrl.length() > 0)) {
            dbUrl = "jdbc:sqlite:sampledb.db";
        }
        Database database = new Database(dbUrl);
        HostDao hosts = new HostDao(database);
        
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Spark.get("/hosts", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("hosts", hosts.findAll());
            
            return new ModelAndView(map, "hosts");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/hosts/addhost", (req, res) -> {
            Host host = new Host(req.queryParams("code"), req.queryParams("species"), 
                    req.queryParams("sex"), req.queryParams("ageGroup"), 
                    Integer.parseInt(req.queryParams("captureYear")), 
                    req.queryParams("captureSite"));
            hosts.saveOrUpdate(host);
            
            res.redirect("/hosts");
            return "";
        });

    }
}
