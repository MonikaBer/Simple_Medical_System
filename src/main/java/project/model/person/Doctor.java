package project.model.person;

public class Doctor extends Person {

    private String specialisation;

    public Doctor(String name, String surname, String pesel, String specialisation) {

        super(name, surname, pesel);
        this.specialisation = specialisation;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
}
