/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.model;

import com.sales.market.dto.ItemInstanceDto;
import com.sales.market.enums.ItemInstanceStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class ItemInstance extends ModelBase<ItemInstanceDto> {
    @OneToOne
    private Item item;
    private String identifier;// sku

    private Boolean featured = Boolean.FALSE;

    private Double price;

    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    private ItemInstanceStatus itemInstanceStatus = ItemInstanceStatus.AVAILABLE;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }


    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public ItemInstanceStatus getItemInstanceStatus() {
        return itemInstanceStatus;
    }

    public void setItemInstanceStatus(ItemInstanceStatus itemInstanceStatus) {
        this.itemInstanceStatus = itemInstanceStatus;
    }

    /*@Override
    public ModelBase toDomain(ItemInstanceDto element, ModelMapper mapper) {
        super.toDomain(element, mapper);
        setItem((Item) new Item().toDomain(element.getItemDto(), mapper));
        return this;
    }*/
}
