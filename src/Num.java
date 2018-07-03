
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Num class.
 */
public class Num implements Expression {
    private Double value;

    /**
     * Constructs a num by given value.
     *
     * @param value the given value.
     */
    public Num(double value) {
        this.value = value;
    }

    /**
     * Evaluates expression according to assignment.
     *
     * @param assignment contains assigns to variables.
     * @return the value of the expression.
     * @throws Exception if evaluates no assigned variable.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.value;
    }

    /**
     * Calls non empty args evaluate with an empty assignment.
     *
     * @return the value of the expression.
     * @throws Exception if evaluates no assigned variable.
     */
    @Override
    public double evaluate() throws Exception {
        return this.value;
    }

    /**
     * Gives all the variables in the expression as a list of strings.
     *
     * @return all the variables in the expression as a list of strings.
     */
    @Override
    public List<String> getVariables() {
        return new ArrayList<>();
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
        return this;
    }

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    @Override
    public String toString() {
        return this.value + "";
    }

    /**
     * Calculates the differentiate of the expression.
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /**
     * Simplifies the expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * No use.
     *
     * @return no use.
     */
    @Override
    public Expression simplifyMore() {
        return this;
    }

    /**
     * No use.
     */
    @Override
    public void turnBonusOn() {
    }

    /**
     * No use.
     */
    @Override
    public void turnBonusOff() {
    }

    /**
     * Checks if this is evaluable.
     *
     * @return if this is evaluable.
     */
    @Override
    public boolean isEvaluable() {
        return true;
    }
}