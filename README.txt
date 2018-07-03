ID 318732484

I used some different techniques in order to extend more the simplification stuff.

One way, maybe the main one, is a function called "moreSimplify", that the function "simplify" calls to it.
I used a fine tactic to distinguish between the regular mode and the bonus mode.
For each expression, we can turn on and off bonus mode. The default is off.
There is a member "isBonus" and functions that minipulate it that handles that.
Of course, turning on bonus mode is using recursion to turn also the children's bonus mode too.
By the help of this, we know when it needs to call also the "moreSimplify" function.
It's very simple. When "isBonus" is true the function "simplify" calls "simplifyMore".

How "simplifyMore" does what it does? It's also very simple.
Checking what is the right expression, what is left, if it can evaluate right or left etc.
I will give an example.
For instance, let it be (x + y) / (y + x) -> 1. There are different things we can do with this.
First, note that comparing the right expression to the left by strings comparison helps us a lot in many cases.
In div: right.toString().equals(left.toString()) handles the case of (x + y) / (x + y) -> 1, or even
cos(x + y)^(z - 4) / cos(x + y)^(z - 4) -> 1.
However, for (x + y) / (y + x) -> 1 we have to use some thing else.
Whats about 'if'? Of course! It really can help.
The following solves the problem: (pseudo)
if right is plus and left is plus, if right.left.toString().equals(left.right.toString()) && etc.
Works well and not too much long.
But, frankly, we can use the commutative feature of the plus and the multiple.
I had implemented "isEquals" function in Plus and Mult classes.
While comparing plus expressions, of course, there is no importance for order. Same with multiple.
Now we can do the following:
if right is Mult && left is Mult, if right.isEqual(left)
that handles (x * y) / (y * x) -> 1 etc.

The second way, converting the text in the "toString" function.
Like what described above, we are using the member that tells us if the expression is in bonus mode.
For instance, we can convert the text of "sin(x) / cos(x)" to "tan(x)".
Here, again, checking some conditions by 'ifs' is very useful.
If in Div the left is Sin and right is Cos, it is probably a tan.. so on with cot, more identities and other stuff. 
More cosmetics can take place in this region, like x / 4 -> 0.25 or 2 * x -> 2x.

I am doing also some simplification outside, means doing manipulations on the final string of the expression.
Let's take the most simple example. 9.0. the dot zero is really really not necessary.
That's why 9.0 -> 9 and x^2.0 -> x^2 and so on.. Of course, we didn't convert 4.5 to 4. 
Only the unnecessary .0 had vanished.
Substrings functions and a lot more, are very useful here.
We can add many cool features following this technic, manipulating the final string.
Removing redundant (). I did that also.
Not only but also () scopes strategy can help us to treat the expressions that the string consists, separately.

Evaluate. This is another place to check the expression and manipulate it.
The natural thing to treat in this area is error / bizarre math checking and special evaluations.
You can see below some special cases I had treated there.

Now we shall look at some real examples, taken from the real output of "simplicationDemo":

==============
Those are some cosmetics, mainly done by manipulating strings in the inner toString or in the outer area,
on the final string.
Tools that we use a lot are checking if the expression is evaluable (I wrote functions to check that) and checking
the types of the expressions, of course, by instace of.

-------cosmetics 1-------
((x + 4.0) + 2.5) -> x + 4 + 2.5
after:  x + 4 + 2.5
removing the redundant .0 is done by string functions.
It's a manipulation on the final string of the expression.

-------cosmetics 2-------
(x + y) -> x + y
after:  x + y
this is a manipulation on the final string of the expression.
if it isn't cos(y + x +70 etc. then the first and last chars are ().
of course, they are redundant.

-------cosmetics 3-------
sin(180) = 1.2246467991473532E-16 -> 0
after:  0
by the help of a special function for the comparsion of doubles considering
small range of mistake, this strange small num turns to be zero.

-------cosmetics 4-------
(x + y) - (x + y) -> x + y - (x + y)
before: (x + y) - z
after:  x + y - z
removing the redundant () from the left expression in a minus expression.
this cosmetic is done in the toString function of the minus.
we check if the left has () and removes it if it can.

-------cosmetics 5-------
remove double parentheses (sin, cos)
before: cos((x + 4))
after:  cos(x + 4)
removing double () is done in the toString function.
it is very easy to be detected by some ifs.
if the expression inside the cos is regular, means not a sin or cos etc.
it has redundant () that we can remove.

-------cosmetics 6-------
log((x/y), (x/z)) -> log(x/y, x/z)
before: log((x / 4), (y / 4))
after:  log(0.25x, 0.25y)
moving redundant () inside log.
the logic is like the one with sin and log, but more difficult
because of there are two expressions inside.

