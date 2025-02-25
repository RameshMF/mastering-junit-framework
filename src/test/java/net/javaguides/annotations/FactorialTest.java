package net.javaguides.annotations;

import net.javaguides.Factorial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    protected void factorialTest(){

        Factorial factorial = new Factorial();

        int actualResult = factorial.factorial(4);

        assertEquals(24, actualResult);

    }

}