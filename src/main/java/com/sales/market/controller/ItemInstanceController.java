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

    @PutMapping("/sale/{idItem}/item")
    public List<ItemInstanceDto> saleItem(@PathVariable("idItem") @NotNull Long idItem,
                                    @RequestBody List<ItemInstanceDto> itemInstanceDtos){

        List<ItemInstanceDto> itemInstancesDto = toDto(service.saleItems(itemInstanceDtos));
        itemInventoryService.updateStockItem(idItem);
        return itemInstancesDto;
    }

    //TODO add mode  filter with iditem and status
    @GetMapping("/state")
    public List<ItemInstanceDto> finAllByStatus(@RequestParam(name = "type") @NotNull String type){
        ItemInstanceStatus state = ItemInstanceStatus.valueOf(type.toUpperCase());
        return toDto(service.getItemsInstanceByState(state));
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
