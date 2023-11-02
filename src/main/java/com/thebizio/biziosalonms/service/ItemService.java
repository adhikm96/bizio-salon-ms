package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.item.CreateUpdateItemDto;
import com.thebizio.biziosalonms.dto.item.ItemDetailDto;
import com.thebizio.biziosalonms.entity.Item;
import com.thebizio.biziosalonms.enums.ItemType;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.AlreadyExistsException;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.item.ItemDetailPrj;
import com.thebizio.biziosalonms.repo.ItemRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ItemDetailPrj> getAllItem(Optional<ItemType> itemType, Optional<String> code, Optional<StatusEnum> status, Optional<String> name) {
        if (code.isPresent()) return itemRepo.findByCode(code.get());
        if (name.isPresent()) return itemRepo.findByName(name.get());
        if (itemType.isPresent() && status.isPresent()) return itemRepo.findAllByItemTypeAndStatus(itemType.get(),status.get());
        if (itemType.isPresent()) return itemRepo.findByItemType(itemType.get());
        if (status.isPresent()) return itemRepo.findByStatus(status.get());
        else return itemRepo.findAllItem();
    }

    public ItemDetailPrj getItemById(UUID itemId) {
        return itemRepo.findItemById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public ItemDetailDto createItem(CreateUpdateItemDto dto) {
        if (itemRepo.existsByCode(dto.getCode())) throw new AlreadyExistsException("Item code already exists");
        Item item = new Item();
        return modelMapper.map(setItemDetails(item,dto),ItemDetailDto.class);
    }

    public Item fetchItemById(UUID itemId){
        return itemRepo.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
    }

    public ItemDetailDto updateItem(UUID itemId, CreateUpdateItemDto dto) {
        Item item = fetchItemById(itemId);
        if (!item.getCode().equals(dto.getCode()) && itemRepo.existsByCode(dto.getCode())) throw new AlreadyExistsException("Item code already exists");
        return modelMapper.map(setItemDetails(item,dto),ItemDetailDto.class);
    }

    private Item setItemDetails(Item item,CreateUpdateItemDto dto){
        item.setName(dto.getName());
        item.setCode(dto.getCode());
        item.setItemType(dto.getItemType());
        item.setBrand(dto.getBrand());
        item.setCost(dto.getCost());
        item.setPrice(dto.getPrice());
        item.setDescription(dto.getDescription());
        if (item.getStatus() == null) item.setStatus(StatusEnum.ENABLED);
        return itemRepo.save(item);
    }

    public String toggleItem(UUID itemId, StatusEnum status) {
        Item item = fetchItemById(itemId);
        if (item.getStatus().equals(status)) throw new ValidationException("Item is already "+status.toString().toLowerCase());
        item.setStatus(status);
        itemRepo.save(item);
        return ConstantMsg.OK;
    }
}
