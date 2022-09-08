package com.atlassian.router;

public interface Router {

    void withRoute(String path, String route);

    String route(String path);

}
