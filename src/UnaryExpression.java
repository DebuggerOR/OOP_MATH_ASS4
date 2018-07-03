
import java.util.ArrayList;
import java.util.List;

/**
 * UnaryExpression class.
 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression expression;

    /**
     * Constructs BinaryExpression with an expression.
     *
     * @param expression the expression.
     */
    protected UnaryExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * Constructs BinaryExpression with an expression.
     *
     * @param expression the expression.
     */
    protected UnaryExpression(double expression) {
        this.expression = new Num(expression);
    }

    /**
     * Constructs BinaryExpression with an expression.
     *
     * @param expression the expression.
     */
    protected UnaryExpression(String expression) {
        this.expression = new Var(expression);
    }

    /**
     * Gives all the variables in the expression as a list of strings.
     *
     * @return all the variables in the expression as a list of strings.
     */
    @Override
    public List<String> getVariables() {
        List<String> vars = new ArrayList<>();

        // add from expression variables avoiding duplicate
        for (int i = 0; i < this.expression.getVariables().size(); i++) {
            if (!vars.contains(this.expression.getVariables().get(i))) {
                vars.add(this.expression.getVariables().get(i));
            }
        }

        return vars;
    }

    /**
     * Turns on isBonus mode.
     */
    public void turnBonusOn() {
        this.expression.turnBonusOn();
        super.setIsBonus(true);
    }

    /**
     * Turns off isBonus mode.
     */
    public void turnBonusOff() {
        this.expression.turnBonusOff();
        super.setIsBonus(false);
    }

    /**
     * Gives the expression.
     *
     * @return the expression.
     */
    protected Expression getExpression() {
        return expression;
    }

    /**
     * Sets the expression.
     *
     * @param newExpression the new expression.
     */
    public void setExpression(Expression newExpression) {
        this.expression = newExpression;
    }

    /**
     * Checks if this is evaluable.
     *
     * @return if this is evaluable.
     */
    @Override
    public boolean isEvaluable() {
        return this.expression.isEvaluable();
    }

    /**
     * Simplifies the expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        this.expression = this.expression.simplify();

        try {
            // try to evaluate
            if (this.isEvaluable()) {
                return new Num(this.evaluate());
            }
            // simplify more in bonus mode
            if (super.isBonus()) {
                return this.simplifyMore();
            }
        } catch (Exception e) {
            // print exception message
            System.out.println(e.getMessage());
        }

        return (Expression) this;
    }
}