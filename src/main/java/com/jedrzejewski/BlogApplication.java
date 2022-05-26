package com.jedrzejewski;


import com.jedrzejewski.api.ServerManager;

import java.io.IOException;

class BlogApplication {

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
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
}