-------cosmetics 7-------
cos(x) * y -> y * cos(x) (sin, cos)
before: cos(x) * 8
after:  8 * cos(x)
putting the trigonometry function after.
first, it is more clean and clear.
second, determining an order is important for more future simplifications

-------cosmetics 8-------
log(x, y) * z -> z * log(x, y)
before: log(x, y) * 8
after:  8 * log(x, y)
first, more clean and clear.
second, the order is important for future simplification.
the logic of this and the same feature of sin and cos is inside the moreSimplification.
some conditions are checked.

-------cosmetics 9-------
x / 4 -> 0.25x
before: cos((x / 4)) * 0.25
after:  0.25 * cos(0.25x)
converting fractions into real numbers is done in the toString.
first need to check if the relevant expressions are evaluable, 
and if yes, evaluate it and put in a new fitting expression. 

-------cosmetics 10-------
x * 4 -> 4x
before: (x * 4) - (x - 4)
after:  4x - (x - 4)
moving the start when the multiple is clear, just like in real life.
this is done, like much more cosmetics, in the toString function.
after checking what is a var and what is a num etc. we can put each
expression in his relevant place with a relelvant operand (or delete the operand)
before him. 

-------cosmetics 11-------
x * y -> xy
before: (x * y) + 4
after:  xy + 4
just as before, thats how we write in the real life.
this cosmetic is done in the toString function, again, like before.

-------cosmetics 12-------
(x * y) / (a * b) -> (xy) / (ab)
before: (x * y) / (a * b)
after:  (xy) / (ab)
more advanced example of removing the stars of the multiple.
here the conditions to check are much complexed, but the motivation is the same.
no mark of multiple like in real life.

-------cosmetics 13-------
(x + y) + (-z) -> x + y - z
before: (x + y) + (-z)
after:  x + y - z
here plus neg turns to be minus.
in plus we check who is the expression in the right.
if it's a neg, we probably change the expression to a minus.

-------cosmetics 14-------
(-z) + (x + y) -> x + y - z
before: (-z) + (x + y)
after:  x + y - z
here it's advanced example.
the neg isn't in the right but in the left.
in plus we all know that the order isn't important,
so after some more (more complexed) conditions, we turn it to be
a fitting minus expression.

===============
Here are some exceptions that their logic takes place in the evaluate area.
They handle errors and some bizarre math.
All the comparsions of doubles is done by a special functions that considers 
a small range of mistake.

-------exceptions 1-------
warns about division by 0
before: x / 0
exception: division by 0
after:  x / 0
division by zero is bad. we all know.
conditions about the type, isEvaluable and comparing the result of the evaluate
reveals that case.
the expression isn't evaluated and the exception's message is written.

-------exceptions 2-------
warns about log of non positive
before: log(4, (-4))
exception: problem with log
after:  log(4, -4)
like the division by zero, this is also bad.
conditions etc. reveal that.
again, the expression isn't evaluated and exceptions' message is written.

-------exceptions 3-------
warns about sqrt of neg
before: (-4)^(1 / 2)
exception: sqrt of negative
after:  (-4)^0.5
sqrt of neg isn't a good thing in our field.
like above, reaviling it with conditions and evaluation.

-------exceptions 4-------
warns about 0^0
before: 0^0
exception: 0^0 has double meaning
after:  0^0
checking wikipedia is a good thing to do to understand
the meaning of this bizarre math.
like above, reaviling it with conditions and evaluation.

===========
Here you can see some trigonometry. Known important identities and more.
Tools for those simplifications are conditions to check types and useful comparing tactics.
We explained some good tactics to compare and check if equal.
One way, simple and good, is to check if the strings of the expressions are equal.
Of course, this doesn't support a change in order, for instance.
That's why we use some conditions to know the types, their order and value.
Another trick is the "isEqual" of the Plus and Mult that don't give importance for order.
The logic can take place in the "moreSimplify" function and the toString of the expressions.

-------trigo 1-------
sin(x) / cos(x) -> tan(x)
before: sin(x) / cos(x)
after:  tan(x)
here, if we find in division that left is Sin and right is Cos,
we change the text to be tan.
one more way to handle this is to create a new expression of tan
and change the sin / cos really to be a tan and not only in the text level.

-------trigo 2-------
cos(x) / sin(x) -> cot(x)
before: cos(x) / sin(x)
after:  cot(x)
like the tan, we can change the text of cos div sin to be cot

-------trigo 3-------
sin(x)^2 + cos(x)^2 -> 1
before: (cos(x)^2) + (sin(x)^2)
after:  1
here it is the well known trigo formula.
need to check the types, evaluate the exponents and check if the expressions
inside are equal.

