package cli;

import java.util.Scanner;
import java.util.Stack;

/**
 * calculator cli.
 */
public class Calculator {
    // instance field
    private Stack<Double> stack;
    private Stack<Double[]> undoStack;

    // constructor method
    public Calculator() {
        stack = new Stack<Double>();
        undoStack = new Stack<Double[]>();
    }

    //class methods
    private boolean add() {
        if (stack.size() < 2) {
            return false;
        }
        double firstNum = stack.pop();
        double secondNum = stack.pop();
        double result = firstNum + secondNum;
        stack.push(result);
        undoStack.push(new Double[] {
            secondNum,
            firstNum
        });
        return true;
    }

    private boolean subtract() {
        if (stack.size() < 2) {
            return false;
        }
        double firstNum = stack.pop();
        double secondNum = stack.pop();
        double result = secondNum - firstNum;
        stack.push(result);
        undoStack.push(new Double[] {
            secondNum,
            firstNum
        });
        return true;
    }

    private boolean multiply() {
        if (stack.size() < 2) {
            return false;
        }
        double firstNum = stack.pop();
        double secondNum = stack.pop();
        double result = firstNum * secondNum;
        stack.push(result);
        undoStack.push(new Double[] {
            secondNum,
            firstNum
        });
        return true;
    }

    private boolean divide() {
        if (stack.size() < 2) {
            return false;
        }
        double firstNum = stack.pop();
        double secondNum = stack.pop();
        double result = secondNum / firstNum;
        stack.push(result);
        undoStack.push(new Double[] {
            secondNum,
            firstNum
        });
        return true;
    }

    private boolean sqrt() {
        if (stack.size() < 1) {
            return false;
        }
        double number = stack.pop();
        double result = Math.sqrt(number);
        stack.push(result);
        undoStack.push(new Double[] {
            number
        });
        return true;
    }

    private void addNumber(double number) {
        stack.push(number);
        undoStack.push(null);
    }

    private void clear() {
        stack.clear();
    }

    private void undo() {
        if (stack.size() < 1) {
            return;
        }
        stack.pop();
        Double[] prev = undoStack.pop();
        if (prev == null) {
            return;
        }
        for (int i = 0; i < prev.length; i++) {
            stack.push(prev[i]);
        }
    }

    private boolean next(Scanner scanner) {
        String input = scanner.nextLine();
        String[] items = input.split("\\s+");

        boolean hasError = false;
        boolean exit = false;
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            switch (item) {
                case "+":
                    hasError = !add();
                    break;
                case "-":
                    hasError = !subtract();
                    break;
                case "*":
                    hasError = !multiply();
                    break;
                case "/":
                    hasError = !divide();
                    break;
                case "sqrt":
                    hasError = !sqrt();
                    break;
                case "clear":
                    clear();
                    break;
                case "undo":
                    undo();
                    break;
                case "exit":
                    exit = true;
                    break;
                case "":
                    break;
                default:
                    try {
                        double number = Double.parseDouble(item);
                        addNumber(number);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input");
                    }
                    break;
            }
            if (exit) {
                break;
            }
            if (hasError) {
                System.out.format("operator %s (position: %d): insufficient parameters \n", item, i);
                break;
            }
        }

        printStack();
        if (exit) {
            scanner.close();
            return false;
        }
        return true;
    }

    /**
     * convert stack to string for printout.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Double num: stack) {
            int numIntVal = num.intValue();
            if (num == numIntVal) {
                sb.append(String.format("%s ", numIntVal));
            } else {
                sb.append(String.format("%s ", num));
            }
        }
        return sb.toString();
    }

    private void printStack() {
        System.out.println(toString());
    }

    /**
     * start calculator loop.
     * @param scanner
     */
    public void startCalculator(Scanner scanner, boolean runOneStep) {
        while (true) {
            if (!next(scanner) || runOneStep) {
                break;
            }
        }
    }

    /**
     * start RPN calculator.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("RPN Calculator. \n Supports +,-,*,/,sqrt,undo,clear "
            + "(e.g. 5 4 + => 9) \n Type exit to exit program \n");

        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        calculator.startCalculator(scanner, false);
    }
}
