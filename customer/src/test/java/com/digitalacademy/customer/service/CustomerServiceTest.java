package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testGetAllCustomer(){
        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getListCustomer());
        List<Customer> resp = customerService.getCustomerList();

        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("Ryan",resp.get(0).getFirstName());
        assertEquals("Giggs",resp.get(0).getLastName());
        assertEquals("Ryan@gamil.com",resp.get(0).getEmail());
        assertEquals("022222222",resp.get(0).getPhoneNo());
        assertEquals(32,resp.get(0).getAge().intValue());

        assertEquals(2,resp.get(1).getId().intValue());
        assertEquals("David",resp.get(1).getFirstName());
        assertEquals("BeckHam",resp.get(1).getLastName());
        assertEquals("David@gamil.com",resp.get(1).getEmail());
        assertEquals("0233333333",resp.get(1).getPhoneNo());
        assertEquals(45,resp.get(1).getAge().intValue());

    }

    @DisplayName("Test get customer by Id should return customer")
    @Test
    void testGetCustomerById(){
        Long reqParam = 10L;

        when(customerRepository.findAllById(reqParam)).thenReturn(CustomerSupportTest.getListCustomer().get(0));
        Customer resp = customerService.getCustomerById(10L);

        assertEquals(1,resp.getId().intValue());
        assertEquals("Ryan",resp.getFirstName());
        assertEquals("Giggs",resp.getLastName());
        assertEquals("Ryan@gamil.com",resp.getEmail());
        assertEquals("022222222",resp.getPhoneNo());
        assertEquals(32,resp.getAge().intValue());

    }

    @DisplayName("Test create customer should return customer")
    @Test
    void testCreateCustomer(){
        when(customerRepository.save(CustomerSupportTest.getCreateCustomer()))
                .thenReturn(CustomerSupportTest.getCreatedCustomer());

        Customer resp = customerService.createCustomer(CustomerSupportTest.getCreateCustomer());
        assertEquals(6,resp.getId().intValue());
        assertEquals("New",resp.getFirstName());
        assertEquals("NewNew",resp.getLastName());
        assertEquals("New@gamil.com",resp.getEmail());
        assertEquals("0244444444",resp.getPhoneNo());
        assertEquals(10,resp.getAge().intValue());

    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer(){
        Long reqId = 3L;
        when(customerRepository.findAllById(reqId))
                .thenReturn(CustomerSupportTest.getBeforeUpdateCustomer());

        when(customerRepository.save(CustomerSupportTest.getAfterUpdateCustomer()))
                .thenReturn(CustomerSupportTest.getAfterUpdateCustomer());

        Customer resp = customerService.updateCustomer(reqId,
                CustomerSupportTest.getAfterUpdateCustomer());

        assertEquals(3,resp.getId().intValue());
        assertEquals("Old",resp.getFirstName());
        assertEquals("OldOld",resp.getLastName());
        assertEquals("Old@gamil.com",resp.getEmail());
        assertEquals("0244455555",resp.getPhoneNo());
        assertEquals(70,resp.getAge().intValue());

    }

    @DisplayName("Test update customer should return fail")
    @Test
    void testUpdateCustomerFail(){
        Long reqId = 3L;
        when(customerRepository.findAllById(reqId))
                .thenReturn(null);

        Customer resp = customerService.updateCustomer(reqId,
                CustomerSupportTest.getAfterUpdateCustomer());

        assertEquals(null,resp);

    }

    @DisplayName("Test delete customer should return true")
    @Test
    void testDeleteCustomer(){
        Long reqId = 1L;

        doNothing().when(customerRepository).deleteById(reqId);
        boolean reps = customerService.deleteById(reqId);
        assertTrue(reps);
        assertTrue(customerService.deleteById(reqId));

    }

    @DisplayName("Test delete customer should return fail")
    @Test
    void testDeleteCustomerFail(){
        Long reqId = 1L;

        doThrow(EmptyResultDataAccessException.class)
                .when(customerRepository).deleteById(reqId);
        boolean reps = customerService.deleteById(reqId);
        assertFalse(reps);
        assertFalse(customerService.deleteById(reqId));
    }







}
