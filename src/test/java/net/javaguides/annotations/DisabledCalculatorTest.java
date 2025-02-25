package net.javaguides.annotations;

import net.javaguides.Calculator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DisabledCalculatorTest {

    @Disabled("Disabled until bug #40 is fixed")
    @Test
    void addTest(){
        Calculator calculator = new Calculator();
        int addition = calculator.add(2, 3);
        assertEquals(5, addition);
    }

    @Disabled
    @Test
    void subtractTest(){
        Calculator calculator = new Calculator();
        int subtraction = calculator.subtract(3, 2);
        assertEquals(1, subtraction);
    }

    @Test
    void multipleTest(){
        Calculator calculator = new Calculator();
        int multiplication = calculator.multiply(2, 3);
        assertEquals(6, multiplication);
    }

    @Test
    void divideTest(){
        Calculator calculator = new Calculator();
        int division = calculator.divide(10, 5);
        assertEquals(2, division);
    }
}