-------trigo 4-------
1 - cos(x)^2 -> sin(x)^2
before: 1 - (cos(x)^2)
after:  sin(x)^2
this is like the passed formula but passing the cos.
the checks are very similar in their logic, but different in pracrice.

-------trigo 5-------
1 - sin(x)^2 -> cos(x)^2
before: 1 - (sin(x)^2)
after:  cos(x)^2
this is like the orgin formula but passing the sin.
the checks are very similar in their logic, but different in pracrice.

-------trigo 6-------
2 * sin(x) * cos(x) -> sin(2x)
before: (cos(x) * sin(x)) + 2
after:  0.5 * sin(2x) + 2
maybe the most cool and loved identity, the double angle of sin.
the logic here is again, checking the types, evaluating and comparing
between the expressions inside.

-------trigo 7-------
cos(-x) -> cos(x)
before: cos((-(x + 4)))
after:  cos(x + 4)
more cool trigo rule. the cos eats minus.
here we convert a neg expression inside the cos to be non neg.
checking types is nesescary.

-------trigo 8-------
sin(-x) -> -sin(x)
before: sin((-(x + 4)))
after:  -sin(x + 4)
the sin doesn't eat the minus but drops it out.
we convert the regular sin to be a neg that the sin is inside.

===========
Here are some different math simplifications. You can find here pow rules and so on.
The tools of the simplification are already known.
We check the types, check if can be evaluated, try to evaluate, check if strings are equal,
check if pllus and mult expressions are equal with no importance for order etc.

-------math 1-------
2x + 3x -> 5x
before: x + ((2 * x) + (3 * x))
after:  6x
very nice thing. like in the real word, we don't write x + 2x but 3x.
here you can see that it works not only about single vars (x + x = 2x) but also on
mults of vars and nums (2x + x + 3x = 6x).
large range type checking, evaluating and more is necessary .

-------math 2-------
5x - 4x -> x
before: (5 * x) - (4 * x)
after:  x
not only plus supports the cool simplification, but also minus.
again, checking types, evaluating and comparing are necessary actions.

-------math 3-------
(x + y) - (2 * x) -> x + y - 2x
before: (x + y) - (2 * x)
after:  x + y - 2x
the minus of the mult turns to be more simple
and with no redundant ()
need to check the types and evaluate

-------math 4-------
(2 * x) * (2 * y) -> 4xy
before: (2 * x) * (2 * y)
after:  4xy
here is an advanced example.
not only redundant () are taken off, but also the nums in the left
parts of the multiple turn to be a simplified multiple itself.
the vars are placed side by side with no * like it is in the real world.
hard work of checking types and comparing.

-------math 5-------
-(-1) -> 1
before: --1
after:  1
neg of neg is pos.
the check is that if inside a neg there is another neg, 
we can create a pos that consists the expression that inside inside the neg.

-------math 6-------
-(-(x)) -> x
before: -(-x)
after:  x
if you think that supports only neg of neg of a num that can be evaluated,
you are wrong.
neg of neg of a var is also supported as described.

-------math 7-------
x - (-y) -> x + y
before: x - (-y)
after:  x + y
minus of neg is a plus
complex work as described somewhere else.

-------math 8-------
x + (-y) || (-y) + x -> x - y
before: ((-x) + 4) + y
after:  4 - x + y
plus of a neg no importance if the neg is the right or the left
complex work as described somewhere else.

-------math 9-------
((-x) + 4) + y -> 4 - x + y
before: ((-x) + 4) + y
after:  4 - x + y
more advanced plus of neg example.
here it is nested.
complex work as described somewhere else.

-------math 10-------
(x / y) / (z / w) -> xw / yz
before: (x / y) / (z / w)
after:  (xw) / (yz)
simplily a fraction of fractions to be fraction of mults.
complex work as above.

-------math 11-------
(x + y) - (x + y) -> 0
before: (x + y) - (x + y)
after:  0
shows the ability to compare more complexed expressions.
minus of equal expressions is 1 of course.

-------math 12-------
(x + y) - (y + x) -> 0
before: (x + y) - (y + x)
after:  0
here the comparison is more complexed because the order of the vars is different.
using more advanced comparing tools that toString.equals() is necessary.
nested if solves it, or better trick of comparing plus and mults that don't give importance for order.

-------math 13-------
(x * y) - (x * y) -> 0
before: (x * y) - (x * y)
after:  0
like before, an example of comparing more complexed expressions of mult.
the ways to solve this are written above.

-------math 14-------
(x * y) - (y * x) -> 0
before: (x * y) - (y * x)
after:  0
here the example is more advanced because the change in the order.
the ways to solve this are written above.

