package project.model.visit;

import project.model.person.Doctor;

import java.util.Date;

public abstract class Visit {
    private String date;
    private String type;
    private Doctor doctor;

    public Visit(String date, String type, Doctor doctor) {
        this.date = date;
        this.type = type;
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
