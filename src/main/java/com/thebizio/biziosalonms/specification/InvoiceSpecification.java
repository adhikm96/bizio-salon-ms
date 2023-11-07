package com.thebizio.biziosalonms.specification;

import com.thebizio.biziosalonms.entity.Appointment;
import com.thebizio.biziosalonms.entity.Invoice;
import com.thebizio.biziosalonms.service.StrUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Map;
import java.util.UUID;

@Service
public class InvoiceSpecification {

    final StrUtil strUtil;

    public InvoiceSpecification(StrUtil strUtil) {
        this.strUtil = strUtil;
    }

    public Specification<Invoice> listWithFilter(@RequestParam Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));

            if(filters.containsKey("status") && !filters.get("status").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), strUtil.getInvoiceStatusEnumFrom(filters.get("status"))));

            if(filters.containsKey("name") && !filters.get("name").isEmpty())
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), filters.get("name")));

            if(filters.containsKey("branch") && !filters.get("branch").isEmpty()){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("branch").get("id"), UUID.fromString(filters.get("branch"))));
            }

            if(filters.containsKey("postingDate") && !filters.get("postingDate").isEmpty()){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("postingDate"), filters.get("postingDate")));
            }

            if(filters.containsKey("customer") && !filters.get("customer").isEmpty()){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.join("customerUser").get("id"), UUID.fromString(filters.get("customer"))));
            }

            if(filters.containsKey("appointment") && !filters.get("appointment").isEmpty()){
                Join<Invoice, Appointment> appointment = root.join( "appointments");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(appointment.get("id"), UUID.fromString(filters.get("appointment"))));
            }

            if(filters.containsKey("customerName") && !filters.get("customerName").isEmpty()){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("customerName"), filters.get("customerName")));
            }

            return query.where(predicate).getRestriction();
        };
    }
}
