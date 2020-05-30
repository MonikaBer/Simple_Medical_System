package project;

import project.model.Hospitalisation;
import project.model.MedicalTestResult;
import project.model.person.Doctor;
import project.model.person.Patient;
import project.model.visit.ArchivedVisit;
import project.model.visit.ScheduledVisit;

import java.sql.*;

public class Database {

    private Connection connection = null;
    private DatabaseMetaData dbmd = null;
    private Statement statement = null;
    private ResultSet result = null;

    public Database() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        this.connection = DriverManager.getConnection("jdbc:derby:SimpleMedicalSystem;create=true");
        this.dbmd = this.connection.getMetaData();
        this.statement = this.connection.createStatement();

        this.createTables();
        //this.deleteTables();
    }

    public void createTables() throws SQLException {
        this.result = this.dbmd.getTables(null,null,"PATIENTS",null);
        if (!this.result.next()) {
            this.statement.execute(	"CREATE TABLE PATIENTS" +
                    "(" +
                    "name		VARCHAR(30) NOT NULL," +
                    "surname	VARCHAR(30) NOT NULL," +
                    "pesel      VARCHAR(11) NOT NULL PRIMARY KEY," +
                    "insurance  VARCHAR(20) NOT NULL," +
                    "address    CLOB" +
                    ")");

            System.out.println("Utworzono tabelę PATIENTS");
        }

        this.result = this.dbmd.getTables(null,null,"DOCTORS",null);
        if (!this.result.next()) {
            this.statement.execute(	"CREATE TABLE DOCTORS" +
                    "(" +
                    "name		        VARCHAR(30) NOT NULL," +
                    "surname	        VARCHAR(30) NOT NULL," +
                    "pesel              VARCHAR(11) NOT NULL PRIMARY KEY," +
                    "specialisation     VARCHAR(30) NOT NULL" +
                    ")");

            System.out.println("Utworzono tabelę DOCTORS");
        }

        this.result = this.dbmd.getTables(null,null,"MEDICAL_TESTS_RESULTS",null);
        if (!this.result.next()) {
            this.statement.execute(	"CREATE TABLE MEDICAL_TESTS_RESULTS" +
                    "(" +
                    "id         INT NOT NULL PRIMARY KEY" +
                    "			GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "date		VARCHAR(50) NOT NULL," +
                    "type   	VARCHAR(30) NOT NULL," +
                    "result     INT NOT NULL," +
                    "units      VARCHAR(20) NOT NULL," +
                    "pesel      VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (pesel) REFERENCES PATIENTS(pesel)" +
                    ")");

            System.out.println("Utworzono tabelę MEDICAL_TESTS_RESULTS");
        }

        this.result = this.dbmd.getTables(null,null,"SCHEDULED_VISITS",null);
        if (!this.result.next()) {
            this.statement.execute(	"CREATE TABLE SCHEDULED_VISITS" +
                    "(" +
                    "id             INT NOT NULL PRIMARY KEY" +
                    "			    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "date		    VARCHAR(50) NOT NULL," +
                    "time           VARCHAR(20) NOT NULL," +
                    "type   	    VARCHAR(50) NOT NULL," +
                    "payment        INT NOT NULL," +
                    "patient_pesel  VARCHAR(11) NOT NULL," +
                    "doctor_pesel   VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (patient_pesel) REFERENCES PATIENTS(pesel)," +
                    "FOREIGN KEY (doctor_pesel) REFERENCES DOCTORS(pesel)" +
                    ")");

            System.out.println("Utworzono tabelę SCHEDULED_VISITS");
        }

        this.result = this.dbmd.getTables(null,null,"ARCHIVED_VISITS",null);
        if (!this.result.next()) {
            this.statement.execute(	"CREATE TABLE ARCHIVED_VISITS" +
                    "(" +
                    "id             INT NOT NULL PRIMARY KEY" +
                    "			    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "date		    VARCHAR(50) NOT NULL," +
                    "type   	    VARCHAR(50) NOT NULL," +
                    "description    CLOB NOT NULL," +
                    "patient_pesel  VARCHAR(11) NOT NULL," +
                    "doctor_pesel   VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (patient_pesel) REFERENCES PATIENTS(pesel)," +
                    "FOREIGN KEY (doctor_pesel) REFERENCES DOCTORS(pesel)" +
                    ")");

            System.out.println("Utworzono tabelę ARCHIVED_VISITS");
        }

        this.result = this.dbmd.getTables(null,null,"HOSPITALISATIONS",null);
        if (!this.result.next()) {
            this.statement.execute(	"CREATE TABLE HOSPITALISATIONS" +
                    "(" +
                    "id         INT NOT NULL PRIMARY KEY" +
                    "			GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "dateFrom	VARCHAR(50) NOT NULL," +
                    "dateTo   	VARCHAR(50) NOT NULL," +
                    "reason     CLOB NOT NULL," +
                    "pesel      VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (pesel) REFERENCES PATIENTS(pesel)" +
                    ")");

            System.out.println("Utworzono tabelę HOSPITALISATIONS");
        }
    }

    public void addPatient(Patient patient) throws SQLException {
        this.statement.execute("INSERT INTO PATIENTS (name, surname, pesel, insurance, address) VALUES" +
                "('"+ patient.getName() + "','" + patient.getSurname() + "','" + patient.getPesel() + "','" +
                patient.getInsurance() + "','" + patient.getAddress() + "')");
    }

    public void addDoctor(Doctor doctor) throws SQLException {
        this.statement.execute("INSERT INTO DOCTORS (name, surname, pesel, specialisation) VALUES" +
                "('"+ doctor.getName() + "','" + doctor.getSurname() + "','" + doctor.getPesel() + "','" +
                doctor.getSpecialisation() + "')");
    }

    public void addMedicalTestResult(Patient patient, MedicalTestResult medicalTestResult) throws SQLException {
        this.statement.execute("INSERT INTO DOCTORS (data, type, result, pesel) VALUES" +
                "('"+ medicalTestResult.getDate() + "','" + medicalTestResult.getType() + "','" +
                medicalTestResult.getResult() + "','" + patient.getPesel() + "')");
    }

    public void addScheduledVisit(Patient patient, ScheduledVisit scheduledVisit) throws SQLException {
        this.statement.execute("INSERT INTO SCHEDULED_VISITS (data, time, type, payment, patient_pesel, " +
                "doctor_pesel)" + " VALUES" + "('"+ scheduledVisit.getDate() + "','" + scheduledVisit.getTime() +
                "','" + scheduledVisit.getType() + "','" + scheduledVisit.getDoctor() + "','" +
                scheduledVisit.getPayment() + "','" + patient.getPesel() + "','" +
                scheduledVisit.getDoctor().getPesel() + "')");
    }

    public void addArchivedVisit(Patient patient, ArchivedVisit archivedVisit) throws SQLException {
        this.statement.execute("INSERT INTO ARCHIVED_VISITS (data, type, description, patient_pesel, " +
                "doctor_pesel)" + " VALUES" + "('"+ archivedVisit.getDate() + "','" + archivedVisit.getType()
                + "','" + archivedVisit.getDoctor() + "','" + archivedVisit.getDescription() + "','" +
                patient.getPesel() + "','" + archivedVisit.getDoctor().getPesel()+ "')");
    }

    public void addHospitalisation(Patient patient, Hospitalisation hospitalisation) throws SQLException {
        this.statement.execute("INSERT INTO HOSPITALISATIONS (dateFrom, dateTo, reason, pesel) VALUES" +
                "'('"+ hospitalisation.getFrom() + "','" + hospitalisation.getTo() + "','" +
                hospitalisation.getReason() + "','" + patient.getPesel() + "')");
    }

    public void deletePatient(String pesel) throws SQLException {
        this.statement.execute("DELETE FROM PATIENTS WHERE pesel='" + pesel + "'");
    }

    public void deleteDoctor(String pesel) throws SQLException {
        this.statement.execute("DELETE FROM DOCTORS WHERE pesel='" + pesel + "'");
    }

    public void deleteMedicalTestResult(String pesel, String date, String type) throws SQLException {
        this.statement.execute("DELETE FROM MEDICAL_TESTS_RESULTS WHERE pesel='" + pesel + "' AND date='" +
                date + "' AND type='" + type + "'");
    }

    public void deleteScheduledVisit(String patientPesel, String date, String time) throws SQLException {
        this.statement.execute("DELETE FROM SCHEDULED_VISITS WHERE patient_pesel='" + patientPesel +
                "' AND date='" + date + "' AND time='" + time + "'");
    }

    public void deleteArchivedVisit(String patientPesel, String date, String doctorPesel) throws SQLException {
        this.statement.execute("DELETE FROM ARCHIVED_VISITS WHERE patient_pesel='" + patientPesel +
                "' AND doctor_pesel='" + doctorPesel + "'AND date='" + date + "'");
    }

    public void deleteHospitalisation(String pesel, String dateFrom, String dateTo) throws SQLException {
        this.statement.execute("DELETE FROM HOSPITALISATIONS WHERE pesel='" + pesel + "' AND dateFrom='" +
                dateFrom + "' AND dateTo='" + dateTo + "'");
    }

    public boolean ifPatientExist(String pesel) throws SQLException {
        this.result = this.statement.executeQuery("SELECT FROM HOSPITALISATIONS WHERE pesel='" + pesel + "'");
        if (!this.result.next()) {
            return false;
        } else {
            return true;
        }
    }

    public void printPatients() throws SQLException {
        System.out.println("\nRekordy tabeli PATIENTS:");
        this.result = this.statement.executeQuery("SELECT * FROM PATIENTS");
        while (this.result.next()) {
            System.out.println(this.result.getString("name") + " " + this.result.getString("surname") + " " +
                    this.result.getString("pesel") + " " + this.result.getString("insurance") + " " +
                    this.result.getString("address"));
        }
    }

    public void printDoctors() throws SQLException {
        System.out.println("\nRekordy tabeli DOCTORS:");
        this.result = this.statement.executeQuery("SELECT * FROM DOCTORS");
        while (this.result.next()) {
            System.out.println(this.result.getString("name") + " " + this.result.getString("surname") + " " +
                    this.result.getString("pesel") + " " + this.result.getString("specialisation"));
        }
    }

    public void printMedicalTestsResults() throws SQLException {
        System.out.println("\nRekordy tabeli MEDICAL_TESTS_RESULTS:");
        this.result = this.statement.executeQuery("SELECT * FROM MEDICAL_TESTS_RESULTS");
        while (this.result.next()) {
            System.out.println(this.result.getString("date") + " " + this.result.getString("type") + " " +
                    this.result.getString("result") + " " + this.result.getString("pesel"));
        }
    }

    public void printScheduledVisits() throws SQLException {
        System.out.println("\nRekordy tabeli SCHEDULED_VISITS:");
        this.result = this.statement.executeQuery("SELECT * FROM SCHEDULED_VISITS");
        while (this.result.next()) {
            System.out.println(this.result.getString("date") + " " + this.result.getString("time") + " " +
                    this.result.getString("type") + " " + this.result.getString("payment") + " " +
                    this.result.getString("patient_pesel") + " " + this.result.getString("doctor_pesel"));
        }
    }

    public void printArchivedVisits() throws SQLException {
        System.out.println("\nRekordy tabeli ARCHIVED_VISITS:");
        this.result = this.statement.executeQuery("SELECT * FROM ARCHIVED_VISITS");
        while (this.result.next()) {
            System.out.println(this.result.getString("date") + " " + this.result.getString("type") + " " +
                    this.result.getString("description") + " " + this.result.getString("patient_pesel") +
                    " " + this.result.getString("doctor_pesel"));
        }
    }

    public void printHospitalisations() throws SQLException {
        System.out.println("\nRekordy tabeli HOSPITALISATIONS:");
        this.result = this.statement.executeQuery("SELECT * FROM HOSPITALISATIONS");
        while (this.result.next()) {
            System.out.println(this.result.getString("from") + " " + this.result.getString("to") + " " +
                    this.result.getString("reason") + " " + this.result.getString("pesel"));
        }
    }

    public void deleteTables() throws SQLException {
        this.statement.executeUpdate("DROP TABLE MEDICAL_TESTS_RESULTS");
        System.out.println("Tabela MEDICAL_TESTS_RESULTS usunięta");
        this.statement.executeUpdate("DROP TABLE SCHEDULED_VISITS");
        System.out.println("Tabela SCHEDULED_VISITS usunięta");
        this.statement.executeUpdate("DROP TABLE ARCHIVED_VISITS");
        System.out.println("Tabela ARCHIVED_VISITS usunięta");
        this.statement.executeUpdate("DROP TABLE HOSPITALISATIONS");
        System.out.println("Tabela HOSPITALISATIONS usunięta");
        this.statement.executeUpdate("DROP TABLE PATIENTS");
        System.out.println("Tabela PATIENTS usunięta");
        this.statement.executeUpdate("DROP TABLE DOCTORS");
        System.out.println("Tabela DOCTORS usunięta");
    }
}
