package com.example.com.bean;

import javax.ejb.Local;
import java.sql.SQLException;

@Local
public interface BeanI<T> {
    T add(T t) throws SQLException;
    T edit(T t);
    T find(T t);
    boolean delete(T t);
}
