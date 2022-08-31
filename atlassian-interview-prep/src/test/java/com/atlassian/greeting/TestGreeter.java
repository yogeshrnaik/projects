package com.atlassian.greeting;

import com.atlassian.greeting.Greeter;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestGreeter {

    @Test
    public void testGreeting() {
        Assert.assertEquals("Hello John!", Greeter.getGreeting("John"));
    }

    @Test
    public void test() {
        AtomicInteger a = new AtomicInteger(1);
        System.out.println(a.getAndDecrement());
        System.out.println(a.get());

        a = new AtomicInteger(1);
        System.out.println(a.decrementAndGet());
        System.out.println(a.get());
    }
}
