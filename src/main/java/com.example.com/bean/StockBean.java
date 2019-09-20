package com.example.com.bean;

import com.example.com.model.Product;

import javax.ejb.Local;
import javax.ejb.Stateful;

@Local
@Stateful
public class StockBean implements StockBeanI{
    @Override
    public int getStockBalance(Product product) {
        return 0;
    }
}
