import java.util.ArrayList;

public class WorkStation {

    int WorkStationID;
    Component.ComponentType[] validComponents;
    ArrayList<Component> storedComponents;

    public WorkStation(int WorkStationID, Component.ComponentType[] validComponents) {
        this.WorkStationID = WorkStationID;
        this.validComponents = validComponents;

        storedComponents = new ArrayList<Component>();
    }

    /* Getters and Setters */
    
    public int getWorkStationID() {
        return WorkStationID;
    }

    public Component.ComponentType[] getValidComponents() {
        return validComponents;
    }

    // WIP
    
}