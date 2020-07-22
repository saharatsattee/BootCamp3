package com.digitalacademy.customer.support;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {

    public static List<Customer> getListCustomer(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ryan");
        customer.setLastName("Giggs");
        customer.setEmail("Ryan@gamil.com");
        customer.setPhoneNo("022222222");
        customer.setAge(32);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("David");
        customer.setLastName("BeckHam");
        customer.setEmail("David@gamil.com");
        customer.setPhoneNo("0233333333");
        customer.setAge(45);
        customerList.add(customer);
        return customerList;
    }

    public static Customer getCreateCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("New");
        customer.setLastName("NewNew");
        customer.setEmail("New@gamil.com");
        customer.setPhoneNo("0244444444");
        customer.setAge(10);
        return customer;
    }

    public static Customer getCreatedCustomer(){
        Customer customer = new Customer();
        customer.setId(6L);
        customer.setFirstName("New");
        customer.setLastName("NewNew");
        customer.setEmail("New@gamil.com");
        customer.setPhoneNo("0244444444");
        customer.setAge(10);
        return customer;
    }

    public static Customer getBeforeUpdateCustomer(){
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirstName("Old");
        customer.setLastName("OldOld");
        customer.setEmail("Old@gamil.com");
        customer.setPhoneNo("0244455555");
        customer.setAge(50);
        return customer;
    }

    public static Customer getAfterUpdateCustomer(){
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirstName("Old");
        customer.setLastName("OldOld");
        customer.setEmail("Old@gamil.com");
        customer.setPhoneNo("0244455555");
        customer.setAge(70);
        return customer;
    }


}
