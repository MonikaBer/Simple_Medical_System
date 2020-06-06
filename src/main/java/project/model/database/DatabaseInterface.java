package project.model.database;

import java.sql.SQLException;

public interface DatabaseInterface {
    void create() throws SQLException;
    void delete() throws SQLException;
    void print() throws SQLException;
}
