import java.util.Random;
public class ModelController {

    float currentEventTime;
    float previousEventTime;
    float[] currentTimeBlocked;
    int[] completedProducts;

    int newEventID = 0;

    EventList eventlist;
    DelayGenerator delayGenerator;
    Random randomGenerator;
    Buffer[] buffers = new Buffer[6];
    Inspector inspector1;
    Inspector inspector2;


    public static void main(String[] args) {
        int cycles = 100;
        float[] averages = new float[11];
        ModelController modelController = new ModelController();
        
        for (int i=0; i<cycles; i++){
            float[] test = modelController.runSimulator(1000000);
            for (int j=0; j<11; j++){
             averages[j] += test[j]/100;
            }
        }
        System.out.println("Averages: ");
        //for (int j=0; j<11; j++){
        //    System.out.print(averages[j]/cycles + " ");
        //}

        System.out.println("----------------");
        System.out.println("Final Time: " + averages[0]);
        System.out.println("Blocked Time Intervals: " + averages[1] + ", " + averages[2]);
        System.out.println("Blocked Time Intervals per unit of time: " + averages[3] + ", " + averages[4]);
        System.out.println("Number of Completed Products: " + averages[5]  + ", " + averages[6] + ", " + averages[7]);
        System.out.println("Number of Completed Products per unit of time: " + averages[8]  + ", " + averages[9] + ", " + averages[10]);
        System.out.println("----------------");
    }

    public float[] runSimulator(float endTime){
        float[] endValues = new float[11];

        eventlist = new EventList();
        delayGenerator = new DelayGenerator();
        randomGenerator = new Random();

        inspector1 = new Inspector(1);
        inspector2 = new Inspector(2);

        Component.ComponentType[] validTypes1 = {Component.ComponentType.C1};
        WorkStation workStation1 = new WorkStation(1, validTypes1);

        Component.ComponentType[] validTypes2 = {Component.ComponentType.C1, Component.ComponentType.C2};
        WorkStation workStation2 = new WorkStation(2, validTypes2);

        Component.ComponentType[] validTypes3 = {Component.ComponentType.C1, Component.ComponentType.C3};
        WorkStation workStation3 = new WorkStation(3, validTypes3);


        buffers = new Buffer[5];
        buffers[0] = new Buffer(Component.ComponentType.C1, workStation1, inspector1);
        buffers[1] = new Buffer(Component.ComponentType.C1, workStation2, inspector1);
        buffers[2] = new Buffer(Component.ComponentType.C2, workStation2, inspector2);
        buffers[3] = new Buffer(Component.ComponentType.C1, workStation3, inspector1);
        buffers[4] = new Buffer(Component.ComponentType.C3, workStation3, inspector2);



        previousEventTime = 0;
        currentEventTime = 0;
        currentTimeBlocked = new float[2];
        completedProducts = new int[3];

        this.newInspectedComponent(inspector1);
        this.newInspectedComponent(inspector2);
        

        while (currentEventTime <= endTime && this.parseNextEvent()) {}

        System.out.println("----------------");
        System.out.println("Final Time: " + currentEventTime);
        System.out.println("Blocked Time Intervals: " + currentTimeBlocked[0] + ", " + currentTimeBlocked[1]);
        System.out.println("Blocked Time Intervals per unit of time: " + currentTimeBlocked[0]/currentEventTime + ", " + currentTimeBlocked[1]/currentEventTime);
        System.out.println("Number of Completed Products: " + completedProducts[0]  + ", " + completedProducts[1] + ", " + completedProducts[2]);
        System.out.println("Number of Completed Products per unit of time: " + completedProducts[0]/currentEventTime  + ", " + completedProducts[1]/currentEventTime + ", " + completedProducts[2]/currentEventTime);
        System.out.println("----------------");

        endValues[0] = currentEventTime;
        endValues[1] = currentTimeBlocked[0];
        endValues[2] = currentTimeBlocked[1];
        endValues[3] = currentTimeBlocked[0]/currentEventTime;
        endValues[4] = currentTimeBlocked[1]/currentEventTime;
        endValues[5] = completedProducts[0];
        endValues[6] = completedProducts[1];
        endValues[7] = completedProducts[2];
        endValues[8] = completedProducts[0]/currentEventTime;
        endValues[9] = completedProducts[1]/currentEventTime;
        endValues[10] = completedProducts[2]/currentEventTime;

        return endValues;
    }

    private Boolean parseNextEvent(){
        Event newestEvent = eventlist.getNewestEvent();
        if (newestEvent == null) return false;

        previousEventTime = currentEventTime;
        currentEventTime = newestEvent.getEventTime(); 

        switch(newestEvent.getType()){
            case COMPONENT_RERPAIRED:
                this.repairedComponent(newestEvent);
                break;
            case PRODUCT_COMPLETE:
                this.completedProduct(newestEvent);
                break;
        }

        return true;
    }



