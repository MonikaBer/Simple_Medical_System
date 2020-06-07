package project.model.database;

import project.model.person.Doctor;
import project.model.visit.ArchivedVisit;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableArchivedVisits implements DatabaseInterface {

    private DatabaseMetaData dbmd = null;
    private Statement statement = null;
    private ResultSet result = null;
    private ResultSet result2 = null;

    public TableArchivedVisits(DatabaseMetaData dbmd, Statement statement) {
        this.dbmd = dbmd;
        this.statement = statement;
    }

    public void create() throws SQLException {
        this.result = this.dbmd.getTables(null, null, "ARCHIVED_VISITS", null);
        if (!this.result.next()) {
            this.statement.execute("CREATE TABLE ARCHIVED_VISITS" +
                    "(" +
                    "id             INT NOT NULL PRIMARY KEY" +
                    "			    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "date		    VARCHAR(50) NOT NULL," +
                    "type   	    VARCHAR(50) NOT NULL," +
                    "description    CLOB NOT NULL," +
                    "patient_pesel  VARCHAR(11) NOT NULL," +
                    "doctor_pesel   VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (patient_pesel) REFERENCES PATIENTS(patient_pesel)," +
                    "FOREIGN KEY (doctor_pesel) REFERENCES DOCTORS(doctor_pesel)" +
                    ")");
        }
    }

    public void delete() throws SQLException {
        this.statement.executeUpdate("DROP TABLE ARCHIVED_VISITS");
    }

    public void print() throws SQLException {
        System.out.println("\nRecords of ARCHIVED_VISITS table:");
        this.result = this.statement.executeQuery("SELECT * FROM ARCHIVED_VISITS");
        while (this.result.next()) {
            System.out.println(this.result.getString("date") + " " + this.result.getString("type") + " " +
                    this.result.getString("description") + " " + this.result.getString("patient_pesel") +
                    " " + this.result.getString("doctor_pesel"));
        }
    }

    public boolean addArchivedVisit(String pesel, ArchivedVisit archivedVisit) throws SQLException {
        if (!this.ifArchivedVisitExists(pesel, archivedVisit.getDate(), archivedVisit.getDoctor().getPesel())) {
            this.statement.execute("INSERT INTO ARCHIVED_VISITS (date, type, description, patient_pesel, " +
                    "doctor_pesel)" + " VALUES" + "('" + archivedVisit.getDate() + "','" + archivedVisit.getType()
                    + "','" + archivedVisit.getDescription() + "','" + pesel + "','" +
                    archivedVisit.getDoctor().getPesel() + "')");
            return true;
        }
        return false;
    }

    public boolean deleteArchivedVisit(String patientPesel, String date, String doctorPesel) throws SQLException {
        if (this.ifArchivedVisitExists(patientPesel, date, doctorPesel)) {
            this.statement.execute("DELETE FROM ARCHIVED_VISITS WHERE patient_pesel='" + patientPesel +
                    "' AND doctor_pesel='" + doctorPesel + "'AND date='" + date + "'");
            return true;
        }
        return false;
    }

    public boolean ifArchivedVisitExists(String patientPesel, String date, String doctorPesel) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM ARCHIVED_VISITS WHERE patient_pesel='" +
                patientPesel + "' AND date='" + date + "' AND doctor_pesel='" + doctorPesel + "'");
        if (!this.result.next()) {
            return false;
        }
        return true;
    }

    public ArrayList<ArchivedVisit> getPatientArchivedVisits(String pesel) throws SQLException {
        ArrayList<ArchivedVisit> archivedVisits = new ArrayList<>();
        this.result = this.statement.executeQuery("SELECT * FROM ARCHIVED_VISITS NATURAL JOIN DOCTORS WHERE " +
                "ARCHIVED_VISITS.patient_pesel='" + pesel + "'");
        while (this.result.next()) {
            Doctor doctor = new Doctor(this.result.getString("doctor_name"),
                    this.result.getString("doctor_surname"), this.result.getString("doctor_pesel"),
                    this.result.getString("specialisation"));
            archivedVisits.add(new ArchivedVisit(this.result.getString("date"),
                    this.result.getString("type"), doctor, this.result.getString("description")));
        }
        return archivedVisits;
    }
}
