
public class Test8 {
    /**
     *
     * @param args the mains arguments.
     * @throws Exception a new Exception.
     */
    public static void main(String[] args) throws Exception {
        Expression e = new Pow(new Pow("x", "y"), new Var("z"));
        System.out.println(e); //(x^y)^z
        e.turnBonusOn();
        System.out.println(e.simplify()); //x^(y*z)
        e = new Plus(new Mult(2, "x"), new Mult(4, "x"));
        System.out.println(e); //2x+4x
        e.turnBonusOn();
        System.out.println(e.simplify()); //6x
        //((2x) + (2 + ((4x) + 1))) => 6x + 3
        e = new Plus(new Mult(2, "x"), new Plus(2, new Plus(new Mult(4, "x"), 1)));
        System.out.println(e); //2x + (2 + (4x + 1))
        e.turnBonusOn();
        System.out.println(e.simplify()); //6x+3
        e = new Plus(new Plus(2, new Plus(new Mult(4, "x"), 1)), new Mult(2, "x"));
        System.out.println(e); //2x + (2 + (4x + 1))
        e.turnBonusOn();
        System.out.println(e.simplify()); //6x+3
        e = new Plus(new Mult(2, "x"), new Plus(new Mult(4, "x"), new Plus("y", 1)));
        System.out.println(e); //((2.0 * x) + ((4.0 * x) + (y + 1.0)))
        e.turnBonusOn();
        System.out.println(e.simplify()); //((6.0 * x) + (y + 1.0))
        e = new Div(new Plus("y", new Plus(6, new Plus("x", 3))), new Plus("x",
                new Plus(3, new Plus("y", 6))));
        System.out.println(e); //(y+6+x+3)/(x+3+y+6)
        e.turnBonusOn();
        System.out.println(e.simplify()); //1
        e = new Div(new Plus("x", 3), new Plus(1, new Plus("x", 2)));
        System.out.println(e);  //(x+3)/(1+(x+2))
        e.turnBonusOn();
        System.out.println(e.simplify()); //(x+3)/(3+x)
        e = new Div(new Mult(new Plus("x", 32), "y"), new Mult("y", new Plus(32, "x"))); //not working
        System.out.println(e); //(((x + 32) * y) / ((32 + x) * y))
        e.turnBonusOn();
        System.out.println(e.simplify()); //1
        e = new Log(new Plus("x", "y"), new Plus("y", "x"));
        System.out.println(e); //Log((x + y), (y + x))
        e.turnBonusOn();
        System.out.println(e.simplify()); //1
        e = new Mult(new Plus("x", "y"), new Plus("y", "x"));
        System.out.println(e); //((x + y) * (y + x))
        e.turnBonusOn();
        System.out.println(e.simplify()); //((x + y)^2)
        e = new Pow("x", "x");
        System.out.println(e); //x^x
        e.turnBonusOn();
        System.out.println(e.differentiate("x").simplify()); //(x^x) * (Log(e, x) + 1)
        e = new Log("e", new Pow("x", 4));
        System.out.println(e); //Log(e, (x^4))
        e.turnBonusOn();
        System.out.println(e.simplify()); //(4 * Log(e, x))
        System.out.println(e.differentiate("x")); //((4.0 / x)
        e = new Minus(4, new Neg("x"));
        System.out.println(e); //4 - (-x)
        e.turnBonusOn();
        System.out.println(e.simplify()); //4 + x
        e = new Sin(new Neg("x"));
        System.out.println(e); //Sin(-x)
        e.turnBonusOn();
        System.out.println(e.simplify()); //-Sin(x)
        e = new Cos(new Neg("x"));
        System.out.println(e); //Cos(-x)
        e.turnBonusOn();
        System.out.println(e.simplify()); //Cos(x)
        e = new Mult(new Div("x", "y"), new Div("y", "x"));
        System.out.println(e); //((x / y) * (y / x))
        e.turnBonusOn();
        System.out.println(e.simplify()); //1.0
        e = new Plus(new Div("x", "y"), new Div("y", "x"));
        System.out.println(e); //((x / y) + (y / x))
        e.turnBonusOn();
        System.out.println(e.simplify()); //(((x^2.0) + (y^2.0)) / (y * x))
        e = new Minus(new Mult(new Cos(new Neg("x")), new Div(new Pow("y", 4), "y")),
                new Neg(new Minus(new Neg(1), 0)));
        System.out.println(e); //((Cos((-x)) * ((y^4.0) / y)) - (-((-1.0) - 0.0)))
        e.turnBonusOn();
        System.out.println(e.simplify()); //((Cos(x) * (y^3.0)) + (-1.0))
        e = new Plus(new Neg(5), "x");
        System.out.println(e); //(-5 + x)
        e.turnBonusOn();
        System.out.println(e.simplify()); //x - 5
        e = new Div("x", new Div("y", 5));
        System.out.println(e); //(x / (y / 5.0))
        e.turnBonusOn();
        System.out.println(e.simplify()); //((5.0 * x) / y)
        e = new Div(new Mult(new Pow("x", 4), 7), new Pow("x", 5));
        System.out.println(e); //(((x^4.0) * 7.0) / (x^5.0))
        e.turnBonusOn();
        System.out.println(e.simplify()); //(7.0 / x)
        e = new Div(new Mult(7, "x"), 7);
        System.out.println(e); //(((x^4.0) * 7.0) / (x^5.0))
        e.turnBonusOn();
        System.out.println(e.simplify()); //(7.0 / x)
        e =  new Plus(new Plus("y", "x"), new Plus("x", "y"));
        System.out.println(e); //(y + x) + (x + y)
        e.turnBonusOn();
        System.out.println(e.simplify()); //2 * (y + x)
        e =  new Minus(new Plus("y", "x"), new Plus("x", "y"));
        System.out.println(e); //(y + x) - (x + y)
        e.turnBonusOn();
        System.out.println(e.simplify()); //0
        e =  new Minus(1, new Pow(new Cos("x"), 2));
        System.out.println(e); //1 + Cos(x)
        e.turnBonusOn();
        System.out.println(e.simplify()); //Sin(x)
        e =  new Minus(1, new Pow(new Sin("x"), 2));
        System.out.println(e); //1 + Sin(x)
        e.turnBonusOn();
        System.out.println(e.simplify()); //Cos(x)
        e =  new Plus(new Pow(new Cos("x"), 2), new Pow(new Sin("x"), 2));
        System.out.println(e); //((Cos(x)^2.0) + (Sin(x)^2.0))
        e.turnBonusOn();
        System.out.println(e.simplify()); //1
    }
}
