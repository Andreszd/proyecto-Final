package com.sales.market.model;

import com.sales.market.dto.ItemInventoryEntryDto;
import com.sales.market.enums.MovementType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ItemInventoryEntry extends ModelBase<ItemInventoryEntryDto> {
    @ManyToOne
    private ItemInventory itemInventory;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private BigDecimal quantity; // represent sale or buy instances quantity

    //private String itemInstanceSkus; represents a list of the sku of the involved item instances

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
