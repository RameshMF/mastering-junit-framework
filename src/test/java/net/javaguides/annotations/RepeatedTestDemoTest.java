package net.javaguides.annotations;

import net.javaguides.Calculator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepeatedTestDemoTest {

    @BeforeEach
    void setUp(){
        System.out.println("setUp method is calling ..");
    }

    @AfterEach
    void tearDown(){
        System.out.println("tearDown method is calling ..");
    }

    @BeforeAll
    static void setUpBeforeClass(){
        System.out.println("setUpBeforeClass method is calling ..");
    }

    @AfterAll
    static void tearDownAfterClass(){
        System.out.println("tearDownAfterClass method is calling ..");
    }

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Test Addition Repeatedly")
    public void addTest(){
        Calculator calculator = new Calculator();
        assertEquals(5, calculator.add(2, 3));
        System.out.println("addTest method is calling ..");
    }
}
