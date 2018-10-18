package tikape.labsampledatabase;

public class Sample {
    private String code;
    private String hostCode;
    private String organ;
    private Boolean dnaIsolated;
    private Boolean rnaIsolated;

    public Sample(String code, String hostCode, String organ, Boolean dnaIsolated, Boolean rnaIsolated) {
        this.code = code;
        this.hostCode = hostCode;
        this.organ = organ;
        this.dnaIsolated = dnaIsolated;
        this.rnaIsolated = rnaIsolated;
    }

    public String getCode() {
        return code;
    }

    public String getHostCode() {
        return hostCode;
    }

    public String getOrgan() {
        return organ;
    }

    public Boolean getDnaIsolated() {
        return dnaIsolated;
    }

    public Boolean getRnaIsolated() {
        return rnaIsolated;
    }
    
    
}
