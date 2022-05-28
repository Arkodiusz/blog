package com.jedrzejewski;


import com.jedrzejewski.api.ServerManager;
import com.jedrzejewski.db.H2DatabaseDriver;

import java.io.IOException;

/**
 * Main class
 */
public class BlogApplication {

    private static final int SERVER_PORT = 8080;
    public static final String DB_URL = "jdbc:h2:~/test";
    public static final String USER = "sa";
    public static final String PASS = "";

    public static H2DatabaseDriver h2DbDriver = new H2DatabaseDriver(DB_URL, USER, PASS);

    /**
     * In main method there is logic necessary to start application.
     * Database is created, server is stared and context for http request is created.
     * After this set of instructions app is listening for http requests.
     */
    public static void main(String[] args) {
        createDB();
        createDummyData();

        ServerManager serverManager;
        try {
            serverManager = new ServerManager(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        serverManager.createContextForBlog();
    }

    /**
     * Method creates tables in database. For this project its called at every application start.
     */
    private static void createDB() {
        h2DbDriver.dropAll();

        h2DbDriver.executeStatement(
                "CREATE TABLE `user` (\n" +
                        "`userid` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,\n" +
                        "`username` VARCHAR( 45 ) NOT NULL ,\n" +
                        "`password` VARCHAR( 45 ) NOT NULL ,\n" +
                        "`permission` VARCHAR( 45 ) NOT NULL ,\n" +
                        "`readonly` VARCHAR( 45 ) NOT NULL\n" +
                        ")"
        );

        h2DbDriver.executeStatement(
                "CREATE TABLE `blog` (\n" +
                        "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,\n" +
                        "`text` TEXT NOT NULL ,\n" +
                        "`userid` INT NOT NULL\n" +
                        ")"
        );
    }


    /**
     * Creates dummy data to allow manual testing
     */
    private static void createDummyData() {
        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 1', 1)");
        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 2', 1)");
        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 3', 1)");
        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 4', 1)");

        h2DbDriver.executeStatement("INSERT INTO `user`(username, password, permission, readonly) VALUES ('admin', 'admin', 'full', 'No')");
        h2DbDriver.executeStatement("INSERT INTO `user`(username, password, permission, readonly) VALUES ('user1', 'password1', 'none', 'Yes')");
        h2DbDriver.executeStatement("INSERT INTO `user`(username, password, permission, readonly) VALUES ('user2', 'password2', 'none', 'Yes')");
    }
}