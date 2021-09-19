/**
 * @author: Edson A. Terceros T.
 */

package com.sales.market.repository;


import com.sales.market.model.ItemInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ItemInstanceRepository extends GenericRepository<ItemInstance> {
    @Query("select sum(price) from ItemInstance where item_id = :idItem")
    BigDecimal totalPriceByIdItem(@Param("idItem") Long idItem);


}
