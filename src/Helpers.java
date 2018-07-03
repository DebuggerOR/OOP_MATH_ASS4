
/**
 * Helpers class.
 * Consists some helpful functions.
 */
public class Helpers {
    private int numExample;
    private String typeSimplification;

    /**
     * Checks doubles equal.
     *
     * @param d1 first double.
     * @param d2 second double.
     * @return if equal.
     */
    public boolean doubleEqual(double d1, double d2) {
        // the allowed mistake
        double epsilon = 0.01;
        // return if the distance is less than epsilon
        return Math.abs(d1 - d2) < epsilon;
    }

    /**
     * Removes redundant dot zero.
     *
     * @param str the string to be simplified.
     * @return the string without dot zero.
     */
    public String removeDotZero(String str) {
        return str.replace(".0", "");
    }

    /**
     * Removes the outer parentheses.
     *
     * @param str the string to be simplified.
     * @return the string without outer parentheses.
     */
    public String removeOuterParentheses(String str) {
        // case first and last char are '(' and ')' take the string inside.
        if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    /**
     * Simplifies the string of the expression.
     *
     * @param str the string of the expression.
     * @return the simplified string.
     */
    public String simplifyString(String str) {
        return removeOuterParentheses(removeDotZero(str));
    }

    /**
     * Displays example of basic and advanced simplifications.
     *
     * @param e   the expression to present simplified and not simplified.
     * @param msg short description of the simplification.
     */
    public void displayExample(Expression e, String msg) {
        // print title
        System.out.println("-------" + typeSimplification + " " + ++this.numExample + "-------");
        System.out.println(msg);

        // print before simplification
        System.out.println("before: " + this.simplifyString(e.toString()));

        // simplify in bonus mode
        e.turnBonusOn();
        e = e.simplify();
        e.turnBonusOn();
        System.out.println("after:  " + this.simplifyString(e.toString()));
    }

    /**
     * Displays example of expressions after simplification.
     *
     * @param e   the expression to present simplified.
     * @param msg short description of the simplification.
     */
    public void displayAfter(Expression e, String msg) {
        // print title
        System.out.println("-------" + typeSimplification + " " +  ++this.numExample + "-------");
        System.out.println(msg);

        // simplify in bonus mode
        e.turnBonusOn();
        e = e.simplify();
        e.turnBonusOn();
        System.out.println("after:  " + this.simplifyString(e.toString()));
    }

    /**
     * Sets type simplification.
     *
     * @param newTypeSimplification the new type of the simplification.
     */
    public void setTypeSimplification(String newTypeSimplification) {
        this.typeSimplification = newTypeSimplification;
    }
}