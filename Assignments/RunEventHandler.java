package org.matsim.Assignment3;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.scenario.ScenarioUtils;

import java.beans.EventHandler;
import java.lang.reflect.Array;
import java.util.Arrays;

public class RunEventHandler {

    public static void main(String[] args){
        // give the path of config file. path to events file. for this you first need to run a simulation
//        Config config = ConfigUtils.loadConfig("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/config.xml");
//        config.controler().setLastIteration(5);
//        Scenario scenario = ScenarioUtils.loadScenario(config);
//       Controler controler = new Controler(scenario);
//       controler.run();
        String inputfile = "/home/madhu/Desktop/IIT Roorkee/matsim-example-project/output/madhu/output_events.xml.gz"; // events file

        //create an events object
        EventsManager events = EventsUtils.createEventsManager();

        //create the handler and add it

//        eventHandler handler1= new eventHandler();
//
//        events.addHandler(handler1);

        eventHandler2 handler2 = new eventHandler2();
        events.addHandler(handler2);

        //craete the reader and read the file
        events.initProcessing();
        MatsimEventsReader reader = new MatsimEventsReader(events);
        reader.readFile(inputfile);
        events.finishProcessing();

//        System.out.println("Enter Event count is: " + handler1.getEnterEventCount());
//
//        System.out.println("Leave Event count is: " + handler1.getLeaveEventcount());
//
//        System.out.println("Arrival Event count is: " + handler1.getArrivalEventCount());
//
//        System.out.println("Departure Event count is: " + handler1.getDepartureEventCount());

        System.out.println("Travel Time of Person_57: " + handler2.getTravelTimeDuration());
    }
}
