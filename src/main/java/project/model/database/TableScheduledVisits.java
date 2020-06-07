package project.model.database;

import project.AppException;
import project.model.person.Doctor;
import project.model.visit.ScheduledVisit;

import javax.print.Doc;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableScheduledVisits implements DatabaseInterface {

    private DatabaseMetaData dbmd = null;
    private Statement statement = null;
    private ResultSet result = null;
    private ResultSet result2 = null;

    public TableScheduledVisits(DatabaseMetaData dbmd, Statement statement) {
        this.dbmd = dbmd;
        this.statement = statement;
    }

    public void create() throws SQLException {
        this.result = this.dbmd.getTables(null, null, "SCHEDULED_VISITS", null);
        if (!this.result.next()) {
            this.statement.execute("CREATE TABLE SCHEDULED_VISITS" +
                    "(" +
                    "id             INT NOT NULL PRIMARY KEY" +
                    "			    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "date		    VARCHAR(50) NOT NULL," +
                    "time           VARCHAR(20) NOT NULL," +
                    "type   	    VARCHAR(30) NOT NULL," +
                    "payment        VARCHAR(20) NOT NULL," +
                    "patient_pesel  VARCHAR(11) NOT NULL," +
                    "doctor_pesel   VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (patient_pesel) REFERENCES PATIENTS(patient_pesel)," +
                    "FOREIGN KEY (doctor_pesel) REFERENCES DOCTORS(doctor_pesel)" +
                    ")");
        }
    }

    public void delete() throws SQLException {
        this.statement.executeUpdate("DROP TABLE SCHEDULED_VISITS");
    }

    public void print() throws SQLException {
        System.out.println("\nRecords of SCHEDULED_VISITS table:");
        this.result = this.statement.executeQuery("SELECT * FROM SCHEDULED_VISITS");
        while (this.result.next()) {
            System.out.println(this.result.getString("date") + " " + this.result.getString("time") + " " +
                    this.result.getString("type") + " " + this.result.getString("payment") + " " +
                    this.result.getString("patient_pesel") + " " + this.result.getString("doctor_pesel"));
        }
    }

    public boolean addScheduledVisit(String patientPesel, ScheduledVisit scheduledVisit) throws SQLException {
        if (!this.ifScheduledVisitExists(patientPesel, scheduledVisit.getDate(), scheduledVisit.getTime())) {
            this.statement.execute("INSERT INTO SCHEDULED_VISITS (date, time, type, payment, patient_pesel, " +
                    "doctor_pesel) VALUES" + "('" + scheduledVisit.getDate() + "','" +
                    scheduledVisit.getTime() + "','" + scheduledVisit.getType() + "', '" +
                    scheduledVisit.getPayment().toString() + "','" + patientPesel + "','" +
                    scheduledVisit.getDoctor().getPesel() + "')");
            return true;
        }
        return false;
    }

    public boolean deleteScheduledVisit(String pesel, String date, String time) throws SQLException {
        if (this.ifScheduledVisitExists(pesel, date, time)) {
            this.statement.execute("DELETE FROM SCHEDULED_VISITS WHERE patient_pesel='" + pesel +
                    "' AND date='" + date + "' AND time='" + time + "'");
            return true;
        }
        return false;
    }

    public boolean ifScheduledVisitExists(String pesel, String date, String time) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM SCHEDULED_VISITS WHERE patient_pesel='" +
                pesel + "' AND date='" + date + "' AND time='" + time + "'");
        if (!this.result.next()) {
            return false;
        }
        return true;
    }

    public ArrayList<ScheduledVisit> getPatientScheduledVisits(String pesel) throws SQLException, AppException {
        ArrayList<ScheduledVisit> scheduledVisits = new ArrayList<>();
        this.result = this.statement.executeQuery("SELECT * FROM SCHEDULED_VISITS NATURAL JOIN DOCTORS WHERE " +
                "SCHEDULED_VISITS.patient_pesel='" + pesel + "'");
        while (this.result.next()) {
            Doctor doctor = new Doctor(this.result.getString("doctor_name"),
                    this.result.getString("doctor_surname"), this.result.getString("doctor_pesel"),
                    this.result.getString("specialisation"));
            scheduledVisits.add(new ScheduledVisit(this.result.getString("date"),
                    this.result.getString("time"), this.result.getString("type"),
                    doctor, Float.parseFloat(this.result.getString("payment"))));
        }
        return scheduledVisits;
    }
}
