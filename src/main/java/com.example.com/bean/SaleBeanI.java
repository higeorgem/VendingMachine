package com.example.com.bean;

import com.example.com.model.Sale;

import javax.ejb.Local;

@Local
public interface SaleBeanI {
    boolean makeSale(Sale sale);
}
