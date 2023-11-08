package com.thebizio.biziosalonms.specification;

import com.thebizio.biziosalonms.entity.Payment;
import com.thebizio.biziosalonms.service.StrUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentSpecification {

    public Specification<Payment> withFilters(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));

            if(filters.containsKey("paymentType") && !filters.get("paymentType").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentType"), StrUtil.getPaymentTypeFrom(filters.get("paymentType"))));

            if(filters.containsKey("paymentDate") && !filters.get("paymentDate").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentDate"), StrUtil.parsedLocalDate(filters.get("paymentDate"))));

            if(filters.containsKey("invoice") && !filters.get("invoice").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("invoice").get("id"), StrUtil.parsedUUID(filters.get("invoice"))));

            if(filters.containsKey("branch") && !filters.get("branch").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("invoice").join("branch").get("id"), StrUtil.parsedUUID(filters.get("branch"))));

            return query.where(predicate).getRestriction();
        };
    }
}
