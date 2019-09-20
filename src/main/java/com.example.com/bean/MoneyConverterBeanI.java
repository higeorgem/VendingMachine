package com.example.com.bean;

import com.example.com.model.Denomination;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Local
public interface MoneyConverterBeanI {
    BigDecimal getMoneyValueFromDenominations(Map<Denomination, Integer> money);
    Map<Denomination,Integer>getDenominationsForMoney(BigDecimal amount) throws SQLException;
}
