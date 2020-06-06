package project.model;

public class MedicalTestResult {
	private String date;
	private String type;
	private Float result;
	private String units;

	public MedicalTestResult(String date, String type, Float result, String units) {
		this.date = date;
		this.type = type;
		this.result = result;
		this.units = units;
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

	public Float getResult() {
		return result;
	}

	public void setResult(Float result) {
		this.result = result;
	}

	public String getUnits() {
		return units;
	}
	
	public void setUnits(String units) {
		this.units = units;
	}
}
