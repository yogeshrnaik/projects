package atlassian.ratelimiter;

public interface RateLimiter {
    boolean grantAccess();
}
