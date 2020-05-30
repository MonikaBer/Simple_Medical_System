package project.model;

import java.util.Date;


public class MedicalTestResult {
	private String date;
	private String type;
	private Double result;
	private String units;

	public MedicalTestResult(String date, String type, Double result, String units) {
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

	public Double getResult() {
		return result;
	}
	
	public void setResult(Double result) {
		this.result = result;
	}

	public String getUnits() {
		return units;
	}
	
	public void setUnits(String units) {
		this.units = units;
	}
}
