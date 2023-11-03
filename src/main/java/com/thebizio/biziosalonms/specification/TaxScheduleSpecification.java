package com.thebizio.biziosalonms.specification;

import com.thebizio.biziosalonms.entity.TaxSchedule;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public class TaxScheduleSpecification {
    public static Specification<TaxSchedule> findWithFilter(Optional<String> name, Optional<UUID> branch, Optional<StatusEnum> status) {
        return (root, query, criteriaBuilder) -> {

            javax.persistence.criteria.Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));

            if(status.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status.get()), predicate);

            if(name.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), name.get()), predicate);

            if(branch.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.join("branch").get("id"), branch.get()), predicate);

            return predicate;
        };
    }
}
