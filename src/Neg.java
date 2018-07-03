
import java.util.Map;

/**
 * Neg class.
 */
public class Neg extends UnaryExpression implements Expression {

    /**
     * Constructs a neg by given expression.
     *
     * @param expression the given expression.
     */
    public Neg(Expression expression) {
        super(expression);
    }

    /**
     * Constructs a neg by given double expression.
     *
     * @param expression the given expression.
     */
    public Neg(double expression) {
        super(expression);
    }

    /**
     * Constructs a neg by given string expression.
     *
     * @param expression the given expression.
     */
    public Neg(String expression) {
        super(expression);
    }

    /**
     * Evaluates the expression using the variable values provided
     * in the assignment, and return the result. If the expression
     * contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment provides variable values.
     * @return the result of the evaluate according to assignment.
     * @throws Exception if assignment of variable isn't provided.
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        Expression expression = super.getExpression();
        return -1 * expression.evaluate(assignment);
    }

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    @Override
    public String toString() {
        Expression expression = super.getExpression();
        return "(-" + expression.toString() + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * (-f(x))' = -f'(x)
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression expression = super.getExpression();
        return new Neg(expression.differentiate(var));
    }

    /**
     * Simplifies the expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Helpers f = new Helpers();
        Expression expression = super.getExpression().simplify();
        super.setExpression(expression);

        try {
            if (expression.isEvaluable()) {
                // -0 = 0
                if (f.doubleEqual(expression.evaluate(), 0)) {
                    return new Num(0);
                }
            }
            // simplify more in bonus mode
            if (super.isBonus()) {
                return this.simplifyMore();
            }
        } catch (Exception e) {
            // print exception message
            System.out.println(e.getMessage());
        }

        return this;
    }

    /**
     * Simplifies the expression more.
     *
     * @return more simplified version of the current expression.
     */
    @Override
    public Expression simplifyMore() {
        Expression expression = super.getExpression();

        try {
            // -(-1) -> 1
            if (expression instanceof Num) {
                if (expression.isEvaluable()) {
                    if (expression.evaluate() < 0) {
                        return new Num(-1 * expression.evaluate());
                    }
                }
            }
        } catch (Exception e) {
            // print exception message
            System.out.println(e.getMessage());
        }

        // -(-(x)) -> x
        if (expression instanceof Neg) {
            return ((Neg) expression).getExpression();
        }

        return this;
    }

    /**
     * Makes a new expression that var is replaced with it's assign.
     *
     * @param var        the variable to be replaced.
     * @param expression the expression to replace its' variables.
     * @return a new expression in which all occurrences of the variable
     * are replaced with the provided expression.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        Expression exp = super.getExpression();
        return new Neg(exp.assign(var, expression));
    }

}