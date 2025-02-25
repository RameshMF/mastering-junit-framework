package net.javaguides;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CalculatorTest {

    @Test
    public void addTest(){

        Calculator calculator = new Calculator();
        int actualResult = calculator.add(10, 20);

        assertEquals(30, actualResult);

    }
  
}