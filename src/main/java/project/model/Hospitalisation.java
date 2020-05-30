package project.model;

import java.util.Date;

public class Hospitalisation {

    private Date from;
    private Date to;
    private String drugs;
    private String conditions;

    public Hospitalisation(Date from, Date to, String drugs, String conditions) {
        this.from = from;
        this.to = to;
        this.drugs = drugs;
        this.conditions = conditions;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
