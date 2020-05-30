package project.model;

import java.util.Date;

public class Hospitalisation {
    private Date from;
    private Date to;
    private String reason;

    public Hospitalisation(Date from, Date to, String reason) {
        this.from = from;
        this.to = to;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
