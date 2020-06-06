package project.model.database;

import project.AppException;
import project.model.Hospitalisation;
import project.model.MedicalTestResult;
import project.model.person.Doctor;
import project.model.person.Patient;
import project.model.visit.ArchivedVisit;
import project.model.visit.ScheduledVisit;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection connection = null;
    private DatabaseMetaData dbmd = null;
    Statement statement = null;
    TablePatients tablePatients = null;
    TableDoctors tableDoctors = null;
    TableMedicalTestsResults tableMedicalTestsResults = null;
    TableScheduledVisits tableScheduledVisits = null;
    TableArchivedVisits tableArchivedVisits = null;
    TableHospitalisations tableHospitalisations = null;

    public Database() throws AppException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.connection = DriverManager.getConnection("jdbc:derby:SimpleMedicalSystem;create=true");
            this.dbmd = this.connection.getMetaData();
            this.statement = this.connection.createStatement();
        } catch (ClassNotFoundException exception){
            throw new AppException("Unknown embedded database driver");
        } catch (SQLException exception) {
            throw new AppException("Error during creation of database ");
        }

        this.tablePatients = new TablePatients(this.dbmd, this.statement);
        this.tableDoctors = new TableDoctors(this.dbmd, this.statement);
        this.tableMedicalTestsResults = new TableMedicalTestsResults(this.dbmd, this.statement);
        this.tableScheduledVisits = new TableScheduledVisits(this.dbmd, this.statement);
        this.tableArchivedVisits = new TableArchivedVisits(this.dbmd, this.statement);
        this.tableHospitalisations = new TableHospitalisations(this.dbmd, this.statement);
    }

    public void createTables() throws AppException  {
        this.createPatientsTable();
        this.createDoctorsTable();
        this.createMedicalTestsResultsTable();
        this.createScheduledVisitsTable();
        this.createArchivedVisitsTable();
        this.createHospitalisationsTable();
    }

    public void deleteTables() throws AppException {
        this.deleteMedicalTestsResultsTable();
        this.deleteScheduledVisitsTable();
        this.deleteArchivedVisitsTable();
        this.deleteHospitalisationsTable();
        this.deletePatientsTable();
        this.deleteDoctorsTable();
    }

    public void createPatientsTable() throws AppException {
        try {
            this.tablePatients.create();
            //System.out.println("Table PATIENTS created");
        } catch (SQLException exception) {
            throw new AppException("Problem with creating table PATIENTS");
        }
    }

    public void createDoctorsTable() throws AppException {
        try {
            this.tableDoctors.create();
            //System.out.println("Table DOCTORS created");
        } catch (SQLException exception) {
            throw new AppException("Problem with creating table DOCTORS");
        }
    }

    public void createMedicalTestsResultsTable() throws AppException {
        try {
            this.tableMedicalTestsResults.create();
            //System.out.println("Table MEDICAL_TESTS_RESULTS created");
        } catch (SQLException exception) {
            throw new AppException("Problem with creating table MEDICAL_TESTS_RESULTS");
        }
    }

    public void createScheduledVisitsTable() throws AppException {
        try {
            this.tableScheduledVisits.create();
            //System.out.println("Table SCHEDULED_VISITS created");
        } catch (SQLException exception) {
            throw new AppException("Problem with creating table SCHEDULED_VISITS");
        }
    }

    public void createArchivedVisitsTable() throws AppException {
        try {
            this.tableArchivedVisits.create();
            //System.out.println("Table ARCHIVED_VISITS created");
        } catch (SQLException exception) {
            throw new AppException("Problem with creating table ARCHIVED_VISITS");
        }
    }

    public void createHospitalisationsTable() throws AppException {
        try {
            this.tableHospitalisations.create();
            //System.out.println("Table HOSPITALISATIONS created");
        } catch (SQLException exception) {
            throw new AppException("Problem with creating table HOSPITALISATIONS");
        }
    }

    public void deletePatientsTable() throws AppException {
        try {
            this.tablePatients.delete();
            System.out.println("Table PATIENTS deleted");
        } catch (Exception exception) {
            throw new AppException("Problem with deleting table PATIENTS");
        }
    }

    public void deleteDoctorsTable() throws AppException {
        try {
            this.tableDoctors.delete();
            System.out.println("Table DOCTORS deleted");
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting table DOCTORS");
        }
    }

    public void deleteMedicalTestsResultsTable() throws AppException {
        try {
            this.tableMedicalTestsResults.delete();
            System.out.println("Table MEDICAL_TESTS_RESULTS deleted");
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting table MEDICAL_TESTS_RESULTS");
        }
    }

    public void deleteScheduledVisitsTable() throws AppException {
        try {
            this.tableScheduledVisits.delete();
            System.out.println("Table SCHEDULED_VISITS deleted");
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting table SCHEDULED_VISTIS");
        }
    }

    public void deleteArchivedVisitsTable() throws AppException {
        try {
            this.tableArchivedVisits.delete();
            System.out.println("Table ARCHIVED_VISITS deleted");
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting table ARCHIVED_VISTIS");
        }
    }

    public void deleteHospitalisationsTable() throws AppException {
        try {
            this.tableHospitalisations.delete();
            System.out.println("Table HOSPITALISATIONS deleted");
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting table HOSPITALISATIONS");
        }
    }

    //--------------------------------------------------------------------------------------------

    public void printPatients() throws AppException {
        try {
            this.tablePatients.print();
        } catch (SQLException exception) {
            throw new AppException("Problem with printing contents of PATIENTS table");
        }
    }

    public void addPatient(Patient patient) throws AppException {
        try {
            this.tablePatients.addPatient(patient);
        } catch (SQLException exception) {
            throw new AppException("Problem with addition new patient to PATIENTS table");
        }
    }

    public void deletePatient(String pesel) throws AppException {
        try {
            this.tablePatients.deletePatient(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting patient from PATIENTS table");
        }
    }

    public boolean ifPatientExists(String pesel) throws AppException {
        try {
            return this.tablePatients.ifPatientExists(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem during checking if patient exists in PATIENTS table");
        }
    }

    public Patient getPatient(String pesel) throws AppException {
        try {
            return this.tablePatients.getPatient(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with getting patient from PATIENTS table");
        }
    }

    public ArrayList<Patient> getPatients() throws AppException {
        try {
            return this.tablePatients.getPatients();
        } catch (SQLException exception) {
            throw new AppException("Problem with getting patients from PATIENTS table");
        }
    }

    //--------------------------------------------------------------------------------------------

    public void printDoctors() throws AppException {
        try {
            this.tableDoctors.print();
        } catch (SQLException exception) {
            throw new AppException("Problem with printing contents of DOCTORS table");
        }
    }

    public void addDoctor(Doctor doctor) throws AppException {
        try {
            this.tableDoctors.addDoctor(doctor);
        } catch (SQLException exception) {
            throw new AppException("Problem with addition new doctor to DOCTORS table");
        }
    }

    public void deleteDoctor(String pesel) throws AppException {
        try {
            this.tableDoctors.deleteDoctor(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting doctor from DOCTORS table");
        }
    }

    public boolean ifDoctorExists(String pesel) throws AppException {
        try {
            return this.tableDoctors.ifDoctorExists(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem during checking if doctor exists in DOCTORS table");
        }
    }

    public Doctor getDoctor(String pesel) throws AppException {
        try {
            return this.tableDoctors.getDoctor(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with getting doctor from DOCTORS table");
        }
    }

    public ArrayList<Doctor> getDoctors() throws AppException {
        try {
            return this.tableDoctors.getDoctors();
        } catch (SQLException exception) {
            throw new AppException("Problem with getting doctors from DOCTORS table");
        }
    }

    //--------------------------------------------------------------------------------------------

    public void printMedicalTestsResults() throws AppException {
        try {
            this.tableMedicalTestsResults.print();
        } catch (SQLException exception) {
            throw new AppException("Problem with printing contents of MEDICAL_TESTS_RESULTS table");
        }
    }

    public void addMedicalTestResult(String pesel, MedicalTestResult medicalTestResult) throws AppException {
        try {
            this.tableMedicalTestsResults.addMedicalTestResult(pesel, medicalTestResult);
        } catch (SQLException exception) {
            throw new AppException("Problem with addition new medical test result to MEDICAL_TESTS_RESULTS table");
        }
    }

    public void deleteMedicalTestResult(String pesel, String date, String type) throws AppException {
        try {
            this.tableMedicalTestsResults.deleteMedicalTestResult(pesel, date, type);
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting medical test result from MEDICAL_TESTS_RESULTS table");
        }
    }

    public boolean ifMedicalTestResultExists(String pesel, String date, String type) throws AppException {
        try {
            return this.tableMedicalTestsResults.ifMedicalTestResultExists(pesel, date, type);
        } catch (SQLException exception) {
            throw new AppException("Problem during checking if medical test result exists in " +
                    "MEDICAL_TESTS_RESULTS table");
        }
    }

    public ArrayList<MedicalTestResult> getPatientMedicalTestResults(String pesel) throws AppException {
        try {
            return this.tableMedicalTestsResults.getPatientMedicalTestsResults(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with getting medical tests results from MEDICAL_TESTS_RESULTS table");
        }
    }

    //--------------------------------------------------------------------------------------------

    public void printScheduledVisits() throws AppException {
        try {
            this.tableScheduledVisits.print();
        } catch (SQLException exception) {
            throw new AppException("Problem with printing contents of SCHEDULED_VISITS table");
        }
    }

    public void addScheduledVisit(String pesel, ScheduledVisit scheduledVisit) throws AppException {
        try {
            this.tableScheduledVisits.addScheduledVisit(pesel, scheduledVisit);
        } catch (SQLException exception) {
            throw new AppException("Problem with addition new scheduled visit to SCHEDULED_VISITS table");
        }
    }

    public void deleteScheduledVisit(String patientPesel, String date, String time) throws AppException {
        try {
            this.tableScheduledVisits.deleteScheduledVisit(patientPesel, date, time);
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting scheduled visit from SCHEDULED_VISITS table");
        }
    }

    public boolean ifScheduledVisitExists(String patientPesel, String date, String time) throws AppException {
        try {
            return this.tableScheduledVisits.ifScheduledVisitExists(patientPesel, date, time);
        } catch (SQLException exception) {
            throw new AppException("Problem during checking if scheduled visit exists in SCHEDULED_VISITS table");
        }
    }

    public ArrayList<ScheduledVisit> getPatientScheduledVisits(String pesel) throws AppException {
        try {
            return this.tableScheduledVisits.getPatientScheduledVisits(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with getting patient scheduled visits from SCHEDULED_VISITS table");
        }
    }

    //--------------------------------------------------------------------------------------------

    public void printArchivedVisits() throws AppException {
        try {
            this.tableArchivedVisits.print();
        } catch (SQLException exception) {
            throw new AppException("Problem with printing contents of ARCHIVED_VISITS table");
        }
    }

    public void addArchivedVisit(String pesel, ArchivedVisit archivedVisit) throws AppException {
        try {
            this.tableArchivedVisits.addArchivedVisit(pesel, archivedVisit);
        } catch (SQLException exception) {
            throw new AppException("Problem with addition new archived visit to ARCHIVED_VISITS table");
        }
    }

    public void deleteArchivedVisit(String patientPesel, String date, String doctorPesel) throws AppException {
        try {
            this.tableArchivedVisits.deleteArchivedVisit(patientPesel, date, doctorPesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting archived visit from ARCHIVED_VISITS table");
        }
    }

    public boolean ifArchivedVisitExists(String patientPesel, String date, String doctorPesel) throws AppException {
        try {
            return this.tableArchivedVisits.ifArchivedVisitExists(patientPesel, date, doctorPesel);
        } catch (SQLException exception) {
            throw new AppException("Problem during checking if archived visit exists in ARCHIVED_VISITS table");
        }
    }

    public ArrayList<ArchivedVisit> getPatientArchivedVisits(String pesel) throws AppException {
        try {
            return this.tableArchivedVisits.getPatientArchivedVisits(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with getting patient archived visits from ARCHIVED_VISITS table");
        }
    }

    //--------------------------------------------------------------------------------------------

    public void printHospitalisations() throws AppException {
        try {
            this.tableHospitalisations.print();
        } catch (SQLException exception) {
            throw new AppException("Problem with printing contents of HOSPITALISATIONS table");
        }
    }

    public void addHospitalisation(String pesel, Hospitalisation hospitalisation) throws AppException {
        try {
            this.tableHospitalisations.addHospitalisation(pesel, hospitalisation);
        } catch (SQLException exception) {
            throw new AppException("Problem with addition new hospitalisation to HOSPITALISATIONS table");
        }
    }

    public void deleteHospitalisation(String pesel, String dateFrom, String dateTo) throws AppException {
        try {
            this.tableHospitalisations.deleteHospitalisation(pesel, dateFrom, dateTo);
        } catch (SQLException exception) {
            throw new AppException("Problem with deleting hospitalisation from HOSPITALISATIONS table");
        }
    }

    public boolean ifHospitalisationExists(String pesel, String dateFrom, String dateTo) throws AppException {
        try {
            return this.tableHospitalisations.ifHospitalisationExists(pesel, dateFrom, dateTo);
        } catch (SQLException exception) {
            throw new AppException("Problem during checking if hospitalisation exists in HOSPITALISATIONS table");
        }
    }

    public ArrayList<Hospitalisation> getPatientHospitalisations(String pesel) throws AppException {
        try {
            return this.tableHospitalisations.getPatientHospitalisations(pesel);
        } catch (SQLException exception) {
            throw new AppException("Problem with getting patient hospitalisations from HOSPITALISATIONS table");
        }
    }

    public void generateData() throws AppException {
        this.createTables();
        this.addPatient(new Patient("Filip", "Kalski", "11111111111",
                    "NFZ", "ul. Komputerowa, Warszawa"));
        this.addPatient(new Patient("Aleksandra", "Kowalska", "22222222222",
                "Prywatne", "ul. Krakowska 33 Poznań"));

        this.addDoctor(new Doctor("Robert", "Kowalski", "55555555555", "internista"));
        this.addDoctor(new Doctor("Anna", "Kowalska", "45555555555", "laryngolog"));

        this.addMedicalTestResult("11111111111", new MedicalTestResult("24.05.2019",
                    "hemoglobina", Float.parseFloat("20.2"), "mg/dl"));
        this.addMedicalTestResult("11111111111", new MedicalTestResult("11.10.2018",
                    "limfocyty", Float.parseFloat("7.8"), "mg/dl"));

        this.addScheduledVisit("11111111111", new ScheduledVisit("11.02.2020", "15:00",
                    "zabieg", this.getDoctor("55555555555"), Float.parseFloat("50.5")));
        this.addScheduledVisit("22222222222", new ScheduledVisit("22.07.2020", "9:00",
                    "porada", this.getDoctor("45555555555"), Float.parseFloat("0.0")));

        this.addArchivedVisit("22222222222", new ArchivedVisit("08.03.2019", "zabieg",
                    this.getDoctor("55555555555"), "Leczenie pourazowe stawu łokciowego pomyślne"));
        this.addArchivedVisit("11111111111", new ArchivedVisit("23.08.2017", "porada",
                    this.getDoctor("45555555555"), "Staw nadgarstkowy z rozpoznaniem cieśni"));

        this.addHospitalisation("11111111111", new Hospitalisation("20.05.2020", "24.05.2020",
                "Zapalenie płuc"));
        this.addHospitalisation("22222222222", new Hospitalisation("30.05.2019", "04.06.2019",
                "Poród"));

        this.printPatients();
        this.printDoctors();
        this.printMedicalTestsResults();
        this.printScheduledVisits();
        this.printArchivedVisits();
        this.printHospitalisations();
        //this.deleteTables();
    }
}
