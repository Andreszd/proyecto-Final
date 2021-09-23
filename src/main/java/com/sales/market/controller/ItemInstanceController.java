/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.controller;

import com.sales.market.dto.ItemInstanceDto;
import com.sales.market.enums.ItemInstanceStatus;
import com.sales.market.enums.MovementType;
import com.sales.market.model.ItemInstance;
import com.sales.market.service.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/iteminstances")
public class ItemInstanceController extends GenericController<ItemInstance, ItemInstanceDto> {
    private ItemInstanceServiceImpl service;
    private ItemInventoryServiceImpl itemInventoryService;
    private ItemInventoryEntryServiceImpl itemInventoryEntryService;

    public ItemInstanceController(ItemInstanceServiceImpl service,
                                  ItemInventoryServiceImpl itemInventoryService,
                                  ItemInventoryEntryServiceImpl itemInventoryEntryService) {
        this.service = service;
        this.itemInventoryService = itemInventoryService;
        this.itemInventoryEntryService = itemInventoryEntryService;
    }

    @PutMapping("/{idItem}/sale")
    public ItemInstanceDto saleItem(@PathVariable("idItem") @NotNull Long idItem){
        //ItemInstance itemInstance = service.saleItem(idItem);
        ItemInstanceDto itemInstanceDto = toDto(service.saleItem(idItem));
        itemInventoryService.updateStockItem(itemInstanceDto.getItem().getId());
        return itemInstanceDto;
    }

    @GetMapping
    public List<ItemInstanceDto> finAllByStatus(@PathVariable @NotNull Long idItem,
                                                @RequestParam @NotNull String itemIntanceStatus){
        ItemInstanceStatus status = ItemInstanceStatus.valueOf(itemIntanceStatus.toUpperCase());
         
    }

    @PostMapping("/{idItem}/item")
    public List<ItemInstanceDto> save(@RequestBody List<ItemInstanceDto> itemInstanceDtos,
                                @PathVariable("idItem") @NotNull Long idItem) {
        List<ItemInstance> itemInstances = service.save(toModel(itemInstanceDtos), idItem);
        itemInventoryService.updateStockItem(idItem);
        itemInventoryEntryService.registerMovement(MovementType.SALE, idItem, itemInstances);
        return toDto(itemInstances);
    }

    @Override
    protected ItemInstanceService getService() {
        return service;
    }
}
