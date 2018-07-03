
import java.util.Map;
import java.util.TreeMap;

/**
 * Expressions test class.
 */
public class ExpressionsTest {

    /**
     * Main function.
     *
     * @param args no use.
     */
    public static void main(String[] args) {
        try {
            // 1. Create the expression (2x) + (sin(4y)) + (e^x).
            Expression expression = new Plus(new Mult(2, "x"), new Plus(new Sin(new Mult(4, "y")),
                    new Pow("e", "x")));
            // 2. Print the expression.
            System.out.println(expression.toString());
            // 3. Print the value of the expression with (x=2,y=0.25,e=2.71).
            Map<String, Double> assignment = new TreeMap<>();
            assignment.put("x", 2.0);
            assignment.put("y", 0.25);
            assignment.put("e", 2.71);
            System.out.println(expression.evaluate(assignment));
            // 4. Print the differentiated expression according to x.
            Expression diff = expression.differentiate("x");
            System.out.println(diff.toString());
            // 5. Print the value of the differentiated expression according to x with the assignment above.
            System.out.println(diff.evaluate(assignment));
            // 6. Print the simplified differentiated expression.
            System.out.println(diff.simplify().toString());

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
