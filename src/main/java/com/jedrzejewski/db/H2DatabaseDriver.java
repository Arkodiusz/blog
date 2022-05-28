package com.jedrzejewski.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbc.JdbcResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * H2 database driver.
 *
 * I decided to use H2 to make this app standalone and ready to testing after running locally without any db setup.
 */
public class H2DatabaseDriver {

    private final String databaseUrl;
    private final String user;
    private final String pass;
    private static final Logger LOGGER = LogManager.getLogger(H2DatabaseDriver.class);

    public H2DatabaseDriver(String databaseUrl, String user, String pass) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Processing SQL statement. Method selects adequate operation to perform on db, opens and closes connection.
     *
     * @param statement         SQL statement ro run
     * @return                  resulting object is preprocessed db response
     */
    public Object executeStatement(String statement) {

        Connection conn = null;
        Statement stmt = null;
        Object result = null;

        try {
            conn = connect();
            LOGGER.info("Connected");

            int i = statement.indexOf(' ');
            String statementType = statement.substring(0, i);

            stmt = conn.createStatement();

            switch (statementType) {
                case "CREATE":
                    result = executeCreateStatement(stmt, statement);
                    break;
                case "INSERT":
                    result = executeInsertStatement(stmt, statement);
                    break;
                case "SELECT":
                    JdbcResultSet resultSet = (JdbcResultSet) executeSelectStatement(stmt, statement);
                    List<Object> list = new ArrayList<>();
                    while (resultSet.next()) {
                        list.add(resultSet.getResult().currentRow());
                    }
                    result = list;
                    break;
                case "DELETE":
                    result = executeDeleteStatement(stmt, statement);
                    break;
            }
            stmt.close();
            conn.close();
        } catch(SQLException se) { //errors for JDBC
            se.printStackTrace();
        } catch(Exception e) { //errors for Class.forName
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        LOGGER.info("Statement executed!");
        return result;
    }

    /**
     * Processing SQL CREATE statement
     *
     * @param stmt              this object executing a static SQL statement and returning the results it produces
     * @param sql               SQL statement
     * @return                  resulting object is raw db response
     */
    private Object executeCreateStatement(Statement stmt, String sql) throws SQLException {
        return stmt.executeUpdate(sql);
    }

    /**
     * Processing SQL INSERT statement
     *
     * @param stmt              this object executing a static SQL statement and returning the results it produces
     * @param sql               SQL statement
     * @return                  resulting object is raw db response
     */
    private Object executeInsertStatement(Statement stmt, String sql) throws SQLException {
        return stmt.execute(sql);
    }

    /**
     * Processing SQL SELECT statement
     *
     * @param stmt              this object executing a static SQL statement and returning the results it produces
     * @param sql               SQL statement
     * @return                  resulting object is raw db response
     */
    private Object executeSelectStatement(Statement stmt, String sql) throws SQLException {
        return stmt.executeQuery(sql);
    }

    /**
     * Processing SQL DELETE statement
     *
     * @param stmt              this object executing a static SQL statement and returning the results it produces
     * @param sql               SQL statement
     * @return                  resulting object is raw db response
     */
    private Object executeDeleteStatement(Statement stmt, String sql) throws SQLException {
        return stmt.executeUpdate(sql);
    }

    /**
     * Connecting to database
     *
     * @return                  resulting object is raw db response
     * @throws SQLException     in case of error during creating connection
     */
    private Connection connect() throws SQLException {
        LOGGER.info("Connecting to database...");
        return DriverManager.getConnection(databaseUrl, user, pass);
    }

    /**
     * Dropping all database
     */
    public void dropAll() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            stmt.executeUpdate("DROP ALL OBJECTS");
            stmt.close();
            conn.close();
        } catch(SQLException se) { //errors for JDBC
            se.printStackTrace();
        } catch(Exception e) { //errors for Class.forName
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        LOGGER.info("DB ERASED!");
    }
}
