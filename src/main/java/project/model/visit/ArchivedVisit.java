package project.model.visit;

import project.model.person.Doctor;

import java.util.Date;

public class ArchivedVisit extends Visit {

    private String shortDescription;

    public ArchivedVisit(Date date, String type, Doctor doctor, String shortDescription) {
        super(date, type, doctor);
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
