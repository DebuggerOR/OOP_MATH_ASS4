
import java.util.Map;

/**
 * Plus class.
 */
public class Plus extends BinaryExpression implements Expression {

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(double left, double right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(String left, String right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(Expression left, double right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(Expression left, String right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(double left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(String left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(double left, String right) {
        super(left, right);
    }

    /**
     * Constructs a plus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Plus(String left, double right) {
        super(left, right);
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
        Expression left = super.getLeft();
        Expression right = super.getRight();
        return left.evaluate(assignment) + right.evaluate(assignment);
    }

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    @Override
    public String toString() {
        Helpers f = new Helpers();
        Expression left = super.getLeft();
        Expression right = super.getRight();

        // strings of left and right expressions
        String leftStr = left.toString();
        String rightStr = right.toString();

        // simplify string in bonus mode
        if (this.isBonus()) {
            // remove redundant () of left
            if (!(left instanceof Neg)) {
                leftStr = f.removeOuterParentheses(leftStr);
            }
            // remove redundant () of right
            if (!(right instanceof Neg)) {
                rightStr = f.removeOuterParentheses(rightStr);
            }
        }

        return "(" + leftStr + " + " + rightStr + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * (f + g)' = f' + g'
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression left = super.getLeft();
        Expression right = super.getRight();
        return new Plus(left.differentiate(var), right.differentiate(var));
    }

    /**
     * Simplifies the expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        Helpers f = new Helpers();
        Expression left = super.getLeft().simplify();
        Expression right = super.getRight().simplify();
        super.setLeft(left);
        super.setRight(right);

        try {
            // try to evaluate
            if (this.isEvaluable()) {
                return new Num(this.evaluate());
            }
            // 0 + x -> x
            if (left.isEvaluable()) {
                if (f.doubleEqual(left.evaluate(), 0)) {
                    return right;
                }
            }
            // x + 0 -> x
            if (right.isEvaluable()) {
                if (f.doubleEqual(right.evaluate(), 0)) {
                    return left;
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
        Helpers f = new Helpers();
        Expression left = super.getLeft().simplify();
        Expression right = super.getRight().simplify();

        // x + (-y) -> x - y
        if (right instanceof Neg) {
            Expression exp = new Minus(left, ((Neg) right).getExpression());
            exp.turnBonusOn();
            return exp.simplify();
        }
        // (-y) + x -> x - y
        if (left instanceof Neg) {
            Expression exp = new Plus(right, left);
            exp.turnBonusOn();
            return exp.simplify();
        }

        try {
            // sin(x)^2 + cos(x)^2 = 1 || cos(x)^2 + sin(x)^2 = 1
            if (left instanceof Pow && right instanceof Pow) {
                // left and right are pows
                if (((Pow) left).getLeft() instanceof Sin && ((Pow) right).getLeft() instanceof Cos) {
                    // the expressions inside are equal
                    if (((Sin) ((Pow) left).getLeft()).getExpression().toString().equals(
                            ((Cos) ((Pow) right).getLeft()).getExpression().toString())) {
                        // can evaluate the exponents
                        if (((Pow) left).getRight().isEvaluable() && ((Pow) right).getRight().isEvaluable()) {
                            // the exponents are 2
                            if (f.doubleEqual(((Pow) left).getRight().evaluate(), 2)
                                    && f.doubleEqual(((Pow) right).getRight().evaluate(), 2)) {
                                return new Num(1);
                            }
                        }
                    }
                }

                // both are pows
                if (((Pow) left).getLeft() instanceof Cos && ((Pow) right).getLeft() instanceof Sin) {
                    // the expressions inside are equal
                    if (((Cos) ((Pow) left).getLeft()).getExpression().toString().equals(
                            ((Sin) ((Pow) right).getLeft()).getExpression().toString())) {
                        // can evaluate the exponents
                        if (((Pow) left).getRight().isEvaluable() && ((Pow) right).getRight().isEvaluable()) {
                            // the exponents are 2
                            if (f.doubleEqual(((Pow) left).getRight().evaluate(), 2)
                                    && f.doubleEqual(((Pow) right).getRight().evaluate(), 2)) {
                                return new Num(1);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // print exception message
            System.out.println(e.getMessage());
        }

        // 2x + 3x -> 5x (both are mults)
        if (left instanceof Mult && right instanceof Mult) {
            // if the rights (of both mults) are vars
            if (((Mult) left).getRight() instanceof Var && ((Mult) right).getRight() instanceof Var) {
                // if those vars in the right are equal
                if (((Var) ((Mult) left).getRight()).varsEqual((Var) ((Mult) right).getRight())) {
                    // return the sum of the coefficients mult the var
                    return new Mult(new Plus(((Mult) left).getLeft(), ((Mult) right).getLeft()).simplify(),
                            ((Mult) left).getRight().simplify());
                }
            }
        }

        // log (x, y) + log (x, z) -> log(x, yz)
        if (left instanceof Log && right instanceof Log) {
            if (((Log) left).getLeft().toString().equals(((Log) right).getLeft().toString())) {
                return new Log(((Log) left).getLeft(),
                        new Mult(((Log) left).getRight(), ((Log) right).getRight()));
            }
        }

        // 2x + x -> 3x (left is mult and right is var)
        if (left instanceof Mult && right instanceof Var) {
            // if the right of the mult is var
            if (((Mult) left).getRight() instanceof Var) {
                // if the vars are equal
                if (((Var) ((Mult) left).getRight()).varsEqual((Var) right)) {
                    // return the sum of the coefficients mult the var
                    return new Mult(new Plus(new Num(1), ((Mult) left).getLeft()), right).simplify();
                }
            }
        }

        // x + 2x -> 3x (left is var and right is mult)
        if (right instanceof Mult && left instanceof Var) {
            // if the right of the mult is var
            if (((Mult) right).getRight() instanceof Var) {
                // if the vars are equal
                if (((Var) ((Mult) right).getRight()).varsEqual((Var) left)) {
                    // return the sum of the coefficients mult the var
                    return new Mult(new Plus(new Num(1), ((Mult) right).getLeft()), left).simplify();
                }
            }
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
        Expression left = super.getLeft();
        Expression right = super.getRight();
        return new Plus(left.assign(var, expression), right.assign(var, expression));
    }

    /**
     * Checks if equal with no importance to order.
     *
     * @param other the expression to check if this equal to.
     * @return if equal.
     */
    public boolean isEqual(Expression other) {
        Expression left = super.getLeft();
        Expression right = super.getRight();

        if (other instanceof Plus) {
            return (((Plus) other).getLeft().toString().equals(left.toString())
                    && ((Plus) other).getRight().toString().equals(right.toString()))
                    || ((Plus) other).getLeft().toString().equals(right.toString())
                    && ((Plus) other).getRight().toString().equals(left.toString());
        }

        return false;
    }
}