package behavioral.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Element {
    int eval();
}

class Integer implements Element {

    private int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOperation implements Element {

    public Type type;
    public Element left, right;

    @Override
    public int eval() {
        switch (type) {
            case ADDITION:
                return left.eval() + right.eval();
            case SUBTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;
        }
    }

    public enum Type {
        ADDITION,
        SUBTRACTION
    }
}

class Token {
    public Type type;
    public String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "`" + text + "`";
    }

    public enum Type {
        INTEGER,
        PLUS,
        MINUS,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS,
    }
}

class ParsingDemo {

    static List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '+':
                    tokens.add(new Token(Token.Type.PLUS, "+"));
                    break;
                case '-':
                    tokens.add(new Token(Token.Type.MINUS, "-"));
                    break;
                case '(':
                    tokens.add(new Token(Token.Type.LEFT_PARENTHESIS, "("));
                    break;
                case ')':
                    tokens.add(new Token(Token.Type.RIGHT_PARENTHESIS, ")"));
                    break;
                default:
                    final StringBuilder stringBuilder = new StringBuilder("" + input.charAt(i));
                    for (int j = i + 1; j < input.length(); j++) {
                        if (Character.isDigit(input.charAt(j))) {
                            stringBuilder.append(input.charAt(j));
                            i++;
                        } else {
                            tokens.add(new Token(Token.Type.INTEGER, stringBuilder.toString()));
                            break;
                        }
                    }
            }
        }

        return tokens;
    }

    static Element parse(List<Token> tokens) {
        final BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false;

        for (int i = 0; i < tokens.size(); i++) {
            final Token token = tokens.get(i);

            switch (token.type) {
                case INTEGER:
                    final Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
                    if (!haveLHS) {
                        result.left = integer;
                        haveLHS = true;
                    } else {
                        result.right = integer;
                    }
                    break;
                case PLUS:
                    result.type = BinaryOperation.Type.ADDITION;
                    break;
                case MINUS:
                    result.type = BinaryOperation.Type.SUBTRACTION;
                    break;
                case LEFT_PARENTHESIS:
                    int j = i; // location of right parenthesis
                    for (; j < tokens.size(); ++j) {
                        if (tokens.get(j).type == Token.Type.RIGHT_PARENTHESIS)
                            break;
                    }
                    final List<Token> subexpression = tokens.stream()
                            .skip((long) i + 1) // skip all the tokens from the start including the parenthesis
                            .limit((long) j - i - 1) // limit to the number of tokens inside the expression
                            .collect(Collectors.toList());
                    final Element element = parse(subexpression);
                    if (!haveLHS) {
                        result.left = element;
                        haveLHS = true;
                    } else {
                        result.right = element;
                    }
                    i = j;
                    break;
                case RIGHT_PARENTHESIS:
                    break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        final String input = "(13+4)-(12+1)";
        List<Token> tokens = lex(input);

        System.out.println(tokens.stream().map(Token::toString).collect(Collectors.joining("\t")));

        final Element parsed = parse(tokens);
        System.out.println(input + " = " + parsed.eval());
    }
}