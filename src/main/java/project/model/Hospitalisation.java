package project.model;

public class Hospitalisation {
    private String from;
    private String to;
    private String reason;

    public Hospitalisation(String from, String to, String reason) {
        this.from = from;
        this.to = to;
        this.reason = reason;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
