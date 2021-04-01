
public class Inspector {

    int inspectorID;
    Component repairedComponent = null;

    public Inspector(int inspectorID) {
        this.inspectorID = inspectorID;
    }

    /* Getters and Setters */

    public int getInspectorID() {
        return inspectorID;
    }

    public Component getRepairedComponent() {
        return repairedComponent;
    }

    public void setRepairedComponent(Component repairedComponent) {
        this.repairedComponent = repairedComponent;
    }

}