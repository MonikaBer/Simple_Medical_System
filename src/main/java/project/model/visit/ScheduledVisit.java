package project.model.visit;

import project.model.person.Doctor;

public class ScheduledVisit extends Visit {

    private String time;
    private Float payment;

    public ScheduledVisit(String date, String time, String type, Doctor doctor, Float payment) {
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

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }
}