    private void repairedComponent(Event newestEvent){
        //System.out.println("Repaired Component: " + newestEvent.getEventComponent().getType());

        Boolean addedToBuffer = false;

        for (int i=(buffers.length-1); i >= 0 ; i--) {
            if ((newestEvent.getEventComponent().getType() == buffers[i].getType()) && (!buffers[i].isFull())){
                
                buffers[i].pushComponent(newestEvent.getEventComponent());

                addedToBuffer = true;
                break;
            }
        }

        // Push Forward All Newly Available Components
        for (int i=0; i < buffers.length; i++) {
            if (!buffers[i].isEmpty() && this.addToWorkStation(buffers[i].peekComponent(), buffers[i].getWorkStation())) {
                buffers[i].popComponent();
            }
        }
        
        Inspector temporaryInspector = newestEvent.getEventInspector();
        
        if (addedToBuffer) {
            this.newInspectedComponent(temporaryInspector);

        } else {
            temporaryInspector.setBlocked(true);
            currentTimeBlocked[temporaryInspector.getInspectorID()-1] += (currentEventTime-previousEventTime);
        }


        if ( (temporaryInspector.getInspectorID() == 1 && inspector2.getBlocked()) ||  (temporaryInspector.getInspectorID() == 2 && inspector1.getBlocked())) {
            currentTimeBlocked[temporaryInspector.getInspectorID()%2] += (currentEventTime-previousEventTime);
        }


    }

    private void newInspectedComponent(Inspector temporaryInspector){

        Component temporaryComponent;
        Event temporaryEvent;

        temporaryInspector.setBlocked(false);

        if (temporaryInspector.getInspectorID() == 1) {
            temporaryComponent = new Component(Component.ComponentType.C1);
            temporaryInspector.setRepairedComponent(temporaryComponent);

            temporaryEvent = new Event(Event.EventType.COMPONENT_RERPAIRED, (newEventID++)%1000, (float) (currentEventTime + delayGenerator.getNewDelay(DelayGenerator.DelayType.COMPONENT_1)), temporaryComponent, temporaryInspector);
            eventlist.addEvent(temporaryEvent);

        } else if (temporaryInspector.getInspectorID() == 2) {
            if (randomGenerator.nextDouble() > 0.5){
                temporaryComponent = new Component(Component.ComponentType.C2);
                temporaryInspector.setRepairedComponent(temporaryComponent);

                temporaryEvent = new Event(Event.EventType.COMPONENT_RERPAIRED, (newEventID++)%1000, (float) (currentEventTime + delayGenerator.getNewDelay(DelayGenerator.DelayType.COMPONENT_2)), temporaryComponent, temporaryInspector);
                eventlist.addEvent(temporaryEvent);

            } else {
                temporaryComponent = new Component(Component.ComponentType.C3);
                temporaryInspector.setRepairedComponent(temporaryComponent);

                temporaryEvent = new Event(Event.EventType.COMPONENT_RERPAIRED, (newEventID++)%1000, (float) (currentEventTime + delayGenerator.getNewDelay(DelayGenerator.DelayType.COMPONENT_3)), temporaryComponent, temporaryInspector);
                eventlist.addEvent(temporaryEvent);
                
            }
        }
    }


    private void completedProduct(Event newestEvent){

        //System.out.println("Completed Product: " + newestEvent.getEventWorkStation().getWorkStationID());

        completedProducts[newestEvent.getEventWorkStation().getWorkStationID()-1]++;
    
        WorkStation completedWorkStation = newestEvent.getEventWorkStation();
        completedWorkStation.clearComponents();
        
        // Push Forward All Newly Available Components
        for (int i=0; i < buffers.length; i++) {
            if (!buffers[i].isEmpty() && this.addToWorkStation(buffers[i].peekComponent(), buffers[i].getWorkStation())) {
                buffers[i].popComponent();
            }

            Inspector bufferInspector = buffers[i].getInspector();
            if (bufferInspector.getBlocked()) {
                buffers[i].pushComponent(bufferInspector.getRepairedComponent());
                this.newInspectedComponent(bufferInspector);
            }
        }

        if (inspector1.getBlocked()) currentTimeBlocked[0] += (currentEventTime-previousEventTime);
        if (inspector2.getBlocked()) currentTimeBlocked[1] += (currentEventTime-previousEventTime);
    
    }

    private Boolean addToWorkStation(Component newComponent, WorkStation completingWorkstation){

        if (completingWorkstation.isComponentPresent(newComponent)) return false;


        completingWorkstation.addComponent(newComponent);

        
        if (completingWorkstation.areComponentsPresent()) {

            DelayGenerator.DelayType workstationDelay = null;

            switch(completingWorkstation.getWorkStationID()){
                case 1:
                    workstationDelay = DelayGenerator.DelayType.WORKSTATION_1;
                    break;
                case 2:
                    workstationDelay = DelayGenerator.DelayType.WORKSTATION_2;
                    break;
                case 3:
                    workstationDelay = DelayGenerator.DelayType.WORKSTATION_3;
                    break;
            }
            Event temporaryEvent = new Event(Event.EventType.PRODUCT_COMPLETE, (newEventID++)%1000, (float) (currentEventTime + delayGenerator.getNewDelay(workstationDelay)), null, completingWorkstation);
            eventlist.addEvent(temporaryEvent);
        }
        return true;
    }


}