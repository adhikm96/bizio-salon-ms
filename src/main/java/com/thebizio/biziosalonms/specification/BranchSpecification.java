package com.thebizio.biziosalonms.specification;

import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.service.StrUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.Predicate;
import java.util.Map;

@Service
public class BranchSpecification {

    final StrUtil strUtil;

    public BranchSpecification(StrUtil strUtil) {
        this.strUtil = strUtil;
    }

    public Specification<Branch> listWithFilter(@RequestParam Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));

            if(filters.containsKey("status") && !filters.get("status").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), strUtil.getEnumFrom(filters.get("status"))));

            if(filters.containsKey("zipcode") && !filters.get("zipcode").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("address").get("zipcode"), filters.get("zipcode")));

            if(filters.containsKey("email") && !filters.get("email").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("email"), filters.get("email")));

            if(filters.containsKey("workSchedule") && !filters.get("workSchedule").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("workSchedule").get("id"), filters.get("workSchedule")));

            return query.where(predicate).getRestriction();
        };
    }
}
