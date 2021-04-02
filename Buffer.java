import java.util.ArrayList;

public class Buffer {

    public enum BufferType {
        COMPONENT_1_BUFFER,
        COMPONENT_2_BUFFER,
        COMPONENT_3_BUFFER
    }

    final int BUFFER_SIZE = 2;

    ArrayList<Component> components;
    Component.ComponentType type;
    WorkStation workStation;
    Inspector inspector;

    public Buffer(Component.ComponentType type, WorkStation workStation, Inspector inspector){
        this.type = type;
        this.workStation = workStation;
        this.inspector = inspector;
        components = new ArrayList<Component>();
    }

    public Component.ComponentType getType() {
        return type;
    }

    public Inspector getInspector() {
        return inspector;
    }

    public WorkStation getWorkStation() {
        return workStation;
    }

    public Boolean pushComponent(Component newComponent){
        if (components.size() >= BUFFER_SIZE) return false;
        components.add(0, newComponent);
        return true;
    }

    public Component popComponent(){
        Component finalComponent = components.get(components.size()-1);
        components.remove(finalComponent);
        return finalComponent;
    }

    public Component peekComponent(){
        return components.get(components.size()-1);
    }

    public Boolean isFull(){
        return components.size() >= BUFFER_SIZE;
    }

    public Boolean isEmpty(){
        return components.isEmpty();
    }

}