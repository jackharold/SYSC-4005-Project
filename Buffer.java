import java.util.ArrayList;

public class Buffer {

    public enum BufferType {
        INSPECTOR_BUFFER,
        WORKSTATION_BUFFER
    }

    ArrayList<Component> components;
    BufferType type;

    public Buffer(BufferType type){
        this.type = type;
        components = new ArrayList<Component>();
    }

    public BufferType getType() {
        return type;
    }

    public Boolean pushComponent(Component newComponent){
        return components.add(newComponent);
    }

    public Component popComponent(){
        Component finalComponent = components.get(components.size()-1);
        components.remove(finalComponent);
        return finalComponent;
    }

    public Component peekComponent(){
        return components.get(components.size()-1);
    }

}