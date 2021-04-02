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

    public Event getNewestEvent(){
        float lowestTime = 1000000;
        int newestEventId = -1;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventTime() < lowestTime){
                lowestTime = events.get(i).getEventTime();
                newestEventId = i;
            }
        }

        if (newestEventId != -1) {
            Event newestEvent = events.get(newestEventId);
            this.removeEvent(events.get(newestEventId));
            return newestEvent;
        }
        return null;
    }

    


}