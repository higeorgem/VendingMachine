package com.example.com.bean;

import com.example.com.model.Denomination;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Local
@Stateless
public class MoneyConverterBean implements MoneyConverterBeanI {
    @EJB
    CashDrawerBeanI cashDrawerBeanI;
    @Override
    public BigDecimal getMoneyValueFromDenominations(Map<Denomination, Integer> money) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Map.Entry m : money.entrySet()){
            Denomination denomination = Denomination.valueOf(m.getKey().toString());
            int count = Integer.parseInt(m.getValue().toString());
            double denominationValue = denomination.getValue()*count;
            amount = amount.add(new BigDecimal(denominationValue));

        }
        return amount;
    }

    @Override
    public Map<Denomination, Integer> getDenominationsForMoney(BigDecimal amount) throws SQLException {
        Map<Denomination, Integer> denominations = new HashMap<>();
        List<Denomination> denominationList = Arrays.asList(Denomination.values());
        for (Denomination denomination : denominationList){
            int denominationCount = getDenominationCount(denomination,amount);
            amount = amount.subtract(new BigDecimal(denomination.getValue()*denominationCount));
            denominations.put(denomination,denominationCount);
        }
        return denominations;
    }

    private int getDenominationCount(Denomination denomination, BigDecimal amount) throws SQLException {
        int denominationCount;
        if (amount.compareTo(new BigDecimal(denomination.getValue()))>=0){
            double i = Double.parseDouble(amount.divide(new BigDecimal(denomination.getValue()))+" ");
            i = Math.floor(i);
            denominationCount = (int) i;
            int avilableCount = getAvailableCountForDenomination(denomination);
//            return avilableCount >= denominationCount? denominationCount:avilableCount;
            return Math.min(avilableCount, denominationCount);
        }
        return 0;
    }
    private int getAvailableCountForDenomination(Denomination denomination) throws SQLException {
        return cashDrawerBeanI.findByDenomination(denomination).getCount();
    }
}
