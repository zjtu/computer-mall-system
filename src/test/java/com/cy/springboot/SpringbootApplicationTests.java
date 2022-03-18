package com.cy.springboot;

import com.cy.springboot.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }

    /**
     * 数据库连接池
     * 1.DBCP
     * 2.C3P0
     * 3.Hikari:管理数据库连接对象
     * @throws SQLException HikariProxyConnection@251666609 wrapping com.mysql.cj.jdbc.ConnectionImpl@759de304
     */
    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
