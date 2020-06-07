package project.model.database;

import project.interfaces.DatabaseInterface;
import project.model.Hospitalisation;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableHospitalisations implements DatabaseInterface {

    private DatabaseMetaData dbmd = null;
    private Statement statement = null;
    private ResultSet result = null;
    private ResultSet result2 = null;

    public TableHospitalisations(DatabaseMetaData dbmd, Statement statement) {
        this.dbmd = dbmd;
        this.statement = statement;
    }

    public void create() throws SQLException {
        this.result = this.dbmd.getTables(null,null,"HOSPITALISATIONS",null);
        if (!this.result.next()) {
            this.statement.execute("CREATE TABLE HOSPITALISATIONS" +
                    "(" +
                    "id             INT NOT NULL PRIMARY KEY" +
                    "			    GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "date_from	    VARCHAR(50) NOT NULL," +
                    "date_to   	    VARCHAR(50) NOT NULL," +
                    "reason         CLOB NOT NULL," +
                    "patient_pesel  VARCHAR(11) NOT NULL," +
                    "" +
                    "FOREIGN KEY (patient_pesel) REFERENCES PATIENTS(patient_pesel)" +
                    ")");
        }
    }

    public void delete() throws SQLException {
        this.statement.executeUpdate("DROP TABLE HOSPITALISATIONS");
    }

    public void print() throws SQLException {
        System.out.println("\nRecords of HOSPITALISATIONS table:");
        this.result = this.statement.executeQuery("SELECT * FROM HOSPITALISATIONS");
        while (this.result.next()) {
            System.out.println(this.result.getString("date_from") + " " + this.result.getString("date_to") + " " +
                    this.result.getString("reason") + " " + this.result.getString("patient_pesel"));
        }
    }

    public boolean addHospitalisation(String pesel, Hospitalisation hospitalisation) throws SQLException {
        if (!this.ifHospitalisationExists(pesel, hospitalisation.getFrom(), hospitalisation.getTo())) {
            this.statement.execute("INSERT INTO HOSPITALISATIONS (date_from, date_to, reason, patient_pesel) VALUES" +
                    "('" + hospitalisation.getFrom() + "','" + hospitalisation.getTo() + "','" +
                    hospitalisation.getReason() + "','" + pesel + "')");
            return true;
        }
        return false;
    }

    public boolean deleteHospitalisation(String pesel, String dateFrom, String dateTo) throws SQLException {
        if (this.ifHospitalisationExists(pesel, dateFrom, dateTo)) {
            this.statement.execute("DELETE FROM HOSPITALISATIONS WHERE patient_pesel='" + pesel +
                    "' AND date_from='" + dateFrom + "' AND date_to='" + dateTo + "'");
            return true;
        }
        return false;
    }

    public boolean ifHospitalisationExists(String pesel, String dateFrom, String dateTo) throws SQLException {
        this.result = this.statement.executeQuery("SELECT * FROM HOSPITALISATIONS WHERE patient_pesel='" +
                pesel + "' AND date_from='" + dateFrom + "' AND date_to='" + dateTo + "'");
        if (!this.result.next()) {
            return false;
        }
        return true;
    }

    public ArrayList<Hospitalisation> getPatientHospitalisations(String pesel) throws SQLException {
        ArrayList<Hospitalisation> hospitalisations = new ArrayList<>();
        this.result = this.statement.executeQuery("SELECT * FROM HOSPITALISATIONS WHERE patient_pesel='" +
                pesel + "'");
        while (this.result.next()) {
            hospitalisations.add(new Hospitalisation(this.result.getString("date_from"),
                    this.result.getString("date_to"), this.result.getString("reason")));
        }
        return hospitalisations;
    }
}
