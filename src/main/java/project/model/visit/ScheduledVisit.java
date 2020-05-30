package project.model.visit;

import project.model.person.Doctor;

import java.util.Date;

public class ScheduledVisit extends Visit {

    private String time;
    private Double payment;

    public ScheduledVisit(Date date, String time, String type, Doctor doctor, Double payment) {
        super(date, type, doctor);
        this.time = time;
        this.payment = payment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }
}
