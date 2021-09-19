/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.service;

import com.sales.market.enums.ItemInstanceStatus;
import com.sales.market.model.ItemInstance;
import com.sales.market.repository.GenericRepository;
import com.sales.market.repository.ItemInstanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ItemInstanceServiceImpl extends GenericServiceImpl<ItemInstance> implements ItemInstanceService {
    private final ItemInstanceRepository repository;
    private final ItemService itemService;

    public ItemInstanceServiceImpl(ItemInstanceRepository repository, ItemService itemService) {
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
        int amountItems = getItemsInstanceByState(ItemInstanceStatus.AVAILABLE.name()).size();
        return new BigDecimal(amountItems);
    }

    //TODO check when the param type isnt how enum type
    public List<ItemInstance> getItemsInstanceByState(String type){
        ItemInstanceStatus typeOfEnum = ItemInstanceStatus.valueOf(type);
        return repository.findAll().stream()
                .filter(item -> item.getItemInstanceStatus() == typeOfEnum)
                .collect(Collectors.toList());
    }
}
