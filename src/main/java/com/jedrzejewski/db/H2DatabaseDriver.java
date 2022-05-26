package com.jedrzejewski.db;

import com.jedrzejewski.domain.Blog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbc.JdbcResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class H2DatabaseDriver {

    private static final Logger LOGGER = LogManager.getLogger(H2DatabaseDriver.class);
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

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
                        list.add(
                                Blog.builder()
                                        .id(resultSet.getInt("id"))
                                        .text(resultSet.getString("text"))
                                        .userid(resultSet.getInt("userid"))
                                        .build()
                        );
                    }
                    result = list;
                    break;
                case "DELETE":
                    result = executeDeleteStatement(stmt, statement);
                    break;
            }
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
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

    private Object executeCreateStatement(Statement stmt, String sql) throws SQLException {
        return stmt.executeUpdate(sql);
    }
    private Object executeInsertStatement(Statement stmt, String sql) throws SQLException {
        return stmt.execute(sql);
    }
    private Object executeSelectStatement(Statement stmt, String sql) throws SQLException {
        return stmt.executeQuery(sql);
    }
    private Object executeDeleteStatement(Statement stmt, String sql) throws SQLException {
        return stmt.executeUpdate(sql);
    }

    private Connection connect() throws ClassNotFoundException, SQLException {
        // STEP 1: Register JDBC driver
        Class.forName(JDBC_DRIVER);

        //STEP 2: Open a connection
        LOGGER.info("Connecting to database...");
        return DriverManager.getConnection(DB_URL,USER,PASS);
    }

    public void dropAll() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            stmt.executeUpdate("DROP ALL OBJECTS");
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
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
