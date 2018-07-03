
import java.util.Map;

/**
 * Pow class.
 */
public class Pow extends BinaryExpression implements Expression {

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(double left, double right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(String left, String right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(Expression left, double right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(Expression left, String right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(double left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(String left, Expression right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(double left, String right) {
        super(left, right);
    }

    /**
     * Constructs a pow by given expressions.
     *
     * @param left  the left expression.
     * @param right the right expression.
     */
    public Pow(String left, double right) {
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

        // case 0^0
        if (f.doubleEqual(left.evaluate(assignment), 0) && f.doubleEqual(right.evaluate(assignment), 0)) {
            throw new Exception("exception: 0^0 has double meaning");
        }
        // case sqrt of negative
        if (left.isEvaluable() && right.isEvaluable()) {
            if (left.evaluate() < 0 && f.doubleEqual(right.evaluate(), 0.5)) {
                throw new Exception("exception: sqrt of negative");
            }
        }

        return Math.pow(left.evaluate(assignment), right.evaluate(assignment));
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
        return "(" + left.toString() + "^" + right.toString() + ")";
    }

    /**
     * Calculates the differentiate of the expression.
     * (f^g)' = f^g * (g' * log(e, f) + (f' * g) / f).
     *
     * @param var the differentiate is according to this.
     * @return the expression of the differentiate.
     */
    @Override
    public Expression differentiate(String var) {
        Expression left = super.getLeft();
        Expression right = super.getRight();

        return new Mult(new Pow(left, right), new Plus(new Mult(right.differentiate(var),
                new Log(new Var("e"), left)), new Div(new Mult(left.differentiate(var), right), left)));


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
            if (left.isEvaluable()) {
                // 0^x = 0
                if (f.doubleEqual(left.evaluate(), 0)) {
                    return new Num(0);
                }
                // 1^x = 1
                if (f.doubleEqual(left.evaluate(), 1)) {
                    return new Num(1);
                }
            }
            if (right.isEvaluable()) {
                // x^0 = 1
                if (f.doubleEqual(right.evaluate(), 0)) {
                    return new Num(1);
                }
            }
            if (right.isEvaluable()) {
                // x^1 = x
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

        // (x^y)^z -> x^yz
        if (left instanceof Pow) {
            Expression exp = new Pow(((Pow) left).getLeft(),
                    new Mult(((Pow) left).getRight(), right));
            exp.turnBonusOn();
            return exp.simplify();
        }

        // x^log(x,y) -> y
        if (right instanceof Log) {
            if (left.toString().equals(((Log) right).getLeft().toString())) {
                return ((Log) right).getRight();
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
        return new Pow(left.assign(var, expression), right.assign(var, expression));
    }
}