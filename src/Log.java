
import java.util.Map;

/**
 * Log class.
 */
public class Log extends BinaryExpression implements Expression {

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(double left, double right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(String left, String right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(Expression left, double right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(Expression left, String right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(double left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(String left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(double left, String right) {
        super(left, right);
    }

    /**
     * Constructs a log by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Log(String left, double right) {
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

        // according formula to change base
        double base = left.evaluate(assignment);
        double num = right.evaluate(assignment);

        // case log of non positive
        if (!(num > 0 && base > 0 && base != 1)) {
            throw new Exception("exception: problem with log");
        }

        return Math.log(num) / Math.log(base);
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

        // simplify string if in bonus mode
        if (this.isBonus()) {
            // remove double parentheses of right
            if (left.toString().charAt(0) == '('
                    && left.toString().charAt(left.toString().length() - 1) == ')') {
                leftStr = f.removeOuterParentheses(leftStr);
            }
            // remove double parentheses of left
            if (right.toString().charAt(0) == '('
                    && right.toString().charAt(right.toString().length() - 1) == ')') {
                rightStr = f.removeOuterParentheses(rightStr);
            }
        }

        return "log(" + leftStr + ", " + rightStr + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * log(f, g)' = (log(e, f) / log(e, g))' = (log(e, f)' * log(e, g) - log(e, g)' * log(e, f)) / log(e, f)^2.
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression left = super.getLeft();
        Expression right = super.getRight();

        // case ln (base is e) ln(f)' = f' / ln(f)
        if (left instanceof Var) {
            if (((Var) left).getName().equals("e")) {
                return new Div(right.differentiate(var), this);
            }
        }

        // case not ln (handles also base isn't num)
        return new Div(new Minus(new Mult(new Log("e", left).differentiate(var),
                new Log("e", right)), new Mult(new Log("e", getRight()).differentiate(var),
                new Log("e", left))), new Pow(new Log("e", left), 2));
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

        // log(x, x) = 1
        if (left.simplify().toString().equals(right.simplify().toString())) {
            return new Num(1);
        }

        try {
            // try to evaluate
            if (this.isEvaluable()) {
                return new Num(this.evaluate());
            }
            // log(x, 1) = 0
            if (right.isEvaluable()) {
                if (f.doubleEqual(right.evaluate(), 1)) {
                    return new Num(0);
                }
            }
            // log(2, 2) = 1
            if (right.isEvaluable() && left.isEvaluable()) {
                if (f.doubleEqual(right.evaluate(), left.evaluate())) {
                    return new Num(1);
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

        // log(x + y, y + x) = 1
        if (left instanceof Plus && right instanceof Plus) {
            if (((Plus) left).getLeft().toString().equals(((Plus) right).getRight().toString())
                    && ((Plus) left).getRight().toString().equals(((Plus) right).getLeft().toString())) {
                return new Num(1);
            }
        }

        // log(x * y, y * x) = 1
        if (left instanceof Mult && right instanceof Mult) {
            if (((Mult) left).getLeft().toString().equals(((Mult) right).getRight().toString())
                    && ((Mult) left).getRight().toString().equals(((Mult) right).getLeft().toString())) {
                return new Num(1);
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
        return new Log(left.assign(var, expression), right.assign(var, expression));
    }
}