import java.util.Stack;

/**
 * A utility class for evaluating simple arithmetic expressions.
 * This evaluator supports basic arithmetic operations (+, -, X, /) and respects the precedence of these operations.
 */
public class ArithmeticEvaluator {

    /**
     * Evaluates the given arithmetic expression.
     * @param expression A string representing a space-separated arithmetic expression.
     * @return The result of the evaluated expression as a double.
     */
    public static double evaluate(String expression) {
        // Split the expression into tokens based on spaces.
        String[] tokens = expression.split("\\s+");
        // Stack for holding numbers/values.
        Stack<Double> values = new Stack<>();
        // Stack for holding operators.
        Stack<Character> ops = new Stack<>();

        // Process each token in the expression.
        for (String token : tokens) {
            if (!token.isEmpty()) {
                char c = token.charAt(0);

                // If the token is a number, push it onto the values stack.
                if (isNumber(token)) {
                    values.push(Double.parseDouble(token));
                } else if (c == '+' || c == '-' || c == 'X' || c == '/' || c == '(' || c == ')') {
                    // Process operators, applying any pending operations with higher or equal precedence.
                    while (!ops.empty() && hasPrecedence(c, ops.peek())) {
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                    }
                    // Push the current operator onto the ops stack.
                    ops.push(c);
                }
            }
        }

        // Apply remaining operations to the remaining values.
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        // The final result is the last value in the stack.
        return values.pop();
    }

    /**
     * Checks if a given string is numeric.
     * @param str The string to check.
     * @return True if the string can be parsed as a double, false otherwise.
     */
    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Determines if the first operator has lower precedence than the second.
     * @param op1 The first operator.
     * @param op2 The second operator.
     * @return True if op1 has lower or equal precedence than op2, false otherwise.
     */
    private static boolean hasPrecedence(char op1, char op2) {
        if((op1 == 'X' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Applies an arithmetic operation to two operands.
     * @param op The operator character (+, -, X, /).
     * @param b The second operand.
     * @param a The first operand.
     * @return The result of applying the operation.
     * @throws UnsupportedOperationException if an attempt is made to divide by zero.
     */
    private static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case 'X':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero.");
                return a / b;
        }
        return 0;
    }
}
