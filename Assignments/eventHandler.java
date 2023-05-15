package org.matsim.Assignment3;

import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;

public class eventHandler implements LinkEnterEventHandler,
        LinkLeaveEventHandler, PersonArrivalEventHandler, PersonDepartureEventHandler{

    private int enterEventCount = 0;
    private int leaveEventcount = 0;

    private int arrivalEventCount = 0;

    private int departureEventCount = 0;

    @Override
    public void handleEvent(LinkEnterEvent linkEnterEvent) {
        System.out.println("LinkEnterEvent");
        System.out.println("Time: " + linkEnterEvent.getTime());
        System.out.println("LinkId: " + linkEnterEvent.getLinkId());
        enterEventCount++;
    }
    public int getEnterEventCount(){
        return enterEventCount;
    }
    @Override
    public void handleEvent(LinkLeaveEvent linkLeaveEvent) {
        System.out.println("LinkEnterEvent");
        System.out.println("Time: " + linkLeaveEvent.getTime());
        System.out.println("LinkId: " + linkLeaveEvent.getLinkId());
        leaveEventcount++;
    }
    public int getLeaveEventcount(){
        return leaveEventcount;
    }
    @Override
    public void handleEvent(PersonArrivalEvent personArrivalEvent) {
        System.out.println("AgentArrivalTime");
        System.out.println("Time: " + personArrivalEvent.getTime());
        System.out.println("LinkId: " + personArrivalEvent.getLinkId());
        System.out.println("PersonId: " + personArrivalEvent.getPersonId());
        arrivalEventCount++;
    }
    public int getArrivalEventCount(){
        return arrivalEventCount;
    }
    @Override
    public void handleEvent(PersonDepartureEvent personDepartureEvent) {
        System.out.println("AgentDepartureEvent");
        System.out.println("Time: " + personDepartureEvent.getTime());
        System.out.println("LinkId: " + personDepartureEvent.getLinkId());
        System.out.println("PersonId: " + personDepartureEvent.getPersonId());
        departureEventCount++;

    }
    public int getDepartureEventCount(){
        return departureEventCount;
    }
}
