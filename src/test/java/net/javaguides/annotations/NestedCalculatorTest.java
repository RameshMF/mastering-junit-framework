package net.javaguides.annotations;

import net.javaguides.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NestedCalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp(){
        calculator = new Calculator();
    }

    @Nested
    class AdditionTests {

        @Test
        public void testAddPositiveNumbers(){
            assertEquals(7, calculator.add(3, 4));
        }

        @Test
        public void testAddPositiveAndNegativeNumbers(){
            assertEquals(1, calculator.add(4, -3));
        }

        @Test
        public void testAddNegativeNumbers(){
            assertEquals(-7, calculator.add(-4, -3));
        }

    }

    @Nested
    class SubtractionTests {

        @Test
        public void testSubtractPositiveNumbers(){
            assertEquals(1, calculator.subtract(4, 3));
        }

        @Test
        public void testSubtractLargerFromSmaller(){
            assertEquals(-4, calculator.subtract(3, 7));
        }

        @Nested
        class EdgeCases {

            @Test
            public void testSubtractZero(){
                assertEquals(2, calculator.subtract(2, 0));
            }

            @Test
            public void testSubtractSelf(){
                assertEquals(0, calculator.subtract(2, 2));
            }

        }

    }
}
