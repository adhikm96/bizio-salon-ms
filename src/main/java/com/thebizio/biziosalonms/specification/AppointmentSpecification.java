package com.thebizio.biziosalonms.specification;

import com.thebizio.biziosalonms.entity.Appointment;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public class AppointmentSpecification {

    public static Specification<Appointment> findWithFilter(Optional<AppointmentStatus> status, Optional<UUID> customer, Optional<LocalDate>appointmentDate,
                                                            Optional<LocalTime> appointmentTime, Optional<UUID> branch, Optional<LocalTime> startTime,
                                                            Optional<LocalTime> endTime, Optional<UUID> assignedTo, Optional<UUID> invoice) {
        return (root, query, criteriaBuilder) -> {

            javax.persistence.criteria.Predicate predicate = criteriaBuilder.isNotNull(root.get("id"));

            if(status.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status.get()), predicate);

            if(customer.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.join("customerUser").get("id"), customer.get()), predicate);

            if(appointmentDate.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("appointmentDate"), appointmentDate.get()), predicate);

            if(appointmentTime.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("appointmentTime"), appointmentTime.get()), predicate);

            if(startTime.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("startTime"), startTime.get()), predicate);

            if(endTime.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("startTime"), endTime.get()), predicate);

            if(branch.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.join("branch").get("id"), branch.get()), predicate);

            if(assignedTo.isPresent()) predicate = criteriaBuilder.and(criteriaBuilder.equal(root.join("assignedTo").get("id"), assignedTo.get()), predicate);

            return predicate;
        };
    }
}
