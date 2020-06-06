package project.model.database;

import project.model.person.Doctor;
import project.model.person.Patient;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableDoctors implements DatabaseInterface {

    private DatabaseMetaData dbmd = null;
    private Statement statement = null;
    private ResultSet result = null;
    private ResultSet result2 = null;

    public TableDoctors(DatabaseMetaData dbmd, Statement statement) {
        this.dbmd = dbmd;
        this.statement = statement;
    }

    public void create() throws SQLException {
        this.result = this.dbmd.getTables(null, null, "DOCTORS", null);
        if (!this.result.next()) {
            this.statement.execute("CREATE TABLE DOCTORS" +
                    "(" +
                    "doctor_name	    VARCHAR(30) NOT NULL," +
                    "doctor_surname	    VARCHAR(30) NOT NULL," +
                    "doctor_pesel       VARCHAR(11) NOT NULL PRIMARY KEY," +
                    "specialisation     VARCHAR(30) NOT NULL" +
                    ")");
        }
    }

    public void delete() throws SQLException {
        this.statement.executeUpdate("DROP TABLE DOCTORS");
    }

    public void print() throws SQLException {
        System.out.println("\nRecords of DOCTORS table:");
        this.result = this.statement.executeQuery("SELECT * FROM DOCTORS");
        while (this.result.next()) {
            System.out.println(this.result.getString("doctor_name") + " " +
                    this.result.getString("doctor_surname") + " " + this.result.getString("doctor_pesel")
                    + " " + this.result.getString("specialisation"));
        }
    }

    public boolean addDoctor(Doctor doctor) throws SQLException{
        if (!this.ifDoctorExists(doctor.getPesel())) {
            this.statement.execute("INSERT INTO DOCTORS (doctor_name, doctor_surname, doctor_pesel, specialisation) " +
                    "VALUES('" + doctor.getName() + "','" + doctor.getSurname() + "','" + doctor.getPesel() + "','" +
                    doctor.getSpecialisation() + "')");
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteDoctor(String pesel) throws SQLException {
        if (this.ifDoctorExists(pesel)) {
            this.statement.execute("DELETE FROM DOCTORS WHERE doctor_pesel='" + pesel + "'");
            return true;
        } else {
            return false;
        }
    }

    public boolean ifDoctorExists(String pesel) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM DOCTORS WHERE doctor_pesel='" + pesel + "'");
        if (!this.result.next()) {
            return false;
        } else {
            return true;
        }
    }

    public Doctor getDoctor(String pesel) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM DOCTORS WHERE doctor_pesel='" + pesel + "'");
        if (!this.result.next())
            return null;
        return new Doctor(this.result.getString("doctor_name"), this.result.getString("doctor_surname"),
                this.result.getString("doctor_pesel"), this.result.getString("specialisation"));
    }

    public ArrayList<Doctor> getDoctors() throws SQLException {
        ArrayList<Doctor> doctors = new ArrayList<>();
        this.result = this.statement.executeQuery("SELECT * FROM DOCTORS");
        while (this.result.next()) {
            doctors.add(new Doctor(this.result.getString("doctor_name"), this.result.getString("doctor_surname"),
                    this.result.getString("doctor_pesel"), this.result.getString("specialisation")));
        }
        return doctors;
    }

    public void updateDoctor(Doctor doctor) throws SQLException {
        this.statement.executeUpdate("UPDATE DOCTORS SET doctor_name='" + doctor.getName() +
                "', doctor_surname=" + doctor.getSurname() + "', specialisation=" + doctor.getSpecialisation() +
                " WHERE doctor_pesel='" + doctor.getPesel() + "'");
    }
}
