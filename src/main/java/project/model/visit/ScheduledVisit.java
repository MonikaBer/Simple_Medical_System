package project.model.visit;

import project.model.person.Doctor;

import java.util.Date;

public class ScheduledVisit extends Visit {

    private Date date;
    private String type;
    private Doctor doctor;
    private String reason;
    private Double payment;

    public ScheduledVisit(Date date, String type, Doctor doctor, String reason, Double payment) {
        super(date, type, doctor);
        this.reason = reason;
        this.payment = payment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }
}
