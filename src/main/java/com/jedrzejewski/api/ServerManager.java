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

    public static final String P_ACTION = "action";
    public static final String P_USERNAME = "username";
    public static final String P_PASSWORD = "password";
    public static final String P_PERMISSION = "permission";
    public static final String P_READ_ONLY = "readonly";
    public static final String P_USER = "user";
    public static final String P_TEXT = "text";
    public static final String P_ID = "id";


    private final HttpServer httpServer;
    private final UserService userService = new UserService();
    private final BlogService blogService = new BlogService();

    public ServerManager(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void createContextForBlog() {
        // Creating actual HttpContext , setting default executor and staring thread.
        // Default executor uses the thread which was created by the start() method
        httpServer.createContext(PATH_FOT_BLOG, (this::handleBlogRequest));
        httpServer.setExecutor(null);
        httpServer.start();
    }

    private void handleBlogRequest(HttpExchange exchange) throws IOException {
        //Handling response and finalizing exchange (request)
        Object response = distinguishRequest(exchange);
        if (METHOD_NOT_ALLOWED.equals(response)) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }
        String responseJson = convertToJson(response);
        exchange.sendResponseHeaders(200, responseJson.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(responseJson.getBytes());
        output.flush();
        exchange.close();
    }

    private Object distinguishRequest(HttpExchange exchange) {
        //Selection of demanded operation. Decision is made based on request method and request parameters
        String requestMethod = exchange.getRequestMethod();
        Map<String, String> params = getParametersFromQuery(exchange);

        String action = params.get(P_ACTION);

        if ("GET".equals(requestMethod)) {
            return blogService.fetchAllEntries();
        }
        if ("POST".equals(requestMethod) && ACTION_LOGIN.equals(action)) {
            return userService.login(params.get(P_USER), params.get(P_PASSWORD));
        }
        if ("POST".equals(requestMethod) && ACTION_NEW_ENTRY.equals(action)) {
            return blogService.createEntry(
                    Blog.builder()
                            .text(params.get(P_TEXT))
                            .userid(1) //TODO: implement user id assigning logic
                            .build()
            );
        }
        if ("POST".equals(requestMethod) && ACTION_NEW_USER.equals(action)) {
            return userService.createUser(
                    User.builder()
                            .username(params.get(P_USERNAME))
                            .password(params.get(P_PASSWORD))
                            .permission(params.get(P_PERMISSION))
                            .readonly(params.get(P_READ_ONLY))
                            .build()
            );
        }
        if ("DELETE".equals(requestMethod) && ACTION_DELETE_ENTRY.equals(action)) {
            String idAsString = params.get(P_ID);
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

    private Map<String, String> getParametersFromQuery(HttpExchange exchange) {
        Map<String, List<String>> splittedQuery = splitQuery(exchange.getRequestURI().getRawQuery());
        Map<String, String> params = new HashMap<>();
        params.put(P_ACTION, getValueFromSplittedQuery(splittedQuery, P_ACTION));
        params.put(P_USERNAME, getValueFromSplittedQuery(splittedQuery, P_USERNAME));
        params.put(P_PASSWORD, getValueFromSplittedQuery(splittedQuery, P_PASSWORD));
        params.put(P_PERMISSION, getValueFromSplittedQuery(splittedQuery, P_PERMISSION));
        params.put(P_READ_ONLY, getValueFromSplittedQuery(splittedQuery, P_READ_ONLY));
        params.put(P_USER, getValueFromSplittedQuery(splittedQuery, P_USER));
        params.put(P_TEXT, getValueFromSplittedQuery(splittedQuery, P_TEXT));
        return params;
    }

    private String getValueFromSplittedQuery(Map<String, List<String>> splittedQuery, String parameter) {
        return splittedQuery.getOrDefault(parameter, List.of(ACTION_NONE)).stream().findFirst().orElse(ACTION_NONE);
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
