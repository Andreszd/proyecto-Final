package com.sales.market.service;

import com.sales.market.model.ItemInventoryEntry;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInventoryEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemInventoryEntryServiceImpl extends GenericServiceImpl<ItemInventoryEntry> implements ItemInventoryEntryService {
    private final ItemInventoryEntryRepository repository;

    public ItemInventoryEntryServiceImpl(ItemInventoryEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    protected GenericRepository<ItemInventoryEntry> getRepository() {
        return repository;
    }
}