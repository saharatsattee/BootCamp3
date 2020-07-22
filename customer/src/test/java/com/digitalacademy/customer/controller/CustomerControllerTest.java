package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testGetCustomerList() throws Exception{
        when(customerService.getCustomerList())
                .thenReturn(CustomerSupportTest.getListCustomer());
        MvcResult mvcResult = mvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Ryan",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("Giggs",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("Ryan@gamil.com",jsonArray.getJSONObject(0).get("email"));
        assertEquals("022222222",jsonArray.getJSONObject(0).get("phoneNo"));
        assertEquals(32,jsonArray.getJSONObject(0).get("age"));

        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("David",jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("BeckHam",jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("David@gamil.com",jsonArray.getJSONObject(1).get("email"));
        assertEquals("0233333333",jsonArray.getJSONObject(1).get("phoneNo"));
        assertEquals(45,jsonArray.getJSONObject(1).get("age"));

        verify(customerService,times(1)).getCustomerList();

    }

    @DisplayName("Test get customer by Id should return customer")
    @Test
    void testGetCustomerById() throws Exception{
        Long reqId = 2L;

        when(customerService.getCustomerById(reqId))
                .thenReturn(CustomerSupportTest.getListCustomer().get(1));

        MvcResult mvcResult = mvc.perform(get("/customer/"+reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("David",jsonObject.get("firstName"));
        assertEquals("BeckHam",jsonObject.get("lastName"));
        assertEquals("David@gamil.com",jsonObject.get("email"));
        assertEquals("0233333333",jsonObject.get("phoneNo"));
        assertEquals(45,jsonObject.get("age"));

    }

    @DisplayName("Test get customer by id should return not found")
    @Test
    void testGetCustomerByIdNotfound()throws Exception{
        Long reqId = 4L;
        mvc.perform(get("/customer"+reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test create customer should return success")
    @Test
    void testCreateCustomer()throws Exception{
        Customer customerReq = CustomerSupportTest.getCreateCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow  = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.createCustomer(customerReq))
                .thenReturn(CustomerSupportTest.getCreatedCustomer());

        MvcResult mvcResult = mvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson)
                ).andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("6",jsonObject.get("id").toString());
        assertEquals("New",jsonObject.get("firstName"));
        assertEquals("NewNew",jsonObject.get("lastName"));
        assertEquals("New@gamil.com",jsonObject.get("email"));
        assertEquals("0244444444",jsonObject.get("phoneNo"));
        assertEquals(10,jsonObject.get("age"));

    }

    @DisplayName("Test create customer with first name is empty")
    @Test
    void testCreateCustomerWithNameEmpty()throws Exception{
        Customer customerReq = CustomerSupportTest.getCreateCustomer();
        customerReq.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow  = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.createCustomer(customerReq))
                .thenReturn(CustomerSupportTest.getCreatedCustomer());

        MvcResult mvcResult = mvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson)
        ).andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitalacademy.customer.controller.CustomerController.createCustomer(com.digitalacademy.customer.model.Customer): [Field error in object 'customer' on field 'firstName': rejected value []; codes [Size.customer.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstName,firstName]; arguments []; default message [firstName],100,1]; default message [Please type your first name size between 1 - 100]] "
                ,mvcResult.getResolvedException().getMessage());

    }

    @DisplayName("Test Update Customer should return success")
    @Test
    void testUpdateCustomer()throws Exception{
        Customer customerReq = CustomerSupportTest.getBeforeUpdateCustomer();
        Long reqId = 3L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow  = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.updateCustomer(reqId,customerReq))
                .thenReturn(CustomerSupportTest.getBeforeUpdateCustomer());

        MvcResult mvcResult = mvc.perform(put("/customer/"+reqId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson)
        ).andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("3",jsonObject.get("id").toString());
        assertEquals("Old",jsonObject.get("firstName"));
        assertEquals("OldOld",jsonObject.get("lastName"));
        assertEquals("Old@gamil.com",jsonObject.get("email"));
        assertEquals("0244455555",jsonObject.get("phoneNo"));
        assertEquals(50,jsonObject.get("age"));

    }

    @DisplayName("test update customer should return not found ")
    @Test
    void testUpdateCustomerIdNotFound()throws Exception{
        Customer customerReq = CustomerSupportTest.getBeforeUpdateCustomer();
        Long reqId = 3L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow  = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.updateCustomer(reqId,customerReq))
                .thenReturn(null);

        MvcResult mvcResult = mvc.perform(get("/customer/"+reqId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson)
        ).andExpect(status().isNotFound())
                .andReturn();

        verify(customerService,times(1));
    }

    @DisplayName("test delete customer should success ")
    @Test
    void testDeleteCustomer()throws Exception{
        Long reqId = 10L;

        when(customerService.deleteById(reqId))
                .thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete("/customer/"+reqId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk())
                .andReturn();

        verify(customerService,times(1));
    }

    @DisplayName("test delete customer should return not found ")
    @Test
    void testDeleteCustomerShouldReturnNotFound()throws Exception{
        Long reqId = 10L;

        when(customerService.deleteById(reqId))
                .thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete("/customer/"+reqId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isNotFound())
                .andReturn();

        verify(customerService,times(1));
    }



}
