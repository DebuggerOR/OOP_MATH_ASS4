
import java.util.ArrayList;
import java.util.List;

/**
 * Simplification demo class.
 */
public class SimplificationDemo {

    /**
     * Main function that runs the bonus stuff.
     *
     * @param args no use.
     */
    public static void main(String[] args) {
        System.out.println("\n\tsome more simplifications in different kinds\n");

        // cosmetics
        coolCosmeticsSimplification();
        // exceptions
        coolExceptionsSimplification();
        // trigonometry
        coolTrigonometrySimplification();
        // math
        coolMathSimplification();

        System.out.println("\n\tthanks for watching :)");
    }

    /**
     * Displays cool cosmetics simplification.
     */
    private static void coolCosmeticsSimplification() {
        Helpers f = new Helpers();
        List<Expression> expressions = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        // sets type of simplification
        f.setTypeSimplification("cosmetics");

        // moves redundant dot zero
        f.displayAfter(new Plus(new Plus("x", 4), 2.5).simplify(), "((x + 4.0) + 2.5) -> x + 4 + 2.5");

        // moves redundant outer ()
        f.displayAfter(new Plus("x", "y").simplify(), "(x + y) -> x + y");

        // sin(180) = 1.2246467991473532E-16 -> 0
        f.displayAfter(new Sin(180).simplify(), "sin(180) = 1.2246467991473532E-16 -> 0");

        // moves redundant () of left expression of minus
        expressions.add(new Minus(new Plus("x", "y"), "z"));
        descriptions.add("(x + y) - (x + y) -> x + y - (x + y)");

        // moves redundant () of expressions inside sin, cos and log
        expressions.add(new Cos(new Plus("x", 4)).simplify());
        descriptions.add("remove double parentheses (sin, cos)");
        expressions.add(new Log(new Div("x", 4), new Div("y", 4)));
        descriptions.add("log((x/y), (x/z)) -> log(x/y, x/z)");

        // put sin, cos and log after
        expressions.add(new Mult(new Cos("x"), 8).simplify());
        descriptions.add("cos(x) * y -> y * cos(x) (sin, cos)");
        expressions.add(new Mult(new Log("x", "y"), 8).simplify());
        descriptions.add("log(x, y) * z -> z * log(x, y)");

        // display fraction as number
        expressions.add(new Sin(new Div("x", 4)).differentiate("x").simplify());
        descriptions.add("x / 4 -> 0.25x");

        // remove the * in some mult expressions
        expressions.add(new Minus(new Mult("x", 4), new Minus("x", 4)).simplify());
        descriptions.add("x * 4 -> 4x");
        expressions.add(new Plus(new Mult("x", "y"), 4));
        descriptions.add("x * y -> xy");
        expressions.add(new Div(new Mult("x", "y"), new Mult("a", "b")));
        descriptions.add("(x * y) / (a * b) -> (xy) / (ab)");

        // turn neg to minus
        expressions.add(new Plus(new Plus("x", "y"), new Neg("z")));
        descriptions.add("(x + y) + (-z) -> x + y - z");

        // move neg from left to minus
        expressions.add(new Plus(new Neg("z"), new Plus("x", "y")));
        descriptions.add("(-z) + (x + y) -> x + y - z");

        // display all examples
        for (int i = 0; i < expressions.size(); i++) {
            f.displayExample(expressions.get(i), descriptions.get(i));
        }
    }

