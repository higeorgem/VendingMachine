package com.example.com.bean;

import exception.InsufficientAmount;
import exception.InsufficientProductQuantity;
import com.example.com.model.CashDrawer;
import com.example.com.model.Denomination;
import com.example.com.model.Product;
import com.example.com.model.Sale;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Singleton
@Startup
@Local
public class VendingMachine implements VendingMachineI {
    @PostConstruct
    public void initialize() throws SQLException {
//        we want to initialize our vending machine with some money amount of each each denomination
        List<Denomination> denominations = Arrays.asList(Denomination.values());
        for (Denomination denomination:denominations){
            CashDrawer cashDrawer = cashDrawerBeanI.findByDenomination(denomination);
            if (cashDrawer == null){
                cashDrawer = new CashDrawer();
                cashDrawer.setDenomination(denomination);
                cashDrawer.setCount(10);
                cashDrawerBeanI.add(cashDrawer);
            }
        }
    }
    @EJB
    SaleBeanI saleBeanI;
    @EJB
    StockBeanI stockBeanI;
    @EJB
    MoneyConverterBeanI moneyConverterBeanI;
    @EJB
    CashDrawerBeanI cashDrawerBeanI;
    @Override
    public BigDecimal calculateRequiredAmount(Product product, int quantity) {
        return product.getUnitPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean makeSale(Product product, int quantity, Map<Denomination, Integer>denominations) throws InsufficientAmount, InsufficientProductQuantity, SQLException {
        BigDecimal amount = moneyConverterBeanI.getMoneyValueFromDenominations(denominations);
//        validate amount
        if (amount.compareTo(this.calculateRequiredAmount(product, quantity)) < 0)
            throw new InsufficientAmount();
//        validate quantity
            if (stockBeanI.getStockBalance(product) < quantity)
                throw new InsufficientProductQuantity();

            boolean giveChange = false;
            if (amount.compareTo(this.calculateRequiredAmount(product, quantity)) > 0)
                giveChange = true;
            //            Add denomination to Vending Machine's cash drawer
        for (Map.Entry m: denominations.entrySet()){
            Denomination denomination1 = Denomination.valueOf(m.getKey().toString());
            CashDrawer cashDrawer = cashDrawerBeanI.findByDenomination(denomination1);
            cashDrawer.setCount(cashDrawer.getCount()+Integer.parseInt(m.getValue()+""));
            cashDrawerBeanI.edit(cashDrawer);
        }
            Sale sale = new Sale();
            sale.setDate(new Date());
            sale.setAmount(this.calculateRequiredAmount(product, quantity));
            sale.setProduct(product);
            sale.setQuantity(quantity);
            boolean status = saleBeanI.makeSale(sale);

            if (giveChange)
            status = status && (giveChange && !this.giveChange(amount.subtract(this.calculateRequiredAmount(product, quantity))).isEmpty());
            if (!status)
                this.refundCustomerMoney(denominations);
            return status;
        }
//        IF THE MACHINE IS NOT ABLE TO GIVE CHANGE WE REFUND CUSTOMER MONEY. WE ALSO DEDUCT THAT MONEY FROM THE CashDrawer
        private void refundCustomerMoney(Map<Denomination,Integer>map) throws SQLException {
            for (Map.Entry m: map.entrySet()){
                Denomination denomination1 = Denomination.valueOf(m.getKey().toString());
                CashDrawer cashDrawer = cashDrawerBeanI.findByDenomination(denomination1);
                cashDrawer.setCount(cashDrawer.getCount()-Integer.parseInt(m.getValue()+""));
                cashDrawerBeanI.edit(cashDrawer);
            }
            System.out.println(map);
        }
    private Map<Denomination, Integer> giveChange(BigDecimal amount) throws SQLException {
           Map<Denomination, Integer> m = moneyConverterBeanI.getDenominationsForMoney(amount);
           return moneyConverterBeanI.getMoneyValueFromDenominations(m).compareTo(amount)==0?m: new HashMap<Denomination, Integer>();
        }
    }