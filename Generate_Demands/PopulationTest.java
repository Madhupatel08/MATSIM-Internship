package org.matsim.madhu;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordUtils;

public class PopulationTest {
    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();
        Scenario scenario = ScenarioUtils.loadScenario(config);
        Population population = scenario.getPopulation();
        PopulationFactory populationFactory = population.getFactory();


        Person person1 = populationFactory.createPerson(Id.createPersonId("person1"));
        Plan plan = populationFactory.createPlan();
        Coord homeCoordinate = new Coord(0, 0);
        Activity home = populationFactory.createActivityFromCoord("home", homeCoordinate);

        home.setEndTime(6*60*60);
        plan.addActivity(home);
        Leg car = populationFactory.createLeg("car");
        car.setMode("car");
        plan.addLeg(car);

        Coord workCoordinate = new Coord(1000, 0);
        Activity work = populationFactory.createActivityFromCoord("work", workCoordinate);
        work.setEndTime(16*60*60);
        plan.addActivity(work);
        plan.addLeg(car);


        person1.addPlan(plan);

        population.addPerson(person1);

        PopulationWriter populationWriter = new PopulationWriter(population);
        populationWriter.write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/population.xml");

    }
}
