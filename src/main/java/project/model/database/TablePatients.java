package project.model.database;

import project.model.person.Patient;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TablePatients implements DatabaseInterface {

    private DatabaseMetaData dbmd = null;
    private Statement statement = null;
    private ResultSet result = null;
    private ResultSet result2 = null;

    public TablePatients(DatabaseMetaData dbmd, Statement statement) {
        this.dbmd = dbmd;
        this.statement = statement;
    }

    public void create() throws SQLException {
        this.result = this.dbmd.getTables(null, null, "PATIENTS", null);
        if (!this.result.next()) {
            this.statement.execute("CREATE TABLE PATIENTS" +
                    "(" +
                    "patient_name		VARCHAR(30) NOT NULL," +
                    "patient_surname	VARCHAR(30) NOT NULL," +
                    "patient_pesel      VARCHAR(11) NOT NULL PRIMARY KEY," +
                    "insurance          VARCHAR(20) NOT NULL," +
                    "address            CLOB" +
                    ")");
        }
    }

    public void delete() throws SQLException {
        this.statement.executeUpdate("DROP TABLE PATIENTS");
    }

    public void print() throws SQLException {
        System.out.println("\nRecords of PATIENTS table:");
        this.result = this.statement.executeQuery("SELECT * FROM PATIENTS");
        while (this.result.next()) {
            System.out.println(this.result.getString("patient_name") + " " +
                    this.result.getString("patient_surname") + " " + this.result.getString("patient_pesel")
                    + " " + this.result.getString("insurance") + " " + this.result.getString("address"));
        }
    }

    public boolean addPatient(Patient patient) throws SQLException {
        if (!this.ifPatientExists(patient.getPesel())) {
            this.statement.execute("INSERT INTO PATIENTS (patient_name, patient_surname, patient_pesel, " +
                    "insurance, address) VALUES('" + patient.getName() + "','" + patient.getSurname() + "','" +
                    patient.getPesel() + "','" + patient.getInsurance() + "','" + patient.getAddress() + "')");
            return true;
        }
        return false;
    }

    public boolean deletePatient(String pesel) throws SQLException {
        if (this.ifPatientExists(pesel)) {
            this.statement.execute("DELETE FROM MEDICAL_TESTS_RESULTS WHERE patient_pesel='" + pesel + "'");
            this.statement.execute("DELETE FROM SCHEDULED_VISITS WHERE patient_pesel='" + pesel + "'");
            this.statement.execute("DELETE FROM ARCHIVED_VISITS WHERE patient_pesel='" + pesel + "'");
            this.statement.execute("DELETE FROM HOSPITALISATIONS WHERE patient_pesel='" + pesel + "'");
            this.statement.execute("DELETE FROM PATIENTS WHERE patient_pesel='" + pesel + "'");
            return true;
        }
        return false;
    }

    public boolean ifPatientExists(String pesel) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM PATIENTS WHERE patient_pesel='" + pesel + "'");
        if (!this.result.next()) {
            return false;
        }
        return true;
    }

    public Patient getPatient(String pesel) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM PATIENTS WHERE patient_pesel='" + pesel + "'");
        if (this.result.next())
            return new Patient(this.result.getString("patient_name"), this.result.getString("patient_surname"),
                    this.result.getString("patient_pesel"), this.result.getString("insurance"),
                    this.result.getString("address"));
        return null;
    }

    public ArrayList<Patient> getPatients() throws SQLException {
        ArrayList<Patient> patients = new ArrayList<>();
        this.result = this.statement.executeQuery("SELECT * FROM PATIENTS");
        while (this.result.next()) {
            patients.add(new Patient(this.result.getString("patient_name"), this.result.getString("patient_surname"),
                    this.result.getString("patient_pesel"), this.result.getString("insurance"),
                    this.result.getString("address")));
        }
        return patients;
    }

    public void updatePatient(Patient patient) throws SQLException {
        this.statement.executeUpdate("UPDATE PATIENTS SET patient_name='" + patient.getName() +
                "', patient_surname='" + patient.getSurname() + "', insurance='" + patient.getInsurance() +
                "', address='" + patient.getAddress() + "' WHERE patient_pesel='" + patient.getPesel() + "'");
    }
}
