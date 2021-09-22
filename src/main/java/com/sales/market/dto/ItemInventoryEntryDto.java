package com.sales.market.dto;

import com.sales.market.enums.MovementType;
import com.sales.market.model.ItemInventory;
import com.sales.market.model.ItemInventoryEntry;

import java.math.BigDecimal;

public class ItemInventoryEntryDto extends DtoBase<ItemInventoryEntry> {
    private ItemInventory itemInventory;

    private MovementType movementType;

    private BigDecimal quantity; // represent sale or buy instances quantity

    public ItemInventory getItemInventory() {
        return itemInventory;
    }

    public void setItemInventory(ItemInventory itemInventory) {
        this.itemInventory = itemInventory;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

}