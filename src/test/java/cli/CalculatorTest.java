package cli;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class CalculatorTest {
    private final InputStream systemIn = System.in;
    private InputStream override;

    @AfterEach
    public void restoreSystemInput() {
        System.setIn(systemIn);
    }

    private void mockInput(String data) {
        override = new ByteArrayInputStream(data.getBytes());
        System.setIn(override);
    }

    @Test
    void testHappyPath() {
        mockInput("2 3 + 5 4 / 8 * 10 * sqrt exit");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, false);
        String stack = calc.toString();
        assertEquals(stack, "5 10 ");
    }

    @Test
    void testInvalidAdd() {
        mockInput("2 3 + +");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "5 ");
    }

    @Test
    void testInvalidSubtract() {
        mockInput("2 3 - - -");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "-1 ");
    }

    @Test
    void testInvalidMultiply() {
        mockInput("2 3 * *");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "6 ");
    }

    @Test
    void testInvalidDivide() {
        mockInput("3 2 / /");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "1.5 ");
    }

    @Test
    void testInvalidSqrt() {
        mockInput("4 clear sqrt");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "");
    }

    @Test
    void testInvalidUndo() {
        mockInput("2 3 undo undo undo");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "");
    }

    @Test
    void testInvalidNumber() {
        mockInput("a / /");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "");
    }

    @Test
    void testClear() {
        mockInput("2 3 + clear 4 5 -");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "-1 ");
    }

    @Test
    void testUndo() {
        mockInput("5 4 3 2 undo undo * undo");

        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();

        calc.startCalculator(scanner, true);
        String stack = calc.toString();
        assertEquals(stack, "5 4 ");
    }
}
