
public class Event {

    public enum EventType {
        COMPONENT_RERPAIRED,
        PRODUCT_COMPLETE
    }

    EventType type;
    int eventID;
    Component eventComponent;
    Inspector eventInspector = null;
    WorkStation eventWorkStation = null;

    public Event(EventType type, int eventID, Component eventComponent, Inspector eventInspector, WorkStation eventWorkStation) {
        this.type = type;
        this.eventID = eventID;
        this.eventComponent =  eventComponent;
        this.eventInspector =  eventInspector;
        this.eventWorkStation =  eventWorkStation;
    }


    /* Getters and Setters */

    public EventType getType() {
        return type;
    }

    public int getEventID() {
        return eventID;
    }

    public Component getEventComponent() {
        return eventComponent;
    }

    public Inspector getEventInspector() {
        return eventInspector;
    }

    public WorkStation getEventWorkStation() {
        return eventWorkStation;
    }

}