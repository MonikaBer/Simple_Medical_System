package project.model.person;


public class Patient extends Person {

	private String insurance;
	private String address;

	public Patient(String name, String surname, String pesel, String insurance, String address){

		super(name, surname, pesel);
		this.insurance = insurance;
		this.address = address;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
