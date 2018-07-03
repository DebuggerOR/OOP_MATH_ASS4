
import java.util.Map;

/**
 * Div class.
 */
public class Div extends BinaryExpression implements Expression {

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(double left, double right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(String left, String right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(Expression left, double right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(Expression left, String right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(double left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(String left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(double left, String right) {
        super(left, right);
    }

    /**
     * Constructs a div by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Div(String left, double right) {
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
        Helpers f = new Helpers();
        Expression left = super.getLeft();
        Expression right = super.getRight();

        // case division by zero
        if (f.doubleEqual(right.evaluate(assignment), 0)) {
            throw new Exception("exception: division by 0");
        }

        return left.evaluate(assignment) / right.evaluate(assignment);
    }

    /**
     * Turns the expression to a string.
     *
     * @return the expression as a string.
     */
    @Override
    public String toString() {
        Expression left = super.getLeft();
        Expression right = super.getRight();

        // simplify in bonus mode
        if (super.isBonus()) {
            // sin(x) / cos(x) = tan(x)
            if (left instanceof Sin && right instanceof Cos) {
                if (((Sin) left).getExpression().toString().equals(((Cos) right).getExpression().toString())) {
                    return "tan(" + ((Sin) left).getExpression().toString() + ")";
                }
            }
            // cos(x) / sin(x) = cot(x)
            if (left instanceof Cos && right instanceof Sin) {
                if (((Cos) left).getExpression().toString().equals(((Sin) right).getExpression().toString())) {
                    return "cot(" + ((Cos) left).getExpression().toString() + ")";
                }
            }

            try {
                if (right.isEvaluable()) {
                    //  x / 2 -> 0.5x
                    if (left instanceof Var && !(right instanceof Var)) {
                        return "(" + new Div(1, right).evaluate() + "" + left.toString() + ")";
                    }
                }
            } catch (Exception e) {
                // print exception message
                System.out.println(e.getMessage());
            }
        }

        // basic return
        return "(" + left.toString() + " / " + right.toString() + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * (f / g)' = (f' * g - g' * f) / g^2.
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression left = super.getLeft();
        Expression right = super.getRight();

        return new Div(new Minus(new Mult(left.differentiate(var), right),
                new Mult(left, right.differentiate(var))), new Mult(right, right));
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

        // x / x = 1
        if (left.toString().equals(right.toString())) {
            return new Num(1);
        }
        try {
            // try to evaluate
            if (this.isEvaluable()) {
                return new Num(this.evaluate());
            }
            // x / 1 -> x
            if (right.isEvaluable()) {
                if (f.doubleEqual(right.evaluate(), 1)) {
                    return left;
                }
            }
            // 0 / x -> x
            if (left.isEvaluable()) {
                if (f.doubleEqual(left.evaluate(), 0)) {
                    return new Num(0);
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

        // (x + y) / (y + x) -> 1
        if (left instanceof Plus && right instanceof Plus) {
            if (((Plus) left).getLeft().toString().equals(((Plus) right).getRight().toString())
                    && ((Plus) left).getRight().toString().equals(((Plus) right).getLeft().toString())) {
                return new Num(1);
            }
        }

        // (x * y) / (y * x) -> 1
        if (left instanceof Mult && right instanceof Mult) {
            if (((Mult) left).getLeft().toString().equals(((Mult) right).getRight().toString())
                    && ((Mult) left).getRight().toString().equals(((Mult) right).getLeft().toString())) {
                return new Num(1);
            }
        }

        // ((x + y) + (a + b)) / ((y + x) + (b + a)) -> 1
        if (left instanceof Plus && right instanceof Plus) {
            if (((Plus) left).getLeft() instanceof Plus && ((Plus) left).getRight() instanceof Plus
                    && ((Plus) right).getRight() instanceof Plus && ((Plus) right).getLeft() instanceof Plus) {
                if (((Plus) ((Plus) left).getLeft()).isEqual(((Plus) right).getLeft())
                        && ((Plus) ((Plus) left).getRight()).isEqual(((Plus) right).getRight())) {
                    return new Num(1);
                }
            }
        }

        // ((x * y) * (a * b)) / ((y * x) * (b * a)) -> 1
        if (left instanceof Mult && right instanceof Mult) {
            if (((Mult) left).getLeft() instanceof Mult && ((Mult) left).getRight() instanceof Mult
                    && ((Mult) right).getRight() instanceof Mult && ((Mult) right).getLeft() instanceof Mult) {
                if (((Mult) ((Mult) left).getLeft()).isEqual(((Mult) right).getLeft())
                        && ((Mult) ((Mult) left).getRight()).isEqual(((Mult) right).getRight())) {
                    return new Num(1);
                }
            }
        }

        // (x / y) / (z / w) -> (xw) / (yz)
        if (left instanceof Div && right instanceof Div) {
            Expression exp = new Div(new Mult(((Div) left).getLeft(), ((Div) right).getRight()),
                    new Mult(((Div) left).getRight(), ((Div) right).getLeft()));
            exp.turnBonusOn();
            return exp.simplify();
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
        return new Div(left.assign(var, expression), right.assign(var, expression));
    }
}