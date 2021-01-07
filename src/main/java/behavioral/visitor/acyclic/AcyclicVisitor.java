package behavioral.visitor.acyclic;

import lombok.AllArgsConstructor;

interface Visitor { // marker interface
}

interface ExpressionVisitor extends Visitor {
    void visit(Expression e);
}

interface DoubleExpressionVisitor extends Visitor {
    void visit(DoubleExpression e);
}

interface AdditionExpressionVisitor extends Visitor {
    void visit(AdditionExpression e);
}

abstract class Expression {

    public void accept(Visitor visitor) {
        if (visitor instanceof ExpressionVisitor) {
            ((ExpressionVisitor) visitor).visit(this);
        }
    }
}

@AllArgsConstructor
class DoubleExpression extends Expression {

    public final double value;

    @Override
    public void accept(Visitor visitor) {
        if (visitor instanceof DoubleExpressionVisitor) {
            ((DoubleExpressionVisitor) visitor).visit(this);
        }
    }
}

@AllArgsConstructor
class AdditionExpression extends Expression {

    public final Expression left;
    public final Expression right;

    @Override
    public void accept(Visitor visitor) {
        if (visitor instanceof AdditionExpressionVisitor) {
            ((AdditionExpressionVisitor) visitor).visit(this);
        }
    }
}

// separation of concerns
class ExpressionPrinter implements DoubleExpressionVisitor, AdditionExpressionVisitor {
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

class AcyclicVisitorDemo {

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
    }
}
