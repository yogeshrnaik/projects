package com.atlassian.router;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestSimpleTrieRouter {

    @Test
    public void testNullPathIsNotAllowed() {
        SimpleTrieRouter router = new SimpleTrieRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute(null, "result"));
    }


    @Test
    public void testNullResultIsNotAllowed() {
        SimpleTrieRouter router = new SimpleTrieRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute("path", null));
    }

    @Test
    public void testEmptyStringPathIsNotAllowed() {
        SimpleTrieRouter router = new SimpleTrieRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute("", "result"));
    }


    @Test
    public void testEmptyStringResultIsNotAllowed() {
        SimpleTrieRouter router = new SimpleTrieRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute("path", ""));
    }

    @Test
    public void testGivenInvalidPathShouldReturnNullResult() {
        SimpleTrieRouter router = new SimpleTrieRouter();
        assertNull(router.route("path"));
    }

    @Test
    public void testGivenValidPathShouldReturnResult() {
        SimpleTrieRouter router = new SimpleTrieRouter();
        router.withRoute("path", "result");
        assertEquals("result", router.route("path"));
    }

    @Test
    public void testGivenRegexPathShouldReturnResultWithRegexMatchingPath() {
        SimpleTrieRouter router = new SimpleTrieRouter();
        router.withRoute("/bar/*/baz", "bar");
        assertEquals("bar", router.route("/bar/abc/baz"));
    }

    @Test
    public void testRouting() {
        SimpleRouter router = new SimpleRouter();
        router.withRoute("/foo/bar", "ExactMatch");
        router.withRoute("/bar/*/baz", "WildcardMatch");

        assertEquals("ExactMatch", router.route("/foo/bar"));      // Exact match
        assertEquals("WildcardMatch", router.route("/bar/xyz/baz")); // Wildcard match
        assertEquals("WildcardMatch", router.route("/bar/123/baz")); // Wildcard match
        assertNull(router.route("/unknown"));  // No match
    }


}
