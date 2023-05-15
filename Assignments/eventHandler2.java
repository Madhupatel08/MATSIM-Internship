package org.matsim.Assignment3;

import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.core.api.experimental.events.EventsManager;

import java.beans.EventHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class eventHandler2 implements PersonArrivalEventHandler, PersonDepartureEventHandler {
    private HashMap<String, Double> startTimeMap = new HashMap<>();
    private Map<String, Double> endTimeMap = new HashMap<>();

    public void reset(int iteration) {
        startTimeMap.clear();
        endTimeMap.clear();
    }
    @Override
    public void handleEvent(PersonArrivalEvent personArrivalEvent) {
        String personId = personArrivalEvent.getPersonId().toString();
        String linkId = personArrivalEvent.getLinkId().toString();
        if(personId.equals("person_57")){
            startTimeMap.put(linkId, personArrivalEvent.getTime());
            System.out.println("Person Arrival Time: "+ personArrivalEvent.getTime());
            System.out.println("LinkId: " + personArrivalEvent.getLinkId());
            System.out.println("PersonId: " + personArrivalEvent.getPersonId());
            System.out.println("------------");
        }
//        String person = personArrivalEvent.getPersonId().toString().equals("person_57");
//        System.out.println("Arrival person = "+ person);
    }

    @Override
    public void handleEvent(PersonDepartureEvent personDepartureEvent) {
        String personId = personDepartureEvent.getPersonId().toString();
        String linkId = personDepartureEvent.getLinkId().toString();
        if(personId.equals("person_57")){
            endTimeMap.put(linkId, personDepartureEvent.getTime());
            System.out.println("Person departure time: "+ personDepartureEvent.getTime());
            System.out.println("LinkId: " + personDepartureEvent.getLinkId());
            System.out.println("PersonId: " + personDepartureEvent.getPersonId());
            System.out.println("------------");
        }
//        String person = personDepartureEvent.getPersonId().toString();
//        System.out.println("departure person = " + person);
    }

    public double getTravelTimeDuration(){

        double traveltimeDuration = 0.0;
        for(String linkId : startTimeMap.keySet()){
            System.out.println("Link Id is:  " + linkId + " StartTime is: " + startTimeMap.get(linkId) + " endTime is: " + endTimeMap.get(linkId));
            traveltimeDuration += Math.abs(startTimeMap.get(linkId) - endTimeMap.get(linkId));
        }
        return traveltimeDuration;
    }
}
