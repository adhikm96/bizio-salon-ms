package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.appointment.AppointmentDetailDto;
import com.thebizio.biziosalonms.dto.appointment.AppointmentListDto;
import com.thebizio.biziosalonms.dto.appointment.CancelReasonDto;
import com.thebizio.biziosalonms.dto.appointment.CreateAppointmentDto;
import com.thebizio.biziosalonms.entity.Appointment;
import com.thebizio.biziosalonms.entity.Invoice;
import com.thebizio.biziosalonms.entity.Item;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import com.thebizio.biziosalonms.exception.AlreadyExistsException;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.repo.AppointmentRepo;
import com.thebizio.biziosalonms.service.keycloak.UtilService;
import com.thebizio.biziosalonms.specification.AppointmentSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {

    Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SalonUserService salonUserService;

    @Autowired
    private UtilService utilService;

    public List<AppointmentListDto> getAllAppointment(Optional<AppointmentStatus> status, Optional<UUID> customer, Optional<LocalDate> appointmentDate,
                                                      Optional<LocalTime> appointmentTime, Optional<UUID> branch, Optional<LocalTime> startTime,
                                                      Optional<LocalTime> endTime, Optional<UUID> assignedTo, Optional<UUID> invoice) {

        List<Appointment> appointments = appointmentRepo.findAll(AppointmentSpecification.findWithFilter(status, customer, appointmentDate,
                appointmentTime, branch, startTime, endTime, assignedTo, invoice), Sort.by(Sort.Direction.DESC, "modified"));

        return modelMapper.map(appointments, new TypeToken<List<AppointmentListDto>>() {
        }.getType());
    }

    public Appointment fetchAppointmentById(UUID appointmentId) {
        return appointmentRepo.findById(appointmentId).orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    public Appointment fetchAppointmentByInvoice(Invoice invoice){
        return appointmentRepo.findByInvoice(invoice).orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    public AppointmentDetailDto getAppointmentById(UUID appointmentId) {
        return modelMapper.map(fetchAppointmentById(appointmentId), AppointmentDetailDto.class);
    }

    public AppointmentDetailDto createAppointment(CreateAppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.setCustomerUser(customerService.fetchCustomerById(dto.getCustomerId()));
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setExpectedStartTime(dto.getExpectedStartTime());
        appointment.setExpectedEndTime(dto.getExpectedEndTime());
        appointment.setNotes(dto.getNotes());
        appointment.setBranch(branchService.findById(dto.getBranchId()));
        appointment.setNotes(dto.getNotes());
        List<Item> items = new ArrayList<>();
        for (UUID itemId : dto.getProductAndServices()) {
            Item item = itemService.fetchItemById(itemId);
            items.add(item);
        }
        appointment.setProductAndServices(items);
        appointment.setStatus(AppointmentStatus.BOOKED);

        if (dto.getAssignedTo() != null) appointment.setAssignedTo(salonUserService.findById(dto.getAssignedTo()));
        return modelMapper.map(appointmentRepo.save(appointment), AppointmentDetailDto.class);
    }

    public String assignAppointment(UUID appointmentId, UUID salonUserId) {
        Appointment appointment = fetchAppointmentById(appointmentId);
        User user = salonUserService.findById(salonUserId);
        if (appointment.getAssignedTo().equals(user))
            throw new AlreadyExistsException("Appointment is already assigned to user");
        appointment.setAssignedTo(user);
        appointmentRepo.save(appointment);
        return ConstantMsg.OK;
    }

    public String unAssignAppointment(UUID appointmentId) {
        Appointment appointment = fetchAppointmentById(appointmentId);
        if (appointment.getAssignedTo() == null) throw new NotFoundException("Appointment is not assigned to anyone");
        appointment.setAssignedTo(null);
        appointmentRepo.save(appointment);
        return ConstantMsg.OK;
    }

    public String startAppointment(UUID appointmentId) {
        Appointment appointment = fetchAppointmentById(appointmentId);
        User user = salonUserService.findAuthSalonUser();
        if (!appointment.getAssignedTo().equals(user))
            throw new ValidationException("Appointment is not assigned to you");
        appointment.setStartTime(LocalTime.now());
        appointment.setStatus(AppointmentStatus.IN_PROGRESS);
        appointmentRepo.save(appointment);
        return ConstantMsg.OK;
    }

    public String cancelAppointment(UUID appointmentId, CancelReasonDto dto) {
        Appointment appointment = fetchAppointmentById(appointmentId);
        if (appointment.getStatus().equals(AppointmentStatus.CANCELLED))
            throw new ValidationException("Appointment is already cancelled");
        if (appointment.getStatus().equals(AppointmentStatus.IN_PROGRESS))
            throw new ValidationException("Can't cancel in-progress appointment");
        if (appointment.getStatus().equals(AppointmentStatus.OVERDUE))
            throw new ValidationException("Can't cancel overdue appointment");
        if (appointment.getStatus().equals(AppointmentStatus.RESCHEDULED))
            throw new ValidationException("Cancel the rescheduled appointment");
        if (appointment.getStatus().equals(AppointmentStatus.CHECKOUT))
            throw new ValidationException("Appointment is in checkout process, Can't cancel it");
        if (appointment.getStatus().equals(AppointmentStatus.COMPLETED))
            throw new ValidationException("Can't cancel completed appointment");

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationFrom(utilService.getAuthEmail());
        appointment.setCancellationReason(dto.getCancelReason());
        appointmentRepo.save(appointment);
        return ConstantMsg.OK;
    }

    public String rescheduleAppointment(UUID appointmentId, UUID rescheduleAppointmentId) {
        Appointment appointment = fetchAppointmentById(appointmentId);
        Appointment rescheduleAppointment = fetchAppointmentById(rescheduleAppointmentId);

        if (appointment.equals(rescheduleAppointment))
            throw new ValidationException("Can't reschedule with same appointment");

        appointment.setRescheduledWith(rescheduleAppointment);
        appointment.setRescheduledBy(utilService.getAuthEmail());
        appointmentRepo.save(appointment);
        return ConstantMsg.OK;
    }

    @Scheduled(cron = "0 */5 * ? * *") // run every 5 min
    public void overdueAppointment() {

        logger.info("---------- schedular for appointment overdue started at " + LocalTime.now() + "-------------");

        List<Appointment> appointments = appointmentRepo.findAllByStatusAndAppointmentDateBeforeAndAppointmentTimeBefore(
                AppointmentStatus.SCHEDULED, LocalDate.now(), LocalTime.now());

        if (!appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                appointment.setStatus(AppointmentStatus.OVERDUE);
                appointmentRepo.save(appointment);
            }
        }

        logger.info("---------- schedular for appointment overdue ended at " + LocalTime.now() + "-------------");
    }
}
