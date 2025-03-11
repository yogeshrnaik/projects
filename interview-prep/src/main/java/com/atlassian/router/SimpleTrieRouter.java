package com.atlassian.router;

import java.util.*;
import java.util.regex.Pattern;

public class SimpleTrieRouter implements Router {

    private static class TrieNode {
        Map<String, TrieNode> children = new HashMap<>();
        String result = null; // Stores result if this node represents a full path
        Pattern regexPattern = null; // Compiled pattern for wildcard paths
    }

    private final TrieNode root;

    public SimpleTrieRouter() {
        root = new TrieNode();
    }

    @Override
    public void withRoute(String path, String result) {
        if (path == null || result == null || path.isEmpty() || result.isEmpty()) {
            throw new IllegalArgumentException("Path and result must not be null or empty");
        }

        String[] parts = path.split("/");
        TrieNode current = root;

        for (String part : parts) {
            if (!current.children.containsKey(part)) {
                current.children.put(part, new TrieNode());
            }
            current = current.children.get(part);
        }

        current.result = result; // Store the result for this exact path

        // If the path contains wildcards, precompile a regex pattern
        if (path.contains("*")) {
            current.regexPattern = Pattern.compile(path.replace("*", "[^/]+"));
        }
    }

    @Override
    public String route(String path) {
        String[] parts = path.split("/");
        return searchTrie(root, parts, 0);
    }

    private String searchTrie(TrieNode node, String[] parts, int index) {
        if (index == parts.length) {
            return node.result; // Return result if we reach the end of the path
        }

        String segment = parts[index];

        // Try exact match first
        if (node.children.containsKey(segment)) {
            String exactMatch = searchTrie(node.children.get(segment), parts, index + 1);
            if (exactMatch != null) {
                return exactMatch;
            }
        }

        // Try wildcard match
        for (Map.Entry<String, TrieNode> entry : node.children.entrySet()) {
            if (entry.getKey().equals("*")) { // Directly handle wildcards
                String wildcardMatch = searchTrie(entry.getValue(), parts, index + 1);
                if (wildcardMatch != null) {
                    return wildcardMatch;
                }
            }
        }

        return null; // No match found
    }
}