
import java.util.ArrayList;
import java.util.List;

/**
 * BinaryExpression class.
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression left;
    private Expression right;

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(double left, double right) {
        this.left = new Num(left);
        this.right = new Num(right);
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(String left, String right) {
        this.left = new Var(left);
        this.right = new Var(right);
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(Expression left, double right) {
        this.left = left;
        this.right = new Num(right);
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(Expression left, String right) {
        this.left = left;
        this.right = new Var(right);
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(double left, Expression right) {
        this.left = new Num(left);
        this.right = right;
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(String left, Expression right) {
        this.left = new Var(left);
        this.right = right;
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(double left, String right) {
        this.left = new Num(left);
        this.right = new Var(right);
    }

    /**
     * Constructs BinaryExpression with left and right expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    protected BinaryExpression(String left, double right) {
        this.left = new Var(left);
        this.right = new Num(right);
    }

    /**
     * Gives all the variables in the expression as a list of strings.
     *
     * @return all the variables in the expression as a list of strings.
     */
    @Override
    public List<String> getVariables() {
        List<String> vars = new ArrayList<>();

        // add from left variables avoiding duplicate
        for (int i = 0; i < this.left.getVariables().size(); i++) {
            if (!vars.contains(this.left.getVariables().get(i))) {
                vars.add(this.left.getVariables().get(i));
            }
        }

        // add from right variables avoiding duplicate
        for (int i = 0; i < this.right.getVariables().size(); i++) {
            if (!vars.contains(this.right.getVariables().get(i))) {
                vars.add(this.right.getVariables().get(i));
            }
        }

        return vars;
    }

    /**
     * Turns on isBonus mode.
     */
    public void turnBonusOn() {
        this.left.turnBonusOn();
        this.right.turnBonusOn();
        super.setIsBonus(true);
    }

    /**
     * Turns off isBonus mode.
     */
    public void turnBonusOff() {
        this.left.turnBonusOff();
        this.right.turnBonusOff();
        super.setIsBonus(false);
    }

    /**
     * Gives the left expression.
     *
     * @return the left expression.
     */
    protected Expression getLeft() {
        return left;
    }

    /**
     * Gives the right expression.
     *
     * @return the right expression.
     */
    protected Expression getRight() {
        return right;
    }

    /**
     * Sets the left expression.
     *
     * @param newLeft the new expression.
     */
    public void setLeft(Expression newLeft) {
        this.left = newLeft;
    }

    /**
     * Sets the right expression.
     *
     * @param newRight the new expression.
     */
    public void setRight(Expression newRight) {
        this.right = newRight;
    }

    /**
     * Checks if this is evaluable.
     *
     * @return if this is evaluable.
     */
    @Override
    public boolean isEvaluable() {
        return this.left.isEvaluable() && this.right.isEvaluable();
    }
}