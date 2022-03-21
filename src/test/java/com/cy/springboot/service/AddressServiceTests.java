package com.cy.springboot.service;

import com.cy.springboot.entity.Address;
import com.cy.springboot.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        try {
            Integer uid = 1;
            String username="管理员";
            Address address = new Address();
            address.setName("张三");
            address.setPhone("1766666666");
            address.setAddress("亚马逊");
            addressService.addNewAddress(uid,username,address);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getByUid() {
        Integer uid = 13;
        List<Address> list = addressService.getByUid(uid);
        System.out.println("count=" + list.size());
        for (Address item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void setDefault() {
        try {
            Integer aid = 13;
            Integer uid = 1;
            String username = "系统管理员";
            addressService.setDefault(aid, uid, username);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void delete() {
        try {
            Integer aid = 18;
            Integer uid = 30;
            String username = "明明";
            addressService.delete(aid, uid, username);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
