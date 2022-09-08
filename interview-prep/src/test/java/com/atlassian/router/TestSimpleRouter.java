package com.atlassian.router;

import org.junit.Assert;
import org.junit.Test;

public class TestSimpleRouter {

    @Test
    public void testNullPathIsNotAllowed() {
        SimpleRouter router = new SimpleRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute(null, "result"));
    }


    @Test
    public void testNullResultIsNotAllowed() {
        SimpleRouter router = new SimpleRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute("path", null));
    }

    @Test
    public void testEmptyStringPathIsNotAllowed() {
        SimpleRouter router = new SimpleRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute("", "result"));
    }


    @Test
    public void testEmptyStringResultIsNotAllowed() {
        SimpleRouter router = new SimpleRouter();
        Assert.assertThrows(IllegalArgumentException.class, () -> router.withRoute("path", ""));
    }

    @Test
    public void testGivenInvalidPathShouldReturnNullResult() {
        SimpleRouter router = new SimpleRouter();
        Assert.assertNull(router.route("path"));
    }

    @Test
    public void testGivenValidPathShouldReturnResult() {
        SimpleRouter router = new SimpleRouter();
        router.withRoute("path", "result");
        Assert.assertEquals("result", router.route("path"));
    }

    @Test
    public void testGivenRegexPathShouldReturnResultWithRegexMatchingPath() {
        SimpleRouter router = new SimpleRouter();
        router.withRoute("/bar/*/baz", "bar");
        Assert.assertEquals("bar", router.route("/bar/abc/baz"));
    }


}
