package creational.builder;

public class HtmlBuilder {

    private String rootName;
    private HtmlElement root = new HtmlElement();

    public HtmlBuilder(String rootName) {
        this.rootName = rootName;
        this.root.name = rootName;
    }

    public HtmlBuilder addChild(String childName, String childText) {
        HtmlElement htmlElement = new HtmlElement(childName, childText);
        this.root.elements.add(htmlElement);
        return this;
    }

    public void clear() {
        this.root = new HtmlElement();
        this.root.name = rootName;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
