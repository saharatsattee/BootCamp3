package com.digitalacademy.customer.api;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.digitalacademy.customer.support.LoanApiSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanApiTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    LoanApi loanApi;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanApi = new LoanApi(restTemplate);
    }

    @DisplayName("Test get loan info should return loan information")
    @Test
    void testGetLoanInfo()throws Exception{
        Long reqId = 1L;
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(LoanApiSupportTest.prepareResponseSuccess());

        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId);

        assertEquals("1",resp.getId().toString());
        assertEquals("OK",resp.getStatus());
        assertEquals("102-222-2200",resp.getAccountPayable());
        assertEquals("102-333-2020",resp.getAccountReceivable());
        assertEquals(3400000.0,resp.getPrincipalAmount());

        verify(restTemplate,times(1))
                .exchange(ArgumentMatchers.<RequestEntity<String>>any()
                        ,ArgumentMatchers.<Class<String>>any());
    }

    @DisplayName("Test get loan info should return LOAN4002")
    @Test
    void testGetLoanInfoLOAN4002()throws Exception{
        Long reqId = 2L;
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(LoanApiSupportTest.prepareResponseEntityLOAN4002());

        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId);

        assertEquals(null,resp.getId());
        assertEquals(null,resp.getStatus());
        assertEquals(null,resp.getAccountPayable());
        assertEquals(null,resp.getAccountReceivable());
        assertEquals(0,resp.getPrincipalAmount());

    }

    @DisplayName("Test get loan info should return LOAN4001")
    @Test
    void testGetLoanInfoLOAN4001()throws Exception{
        Long reqId = 3L;
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(LoanApiSupportTest.prepareResponseEntityLOAN4001());

        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId);

        assertEquals(null,resp.getId());
        assertEquals(null,resp.getStatus());
        assertEquals(null,resp.getAccountPayable());
        assertEquals(null,resp.getAccountReceivable());
        assertEquals(0,resp.getPrincipalAmount());

    }


}
