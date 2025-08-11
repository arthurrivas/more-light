package br.com.more_light.repository.specification;

import br.com.more_light.domain.Account;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;

public class AccountSpecification {
    public static Specification<Account> emailContains(String email) {
        return (root, query, cb) -> (email == null || email.trim().isEmpty()) ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Account> usernameContains(String username) {
        return (root, query, cb) -> (username == null || username.trim().isEmpty()) ? null : cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<Account> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null : cb.equal(root.get("active"), active);
    }

    public static Specification<Account> personNameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;
            Join<Object, Object> person = root.join("person", jakarta.persistence.criteria.JoinType.LEFT);
            return cb.like(cb.lower(person.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Account> personCpfContains(String cpf) {
        return (root, query, cb) -> {
            if (cpf == null || cpf.trim().isEmpty()) return null;
            Join<Object, Object> person = root.join("person", jakarta.persistence.criteria.JoinType.LEFT);
            return cb.like(cb.lower(person.get("cpf")), "%" + cpf.toLowerCase() + "%");
        };

    }
}
