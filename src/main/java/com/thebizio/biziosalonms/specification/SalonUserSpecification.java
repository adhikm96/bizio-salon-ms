package com.thebizio.biziosalonms.specification;

import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Order;
import java.util.Optional;
import java.util.UUID;

public class SalonUserSpecification {
    public static Specification<User> findWithFilter(Optional<StatusEnum> status, Optional<String> email, Optional<String> empCode, Optional<String> empType, Optional<PaySchedule> paySchedule, Optional<UUID> branch, Optional<UUID> workSchedule) {
        return (root, query, criteriaBuilder) -> {

            javax.persistence.criteria.Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));

            if(status.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status.get()), predicate);

            if(email.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("email"), email.get()), predicate);

            if(empCode.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("empCode"), empCode.get()), predicate);

            if(empType.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("empType"), empType.get()), predicate);

            if(paySchedule.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("paySchedule"), paySchedule.get()), predicate);

            if(branch.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.join("branch").get("id"), branch.get()), predicate);

            if(workSchedule.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.join("workSchedule").get("id"), workSchedule.get()), predicate);

            return predicate;
        };
    }
}
