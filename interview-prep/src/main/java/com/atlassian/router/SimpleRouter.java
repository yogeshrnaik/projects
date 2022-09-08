package com.atlassian.router;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRouter implements Router {

    private Map<String, String> pathToResultMapping;

    public SimpleRouter() {
        pathToResultMapping = new HashMap<>();
    }

    @Override
    public void withRoute(String path, String result) {
        if (path == null || result == null || path.length() == 0 || result.length() == 0) {
            throw new IllegalArgumentException("path and result must be not null or empty");
        }
        pathToResultMapping.put(path, result);
    }

    @Override
    public String route(String path) {
        for (Map.Entry<String, String> mapping : pathToResultMapping.entrySet()) {
            String key = mapping.getKey();
            Pattern pattern = Pattern.compile(key.replace("*", ".*"));
            Matcher matcher = pattern.matcher(path);
            if (matcher.matches()) {
                return mapping.getValue();
            }
        }
        return null;
    }
}
