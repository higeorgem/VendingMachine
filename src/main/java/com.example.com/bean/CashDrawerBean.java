package com.example.com.bean;

import com.example.com.model.CashDrawer;
import com.example.com.model.Denomination;
import dao.DbClass;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Local
@Stateless
public class CashDrawerBean extends Bean<CashDrawer> implements CashDrawerBeanI {
    @Inject
    DbClass dbClass;
    @Override
    public CashDrawer findByDenomination(Denomination denomination) throws SQLException {
        CashDrawer cashDrawer = new CashDrawer();
        String sql = "SELECT * FROM cashDrawer_tbl WHERE denomination ='"+denomination+"'";
        Statement statement = dbClass.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            cashDrawer.setId(rs.getInt("id"));
            cashDrawer.setDenomination(denomination);
            cashDrawer.setCount(rs.getInt("dn_count"));
        }
        return cashDrawer;
    }
}
