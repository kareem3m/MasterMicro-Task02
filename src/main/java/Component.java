public abstract class Component {
    private final String id;
    private final String type;

    Component(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    abstract public String[] getNetlist();

    @Override
    public String toString() {
        return id;
    }
}