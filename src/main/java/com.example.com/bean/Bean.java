package com.example.com.bean;

import com.example.com.model.CashDrawer;
import com.example.com.model.Product;
import com.example.com.model.Sale;
import com.example.com.model.Stock;
import dao.DbClass;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Local
@Stateless
public class Bean<T> implements BeanI<T> {
@EJB
private DbClass dbClass;

Statement statement;
    @Override
    public T add(T t) throws SQLException {

           String sql = generateSql(1,t);
           statement = dbClass.getConnection().createStatement();
           statement.executeUpdate(sql);
        return t;
    }

    @Override
    public T edit(T t) {
        return null;
    }

    @Override
    public T find(T t) {
        return null;
    }

    @Override
    public boolean delete(T t) {
        return false;
    }
    private String generateSql(int operation,T t) throws SQLException {
        switch (operation) {
            case 1:
                if (t instanceof CashDrawer) {
                    CashDrawer cashDrawer= (CashDrawer) t;
                    return "INSERT INTO cashdrawer_tbl(denomination,dn_count) VALUES('"+cashDrawer.getDenomination()+"','"+cashDrawer.getCount()+"')";
                }else if (t instanceof Product) {
                    Product product = (Product) t;
                    return "INSERT INTO product_tbl(name,unitPrice) VALUES('" +product.getName()+"','"+product.getUnitPrice()+"')";
                }else if (t instanceof Stock){
                    Stock stock = (Stock) t;
                    //if (stock.getQuantity().)
                    return "INSERT INTO stock_tbl(product,quantity) VALUES('" +stock.getProduct().getId()+"','"+stock.getQuantity()+"')";
                }else if (t instanceof Sale) {
                    Sale sale = (Sale) t;
                    return "INSERT INTO stock_tbl(product,quantity) VALUES('" +stock.getProduct().getId()+"','"+stock.getQuantity()+"')";
                }
                break;
            case 2:
                if (t instanceof CashDrawer)
                    return "INSERT INTO sale_tbl()";
                break;
            case 3:
                if (t instanceof CashDrawer)
                    return "SELECT ";
                break;
            case 4:
                break;
        }
        return null;
    }

}
