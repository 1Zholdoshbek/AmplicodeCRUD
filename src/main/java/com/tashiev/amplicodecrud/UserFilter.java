package com.tashiev.amplicodecrud;

import com.tashiev.amplicodecrud.user.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;

public record UserFilter(Instant createdDateGte, Instant createdDateLte, Instant createdBy, String usernameStarts) {
    public Specification<User> toSpecification() {
        return Specification.where(createdDateGteSpec())
                .and(createdDateLteSpec())
                .and(createdBySpec())
                .and(usernameStartsSpec());
    }

    private Specification<User> createdDateGteSpec() {
        return ((root, query, cb) -> createdDateGte != null
                ? cb.greaterThanOrEqualTo(root.get("createdDate"), createdDateGte)
                : null);
    }

    private Specification<User> createdDateLteSpec() {
        return ((root, query, cb) -> createdDateLte != null
                ? cb.lessThanOrEqualTo(root.get("createdDate"), createdDateLte)
                : null);
    }

    private Specification<User> createdBySpec() {
        return ((root, query, cb) -> createdBy != null
                ? cb.equal(root.get("createdBy"), createdBy)
                : null);
    }

    private Specification<User> usernameStartsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(usernameStarts)
                ? cb.like(cb.lower(root.get("username")), usernameStarts.toLowerCase() + "%")
                : null);
    }
}