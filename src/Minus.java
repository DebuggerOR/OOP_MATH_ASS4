
import java.util.Map;

/**
 * Minus class.
 */
public class Minus extends BinaryExpression implements Expression {

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(double left, double right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(String left, String right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(Expression left, double right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(Expression left, String right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(double left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(String left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(double left, String right) {
        super(left, right);
    }

    /**
     * Constructs a minus by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Minus(String left, double right) {
        super(left, right);
    }

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
    public double evaluate(Map<String, Double> assignment) throws Exception {
        Expression left = super.getLeft();
        Expression right = super.getRight();
        return left.evaluate(assignment) - right.evaluate(assignment);
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

        // simplify more in bonus mode
        if (this.isBonus()) {
            if (!(left instanceof Neg)) {
                // x - (4y) -> x - 4y
                if (right instanceof Mult) {
                    if (!(((Mult) right).getRight() instanceof Var) || !(((Mult) right).getLeft() instanceof Var)) {
                        return "(" + f.removeOuterParentheses(left.toString()) + " - "
                                + f.removeOuterParentheses(right.toString()) + ")";
                    }
                }
                // (x + y) - 4 -> x + y - 4
                return "(" + f.removeOuterParentheses(left.toString()) + " - " + right.toString() + ")";
            }
        }

        // regular minus string
        return "(" + left.toString() + " - " + right.toString() + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * (f - g)' = f' - g'
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression left = super.getLeft();
        Expression right = super.getRight();
        return new Minus(left.differentiate(var), right.differentiate(var));
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

        // x - x -> 0
        if (right.toString().equals(left.toString())) {
            return new Num(0);
        }

        try {
            // try to evaluate
            if (this.isEvaluable()) {
                return new Num(this.evaluate());
            }
            // 0 - x -> -x
            if (left.isEvaluable()) {
                if (f.doubleEqual(left.evaluate(), 0)) {
                    return new Neg(right);
                }
            }
            // x - 0 -> x
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
        Expression left = super.getLeft();
        Expression right = super.getRight();

        // (x + y) - (y + x) -> 0
        if (left instanceof Plus && right instanceof Plus) {
            if (((Plus) left).isEqual(right)) {
                return new Num(0);
            }
        }

        // (x * y) - (y * x) -> 0
        if (left instanceof Mult && right instanceof Mult) {
            if (((Mult) left).isEqual(right)) {
                return new Num(0);
            }
        }

        try {
            if (left instanceof Num && right instanceof Pow) {
                if (left.isEvaluable()) {
                    // 1 - cos(x)^2 -> sin(x)^2
                    if (f.doubleEqual(left.evaluate(), 1) && ((Pow) right).getLeft() instanceof Cos && ((Pow) right)
                            .getRight() instanceof Num) {
                        if (((Pow) right).getRight().isEvaluable()) {
                            if (f.doubleEqual(((Pow) right).getRight().evaluate(), 2)) {
                                return new Pow(new Sin(((Cos) ((Pow) right).getLeft()).getExpression()), 2);
                            }
                        }
                    }
                    // 1 - sin(x)^2 -> cos(x)^2
                    if (f.doubleEqual(left.evaluate(), 1) && ((Pow) right).getLeft() instanceof Sin && ((Pow) right)
                            .getRight() instanceof Num) {
                        if (((Pow) right).getRight().isEvaluable()) {
                            if (f.doubleEqual(((Pow) right).getRight().evaluate(), 2)) {
                                return new Pow(new Cos(((Sin) ((Pow) right).getLeft()).getExpression()), 2);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // print exception message
            System.out.println(e.getMessage());
        }

        // x - (-y) -> x + y
        if (right instanceof Neg) {
            return new Plus(left, ((Neg) right).getExpression());
        }

        // 5x - 4x -> 2x
        if (left instanceof Mult && right instanceof Mult) {
            if (((Mult) left).getRight().toString().equals(((Mult) right).getRight().toString())) {
                return new Mult(new Minus(((Mult) left).getLeft(),
                        ((Mult) right).getLeft()).simplify(), ((Mult) left).getRight()).simplify();
            }
        }

        // log (x, y) - log (x, z) -> log(x, y/z)
        if (left instanceof Log && right instanceof Log) {
            if (((Log) left).getLeft().toString().equals(((Log) right).getLeft().toString())) {
                return new Log(((Log) left).getLeft(), new Div(((Log) left).getRight(), ((Log) right).getRight()));
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
        return new Minus(left.assign(var, expression), right.assign(var, expression));
    }
}