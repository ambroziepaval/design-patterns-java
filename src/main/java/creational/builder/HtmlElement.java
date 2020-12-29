package creational.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlElement {

    private static final int INDENT_SIZE = 2;
    private final String newLine = System.lineSeparator();

    public String name;
    public String text;
    public List<HtmlElement> elements = new ArrayList<>();

    public HtmlElement() {
    }

    public HtmlElement(String name, String text) {
        this.name = name;
        this.text = text;
    }

    private String toStringImpl(int indent) {
        StringBuilder stringBuilder = new StringBuilder();
        String i = String.join("", Collections.nCopies(INDENT_SIZE * indent, " "));
        stringBuilder.append(String.format("%s<%s>%s", i, name, newLine));

        if (text != null && !text.isEmpty()) {
            stringBuilder.append(String.join("", Collections.nCopies(INDENT_SIZE * (indent + 1), " ")))
                    .append(text)
                    .append(newLine);
        }

        for (HtmlElement element : elements) {
            stringBuilder.append(element.toStringImpl(indent + 1));
        }

        stringBuilder.append(String.format("%s</%s>%s", i, name, newLine));
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return toStringImpl(0);
    }
}
