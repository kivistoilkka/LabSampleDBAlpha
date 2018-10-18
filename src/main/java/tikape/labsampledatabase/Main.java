package tikape.labsampledatabase;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.dao.Database;
import tikape.dao.HostDao;
import tikape.dao.SampleDao;

public class Main {

    public static void main(String[] args) throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (!(dbUrl != null && dbUrl.length() > 0)) {
            dbUrl = "jdbc:sqlite:sampledb.db";
        }
        Database database = new Database(dbUrl);
        HostDao hosts = new HostDao(database);
        SampleDao samples = new SampleDao(database);

        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Spark.get("/hosts", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("hosts", hosts.findAll());

            return new ModelAndView(map, "hosts");
        }, new ThymeleafTemplateEngine());

        Spark.post("/hosts/addhost", (req, res) -> {
            // Lisätään toiminnallisuus syötteiden olemassaolon tarkistukseen

            Integer captureYear = 0;
            try {
                captureYear = Integer.parseInt(req.queryParams("captureYear"));
            } catch (NumberFormatException e) {
                res.redirect("/hosts");
                return "";
            }

            Host host = new Host(req.queryParams("code"), req.queryParams("species"),
                    req.queryParams("sex"), req.queryParams("ageGroup"),
                    captureYear, req.queryParams("captureSite"));
            hosts.saveOrUpdate(host);

            res.redirect("/hosts");
            return "";
        });

        Spark.post("/hosts/deletehost", (req, res) -> {
            String code = req.queryParams("code");
            hosts.delete(code);

            res.redirect("/hosts");
            return "";
        });

        Spark.get("/samples/:code", (req, res) -> {
            HashMap map = new HashMap<>();
            String hostCode = req.params(":code");
            map.put("host", hosts.findOne(hostCode));
            map.put("samples", samples.findAllForHost(hostCode));

            return new ModelAndView(map, "samples");
        }, new ThymeleafTemplateEngine());

        Spark.post("/samples/addsample", (req, res) -> {
            String hostCode = req.queryParams("hostCode");
            
            // Lisätään toiminnallisuus syötteiden olemassaolon tarkistamiseen

            Boolean dnaIsolated = false;
            if (req.queryParams("dnaIsolated") != null) {
                dnaIsolated = true;
            }
            Boolean rnaIsolated = false;
            if (req.queryParams("rnaIsolated") != null) {
                rnaIsolated = true;
            }

            Sample sample = new Sample(req.queryParams("sampleCode"), hostCode,
                    req.queryParams("organ"), dnaIsolated, rnaIsolated);
            samples.saveOrUpdate(sample);

            res.redirect("/samples/" + hostCode);
            return "";
        });

        Spark.post("/samples/deletesample", (req, res) -> {
            String hostCode = req.queryParams("hostCode");
            String sampleCode = req.queryParams("sampleCode");
            samples.delete(sampleCode);

            res.redirect("/samples/" + hostCode);
            return "";
        });

    }
}
