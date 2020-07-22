package com.digitalacademy.customer.unit;


import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Data
class Person{
    private String firstName;
    private String lastName;

    public Person(String firstName,String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

public class CalculatorTest {

    private final Person person = new Person("John","Doe");

    @Test
    void groupedAssertions(){
        assertAll("person",
                ()-> assertEquals("John",person.getFirstName()),
                ()-> assertEquals("Doe",person.getLastName()));
    }

    @Test
    void testMethodAdd(){
        assertEquals(5,Calculator.add(2,3));
        assertEquals(10,Calculator.add(7,3));
    }

    @Test
    void testMethodMinus(){
        assertEquals(3,Calculator.minus(5,2));
        assertEquals(2,Calculator.minus(10,8));
        assertEquals(0,Calculator.minus(10,10));
    }

    @Test
    void testDevide(){
        assertEquals(5,Calculator.divide(10,2));
        assertEquals(8,Calculator.divide(40,5));
        assertEquals(3,Calculator.divide(6,2));
        Exception ex = assertThrows(ArithmeticException.class, () -> Calculator.divide(1,0));
        assertEquals("/ by zero", ex.getMessage());
    }
}
