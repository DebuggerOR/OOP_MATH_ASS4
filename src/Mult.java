
import java.util.Map;

/**
 * Mult class.
 */
public class Mult extends BinaryExpression implements Expression {

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(double left, double right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(String left, String right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(Expression left, double right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(Expression left, String right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(double left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(String left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(double left, String right) {
        super(left, right);
    }

    /**
     * Constructs a mult by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Mult(String left, double right) {
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
        return left.evaluate(assignment) * right.evaluate(assignment);
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

        if (super.isBonus()) {
            // x * y -> xy
            if (left instanceof Var && right instanceof Var) {
                return "(" + left.toString() + "" + right.toString() + ")";
            }
            // 4 * x -> 4x
            if (right instanceof Var) {
                return "(" + left.toString() + "" + right.toString() + ")";
            }
            // x * 4 -> 4x
            if (left instanceof Var) {
                return "(" + right.toString() + "" + left.toString() + ")";
            }
            // 4 * (x^2) -> 4(x^2)
            if (right instanceof Pow) {
                return "(" + left.toString() + "" + right.toString() + ")";
            }
            // 4 * (xy) -> 4xy
            if (right instanceof Mult) {
                if (((Mult) right).getLeft() instanceof Var && ((Mult) right).getRight() instanceof Var) {
                    return "(" + left.toString() + "" + f.removeOuterParentheses(right.toString()) + ")";
                }
            }
        }
        return "(" + left.toString() + " * " + right.toString() + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * (f * g)' = f' * g + g' * f.
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression left = super.getLeft();
        Expression right = super.getRight();
        return new Plus(new Mult(left.differentiate(var), right), new Mult(left, right.differentiate(var)));
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
            // 0 * x -> 0 || 1 * x -> x
            if (left.isEvaluable()) {
                if (f.doubleEqual(left.evaluate(), 0)) {
                    return new Num(0);
                }
                if (f.doubleEqual(left.evaluate(), 1)) {
                    return right;
                }
            }
            // x * 0 -> 0 || x * 1 -> x
            if (right.isEvaluable()) {
                if (f.doubleEqual(right.evaluate(), 0)) {
                    return new Num(0);
                }
                if (f.doubleEqual(right.evaluate(), 1)) {
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
        Expression left = super.getLeft();
        Expression right = super.getRight();

        // x * x -> x^2
        if (left.toString().equals(right.toString())) {
            return new Pow(left, 2);
        }

        // ((2.0 * x) * (x * 3.0)) -> 6x^2
        if (left instanceof Mult && right instanceof Mult) {
            if (((Mult) left).getRight().toString().equals(((Mult) right).getLeft().toString())) {
                Expression exp = new Mult(new Mult(((Mult) left).getLeft(), ((Mult) right).getRight()),
                        new Mult(((Mult) left).getRight(), ((Mult) right).getLeft()));
                exp.turnBonusOn();
                return exp.simplify();
            }
        }

        // 2x * 2x -> 4x^2
        if (left instanceof Mult && right instanceof Mult) {
            if (((Mult) left).getRight().toString().equals(((Mult) right).getRight().toString())) {
                return new Mult(new Mult(((Mult) left).getLeft(), ((Mult) right).getLeft()).simplify(),
                        new Pow(((Mult) left).getRight(), 2)).simplify();
            }
        }

        // 2x * 2y -> 4xy
        if (left instanceof Mult && right instanceof Mult) {
            return new Mult(new Mult(((Mult) left).getLeft(), ((Mult) right).getLeft()).simplify(),
                    new Mult(((Mult) left).getRight(), ((Mult) right).getRight())).simplify();
        }

        // sin(x) * cos(x) -> 0.5 * sin(2x)
        if (right instanceof Sin && left instanceof Cos) {
            Expression exp = new Mult(0.5, new Sin(new Mult(2, ((Sin) right).getExpression())));
            exp.turnBonusOn();
            return exp.simplify();
        }

        // cos(x) * sin(x) -> 0.5 * sin(2x)
        if (right instanceof Cos && left instanceof Sin) {
            Expression exp = new Sin(new Mult(2, ((Cos) right).getExpression()));
            exp.turnBonusOn();
            return exp.simplify();
        }

        // log(x, y) * 2 -> 2 * log(x, y)
        if (left instanceof Sin || left instanceof Cos || left instanceof Log) {
            Expression exp = new Mult(right, left);
            exp.turnBonusOn();
            return exp.simplify();
        }

        // x^2 * x^3 -> x^5
        if (left instanceof Pow && right instanceof Pow) {
            if (((Pow) left).getLeft().toString().equals(((Pow) right).getLeft().toString())) {
                Expression exp = new Pow(((Pow) left).getLeft(),
                        new Plus(((Pow) left).getRight(), ((Pow) right).getRight()));
                exp.turnBonusOn();
                return exp.simplify();
            }
        }

        // x^2 * x -> x^3
        if (left instanceof Pow) {
            if (((Pow) left).getLeft().toString().equals(right.toString())) {
                Expression exp = new Pow(((Pow) left).getLeft(), new Plus(((Pow) left).getRight(), 1));
                exp.turnBonusOn();
                return exp.simplify();
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
        return new Mult(left.assign(var, expression), right.assign(var, expression));
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

        if (other instanceof Mult) {
            return (((Mult) other).getLeft().toString().equals(left.toString())
                    && ((Mult) other).getRight().toString().equals(right.toString()))
                    || ((Mult) other).getLeft().toString().equals(right.toString())
                    && ((Mult) other).getRight().toString().equals(left.toString());
        }

        return false;
    }
}