
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Num class.
 */
public class Var implements Expression {
    private double value;
    private String name;

    /**
     * Constructs a var by given name.
     *
     * @param var the given name.
     */
    public Var(String var) {
        this.name = var;

        // default value
        this.value = Double.MIN_VALUE;
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
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        if (!assignment.containsKey(this.name)) {
            // consts defaults if no user assignment
            if (this.name.equals("e")) {
                return Math.E;
            }
            if (this.name.equals("PI")) {
                return Math.PI;
            }

            // case evaluate with no assignment
            throw new Exception("no assign to " + this.name);
        }
        return assignment.get(this.name);
    }

    /**
     * Calls non empty args evaluate with an empty assignment.
     *
     * @return the value of the expression.
     * @throws Exception if evaluates no assigned variable.
     */
    @Override
    public double evaluate() throws Exception {
        Helpers f = new Helpers();
        if (f.doubleEqual(this.value, Double.MIN_VALUE)) {
            // consts defaults if no user assignment
            if (this.name.equals("e")) {
                return Math.E;
            }
            if (this.name.equals("PI")) {
                return Math.PI;
            }

            // case evaluate with no assignment
            throw new Exception("no assign to " + this.name);
        }
        return this.value;
    }

    /**
     * Gives all the variables in the expression as a list of strings.
     *
     * @return all the variables in the expression as a list of strings.
     */
    @Override
    public List<String> getVariables() {
        List<String> var = new ArrayList<>();
        var.add(this.name);
        return var;
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
        // if need to change
        if (var.equals(this.name)) {
            return expression;
        }
        // if no need to change
        return this;
    }

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    @Override
    public String toString() {
        Helpers f = new Helpers();

        if (!f.doubleEqual(this.value, Double.MIN_VALUE)) {
            return this.value + "";
        }
        return this.name;
    }

    /**
     * Calculates the differentiate of the expression.
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        if (var.equals(this.name)) {
            return new Num(1);
        }
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
     * Simplifies the expression more.
     *
     * @return more simplified version of the current expression.
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
        Helpers f = new Helpers();
        return !f.doubleEqual(this.value, Double.MIN_VALUE);
    }

    /**
     * Gives the name of the var.
     *
     * @return the name of the var.
     */
    public String getName() {
        return name;
    }

    /**
     * Check if vars are equal.
     *
     * @param other the other var to check if this equal to.
     * @return if the vars are equal.
     */
    public boolean varsEqual(Var other) {
        return (other.getName().equals(this.name));
    }
}