/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.service;

import com.sales.market.dto.ItemInstanceDto;
import com.sales.market.enums.ItemInstanceStatus;
import com.sales.market.enums.MovementType;
import com.sales.market.model.Item;
import com.sales.market.model.ItemInstance;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInstanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemInstanceServiceImpl extends GenericServiceImpl<ItemInstance> implements ItemInstanceService {
    private final ItemInstanceRepository repository;
    private final ItemService itemService;

    public ItemInstanceServiceImpl(ItemInstanceRepository repository,
                                   ItemService itemService) {
        this.repository = repository;
        this.itemService = itemService;
    }

    @Override
    protected GenericRepository<ItemInstance> getRepository() {
        return repository;
    }

    @Override
    public ItemInstance bunchSave(ItemInstance itemInstance) {
        // here make all objects save other than this resource
        if (itemInstance.getItem() != null) {
            // todo habria que distinguir si permitiremos guardar y  actualizar o ambos mitando el campo id
            itemService.save(itemInstance.getItem());
        }
        return super.bunchSave(itemInstance);
    }

    public BigDecimal sumTotalPriceByIdItem(Long itemId){
        return repository.totalPriceByIdItem(itemId);
    }

    public BigDecimal amountItemsInStock(){
        int amountItems = getItemsInstanceByState(ItemInstanceStatus.AVAILABLE).size();
        return new BigDecimal(amountItems);
    }

    //TODO check when the param type isnt how enum type
    public List<ItemInstance> getItemsInstanceByState(ItemInstanceStatus type){
        return repository.findAll().stream()
                .filter(item -> item.getItemInstanceStatus() == type)
                .collect(Collectors.toList());
    }

    public ItemInstance updateStatusItem(Long idItemInstance, ItemInstanceStatus itemInstanceStatus){
        ItemInstance itemInstance = repository.getById(idItemInstance);
        if (itemInstanceStatus == ItemInstanceStatus.AVAILABLE){
            return itemInstance;
        }

        if (itemInstanceStatus == ItemInstanceStatus.SCREWED  &&
                itemInstance.getItemInstanceStatus() != ItemInstanceStatus.SOLD){
            itemInstance.setItemInstanceStatus(itemInstanceStatus);
            itemInstance.setPrice(0L);
        }

        if (itemInstanceStatus == ItemInstanceStatus.SOLD &&
                itemInstance.getItemInstanceStatus() != ItemInstanceStatus.SCREWED){
            itemInstance.setItemInstanceStatus(itemInstanceStatus);
        }

        return repository.save(itemInstance);
    }


    public List<ItemInstance> updateStatusItems(List<ItemInstanceDto> itemInstanceDtos,
                                                ItemInstanceStatus itemInstanceStatus){
        List<ItemInstance> itemInstances = new ArrayList<>();
        try{
            for (ItemInstanceDto itemInstance : itemInstanceDtos) {
                Long idItem = itemInstance.getId();
                if (idItem != null){
                    itemInstances.add(updateStatusItem(idItem, itemInstanceStatus));
                }
            }
            return itemInstances;
        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            return itemInstances;
        }
    }

    public ItemInstance save(ItemInstance model, Long idItem) {
        Item item;
        if (model.getItem() == null){
           item = itemService.findById(idItem);
           model.setItem(item);
        }
        return super.save(model);
    }

    public List<ItemInstance> save(List<ItemInstance> itemInstances, Long idItem) {
        List<ItemInstance> result = new ArrayList<>();
        for (ItemInstance itemInstance:itemInstances) {
            result.add(save(itemInstance, idItem));
        }
        return result;
    }

    public MovementType parseEnumItemInstanceToEnumMovementType(ItemInstanceStatus itemInstanceStatus){
        MovementType movementType = null;
        if (ItemInstanceStatus.SOLD == itemInstanceStatus){
            movementType = MovementType.SALE;
        }
        if (ItemInstanceStatus.SCREWED == itemInstanceStatus){
            movementType =   MovementType.REMOVED;
        }
        if (ItemInstanceStatus.AVAILABLE == itemInstanceStatus){
            movementType =   MovementType.BUY;
        }
        return movementType;
    }
}
