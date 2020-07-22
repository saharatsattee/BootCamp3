package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomerList(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findAllById(id);
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id,Customer customerReq){
        return customerRepository.findById(id) != null ?
                customerRepository.save(customerReq) :
                null;
    }

    public List<Customer> getCustomerByName(String firtName){
        List<Customer> customer = customerRepository.findByFirstName(firtName);
        return customer != null ? customer : null;
    }

    public boolean deleteById(Long id){
        try{
            customerRepository.deleteById(id);
            return true;
        }catch (EmptyResultDataAccessException e){
            System.out.println("Can not delete by id");
            return false;
        }
    }



//    public List<Customer> getDemoList(){
//
//        List<Customer> customerList = new ArrayList<>();
//        Customer cs = new Customer();
//        cs.setId(1L);
//        cs.setFirstName("Noon");
//        cs.setLastName("Bow");
//        cs.setEmail("Noon@gmail.com");
//        cs.setPhoneNo("0662636172");
//        cs.setAge(20);
//        customerList.add(cs);
//
//        cs = new Customer();
//        cs.setId(2L);
//        cs.setFirstName("Somsak");
//        cs.setLastName("Suthin");
//        cs.setEmail("Somsak@gmail.com");
//        cs.setPhoneNo("0662633212");
//        cs.setAge(30);
//        customerList.add(cs);
//
//
//        return customerList;
//    }
}