-------math 15-------
(x + y) / (x + y) -> 1
before: (x + y) / (x + y)
after:  1
here it's an example of non trivial comparsion in a div.
the ways to solve this are written above.

-------math 16-------
(x + y) / (y + x) -> 1
before: (x + y) / (y + x)
after:  1
here it's advanced because the change in the order.
the ways to solve this are written above.

-------math 17-------
(x * y) / (x * y) -> 1
before: (x * y) / (x * y)
after:  1
here it's more example of non trivial comparing.
the ways to solve this are written above.

-------math 18-------
(x * y) / (y * x) -> 1
before: (x * y) / (y * x)
after:  1
addvanced beacause the change in the order.
the ways to solve this are written above.

-------math 19-------
((x + y) + (a + b)) / ((y + x) + (b + a)) -> 1
before: ((x + y) + (a + b)) / ((y + x) + (b + a))
after:  1
here it's much advanced beacuse the nested expressions.
the solution is more complex but the principles are like above.

-------math 20-------
((x * y) * (a * b)) / ((y * x) * (b * a)) -> 1
before: ((x * y) * (a * b)) / ((y * x) * (b * a))
after:  1
here it's much advanced beacuse the nested expressions.
the solution is more complex but the principles are like above.

-------math 21-------
log(x * y, x * y) -> 1
before: log((x * y), (x * y))
after:  1
here it's an example of no trivial comparison inside a log.
the ways to solve this are written above.

-------math 22-------
log(x * y, y * x) -> 1
before: log((x * y), (y * x))
after:  1
here it's more complexed because of the order.
the ways to solve this are written above.

-------math 23-------
log(((x * 9) + 2), (2 + (9 * x))) -> 1
before: log(((x * 9) + 2), (2 + (9 * x)))
after:  1
here it's much advanced beacuse the nested expressions.
the solution is more complex but the principles are like above.

-------math 24-------
x * x -> x^2
before: x * x
after:  x^2
mult by itself turns to be pow with exponent 2.
needs type checking and advanced comparing.

-------math 25-------
(x * y) * (y * x) -> (x^2)(y^2)
before: (x * y) * (y * x)
after:  (x^2)(y^2)
the mult is nested and the order is bad, but it simplified to separate
x^2 and y^2.
needs type checking and comparing.

-------math 26-------
4 + ((x^4)^4) -> 4 + x^16
before: 4 + ((x^4)^4)
after:  4 + x^16
turn pow of a pow to be a mult.
complex work of type checking, comparing, check if evaluate and evaluate.

-------math 27-------
(2 / x) / (x / 3) -> 6 / (x^2)
before: (2 / x) / (x / 3)
after:  6 / (x^2)
this is advanced example.
complex work with a fraction of a fraction, evaluating and comparing.

-------math 28-------
(2 * x) * (x * 3) -> 6(x^2)
before: (2 * x) * (x * 3)
after:  6(x^2)
turns un ordered expression with nums and vars to be much simplified.
the num is before and the var gets inside a pow.

-------math 29-------
log (x, y) + log (x, z) -> log(x, yz)
before: log(x, y) + log(x, z)
after:  log(x, yz)
log rules.
need type checking and comparing.

-------math 30-------
log(x, y) - log(x, z) -> log(x, y / z)
before: log(x, y) - log(x, z)
after:  log(x, y / z)
more cool log rules.
need type checking and comparing.

-------math 31-------
0^x -> 0
before: 0^x
after:  0
pow with a zero base is always 0
(except 0^0 that has a double meaning)
needs type comparing, evaluating, checking if zero etc.

-------math 32-------
(x + y)^1 -> x + y
before: (x + y)^1
after:  x + y
one as an exponent is the base itself.
works also with more complexed expressions as the base.
needs some type checking and comparing.

-------math 33-------
(x + y)^0 -> 1
before: (x + y)^0
after:  1
zero as an exponent gives 1 (some say that 0^0 also).
works also with more complexed expressions as the base.
needs some type checking and comparing.

-------math 34-------
x^2 * x^3 -> x^5
before: (x^2) * (x^3)
after:  x^5
pows rules.
cool simplification of mult of pows.
add the pows if the bases are equal.
needs some type checking, comparing, check if evaluable and evaluate.

-------math 35-------
x^2 * x -> x^3
before: (x^2) * x
after:  x^3
pows rules.
cool simplification of mult of pow and a var.
here the var isn't a pow expression, but we can treat it as pow with exponent 1.
add the pows if the bases are equal.
needs some type checking, comparing, check if evaluable and evaluate.

	thanks for watching :)

