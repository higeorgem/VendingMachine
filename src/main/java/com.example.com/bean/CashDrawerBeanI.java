package com.example.com.bean;

import com.example.com.model.CashDrawer;
import com.example.com.model.Denomination;

import javax.ejb.Local;
import java.sql.SQLException;

@Local
public interface CashDrawerBeanI extends BeanI<CashDrawer>{
    CashDrawer findByDenomination(Denomination denomination) throws SQLException;
}
