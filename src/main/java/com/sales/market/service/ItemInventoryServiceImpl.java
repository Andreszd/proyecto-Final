package com.sales.market.service;

import com.sales.market.model.Item;
import com.sales.market.model.ItemInventory;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInventoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
        if (item == null) {
            System.out.println("error");
        }
        itemInventory.setItem(item);
        itemInventory.setTotalPrice(itemInstanceService.sumTotalPriceByIdItem(idItem));
        itemInventory.setStockQuantity(itemInstanceService.amountItemsInStock());
        return repository.save(itemInventory);
    }

    public List<ItemInventory> getAllItemInventoryByIdItem(Long idItem){
        List<ItemInventory> itemInventories, itemInventoriesByIdItem;
        itemInventories = repository.findAll();
        itemInventoriesByIdItem = itemInventories
                .stream()
                .filter(inventory -> inventory.getItem().getId().equals(idItem))
                .collect(Collectors.toList());

        return itemInventoriesByIdItem;
    }

    public ItemInventory getItemInventoryByIdItem(Long idItem){
        return getAllItemInventoryByIdItem(idItem).get(0);
    }

    public void updateStockItem(Long idItem){
        BigDecimal amountItemsInStock = itemInstanceService.amountItemsInStock();
        ItemInventory itemInventory = getItemInventoryByIdItem(idItem);
        if (itemInventory != null){
            if (amountItemsInStock.compareTo(itemInventory.getLowerBoundThreshold()) < -1){
                //amount stock is less than limit defined
                System.out.println("amount stock is less than limit defined");
                //TODO define feature send email of advice about actual stock to the admin
            }
            itemInventory.setStockQuantity(amountItemsInStock);
            itemInventory.setTotalPrice(itemInstanceService.sumTotalPriceByIdItem(idItem));
            repository.save(itemInventory);
        }
    }
}