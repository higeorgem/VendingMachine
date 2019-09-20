package dao;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Local
@Stateful
public class DbClass {
    @Resource(lookup = "java:/vmDS")
    private DataSource dataSource;
    @Produces
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
