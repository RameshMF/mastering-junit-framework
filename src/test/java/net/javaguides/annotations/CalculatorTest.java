package net.javaguides.annotations;

import net.javaguides.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Operations Test")
public class CalculatorTest {

    @DisplayName("Test Addition of Two Numbers")
    @Test
    void addTest(){

        Calculator calculator = new Calculator();
        int addition = calculator.add(2,3);

        assertEquals(5, addition);
    }

    @DisplayName("Test Subtraction of Two Numbers @#$% \uD83D\uDE03")
    @Test
    void subtractTest(){

        Calculator calculator = new Calculator();
        int subtraction= calculator.subtract(3,2);

        assertEquals(1, subtraction);
    }
}
