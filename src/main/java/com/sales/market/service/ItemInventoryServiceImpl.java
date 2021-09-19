package com.sales.market.service;

import com.sales.market.model.Item;
import com.sales.market.model.ItemInventory;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemInventoryServiceImpl extends GenericServiceImpl<ItemInventory> implements ItemInventoryService {
    private final ItemInventoryRepository repository;
    private final ItemServiceImpl itemService;
    private final ItemInstanceServiceImpl itemInstanceService;

    public ItemInventoryServiceImpl(ItemInventoryRepository repository, ItemServiceImpl itemService, ItemInstanceServiceImpl itemInstanceService) {
        this.repository = repository;
        this.itemService = itemService;
        this.itemInstanceService = itemInstanceService;
    }

    @Override
    protected GenericRepository<ItemInventory> getRepository() {
        return repository;
    }

    public ItemInventory createItemInventory(Long idItem, ItemInventory itemInventory) {
        Item item = itemService.findById(idItem);
        itemInventory.setItem(item);
        itemInventory.setTotalPrice(itemInstanceService.sumTotalPriceByIdItem(idItem));
        itemInventory.setStockQuantity(itemInstanceService.amountItemsInStock());
        return repository.save(itemInventory);
    }
}