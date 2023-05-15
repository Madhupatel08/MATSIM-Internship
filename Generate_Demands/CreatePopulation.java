package org.matsim.madhu;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.api.internal.MatsimWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class CreatePopulation {

    public static void main(String[] args) {

        Config config = ConfigUtils.createConfig();
        Scenario scenario = ScenarioUtils.createScenario(config);
        Population population =  scenario.getPopulation();
        PopulationFactory populationFactory = population.getFactory();

        //Create population and plans
		//Create 500 People designated by the number of iteration.
        //Create same plan for all people.

        //Complete the for loop

        for (int i=1;i<=500;i++){

            //Create person
            String personId = "person_"+i;
            Person person = populationFactory.createPerson(Id.createPersonId(personId));


            //Create plans.

            Plan plan = populationFactory.createPlan();
            //<-------------------------Add home activity---------------------------------------------->
            //Create and add"home" activity for the i^th Person.
            Coord homeCoordinate = new Coord(0, 0);
            Activity home = populationFactory.createActivityFromCoord("home",homeCoordinate);

            //set the start and end time of activity
            home.setEndTime(6*60*60);

            //add activity to person's plan
            plan.addActivity(home);

            //Add leg to the person's plan
            Leg leg = populationFactory.createLeg("car");
            plan.addLeg(leg);

            //<-------------------------Add work activity---------------------------------------------->
            //Create and add "work"
            Coord workCoordinate = new Coord(6000, 0);
            Activity work = populationFactory.createActivityFromCoord("work",workCoordinate);

            //set the start and end time of activity

            work.setEndTime(16*60*60);

            //add activity to person's plan
            plan.addActivity(work);

            //Add leg to the person's plan
            plan.addLeg(leg);

            //<---------------------------Add another home activity-------------------------------------------->
//            populationFactory.createActivityFromCoord("home", homeCoordinate);
            plan.addActivity(home);

            //<--------------------------------------------------------------------------------------------------->
            //Add plan to person
            person.addPlan(plan);
            //Add person to population
            population.addPerson(person);
        }


		// Write the population to a file.
        PopulationWriter populationWriter = new PopulationWriter(population);
        populationWriter.write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/populationAssign.xml");

    }

}
