package project.model.database;

import project.model.MedicalTestResult;
import project.model.person.Patient;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableMedicalTestsResults implements DatabaseInterface {

    private DatabaseMetaData dbmd = null;
    private Statement statement = null;
    private ResultSet result = null;
    private ResultSet result2 = null;

    public TableMedicalTestsResults(DatabaseMetaData dbmd, Statement statement) {
        this.dbmd = dbmd;
        this.statement = statement;
    }

    public void create() throws SQLException {
        this.result = this.dbmd.getTables(null, null, "MEDICAL_TESTS_RESULTS", null);
        if (!this.result.next()) {
            this.statement.execute("CREATE TABLE MEDICAL_TESTS_RESULTS" +
                    "(" +
                    "id             INT NOT NULL PRIMARY KEY" +
                    "			    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "date		    VARCHAR(50) NOT NULL," +
                    "type   	    VARCHAR(30) NOT NULL," +
                    "result         VARCHAR(20) NOT NULL," +
                    "units          VARCHAR(20) NOT NULL," +
                    "patient_pesel  VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (patient_pesel) REFERENCES PATIENTS(patient_pesel)" +
                    ")");
        }
    }

    public void delete() throws SQLException {
        this.statement.executeUpdate("DROP TABLE MEDICAL_TESTS_RESULTS");
    }

    public void print() throws SQLException {
        System.out.println("\nRecords of MEDICAL_TESTS_RESULTS table:");
        this.result = this.statement.executeQuery("SELECT * FROM MEDICAL_TESTS_RESULTS");
        while (this.result.next()) {
            System.out.println(this.result.getString("date") + " " + this.result.getString("type") + " " +
                    this.result.getString("result") + " " + this.result.getString("units") + " " +
                    this.result.getString("patient_pesel"));
        }
    }

    public boolean addMedicalTestResult(String pesel, MedicalTestResult medicalTestResult) throws SQLException {
        if (!this.ifMedicalTestResultExists(pesel, medicalTestResult.getDate(), medicalTestResult.getType())) {
            this.statement.execute("INSERT INTO MEDICAL_TESTS_RESULTS (date, type, result, units, patient_pesel) " +
                    "VALUES" + "('" + medicalTestResult.getDate() + "','" + medicalTestResult.getType() +
                    "','" + medicalTestResult.getResult().toString() + "', '" + medicalTestResult.getUnits() + "','" +
                    pesel + "')");
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteMedicalTestResult(String pesel, String date, String type) throws SQLException {
        if (this.ifMedicalTestResultExists(pesel, date, type)) {
            this.statement.execute("DELETE FROM MEDICAL_TESTS_RESULTS WHERE patient_pesel='" + pesel + "' AND date='" +
                    date + "' AND type='" + type + "'");
            return true;
        } else {
            return false;
        }
    }

    public boolean ifMedicalTestResultExists(String pesel, String date, String type) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM MEDICAL_TESTS_RESULTS WHERE patient_pesel='" +
                pesel + "' AND date='" + date + "' AND type='" + type + "'");
        if (!this.result.next()) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<MedicalTestResult> getPatientMedicalTestsResults(String pesel) throws SQLException {
        ArrayList<MedicalTestResult> medicalTestResults = new ArrayList<>();
        this.result = this.statement.executeQuery("SELECT * FROM MEDICAL_TESTS_RESULTS WHERE patient_pesel='" +
                pesel + "'");
        while (this.result.next()) {
            medicalTestResults.add(new MedicalTestResult(this.result.getString("date"),
                    this.result.getString("type"), Float.parseFloat(this.result.getString("result")),
                    this.result.getString("units")));
        }
        return medicalTestResults;
    }
}
