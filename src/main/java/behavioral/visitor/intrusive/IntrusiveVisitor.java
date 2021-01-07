package behavioral.visitor.intrusive;

abstract class Expression {

    // adding the extra behaviour breaks the Open-Closed Principle, as well as the Single Responsibility Principle.
    public abstract void print(StringBuilder stringBuilder);
}

class DoubleExpression extends Expression {

    private final double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    public void print(StringBuilder stringBuilder) {
        stringBuilder.append(value);
    }
}

class AdditionExpression extends Expression {

    private final Expression left;
    private final Expression right;

    public AdditionExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(StringBuilder stringBuilder) {
        stringBuilder.append("(");
        left.print(stringBuilder);
        stringBuilder.append("+");
        right.print(stringBuilder);
        stringBuilder.append(")");
    }
}

class IntrusiveVisitorDemo {

    public static void main(String[] args) {
        // 1+(2+3)
        AdditionExpression e = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2),
                        new DoubleExpression(3)
                ));

        StringBuilder stringBuilder = new StringBuilder();
        e.print(stringBuilder);
        System.out.println(stringBuilder);
    }
}