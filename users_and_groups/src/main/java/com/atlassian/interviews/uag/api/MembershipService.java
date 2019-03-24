package com.atlassian.interviews.uag.api;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A service that associates users with the groups that they belong to.
 */
@ParametersAreNonnullByDefault
public interface MembershipService {

    /**
     * Adds the given user to the given group. If the user already belongs to that group, then the request is silently ignored.
     *
     * @param user the user to add to the group; must not be {@code null}
     * @param group the group that the user will be added to; must not be {@code null}
     * @throws IllegalArgumentException if either {@code user} or {@code group} does not exist according to the {@code UserService} and
     *             {@code GroupService}
     */
    void addUserToGroup(User user, Group group);

    /**
     * Adds the given child group the the given parent group.
     * <p>
     * Users that are a member of the child group are transitively a member of any parent groups for it. For example, suppose we want things
     * to look like this:
     * </p>
     * 
     * <pre>
     * <code>
     *        STUDENTS
     *        /      \
     *     SCIENCE  HISTORY
     *    /       \
     * ASTRONOMY  PHYSICS
     * </code>
     * </pre>
     * <p>
     * This structure is created with:
     * </p>
     * 
     * <pre>
     * <code>
     *     addGroupToGroup(SCIENCE, STUDENTS);
     *     addGroupToGroup(HISTORY, STUDENTS);
     *     addGroupToGroup(ASTRONOMY, SCIENCE);
     *     addGroupToGroup(PHYSICS, SCIENCE);
     * </code>
     * </pre>
     * <p>
     * A user assigned to the PHYSICS group inherits being in the SCIENCE and STUDENTS groups as well.
     * </p>
     * <p>
     * <strong>WARNING: Inheritance isn't fully implemented yet!</strong>
     * </p>
     *
     * @param child the group that inherits from the parent
     * @param parent the group that is inherited by the child
     */
    void addGroupToGroup(Group child, Group parent);

    /**
     * Returns whether or not the given user is a member of the given group.
     *
     * @param user the user whose group membership is to be checked
     * @param group the group to check for
     * @return {@code true} if {@code user} is a member of {@code group}
     */
    boolean isUserInGroup(User user, Group group);

    /**
     * Returns whether or not the given child is inheriting from the given parent.
     * <p>
     * If a use belongs to {@code child}, then that user implicitly belongs to {@code parent}, too.
     * </p>
     *
     * @param child the group that inherits from the parent
     * @param parent the group that is inherited by the child
     * @return {@code true} if the child inherits from the parent
     */
    boolean isGroupInGroup(Group child, Group parent);

    /**
     * Returns the current members of the specified group.
     *
     * @param group the group for which to retrieve all existing members
     * @return the users that belong to that group
     */
    Optional<Collection<User>> getUsersInGroup(Group group);

    /**
     * Removes a user from a group. If the user does not belong to that group, then the request is silently ignored.
     *
     * @param user the user to remove from the group; must not be {@code null}
     * @param group the group that the user will be removed from; must not be {@code null}
     */
    void removeUserFromGroup(User user, Group group);

    /**
     * Removes a nested group membership.
     * <p>
     * Note that this can only remove direct memberships. If the child inherits from the parent through another group, then this will not do
     * anything.
     * </p>
     *
     * @param child the group that was inheriting from the parent
     * @param parent the group that was inherited by the child
     */
    void removeGroupFromGroup(Group child, Group parent);

    void removeAllUsers(Group group);
}
