package net.javaguides.annotations;

import net.javaguides.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeforeEachDemoTest {

    private Calculator calculator;

    @BeforeEach
    void setUp(){
        calculator = new Calculator();
        System.out.println("setUp method calling..");
    }

    @Test
    void addTest(){
        int addition = calculator.add(2, 3);
        assertEquals(5, addition);
        System.out.println("addTest method calling..");
    }

    @Test
    void subtractTest(){
        int subtraction = calculator.subtract(3, 2);
        assertEquals(1, subtraction);
        System.out.println("subtractTest method calling..");
    }

    @Test
    void multipleTest(){
        int multiplication = calculator.multiply(2, 3);
        assertEquals(6, multiplication);
        System.out.println("multipleTest method calling..");
    }

    @Test
    void divideTest(){
        int division = calculator.divide(10, 5);
        assertEquals(2, division);
        System.out.println("divideTest method calling..");
    }
}
