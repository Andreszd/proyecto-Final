package com.sales.market.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemInventoryServiceImplTest {
    @Autowired
    private ItemInventoryServiceImpl itemInventoryService;

    @Test
    public void givenItemInventoryWhenPersistItThenSuccessful(){
    }

}