    /**
     * Displays cool trigonometry simplification.
     */
    private static void coolTrigonometrySimplification() {
        Helpers f = new Helpers();
        List<Expression> expressions = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        // sets type of simplification
        f.setTypeSimplification("trigo");

        // identities of tan and cot
        expressions.add(new Div(new Sin("x"), new Cos("x")).simplify());
        descriptions.add("sin(x) / cos(x) -> tan(x)");
        expressions.add(new Div(new Cos("x"), new Sin("x")).simplify());
        descriptions.add("cos(x) / sin(x) -> cot(x)");

        // most basic trigonometry formula: sin(x)^2 + cos(x)^2 -> 1
        expressions.add(new Plus(new Pow(new Cos("x"), 2), new Pow(new Sin("x"), 2)));
        descriptions.add("sin(x)^2 + cos(x)^2 -> 1");

        // other views of the basic formula
        expressions.add(new Minus(1, new Pow(new Cos("x"), 2)).simplify());
        descriptions.add("1 - cos(x)^2 -> sin(x)^2");
        expressions.add(new Minus(1, new Pow(new Sin("x"), 2)).simplify());
        descriptions.add("1 - sin(x)^2 -> cos(x)^2");

        // double angle of sin
        expressions.add(new Plus(new Mult(new Cos("x"), new Sin("x")), 2).simplify());
        descriptions.add("2 * sin(x) * cos(x) -> sin(2x)");

        // cos "eats" minus
        expressions.add(new Cos(new Neg(new Plus("x", 4))).simplify());
        descriptions.add("cos(-x) -> cos(x)");

        // sin "drops" minus out
        expressions.add(new Sin(new Neg(new Plus("x", 4))).simplify());
        descriptions.add("sin(-x) -> -sin(x)");

        // display all examples
        for (int i = 0; i < expressions.size(); i++) {
            f.displayExample(expressions.get(i), descriptions.get(i));
        }
    }

    /**
     * Displays cool exceptions.
     */
    private static void coolExceptionsSimplification() {
        Helpers f = new Helpers();
        List<Expression> expressions = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        // sets type of simplification
        f.setTypeSimplification("exceptions");

        // division by zero
        expressions.add(new Div("x", 0));
        descriptions.add("warns about division by 0");

        // log of non positive
        expressions.add(new Log(4, new Neg(4)));
        descriptions.add("warns about log of non positive");

        // sqrt of negative like (-1)^0.5
        expressions.add(new Pow(new Neg(4), new Div(1, 2)));
        descriptions.add("warns about sqrt of neg");

        // 0^0 needs some philosophy..
        expressions.add(new Pow(0, 0));
        descriptions.add("warns about 0^0");

        // display all examples
        for (int i = 0; i < expressions.size(); i++) {
            f.displayExample(expressions.get(i), descriptions.get(i));
        }
    }

