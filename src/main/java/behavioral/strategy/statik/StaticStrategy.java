package behavioral.strategy.statik;


import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

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

class TextProcessor<LS extends ListStrategy> {

    private final StringBuilder stringBuilder = new StringBuilder();
    // cannot do this
    // private LS listStrategy = new LS();
    private final ListStrategy listStrategy;

    public TextProcessor(Supplier<? extends LS> ctor) {
        listStrategy = ctor.get();
    }

    public void appendList(List<String> items) {
        listStrategy.start(stringBuilder);
        for (String item : items) {
            listStrategy.addListItem(stringBuilder, item);
        }
        listStrategy.end(stringBuilder);
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}

class StaticStrategyDemo {

    public static void main(String[] args) {
        TextProcessor<MarkdownListStrategy> textProcessor = new TextProcessor<>(MarkdownListStrategy::new);
        textProcessor.appendList(Arrays.asList("liberte", "egalite", "fraternite"));
        System.out.println(textProcessor);


        TextProcessor<HtmlListStrategy> textProcessor2 = new TextProcessor<>(HtmlListStrategy::new);
        textProcessor2.appendList(Arrays.asList("inheritance", "encapsulation", "polymorphism"));
        System.out.println(textProcessor2);
    }
}
