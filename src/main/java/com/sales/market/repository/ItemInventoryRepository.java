package com.sales.market.repository;

import com.sales.market.model.Item;
import com.sales.market.model.ItemInventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemInventoryRepository extends GenericRepository<ItemInventory> {
    @Query("select inv from ItemInventory as inv, Item where  inv.id = :idItem")
    List<ItemInventory> getAllByIdItem(@Param("idItem") Long idItem);
}