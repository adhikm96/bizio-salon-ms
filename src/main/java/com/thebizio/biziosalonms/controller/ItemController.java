package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.item.CreateUpdateItemDto;
import com.thebizio.biziosalonms.dto.item.ItemDetailDto;
import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.enums.ItemType;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.item.ItemDetailPrj;
import com.thebizio.biziosalonms.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    ResponseEntity<List<ItemDetailPrj>> getAll(@RequestParam Optional<ItemType> itemType, @RequestParam Optional<String> code,
                                               @RequestParam Optional<StatusEnum> status, @RequestParam Optional<String> name) {
        return ResponseEntity.ok(itemService.getAllItem(itemType,code,status,name));
    }

    @GetMapping("/{itemId}")
    ResponseEntity<ItemDetailPrj> getById(@PathVariable UUID itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    @PostMapping
    public ResponseEntity<ItemDetailDto> create(@RequestBody @Valid CreateUpdateItemDto dto){
        return ResponseEntity.ok(itemService.createItem(dto));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemDetailDto> update(@PathVariable(name = "itemId") UUID itemId,
                                                @RequestBody @Valid CreateUpdateItemDto dto){
        return ResponseEntity.ok(itemService.updateItem(itemId,dto));
    }

    @PostMapping("/enable/{itemId}")
    public ResponseEntity<ResponseMessageDto> enableItem(@PathVariable(name = "itemId") UUID itemId){
        return ResponseEntity.ok(new ResponseMessageDto(itemService.toggleItem(itemId, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{itemId}")
    public ResponseEntity<ResponseMessageDto> disableItem(@PathVariable(name = "itemId") UUID itemId) {
        return ResponseEntity.ok(new ResponseMessageDto(itemService.toggleItem(itemId, StatusEnum.DISABLED)));
    }
}
