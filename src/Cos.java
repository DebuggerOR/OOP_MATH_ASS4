
import java.util.Map;

/**
 * Cos class.
 */
public class Cos extends UnaryExpression implements Expression {

    /**
     * Constructs a cos by a given expression.
     *
     * @param expression the given expression.
     */
    public Cos(Expression expression) {
        super(expression);
    }

    /**
     * Constructs a cos by a given double expression.
     *
     * @param expression the given expression.
     */
    public Cos(double expression) {
        super(expression);
    }

    /**
     * Constructs a cos by a given string expression.
     *
     * @param expression the given expression.
     */
    public Cos(String expression) {
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
        Helpers f = new Helpers();
        Expression expression = super.getExpression();

        // input and output in degrees but calculation in radians
        double val = Math.cos(Math.toRadians(expression.evaluate(assignment)));

        // return 0 instead of very small number
        if (f.doubleEqual(val, 0)) {
            return 0;
        }
        return val;
    }

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    @Override
    public String toString() {
        Expression expression = super.getExpression();

        // simplify string if in bonus mode
        if (this.isBonus()) {
            // remove double parentheses
            if (expression.toString().charAt(0) == '('
                    && expression.toString().charAt(expression.toString().length() - 1) == ')') {
                return "cos" + expression.toString();
            }
        }

        return "cos(" + expression.toString() + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * (cos(f(x)))' = -sin(x) * f'(x)
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression expression = super.getExpression();
        return new Neg(new Mult(new Sin(expression), expression.differentiate(var)));
    }

    /**
     * Simplifies the expression more.
     *
     * @return more simplified version of the current expression.
     */
    @Override
    public Expression simplifyMore() {
        Expression expression = super.getExpression();

        // cos(-x) -> cos(x)
        if (expression instanceof Neg) {
            return new Cos(((Neg) expression).getExpression()).simplifyMore();
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
        return new Cos(exp.assign(var, expression));
    }
}