package com.atlassian.interviews.uag.memory;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.atlassian.interviews.uag.api.Group;
import com.atlassian.interviews.uag.api.GroupService;
import com.atlassian.interviews.uag.api.MembershipService;
import com.atlassian.interviews.uag.api.User;
import com.atlassian.interviews.uag.api.UserService;
import com.atlassian.interviews.uag.core.ServiceFactory;
import com.atlassian.interviews.uag.core.Services;

public class MemoryMembershipNestedGroupsTest {

    private static final User EVAN = new User("evan");
    private static final User FRED = new User("fred");
    private static final User GEORGE = new User("george");
    private static final User NOBODY = new User("nobody");
    private static final Group HACKERS = new Group("hackers");
    private static final Group ADMINS = new Group("admins");
    private static final Group PEOPLE = new Group("people");
    private static final Group NOGROUP = new Group("nogroup");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MembershipService membershipService;

    @Before
    public void setUp() {
        final Services services = ServiceFactory.createServices();

        final UserService userService = services.getUserService();
        userService.create(EVAN);
        userService.create(FRED);
        userService.create(GEORGE);

        final GroupService groupService = services.getGroupService();
        groupService.create(ADMINS);
        groupService.create(HACKERS);
        groupService.create(PEOPLE);

        membershipService = services.getMembershipService();
        membershipService.addUserToGroup(EVAN, PEOPLE);
        membershipService.addUserToGroup(FRED, ADMINS);
        membershipService.addUserToGroup(FRED, PEOPLE);
        membershipService.addUserToGroup(GEORGE, HACKERS);
        membershipService.addGroupToGroup(ADMINS, PEOPLE);
        membershipService.addGroupToGroup(HACKERS, PEOPLE);
    }

    @Test
    public void addGroupToGroup_nullParent() {
        thrown.expect(NullPointerException.class);
        membershipService.addGroupToGroup(HACKERS, null);
    }

    @Test
    public void addGroupToGroup_nullChild() {
        thrown.expect(NullPointerException.class);
        membershipService.addGroupToGroup(null, PEOPLE);
    }

    @Ignore("TODO - Inheritance isn't working yet")
    @Test
    public void addGroupToGroup_duplicate() {
        membershipService.addGroupToGroup(HACKERS, PEOPLE);
        membershipService.addGroupToGroup(HACKERS, PEOPLE);
        assertTrue("george is a hacker, and hackers are people", membershipService.isUserInGroup(GEORGE, PEOPLE));

        membershipService.removeGroupFromGroup(HACKERS, PEOPLE);
        assertFalse("one remove is good enough", membershipService.isUserInGroup(GEORGE, PEOPLE));
    }

    @Test
    public void testIsGroupInGroup_yes() {
        assertTrue("hackers are people", membershipService.isGroupInGroup(HACKERS, PEOPLE));
    }

    @Test
    public void testIsGroupInGroup_no() {
        assertFalse("hackers are not implicitly admins", membershipService.isGroupInGroup(HACKERS, ADMINS));
        assertFalse("people are not implicitly admins", membershipService.isGroupInGroup(PEOPLE, ADMINS));
        assertFalse("people are not implicitly hackers", membershipService.isGroupInGroup(PEOPLE, HACKERS));
    }

    @Test
    public void removeGroupFromGroup_nullParent() {
        thrown.expect(NullPointerException.class);
        membershipService.removeGroupFromGroup(HACKERS, null);
    }

    @Test
    public void removeGroupFromGroup_nullChild() {
        thrown.expect(NullPointerException.class);
        membershipService.removeGroupFromGroup(null, PEOPLE);
    }

    @Ignore("TODO - Inheritance isn't working yet")
    @Test
    public void removeUserFromGroup_indirect() {
        membershipService.removeUserFromGroup(GEORGE, PEOPLE);
        assertTrue("did not remove the indirect membership", membershipService.isUserInGroup(GEORGE, PEOPLE));
    }

    @Test
    public void removeGroupFromGroup_removed() {
        membershipService.removeGroupFromGroup(HACKERS, PEOPLE);
        assertFalse("hackers are no longer people", membershipService.isGroupInGroup(HACKERS, PEOPLE));
    }

    @Test
    public void removeGroupFromGroup_notMember() {
        membershipService.removeGroupFromGroup(HACKERS, ADMINS);
        membershipService.removeGroupFromGroup(HACKERS, ADMINS);
    }

    @Test
    public void testDirectMembershipsWork() {
        assertTrue("fred is an admin", membershipService.isUserInGroup(FRED, ADMINS));
        assertTrue("george is a hacker", membershipService.isUserInGroup(GEORGE, HACKERS));
        assertFalse("fred is not a hacker", membershipService.isUserInGroup(FRED, HACKERS));
        assertFalse("george is not an admin", membershipService.isUserInGroup(GEORGE, ADMINS));
    }

    @Ignore("TODO - Inheritance isn't working yet")
    @Test
    public void testInheritedMembership() {
        assertTrue("george is a hacker, and hackers are people", membershipService.isUserInGroup(GEORGE, PEOPLE));
    }

    @Test
    public void testGetUsersInGroupDoesNotDoInheritance() {
        final Set<User> expectedPeople = new HashSet<>(asList(EVAN, FRED));
        final Set<User> actualPeople = new HashSet<>(membershipService.getUsersInGroup(PEOPLE).get());
        assertEquals(expectedPeople, actualPeople);
    }
}
