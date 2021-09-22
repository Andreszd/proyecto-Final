package com.sales.market.controller;

import com.sales.market.dto.ItemInventoryEntryDto;
import com.sales.market.model.ItemInventoryEntry;
import com.sales.market.service.ItemInventoryEntryService;
import com.sales.market.service.GenericService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/iteminventoryentrys")
public class ItemInventoryEntryController extends GenericController<ItemInventoryEntry, ItemInventoryEntryDto> {
    private ItemInventoryEntryService service;

    public ItemInventoryEntryController(ItemInventoryEntryService service) {
        this.service = service;
    }

    @Override
    protected GenericService getService() {
        return service;
    }
}