package com.atlassian.interviews.uag.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atlassian.interviews.uag.api.Group;
import com.atlassian.interviews.uag.api.GroupService;
import com.atlassian.interviews.uag.core.ServiceFactory;
import com.atlassian.interviews.uag.core.Services;

public class MemoryGroupServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GroupService groupService;

    @Before
    public void setUp() {
        final Services services = ServiceFactory.createServices();
        groupService = services.getGroupService();
    }

    @Test
    public void testCreateGroup_duplicate() {
        final Group hackers = new Group("hackers");
        groupService.create(hackers);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Group hackers already exists");
        groupService.create(hackers);
    }

    @Test
    public void testCreateGroup_npe() {
        thrown.expect(NullPointerException.class);
        groupService.create(null);
    }

    @Test
    public void testCreateGroup_ok() {
        assertNull("hackers should not exist yet", groupService.findByName("hackers"));

        final Group hackers = new Group("hackers");
        groupService.create(hackers);

        assertEquals("hackers should exist now", hackers, groupService.findByName("hackers"));
    }

    @Test
    public void testDeleteGroup_notExists() {
        assertNull("hackers should not exist yet", groupService.findByName("hackers"));

        final Group hackers = new Group("hackers");
        groupService.delete(hackers);

        assertNull("hackers still should not exist", groupService.findByName("hackers"));
    }

    @Test
    public void testDeleteGroup_npe() {
        thrown.expect(NullPointerException.class);
        groupService.delete(null);
    }

    @Test
    public void testDeleteGroup_ok() {
        final Group hackers = new Group("hackers");

        groupService.create(hackers);
        assertEquals("hackers should exist", hackers, groupService.findByName("hackers"));

        groupService.delete(hackers);
        assertNull("hackers should be deleted", groupService.findByName("hackers"));
    }
}
