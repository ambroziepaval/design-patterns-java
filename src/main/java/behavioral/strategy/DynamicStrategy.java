package behavioral.strategy;

import java.util.Arrays;
import java.util.List;

enum OutputFormat {
    MARKDOWN, HTML
}

interface ListStrategy {
    default void start(StringBuilder stringBuilder) {
    }

    void addListItem(StringBuilder stringBuilder, String item);

    default void end(StringBuilder stringBuilder) {
    }
}

class MarkdownListStrategy implements ListStrategy {

    @Override
    public void addListItem(StringBuilder stringBuilder, String item) {
        stringBuilder.append("* ").append(item).append(System.lineSeparator());
    }
}

class HtmlListStrategy implements ListStrategy {

    @Override
    public void start(StringBuilder stringBuilder) {
        stringBuilder.append("<ul>").append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder stringBuilder, String item) {
        stringBuilder.append("\t<li>")
                .append(item)
                .append("</li>")
                .append(System.lineSeparator());
    }

    @Override
    public void end(StringBuilder stringBuilder) {
        stringBuilder.append("</ul>").append(System.lineSeparator());
    }
}

class TextProcessor {

    private StringBuilder stringBuilder = new StringBuilder();
    private ListStrategy listStrategy;

    public TextProcessor(OutputFormat format) {
        setOutputFormat(format);
    }

    public void setOutputFormat(OutputFormat format) {
        switch (format) {
            case MARKDOWN:
                listStrategy = new MarkdownListStrategy();
                break;
            case HTML:
                listStrategy = new HtmlListStrategy();
                break;
        }
    }

    public void appendList(List<String> items) {
        listStrategy.start(stringBuilder);
        for (String item : items) {
            listStrategy.addListItem(stringBuilder, item);
        }
        listStrategy.end(stringBuilder);
    }

    public void clear() {
        stringBuilder.setLength(0);
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}

class DynamicStrategyDemo {

    public static void main(String[] args) {
        final TextProcessor textProcessor = new TextProcessor(OutputFormat.MARKDOWN);
        textProcessor.appendList(Arrays.asList("liberte", "egalite", "fraternite"));
        System.out.println(textProcessor);

        textProcessor.clear();
        textProcessor.setOutputFormat(OutputFormat.HTML);
        textProcessor.appendList(Arrays.asList("inheritance", "encapsulation", "polymorphism"));
        System.out.println(textProcessor);
    }
}
