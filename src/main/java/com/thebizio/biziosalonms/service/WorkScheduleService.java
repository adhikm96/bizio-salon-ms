package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.work_schedule.*;
import com.thebizio.biziosalonms.entity.WorkSchedule;
import com.thebizio.biziosalonms.entity.WorkScheduleItem;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.repo.WorkScheduleRepo;
import com.thebizio.biziosalonms.specification.WorkScheduleSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkScheduleService {
    final WorkScheduleRepo workScheduleRepo;

    final WorkScheduleSpecification workScheduleSpecification;

    final ModelMapper modelMapper;

    public WorkScheduleService(WorkScheduleRepo workScheduleRepo, WorkScheduleSpecification workScheduleSpecification, ModelMapper modelMapper) {
        this.workScheduleRepo = workScheduleRepo;
        this.workScheduleSpecification = workScheduleSpecification;
        this.modelMapper = modelMapper;
    }

    public WorkSchedule findById(UUID id) {
        return workScheduleRepo.findById(id).orElseThrow(() -> new NotFoundException("work-schedule not found"));
    }

    public String toggleWorkSchedule(UUID wsId, StatusEnum status) {
        WorkSchedule salonUser = findById(wsId);
        if (salonUser.getStatus().equals(status)) throw new ValidationException("Work Schedule is already " + status.toString().toLowerCase());
        salonUser.setStatus(status);
        workScheduleRepo.save(salonUser);
        return ConstantMsg.OK;
    }

    public List<WorkScheduleListResDto> list(Map<String, String> filters) {
        return workScheduleRepo.findAll(workScheduleSpecification.listWithFilter(filters), Sort.by(Sort.Direction.DESC,"modified"))
                .stream()
                .map(this::mapToListDto)
                .collect(Collectors.toList());
    }

    public WorkScheduleListResDto mapToListDto(WorkSchedule ws) {
        return modelMapper.map(ws, WorkScheduleListResDto.class);
    }

    @Transactional
    public WorkScheduleDetailDto saveWorkSchedule(WorkScheduleCreateUpdateDto createDto) {
        WorkSchedule ws = new WorkSchedule();
        ws.setStatus(StatusEnum.ENABLED);
        setFields(ws, createDto);
        workScheduleRepo.save(ws);
        return mapToDetailDto(ws);
    }

    private WorkScheduleDetailDto mapToDetailDto(WorkSchedule ws) {
        WorkScheduleDetailDto detailDto = modelMapper.map(ws, WorkScheduleDetailDto.class);
        detailDto.setItems(ws.getWorkScheduleItems().stream().map(i -> modelMapper.map(i, WsItemWithId.class)).collect(Collectors.toList()));
        return detailDto;
    }

    private void setFields(WorkSchedule ws, WorkScheduleCreateUpdateDto createDto) {
        ws.setName(createDto.getName());
        ws.clearItems();
        if(createDto.getItems() != null) createDto.getItems().forEach(itemDto -> {
            ws.appendItem(modelMapper.map(itemDto, WorkScheduleItem.class));
        });
    }

    @Transactional
    public void updateWorkSchedule(UUID wsId, WorkScheduleCreateUpdateDto dto) {
        WorkSchedule ws = findById(wsId);
        setFields(ws, dto);
        workScheduleRepo.save(ws);
    }

    public WorkScheduleDetailDto fetchWorkSchedule(UUID wsId) {
        return mapToDetailDto(findById(wsId));
    }
}
