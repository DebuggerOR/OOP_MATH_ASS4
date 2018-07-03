
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Base expression class.
 */
public abstract class BaseExpression {
    private boolean isBonus;

    /**
     * Evaluates the expression using the variable values provided
     * in the assignment, and return the result. If the expression
     * contains a variable which is not in the assignment, an exception is thrown.
     *
     * @param assignment provides variable values.
     * @return the result of the evaluate according to assignment.
     * @throws Exception if assignment of variable isn't provided.
     */
    public abstract double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Calls non empty args evaluate with an empty assignment.
     *
     * @return the value of the expression.
     * @throws Exception if evaluates no assigned variable.
     */
    public double evaluate() throws Exception {
        Map<String, Double> map = new TreeMap<>();
        return this.evaluate(map);
    }

    /**
     * Makes a new expression that var is replaced with it's assign.
     *
     * @param var        the variable to be replaced.
     * @param expression the expression to replace its' variables.
     * @return a new expression in which all occurrences of the variable
     * are replaced with the provided expression.
     */
    public abstract Expression assign(String var, Expression expression);

    /**
     * Gives all the variables in the expression as a list of strings.
     *
     * @return all the variables in the expression as a list of strings.
     */
    public abstract List<String> getVariables();

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    public abstract String toString();

    /**
     * Turns on isBonus mode.
     */
    public abstract void turnBonusOn();

    /**
     * Turns off isBonus mode.
     */
    public abstract void turnBonusOff();

    /**
     * Gives if in bonus mode.
     *
     * @return if in bonus mode.
     */
    protected boolean isBonus() {
        return isBonus;
    }

    /**
     * Sets if in bonus mode.
     *
     * @param bonus the new state to isBonus mode.
     */
    protected void setIsBonus(boolean bonus) {
        isBonus = bonus;
    }

    /**
     * Checks if this is evaluable.
     *
     * @return if this is evaluable.
     */
    public abstract boolean isEvaluable();

    /**
     * Simplifies the expression.
     *
     * @return a simplified version of the current expression.
     */
    public abstract Expression simplify();

    /**
     * Simplifies the expression more.
     *
     * @return more simplified version of the current expression.
     */
    public abstract Expression simplifyMore();
}