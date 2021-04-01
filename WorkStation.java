import java.util.ArrayList;
import java.util.Arrays;

public class WorkStation {

    int WorkStationID;
    Component.ComponentType[] validComponentTypes;
    ArrayList<Component> storedComponents;

    public WorkStation(int WorkStationID, Component.ComponentType[] validComponentTypes) {
        this.WorkStationID = WorkStationID;
        this.validComponentTypes = validComponentTypes;

        storedComponents = new ArrayList<Component>();
    }

    /* Getters and Setters */
    
    public int getWorkStationID() {
        return WorkStationID;
    }

    public Boolean isComponentValid(Component testingComponent) {
        return Arrays.stream(validComponentTypes).anyMatch(testingComponent.getType()::equals);
    }

    public Boolean isComponentPresent(Component testingComponent){
        return storedComponents.contains(testingComponent);
    }

    public void addComponent(Component addedComponent){
        storedComponents.add(addedComponent);
    }

    public void clearComponents(){
        storedComponents.clear();
    }
    
}