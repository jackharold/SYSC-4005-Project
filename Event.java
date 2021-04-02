
public class Event {

    public enum EventType {
        COMPONENT_RERPAIRED,
        PRODUCT_COMPLETE
    }

    EventType type;
    int eventID;
    float eventTime;
    Component eventComponent;
    Inspector eventInspector = null;
    WorkStation eventWorkStation = null;

    public Event(EventType type, int eventID, float eventTime, Component eventComponent, Inspector eventInspector) {
        this.type = type;
        this.eventID = eventID;
        this.eventTime = eventTime;
        this.eventComponent =  eventComponent;
        this.eventInspector =  eventInspector;
        this.eventWorkStation =  null;
    }

    public Event(EventType type, int eventID, float eventTime, Component eventComponent, WorkStation eventWorkStation) {
        this.type = type;
        this.eventID = eventID;
        this.eventTime = eventTime;
        this.eventComponent =  eventComponent;
        this.eventInspector =  null;
        this.eventWorkStation =  eventWorkStation;
    }


    /* Getters and Setters */

    public EventType getType() {
        return type;
    }

    public int getEventID() {
        return eventID;
    }

    public float getEventTime() {
        return eventTime;
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