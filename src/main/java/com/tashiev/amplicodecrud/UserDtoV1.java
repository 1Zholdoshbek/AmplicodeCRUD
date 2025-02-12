package com.tashiev.amplicodecrud;

import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link com.tashiev.amplicodecrud.user.User}
 */
public class UserDtoV1 {
    private final String username;
    private final Instant createdDate;
    private final Instant createdBy;

    public UserDtoV1(String username, Instant createdDate, Instant createdBy) {
        this.username = username;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public String getUsername() {
        return username;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getCreatedBy() {
        return createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoV1 entity = (UserDtoV1) o;
        return Objects.equals(this.username, entity.username) &&
                Objects.equals(this.createdDate, entity.createdDate) &&
                Objects.equals(this.createdBy, entity.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, createdDate, createdBy);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ", " +
                "createdDate = " + createdDate + ", " +
                "createdBy = " + createdBy + ")";
    }
}