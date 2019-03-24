package com.atlassian.interviews.uag.memory;

import com.atlassian.interviews.uag.api.User;
import com.atlassian.interviews.uag.api.UserService;
import com.atlassian.interviews.uag.core.ServiceFactory;
import com.atlassian.interviews.uag.core.Services;
import com.atlassian.interviews.uag.memory.MemoryUserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MemoryUserServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UserService userService;

    @Before
    public void setUp() {
        final Services services = ServiceFactory.createServices();
        userService = services.getUserService();
    }

    @Test
    public void testCreateUser_duplicate() {
        final User fred = new User("fred");
        userService.create(fred);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("User fred already exists");
        userService.create(fred);
    }


    @Test
    public void testCreateUser_npe() {
        thrown.expect(NullPointerException.class);
        userService.create(null);
    }

    @Test
    public void testCreateUser_ok() {
        assertNull("fred should not exist yet", userService.findByName("fred"));

        final User fred = new User("fred");
        userService.create(fred);

        assertEquals("fred should exist now", fred, userService.findByName("fred"));
    }

    @Test
    public void testDeleteUser_notExists() {
        assertNull("fred should not exist yet", userService.findByName("fred"));

        final User fred = new User("fred");
        userService.delete(fred);

        assertNull("fred still should not exist", userService.findByName("fred"));
    }

    @Test
    public void testDeleteUser_npe() {
        thrown.expect(NullPointerException.class);
        userService.delete(null);
    }

    @Test
    public void testDeleteUser_ok() {
        final User fred = new User("fred");

        userService.create(fred);
        assertEquals("fred should exist", fred, userService.findByName("fred"));

        userService.delete(fred);
        assertNull("fred should be deleted", userService.findByName("fred"));
    }
}
