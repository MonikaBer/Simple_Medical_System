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

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (obj == null || this.getClass() != obj.getClass()) {
//			return false;
//		}
//		Patient other = (Patient)obj;
//		if (this.pesel == null) {
//			if (other.pesel != null) {
//				return false;
//			}
//		} else if (!this.pesel.equals(other.pesel)) {
//			return false;
//		}
//		return true;
//	}
	
	public static boolean isPeselCorrect(String p) {
		if (p.length() != 11)
			return false;
		
		char[] peselInCharArray = p.toCharArray();
		for (char c : peselInCharArray) {
			if (!Character.isDigit(c))
				return false;
		}
		
		return true;
	}
}
