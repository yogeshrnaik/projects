package com.atlassian.interviews.uag.api;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A service that keeps track of which users exist.
 */
@ParametersAreNonnullByDefault
public interface UserService {
    /**
     * Find the user with the given name.
     *
     * @param name the name of the user to find; case-sensitive
     * @return the user that was found, or {@code null} if that user does not exist
     */
    @Nullable
    User findByName(String name);

    /**
     * Creates the given user.
     *
     * @param user the user to create
     * @throws IllegalArgumentException if a user with that {@code name} already exists.
     */
    void create(User user);

    /**
     * Deletes the given user.
     * If the user does not exist, then the request is silently ignored.
     *
     * @param user the user to delete.
     */
    void delete(User user);
}
