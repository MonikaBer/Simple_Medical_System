package project.model.visit;

import project.model.person.Doctor;

import java.util.Date;

public class ArchivedVisit extends Visit {

    private String description;

    public ArchivedVisit(Date date, String type, Doctor doctor, String description) {
        super(date, type, doctor);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
