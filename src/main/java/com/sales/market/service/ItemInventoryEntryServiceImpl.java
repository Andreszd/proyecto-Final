package com.sales.market.service;

import com.sales.market.enums.MovementType;
import com.sales.market.model.ItemInstance;
import com.sales.market.model.ItemInventory;
import com.sales.market.model.ItemInventoryEntry;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInventoryEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemInventoryEntryServiceImpl extends GenericServiceImpl<ItemInventoryEntry> implements ItemInventoryEntryService {
    private final ItemInventoryEntryRepository repository;
    private final ItemInventoryServiceImpl itemInventoryService;

    public ItemInventoryEntryServiceImpl(ItemInventoryEntryRepository repository,
                                         ItemInventoryServiceImpl itemInventoryService) {
        this.repository = repository;
        this.itemInventoryService = itemInventoryService;
    }

    @Override
    protected GenericRepository<ItemInventoryEntry> getRepository() {
        return repository;
    }

    public void registerMovement(MovementType movementType,
                                 Long idItem,
                                 List<ItemInstance> itemInstanceList){
        ItemInventory itemInventory = itemInventoryService.getItemInventoryByIdItem(idItem);
        ItemInventoryEntry itemInventoryEntry = new ItemInventoryEntry();
        itemInventoryEntry.setMovementType(movementType);
        itemInventoryEntry.setQuantity(new BigDecimal(itemInstanceList.size()));
        itemInventoryEntry.setItemInventory(itemInventory);
        repository.save(itemInventoryEntry);
    }
}