
import java.util.List;
import java.util.Map;

/**
 * Expression interface.
 */
public interface Expression {

    /**
     * Evaluates the expression using the variable values provided
     * in the assignment, and return the result. If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment provides variable values.
     * @return the result of the evaluate according to assignment.
     * @throws Exception if assignment of variable isn't provided.
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Calls non empty args evaluate with an empty assignment.
     *
     * @return the value of the expression.
     * @throws Exception if evaluates no assigned variable.
     */
    double evaluate() throws Exception;

    /**
     * Gives all the variables in the expression as a list of strings.
     *
     * @return all the variables in the expression as a list of strings.
     */
    List<String> getVariables();

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    String toString();

    /**
     * Makes a new expression that var is replaced with it's assign.
     *
     * @param var        the variable to be replaced.
     * @param expression the expression to replace its' variables.
     * @return a new expression in which all occurrences of the variable
     * are replaced with the provided expression.
     */
    Expression assign(String var, Expression expression);

    /**
     * Calculates the differentiate of the expression.
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    Expression differentiate(String var);

    /**
     * Simplifies the expression.
     *
     * @return a simplified version of the current expression.
     */
    Expression simplify();

    /**
     * Simplifies the expression more.
     *
     * @return more simplified version of the current expression.
     */
    Expression simplifyMore();

    /**
     * Turns on isBonus mode.
     */
    void turnBonusOn();

    /**
     * Turns off isBonus mode.
     */
    void turnBonusOff();

    /**
     * Checks if this is evaluable.
     *
     * @return if this is evaluable.
     */
    boolean isEvaluable();
}