/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.controller;

import com.sales.market.dto.ItemInstanceDto;
import com.sales.market.model.Item;
import com.sales.market.model.ItemInstance;
import com.sales.market.service.ItemInstanceService;
import com.sales.market.service.ItemInstanceServiceImpl;
import com.sales.market.service.ItemInventoryServiceImpl;
import com.sales.market.service.ItemServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/iteminstances")
public class ItemInstanceController extends GenericController<ItemInstance, ItemInstanceDto> {
    private ItemInstanceServiceImpl service;
    private ItemInventoryServiceImpl itemInventoryService;

    public ItemInstanceController(ItemInstanceServiceImpl service,
                                  ItemInventoryServiceImpl itemInventoryService) {
        this.service = service;
        this.itemInventoryService = itemInventoryService;
    }

    @PutMapping("/{idItem}/sale")
    public ItemInstanceDto saleItem(@PathVariable("idItem") @NotNull Long idItem){
        //ItemInstance itemInstance = service.saleItem(idItem);
        ItemInstanceDto itemInstanceDto = toDto(service.saleItem(idItem));
        itemInventoryService.updateStockItem(itemInstanceDto.getItem().getId());
        return itemInstanceDto;
    }

    @PostMapping("/{idItem}/item")
    public ItemInstanceDto save(@RequestBody ItemInstanceDto element,
                             @PathVariable("idItem") Long idItem) {

        ItemInstanceDto itemInstanceDto = toDto(service.save(toModel(element), idItem));
        itemInventoryService.updateStockItem(idItem);
        return itemInstanceDto;
    }

    @Override
    protected ItemInstanceService getService() {
        return service;
    }
}
