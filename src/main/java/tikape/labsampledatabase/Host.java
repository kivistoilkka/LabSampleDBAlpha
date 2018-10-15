package tikape.labsampledatabase;

public class Host {
    private String code;
    private String species;
    private String sex;
    private String ageGroup;
    private Integer captureYear;
    private String captureSite;

    public Host(String code, String species, String sex, String ageGroup, int captureYear, String captureSite) {
        this.code = code;
        this.species = species;
        this.sex = sex;
        this.ageGroup = ageGroup;
        this.captureYear = captureYear;
        this.captureSite = captureSite;
    }

    public String getCode() {
        return code;
    }

    public String getSpecies() {
        return species;
    }

    public String getSex() {
        return sex;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public int getCaptureYear() {
        return captureYear;
    }

    public String getCaptureSite() {
        return captureSite;
    }
    
    
    
}
