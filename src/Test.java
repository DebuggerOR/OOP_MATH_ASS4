
/**
 * Test.
 */
public class Test {

    /**
     * Test.
     *
     * @param args no use.
     * @throws Exception no use.
     */
    public static void main(String[] args) throws Exception {
        // basic simplify
        Expression e = new Mult("x", 1);
        if (!e.simplify().toString().equals("x")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Mult("x", 0);
        if (!e.simplify().toString().equals("0.0")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Plus("x", 0);
        if (!e.simplify().toString().equals("x")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Div("x", "x");
        if (!e.simplify().toString().equals("1.0")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Div("x", 1);
        if (!e.simplify().toString().equals("x")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Minus("x", 0);
        if (!e.simplify().toString().equals("x")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Minus(0, "x");
        if (!e.simplify().toString().equals("(-x)")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Minus("x", "x");
        if (!e.simplify().toString().equals("0.0")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Log("x", "x");
        if (!e.simplify().toString().equals("1.0")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Mult(2, 8);
        if (!e.simplify().toString().equals("16.0")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2))
                .differentiate("x");
        if (!e.simplify().toString().equals("(((x + y)^2.0) * (2.0 / (x + y)))")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }


        e = new Pow(new Var("e"), new Var("x"));
        if (!e.simplify().toString().equals("(e^x)")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }


        e = new Mult(new Log(new Mult(9, "x"), new Mult(9, "x")), new
                Mult(2, "y"));
        if (!e.simplify().toString().equals("(2.0 * y)")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        e = new Plus(new Mult(new Plus(3, 6), "x"), new Mult(new Mult(4,
                "x"), new Sin(0)));
        if (!e.simplify().toString().equals("(9.0 * x)")) {
            System.out.println(e.simplify().toString());
            System.out.println("******");
        }

        Helpers f = new Helpers();

        e = new Pow(new Pow("x", "y"), "z");
        e.turnBonusOn();
        if (!f.simplifyString(e.simplify().toString()).equals("x^(yz)")) {
            System.out.println(f.simplifyString(e.simplify().toString()));
            System.out.println("******");
        }

        e = new Plus(new Mult(2, "x"), new Mult(4, "x"));
        e.turnBonusOn();
        if (!f.simplifyString(e.simplify().toString()).equals("6x")) {
            System.out.println(f.simplifyString(e.simplify().toString()));
            System.out.println("******");
        }

        e = new Plus(new Mult(2, "x"), new Plus(2, new Plus(new Mult(4,
                "x"), 1)));
        e.turnBonusOn();
        if (!f.simplifyString(e.simplify().toString()).equals("6x + 3")) {
            System.out.println(f.simplifyString(e.simplify().toString()));
            System.out.println("******");
        }

        // not bonus of last year
        e = new Mult(new Pow("x", 2), new Plus(1, new Plus(new Pow(2,
                "y"), new Pow(2, "x"))));
        e.turnBonusOn();
        if (!f.simplifyString(e.simplify().toString()).equals("6x + 3")) {
            System.out.println(f.simplifyString(e.simplify().toString()));
            System.out.println(f.simplifyString(e.differentiate("x").simplify().toString()));
            System.out.println("******");
        }

        e = new Mult(new Sin("x"), new Plus(new Cos("x"),
                new Cos("y")));
        e.turnBonusOn();
        if (!f.simplifyString(e.simplify().toString()).equals("6x + 3")) {
            System.out.println(f.simplifyString(e.simplify().toString()));
            System.out.println(f.simplifyString(e.differentiate("x").simplify().toString()));
            System.out.println("******");
        }

        e = new Minus(new Log(new Plus(new Plus(new Pow("x", 2), new Pow("x",
                4)), 4), 4), "x");
        e.turnBonusOn();
        if (!f.simplifyString(e.simplify().toString()).equals("6x + 3")) {
            System.out.println(f.simplifyString(e.simplify().toString()));
            System.out.println(f.simplifyString(e.differentiate("x").simplify().toString()));
            System.out.println("******");
        }

        e = new Mult(new Log(new Mult(9, "x"), new Mult(9, "x")), new
                Mult(2, "y"));
        e.turnBonusOn();
        if (!f.simplifyString(e.simplify().toString()).equals("6x + 3")) {
            System.out.println(f.simplifyString(e.simplify().toString()));
            System.out.println(f.simplifyString(e.differentiate("x").simplify().toString()));
            System.out.println("******");
        }
    }
}
