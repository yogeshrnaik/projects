package com.atlassian.interviews.uag.api;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A service that keeps track of which groups exist.
 */
@ParametersAreNonnullByDefault
public interface GroupService {
    /**
     * Find the group with the given name.
     *
     * @param name the name of the group to find; case-sensitive
     * @return the group that was found, or {@code null} if that group does not exist
     */
    @Nullable
    Group findByName(String name);

    /**
     * Creates the given group.
     *
     * @param group the group to create
     * @throws IllegalArgumentException if a group with that {@code name} already exists.
     */
    void create(Group group);

    /**
     * Deletes the given group.
     * If the group does not exist, then the request is silently ignored.
     *
     * @param group the group to delete.
     */
    void delete(Group group);
}
