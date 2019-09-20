package com.example.com.bean;

import com.example.com.model.Sale;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
@Stateless
public class SaleBean implements SaleBeanI{
    @Override
    public boolean makeSale(Sale sale) {
        return false;
    }
}
