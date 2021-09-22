package com.sales.market.controller;

import com.sales.market.dto.ItemInventoryDto;
import com.sales.market.model.ItemInventory;
import com.sales.market.service.ItemInventoryService;
import com.sales.market.service.GenericService;
import com.sales.market.service.ItemInventoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/iteminventories")
public class ItemInventoryController extends GenericController<ItemInventory, ItemInventoryDto> {
    private ItemInventoryServiceImpl service;

    public ItemInventoryController(ItemInventoryServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/") //NOTE if doesnt a path so get principal path, so cast exception
    @ResponseStatus(HttpStatus.CREATED)
    public ItemInventory createItemInventory(@RequestBody ItemInventoryDto itemInventoryDto){
        Long idItem = itemInventoryDto.getIdItem();
        ItemInventory itemInventory = toModel(itemInventoryDto);
        return service.createItemInventory(idItem, itemInventory);
    }

    @Override
    protected GenericService getService() {
        return service;
    }
}