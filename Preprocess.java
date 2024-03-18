import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Preprocess class contains static methods to preprocess arithmetic expressions.
 * It handles transformations such as evaluating square roots, powers, sine, and cosine functions within the expressions.
 */
public class Preprocess {

    /**
     * Processes an arithmetic expression to evaluate square roots, powers, and trigonometric functions.
     * @param expression The input expression as a String.
     * @return The processed expression with specific functions evaluated.
     */
    static String preprocessExpression(String expression) {
        expression = preprocessSqrt(expression); // Evaluate square roots.
        expression = preprocessPower(expression); // Evaluate powers.
        expression = preprocessSinCos(expression); // Evaluate sine and cosine functions.
        return expression;
    }

    /**
     * Searches for and evaluates all square root functions within the expression.
     * @param expression The input expression.
     * @return The expression with all instances of square root functions evaluated.
     */
    private static String preprocessSqrt(String expression) {
        Pattern sqrtPattern = Pattern.compile("SQRT\\(([^)]+)\\)");
        Matcher matcher = sqrtPattern.matcher(expression);
        StringBuffer newExpression = new StringBuffer();
        while (matcher.find()) {
            double operand = ArithmeticEvaluator.evaluate(matcher.group(1));
            double result = Math.sqrt(operand);
            matcher.appendReplacement(newExpression, String.valueOf(result));
        }
        matcher.appendTail(newExpression);
        return newExpression.toString();
    }

    /**
     * Searches for and evaluates all power functions (to the power of 2) within the expression.
     * @param expression The input expression.
     * @return The expression with all instances of power functions evaluated.
     */
    private static String preprocessPower(String expression) {
        Pattern powerPattern = Pattern.compile("\\(([^)]+)\\)\\^2");
        Matcher matcher = powerPattern.matcher(expression);
        StringBuffer newExpression = new StringBuffer();
        while (matcher.find()) {
            double operand = ArithmeticEvaluator.evaluate(matcher.group(1));
            double result = Math.pow(operand, 2);
            matcher.appendReplacement(newExpression, String.valueOf(result));
        }
        matcher.appendTail(newExpression);
        return newExpression.toString();
    }

    /**
     * Searches for and evaluates all sine and cosine functions within the expression.
     * @param expression The input expression.
     * @return The expression with all instances of sine and cosine functions evaluated.
     */
    private static String preprocessSinCos(String expression) {
        Pattern pattern = Pattern.compile("(sin|cos)\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer newExpression = new StringBuffer();

        while (matcher.find()) {
            String function = matcher.group(1);
            String insideExpression = matcher.group(2);

            double evaluatedInsideExpression = ArithmeticEvaluator.evaluate(insideExpression);
            double trigResult = switch (function) {
                case "sin" -> Math.sin(evaluatedInsideExpression);
                case "cos" -> Math.cos(evaluatedInsideExpression);
                default -> 0; // Should never hit this default case if regex is correct.
            };

            matcher.appendReplacement(newExpression, Matcher.quoteReplacement(String.valueOf(trigResult)));
        }
        matcher.appendTail(newExpression);

        return newExpression.toString();
    }
}
