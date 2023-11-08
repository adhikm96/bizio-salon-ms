package com.thebizio.biziosalonms.specification;

import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.WorkSchedule;
import com.thebizio.biziosalonms.service.StrUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

@Service
public class WorkScheduleSpecification {

    public Specification<WorkSchedule> listWithFilter(@RequestParam Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));

            if(filters.containsKey("status") && !filters.get("status").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), StrUtil.getStatusEnumFrom(filters.get("status"))));

            if(filters.containsKey("name") && !filters.get("name").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), filters.get("name")));

            if(filters.containsKey("branch") && !filters.get("branch").isEmpty()){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("branches").get("name"), filters.get("branch")));
            }

            return query.where(predicate).getRestriction();
        };
    }
}
