package com.jedrzejewski;


import com.jedrzejewski.api.ServerManager;

import java.io.IOException;

class BlogApplication {

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {

        ServerManager serverManager;
        try {
            serverManager = new ServerManager(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        serverManager.createContextForBlog();
    }
}