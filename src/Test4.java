
import java.util.Map;
import java.util.TreeMap;

public class Test4 {
    public static void main(String[] args) throws Exception {
        try {
            Expression e = new Plus(new Plus(new Mult(2, "x"), new Sin(new Mult(4, "y"))), new Pow("e", "x"));
            System.out.println(e.toString());
            Map<String, Double> assignment = new TreeMap<String, Double>();
            assignment.put("x", (double) 2);
            assignment.put("y", 0.25);
            assignment.put("e", Math.E);
            double value = e.evaluate(assignment);
            System.out.println(value);
            System.out.println(e.differentiate("x"));
            System.out.println(e.differentiate("x").evaluate(assignment));
            System.out.println(e.differentiate("x").simplify());
        }catch (Exception e){}
    }
}