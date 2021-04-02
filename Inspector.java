
public class Inspector {

    int inspectorID;
    Boolean blocked;
    Component repairedComponent = null;

    public Inspector(int inspectorID) {
        this.inspectorID = inspectorID;
        this.blocked = false;
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

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

}