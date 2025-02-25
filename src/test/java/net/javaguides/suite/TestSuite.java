package net.javaguides.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
//@SelectClasses({ClassATest.class, ClassBTest.class, ClassCTest.class})
@SelectPackages({"net.javaguides.annotations", "net.javaguides.suite"})
public class TestSuite {
}
