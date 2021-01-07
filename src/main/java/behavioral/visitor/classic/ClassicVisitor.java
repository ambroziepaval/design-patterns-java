package behavioral.visitor.classic;

import lombok.AllArgsConstructor;

// Classic Visitor (Double Dispatch)

interface ExpressionVisitor {
    void visit(DoubleExpression e);

    void visit(AdditionExpression e);
}

abstract class Expression {
    public abstract void accept(ExpressionVisitor visitor);
}

@AllArgsConstructor
class DoubleExpression extends Expression {

    public final double value;

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

@AllArgsConstructor
class AdditionExpression extends Expression {

    public final Expression left;
    public final Expression right;

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

// separation of concerns
class ExpressionPrinter implements ExpressionVisitor {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpression e) {
        sb.append(e.value);
    }

    @Override
    public void visit(AdditionExpression e) {
        sb.append("(");
        e.left.accept(this);
        sb.append("+");
        e.right.accept(this);
        sb.append(")");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class ExpressionCalculator implements ExpressionVisitor {
    public double result;

    @Override
    public void visit(DoubleExpression e) {
        result = e.value;
    }

    @Override
    public void visit(AdditionExpression e) {
        e.left.accept(this);
        double a = result;
        e.right.accept(this);
        double b = result;
        result = a + b;

    }
}

class ClassicVisitorDemo {

    public static void main(String[] args) {
        // 1+(2+3)
        AdditionExpression e = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2),
                        new DoubleExpression(3)
                ));
        ExpressionPrinter ep = new ExpressionPrinter();
        ep.visit(e);
        System.out.println(ep);

        ExpressionCalculator calc = new ExpressionCalculator();
        calc.visit(e);
        System.out.println(ep + " = " + calc.result);
    }
}
