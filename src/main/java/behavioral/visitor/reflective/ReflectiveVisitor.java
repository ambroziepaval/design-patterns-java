package behavioral.visitor.reflective;

import lombok.AllArgsConstructor;

abstract class Expression {
}

@AllArgsConstructor
class DoubleExpression extends Expression {

    public final double value;
}

@AllArgsConstructor
class AdditionExpression extends Expression {

    public final Expression left;
    public final Expression right;
}

// separation of concerns
class ExpressionPrinter {

    public static void print(Expression e, StringBuilder sb) {

        if (e.getClass() == DoubleExpression.class) {
            sb.append(((DoubleExpression) e).value);

        } else if (e.getClass() == AdditionExpression.class) {
            AdditionExpression ae = (AdditionExpression) e;
            sb.append("(");
            print(ae.left, sb);
            sb.append("+");
            print(ae.right, sb);
            sb.append(")");
        }
    }
}

class ReflectiveVisitorDemo {

    public static void main(String[] args) {
        // 1+(2+3)
        AdditionExpression e = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2),
                        new DoubleExpression(3)
                ));

        StringBuilder stringBuilder = new StringBuilder();
        ExpressionPrinter.print(e, stringBuilder);
        System.out.println(stringBuilder);

    }
}
