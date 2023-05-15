package org.matsim.delhi;

import org.locationtech.jts.geom.Point;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.GeometryUtils;
import org.matsim.core.utils.geometry.geotools.MGC;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.gis.ShapeFileReader;
import org.opengis.feature.simple.SimpleFeature;

import java.awt.*;
import java.time.Period;
import java.util.List;
import java.util.Random;

public class selfDemand {
    static Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
    static Population population =  scenario.getPopulation();
    static PopulationFactory populationFactory = population.getFactory();

    public static void main(String[] args) {
        CoordinateTransformation crs = TransformationFactory.getCoordinateTransformation("WGS84", "EPSG:7760");
        List<SimpleFeature> features = (List<SimpleFeature>) ShapeFileReader.getAllFeatures("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/Delhi_Wards.shp");

        System.out.println(features);
        int i = 0;
        for(SimpleFeature originFeatures : features){

            for(SimpleFeature destinationFeatures : features){
                Point randomOriginPoint = GeometryUtils.getRandomPointInFeature(new Random(), originFeatures);
                Point randomDestination = GeometryUtils.getRandomPointInFeature(new Random(), destinationFeatures);

                Coord origin = crs.transform(MGC.point2Coord(randomOriginPoint));
                Coord destination = crs.transform(MGC.point2Coord(randomDestination));


                Person person = getPerson(origin, destination, i);
                population.addPerson(person);
                i++;
            }
        }

        PopulationWriter pw = new PopulationWriter(scenario.getPopulation(), scenario.getNetwork());
        pw.write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/plansSelf.xml");
    }

    private static Person getPerson(Coord originPoint, Coord destinationPoint, int i) {
        Person person = populationFactory.createPerson(Id.createPersonId(scenario.getPopulation().getPersons().size()));
        Plan plan = populationFactory.createPlan();
        Activity home = populationFactory.createActivityFromCoord("home", originPoint);
        home.setEndTime(9*3600);
        plan.addActivity(home);

        if( i%2 == 0 && i%3 == 0){
            Leg car = populationFactory.createLeg(TransportMode.car);
            plan.addLeg(car);
        }else if(i% 3 == 0){
            Leg bike = populationFactory.createLeg(TransportMode.bike);
            plan.addLeg(bike);
        } else if (i%2 == 0 ) {
            Leg walk = populationFactory.createLeg(TransportMode.walk);
            plan.addLeg(walk);
        }else{
            Leg train = populationFactory.createLeg(TransportMode.train);
            plan.addLeg(train);
        }


        Activity work = populationFactory.createActivityFromCoord("work", destinationPoint);
        plan.addActivity(work);
        person.addPlan(plan);
        return person;
    }
}
