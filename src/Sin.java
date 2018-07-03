
import java.util.Map;

/**
 * Sin class.
 */
public class Sin extends UnaryExpression implements Expression {

    /**
     * Constructs a sin by a given expression.
     *
     * @param expression the given expression.
     */
    public Sin(Expression expression) {
        super(expression);
    }

    /**
     * Constructs a sin by a given double expression.
     *
     * @param expression the given expression.
     */
    public Sin(double expression) {
        super(expression);
    }

    /**
     * Constructs a sin by a given string expression.
     *
     * @param expression the given expression.
     */
    public Sin(String expression) {
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
        double val = Math.sin(Math.toRadians(expression.evaluate(assignment)));

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
        Helpers f = new Helpers();
        Expression expression = super.getExpression();

        // simplify string if in bonus mode
        if (super.isBonus()) {
            // remove double parentheses
            return "sin(" + f.removeOuterParentheses(expression.toString()) + ")";
        }

        return "sin(" + expression.toString() + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression expression = super.getExpression();
        return new Mult(new Cos(expression), expression.differentiate(var));
    }

    /**
     * Simplifies the expression more.
     *
     * @return more simplified version of the current expression.
     */
    @Override
    public Expression simplifyMore() {
        Expression expression = super.getExpression();

        // sin(-x) = -sin(x)
        if (expression instanceof Neg) {
            super.setExpression(((Neg) expression).getExpression());
            return new Neg(this);
        }

        return this;
    }

    /**
     * Makes a new expression that var is replaced with it's assign.
     *
     * @param var the variable to be replaced.
     * @param expression the expression to replace its' variables.
     * @return a new expression in which all occurrences of the variable
     * are replaced with the provided expression.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        Expression exp = super.getExpression();
        return new Sin(exp.assign(var, expression));
    }
}