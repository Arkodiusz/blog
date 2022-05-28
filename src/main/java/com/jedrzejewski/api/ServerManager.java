package com.jedrzejewski.api;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Class has responsibility of initializing HTTP server
 * and creating context (listener) to request at specific HTTP path.
 */
public class ServerManager {

    private static final String PATH_FOT_BLOG = "/blog";

    private final HttpServer httpServer;

    /**
     * Constructor initializes Http server on given port.
     *
     * @throws IOException              exception thrown after server initializing failure
     */
    public ServerManager(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }

    /**
     * Creating actual HttpContext and staring thread.
     * Default executor uses the thread which was created by the start() method
     */
    public void createContextForBlog() {
        httpServer.createContext(PATH_FOT_BLOG, (RequestHandler::handleBlogRequest));
        httpServer.setExecutor(null);
        httpServer.start();
    }
}
