
public class Component {

    public enum ComponentType {
        C1,
        C2,
        C3
    }

    ComponentType type;

    public Component(ComponentType type) {
        this.type = type;
    }

    public ComponentType getType() {
        return type;
    }

}