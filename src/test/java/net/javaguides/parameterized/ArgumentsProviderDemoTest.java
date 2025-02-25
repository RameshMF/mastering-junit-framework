package net.javaguides.parameterized;

import net.javaguides.MathUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgumentsProviderDemoTest {

    @ParameterizedTest
    @ArgumentsSource(FactorialArgumentsProvider.class)
    void factorialTest(int number, int expected){
        MathUtils mathUtils = new MathUtils();
        assertEquals(expected, mathUtils.factorial(number));
    }
}
