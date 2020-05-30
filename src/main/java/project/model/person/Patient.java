package project.model.person;


public class Patient extends Person {

	private String street;
	private String homeNr;
	private String flatNr;
	private String locality;
	private String postcode;
	private String phoneNr;
	private String insurance;
	
	public Patient(String name, String surname, String pesel, String street, String homeNr, String flatNr,
				   String locality, String postcode, String phoneNr, String insurance){

		super(name, surname, pesel);
		this.street = street;
		this.homeNr = homeNr;
		this.flatNr = flatNr;
		this.locality = locality;
		this.postcode = postcode;
		this.phoneNr = phoneNr;
		this.insurance = insurance;
	}

	public String getStreet() {
		return street;
	}

	public String getHomeNr() {
		return homeNr;
	}

	public String getFlatNr() {
		return flatNr;
	}

	public String getLocality() {
		return locality;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getPhoneNr() {
		return phoneNr;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setHomeNr(String homeNr) {
		this.homeNr = homeNr;
	}

	public void setFlatNr(String flatNr) {
		this.flatNr = flatNr;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
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
