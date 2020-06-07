package project.model.visit;

import project.model.person.Doctor;

public class ArchivedVisit extends Visit {

    private String description;

    public ArchivedVisit(String date, String type, Doctor doctor, String description) {
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
