package com.jedrzejewski;


import com.jedrzejewski.api.ServerManager;
import com.jedrzejewski.db.H2DatabaseDriver;

import java.io.IOException;

class BlogApplication {

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {

        createDB();

        //  Trying to create server on given port. In case of exception application stops.
        ServerManager serverManager;
        try {
            serverManager = new ServerManager(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //  Creating HttpContext for "/blog" path. Application will now listen for requests.
        serverManager.createContextForBlog();
    }

    private static void createDB() {

        H2DatabaseDriver h2DbDriver = new H2DatabaseDriver();

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

        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 1', 1)");
        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 2', 1)");
        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 3', 1)");
        h2DbDriver.executeStatement("INSERT INTO `blog`(text, userid) VALUES ('test text 4', 1)");

        h2DbDriver.executeStatement("INSERT INTO `user` VALUES (1, 'user1', 'password1', 'none', 'Yes')");
        h2DbDriver.executeStatement("INSERT INTO `user` VALUES (2, 'user2', 'password2', 'none', 'Yes')");
    }
}