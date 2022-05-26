package com.jedrzejewski.api;

import com.jedrzejewski.domain.Blog;
import com.jedrzejewski.domain.User;
import com.jedrzejewski.service.BlogService;
import com.jedrzejewski.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.regex.Pattern;

import static java.net.URLDecoder.decode;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class ServerManager {

    private static final String PATH_FOT_BLOG = "/blog";
    private static final String METHOD_NOT_ALLOWED = "error";
    private static final String ACTION_LOGIN = "login";
    private static final String ACTION_NEW_ENTRY = "new";
    private static final String ACTION_NEW_USER = "new_user";
    private static final String ACTION_DELETE_ENTRY = "delete";
    private static final String ACTION_NONE = "";

    private final HttpServer httpServer;
    private final UserService userService = new UserService();
    private final BlogService blogService = new BlogService();

    public ServerManager(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void createContextForBlog() {
        httpServer.createContext(PATH_FOT_BLOG, (this::handleBlogRequest));
        httpServer.setExecutor(null);
        httpServer.start();
    }

    private void handleBlogRequest(HttpExchange exchange) throws IOException {
        Object response = distinguishRequest(exchange);
        String responseJson = convertToJson(response);
        if (METHOD_NOT_ALLOWED.equals(responseJson)) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }
        exchange.sendResponseHeaders(200, responseJson.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(responseJson.getBytes());
        output.flush();
        exchange.close();
    }

    private Object distinguishRequest(HttpExchange exchange) {
        String requestMethod = exchange.getRequestMethod();

        Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
        String action = params.getOrDefault("action", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);

        if ("GET".equals(requestMethod)) {
            return blogService.fetchAllEntries();
        }
        if ("POST".equals(requestMethod) && ACTION_LOGIN.equals(action)) {
            String user = params.getOrDefault("user", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            String password = params.getOrDefault("password", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            return userService.login(user, password);
        }
        if ("POST".equals(requestMethod) && ACTION_NEW_ENTRY.equals(action)) {
            String text = params.getOrDefault("text", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            return blogService.createEntry(
                    Blog.builder()
                            .text(text)
                            .userid(1) //TODO: implement user id assigning logic
                            .build()
            );
        }
        if ("POST".equals(requestMethod) && ACTION_NEW_USER.equals(action)) {
            String username = params.getOrDefault("username", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            String password = params.getOrDefault("password", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            String permission = params.getOrDefault("permission", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            String readonly = params.getOrDefault("readonly", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            return userService.createUser(
                    User.builder()
                            .username(username)
                            .password(password)
                            .permission(permission)
                            .readonly(readonly)
                            .build()
            );
        }
        if ("DELETE".equals(requestMethod) && ACTION_DELETE_ENTRY.equals(action)) {
            String idAsString = params.getOrDefault("id", List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
            int id;
            try {
                id = Integer.parseInt(idAsString);
                blogService.deleteEntryById(id);
            } catch (NumberFormatException e) {
                return METHOD_NOT_ALLOWED;
            }
            return "";
        }
        return METHOD_NOT_ALLOWED;

    }

    private Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }
        Map<String, List<String>> collect;
        collect = Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));
        return collect;
    }

    private String convertToJson(Object response) {
        return response.toString(); //TODO: converting to JSON
    }
}
