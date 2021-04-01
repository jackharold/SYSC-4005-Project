import java.util.ArrayList;

public class EventList {

    ArrayList<Event> events;

    public EventList(){
        events = new ArrayList<Event>();
    }

    public Boolean addEvent(Event newEvent){
        return events.add(newEvent);
    }

    public Boolean removeEvent(Event removedEvent){
        return events.remove(removedEvent);
    }


}