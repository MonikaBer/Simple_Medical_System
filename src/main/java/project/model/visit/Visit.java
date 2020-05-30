package project.model.visit;

import project.model.person.Doctor;

import java.util.Date;

public abstract class Visit {
    private Date date;
    private String type;
    private Doctor doctor;

    public Visit(Date date, String type, Doctor doctor) {
        this.date = date;
        this.type = type;
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
