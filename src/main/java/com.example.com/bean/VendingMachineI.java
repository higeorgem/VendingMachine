package com.example.com.bean;

import com.example.com.model.Denomination;
import com.example.com.model.Product;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Map;

@Local
public interface VendingMachineI {
    BigDecimal calculateRequiredAmount(Product product, int quantity);
    boolean makeSale(Product product, int quantity, Map<Denomination, Integer>denominations)throws Exception;
}