    /**
     * Displays cool math simplification.
     */
    private static void coolMathSimplification() {
        Helpers f = new Helpers();
        List<Expression> expressions = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        // sets type of simplification
        f.setTypeSimplification("math");

        // simplify basic vars plus and minus
        expressions.add(new Plus("x", new Plus(new Mult(2, "x"), new Mult(3, "x"))));
        descriptions.add("2x + 3x -> 5x");
        expressions.add(new Minus(new Mult(5, "x"), new Mult(4, "x")));
        descriptions.add("5x - 4x -> x");

        // simplify basic mults
        expressions.add(new Minus(new Plus("x", "y"), new Mult(2, "x")).simplify());
        descriptions.add("(x + y) - (2 * x) -> x + y - 2x");
        expressions.add(new Mult(new Mult(2, "x"), new Mult(2, "y")));
        descriptions.add("(2 * x) * (2 * y) -> 4xy");

        // double minus is plus
        expressions.add(new Neg(-1).simplify());
        descriptions.add("-(-1) -> 1");
        expressions.add(new Neg(new Neg("x")).simplify());
        descriptions.add("-(-(x)) -> x");
        expressions.add(new Minus("x", new Neg("y")));
        descriptions.add("x - (-y) -> x + y");

        // plus negative is minus
        expressions.add(new Plus(new Plus(new Neg("x"), 4), "y").simplify());
        descriptions.add("x + (-y) || (-y) + x -> x - y");
        expressions.add(new Plus(new Plus(new Neg("x"), 4), "y").simplify());
        descriptions.add("((-x) + 4) + y -> 4 - x + y");

        // division of fractions
        expressions.add(new Div(new Div("x", "y"), new Div("z", "w")));
        descriptions.add("(x / y) / (z / w) -> xw / yz");

        // minus of equal expressions
        expressions.add(new Minus(new Plus("x", "y"), new Plus("x", "y").simplify()));
        descriptions.add("(x + y) - (x + y) -> 0");
        expressions.add(new Minus(new Plus("x", "y"), new Plus("y", "x").simplify()));
        descriptions.add("(x + y) - (y + x) -> 0");
        expressions.add(new Minus(new Mult("x", "y"), new Mult("x", "y")));
        descriptions.add("(x * y) - (x * y) -> 0");
        expressions.add(new Minus(new Mult("x", "y"), new Mult("y", "x")).simplify());
        descriptions.add("(x * y) - (y * x) -> 0");

        // division of equal expressions
        expressions.add(new Div(new Plus("x", "y"), new Plus("x", "y")));
        descriptions.add("(x + y) / (x + y) -> 1");
        expressions.add(new Div(new Plus("x", "y"), new Plus("y", "x")).simplify());
        descriptions.add("(x + y) / (y + x) -> 1");
        expressions.add(new Div(new Mult("x", "y"), new Mult("x", "y")));
        descriptions.add("(x * y) / (x * y) -> 1");
        expressions.add(new Div(new Mult("x", "y"), new Mult("y", "x")).simplify());
        descriptions.add("(x * y) / (y * x) -> 1");
        expressions.add(new Div(new Plus(new Plus("x", "y"), new Plus("a", "b")),
                new Plus(new Plus("y", "x"), new Plus("b", "a"))).simplify());
        descriptions.add("((x + y) + (a + b)) / ((y + x) + (b + a)) -> 1");
        expressions.add(new Div(new Mult(new Mult("x", "y"), new Mult("a", "b")),
                new Mult(new Mult("y", "x"), new Mult("b", "a"))).simplify());
        descriptions.add("((x * y) * (a * b)) / ((y * x) * (b * a)) -> 1");

        // log of equal expressions
        expressions.add(new Log(new Mult("x", "y"), new Mult("x", "y")));
        descriptions.add("log(x * y, x * y) -> 1");
        expressions.add(new Log(new Mult("x", "y"), new Mult("y", "x")).simplify());
        descriptions.add("log(x * y, y * x) -> 1");
        expressions.add(new Log(new Plus(new Mult("x", 9), 2),
                new Plus(2, new Mult(9, "x"))).simplify());
        descriptions.add("log(((x * 9) + 2), (2 + (9 * x))) -> 1");

        // convert mult to pow
        expressions.add(new Mult("x", "x"));
        descriptions.add("x * x -> x^2");
        expressions.add(new Mult(new Mult("x", "y"), new Mult("y", "x")));
        descriptions.add("(x * y) * (y * x) -> (x^2)(y^2)");
        expressions.add(new Plus(4, new Pow(new Pow("x", 4), 4)).simplify());
        descriptions.add("4 + ((x^4)^4) -> 4 + x^16");
        expressions.add(new Div(new Div(2, "x"), new Div("x", 3)));
        descriptions.add("(2 / x) / (x / 3) -> 6 / (x^2)");
        expressions.add(new Mult(new Mult(2, "x"), new Mult("x", 3)));
        descriptions.add("(2 * x) * (x * 3) -> 6(x^2)");

        // plus of logs is log of mult
        expressions.add(new Plus(new Log("x", "y"), new Log("x", "z")));
        descriptions.add("log (x, y) + log (x, z) -> log(x, yz)");

        // minus of logs is log of division
        expressions.add(new Minus(new Log("x", "y"), new Log("x", "z")));
        descriptions.add("log(x, y) - log(x, z) -> log(x, y / z)");

        // pow simplifications
        expressions.add(new Pow(0, "x"));
        descriptions.add("0^x -> 0");
        expressions.add(new Pow(new Plus("x", "y"), 1));
        descriptions.add("(x + y)^1 -> x + y");
        expressions.add(new Pow(new Plus("x", "y"), 0));
        descriptions.add("(x + y)^0 -> 1");
        expressions.add(new Mult(new Pow("x", 2), new Pow("x", 3)));
        descriptions.add("x^2 * x^3 -> x^5");
        expressions.add(new Mult(new Pow("x", 2), "x"));
        descriptions.add("x^2 * x -> x^3");

        // display all examples
        for (int i = 0; i < expressions.size(); i++) {
            f.displayExample(expressions.get(i), descriptions.get(i));
        }
    }
}