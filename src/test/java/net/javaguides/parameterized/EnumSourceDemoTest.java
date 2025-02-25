package net.javaguides.parameterized;

import net.javaguides.Days;
import net.javaguides.MathUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumSourceDemoTest {

    @ParameterizedTest
    @EnumSource(value = Days.class, names = {"SUNDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"})
    void isWeekdayTest(Days day){
        MathUtils mathUtils = new MathUtils();
        assertTrue(mathUtils.isWeekday(day));
    }
}
