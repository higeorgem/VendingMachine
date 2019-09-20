package com.example.com.bean;

import com.example.com.model.Product;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
@Stateless
public class ProductBean implements ProductBeanI{
    @Override
    public void addProduct(Product product) {
    }
}
