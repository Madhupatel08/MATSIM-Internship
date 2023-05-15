package org.matsim.delhi;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.config.groups.StrategyConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule;
import org.matsim.core.router.RoutingModule;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.vehicles.VehicleUtils;
import org.matsim.vehicles.VehicleType;


import java.util.Arrays;

import static org.matsim.core.config.groups.QSimConfigGroup.LinkDynamics.PassingQ;
import static org.matsim.core.config.groups.QSimConfigGroup.TrafficDynamics.queue;

public class RunMatsim {

//    public class MyModule extends AbstractModule {
//        @Override
//        protected void configure() {
//            // Bind the bicycleRoutingModule to the bicycle mode
//            bind(RoutingModule.class)
//                    .annotatedWith(Names.named("bicycles"))
//                    .to(BicycleRoutingModule.class);
//        }
//    }
    public static void main(String[] args) {

        Config config = ConfigUtils.createConfig();
        config.controler().setLastIteration(50);
        config.controler().setOverwriteFileSetting( OverwriteFileSetting.deleteDirectoryIfExists );


        config.plansCalcRoute().removeModeRoutingParams(  TransportMode.bike );
        config.plansCalcRoute().removeModeRoutingParams(  TransportMode.walk );

        config.plansCalcRoute().removeModeRoutingParams(  TransportMode.ride );
//        config.plansCalcRoute().removeModeRoutingParams(  TransportMode.motorcycle );

        config.network().setInputFile("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/networkStudyArea.xml");
        config.plans().setInputFile("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/output_plans.xml");
        config.controler().setOutputDirectory("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/output/Delhi");
        config.controler().setOverwriteFileSetting(OverwriteFileSetting.overwriteExistingFiles);
//        config.vehicles().setVehiclesFile();

        config.qsim().setMainModes(Arrays.asList(TransportMode.walk, TransportMode.bike, TransportMode.car, TransportMode.ride, "bicycles"));

        config.qsim().setStartTime(4*60*60);
        config.qsim().setEndTime(20*60*60);

        config.plansCalcRoute().setNetworkModes(Arrays.asList(TransportMode.walk, TransportMode.bike, TransportMode.car, TransportMode.ride, "bicycles"));
        config.qsim().setMainModes(Arrays.asList(TransportMode.walk, TransportMode.bike, TransportMode.car, TransportMode.ride, "bicycles"));

        config.qsim().setVehiclesSource(QSimConfigGroup.VehiclesSource.modeVehicleTypesFromVehiclesData);

        config.qsim().setLinkDynamics(PassingQ);
        config.qsim().setTrafficDynamics(queue);

        config.qsim().setFlowCapFactor(0.01);
        config.qsim().setStorageCapFactor(0.01);

        PlanCalcScoreConfigGroup.ActivityParams home = new PlanCalcScoreConfigGroup.ActivityParams("home");
        home.setTypicalDuration(16 * 60 * 60);
        config.planCalcScore().addActivityParams(home);

        PlanCalcScoreConfigGroup.ActivityParams work = new PlanCalcScoreConfigGroup.ActivityParams("work");
        work.setTypicalDuration(8 * 60 * 60);
        config.planCalcScore().addActivityParams(work);

        // define strategies:
        {
            StrategyConfigGroup.StrategySettings strat = new StrategyConfigGroup.StrategySettings();
            strat.setStrategyName(DefaultPlanStrategiesModule.DefaultStrategy.ReRoute.toString());
            strat.setWeight(0.15);
            config.strategy().addStrategySettings(strat);
        }
        {
            StrategyConfigGroup.StrategySettings strat = new StrategyConfigGroup.StrategySettings();
            strat.setStrategyName(DefaultPlanStrategiesModule.DefaultSelector.ChangeExpBeta.toString());
            strat.setWeight(0.9);
            config.strategy().addStrategySettings(strat);
        }
//
//        config.strategy().setFractionOfIterationsToDisableInnovation(0.9);
//        config.vspExperimental().setWritingOutputEvents(true);
//
//        {
//            String mode = "car";
//            PlanCalcScoreConfigGroup.ModeParams params= new PlanCalcScoreConfigGroup.ModeParams(mode);
//            config.planCalcScore().addModeParams( params );
//        }
//
//        {
//            String mode = "bike";
//            PlanCalcScoreConfigGroup.ModeParams params= new PlanCalcScoreConfigGroup.ModeParams(mode);
//            config.planCalcScore().addModeParams( params );
//        }
//
        {
            String mode = "bicycles";
            PlanCalcScoreConfigGroup.ModeParams params= new PlanCalcScoreConfigGroup.ModeParams(mode);
            config.planCalcScore().addModeParams( params );
        }
//
//        {
//            String mode = "walk";
//            PlanCalcScoreConfigGroup.ModeParams params= new PlanCalcScoreConfigGroup.ModeParams(mode);
//            config.planCalcScore().addModeParams( params );
//        }

        Scenario scenario = ScenarioUtils.loadScenario( config ) ;


        //
//        MyModule myModule = new MyModule();
//        .run(MyModule, myModule);

        VehicleType car = VehicleUtils.getFactory().createVehicleType( Id.create("car", VehicleType.class ) );
        car.setMaximumVelocity(60.0/3.6);
        car.setNetworkMode("car");
        car.setPcuEquivalents(1.0);
        scenario.getVehicles().addVehicleType(car);

        VehicleType bike = VehicleUtils.getFactory().createVehicleType(Id.create("bike", VehicleType.class));
        bike.setMaximumVelocity(60.0/3.6);
        bike.setPcuEquivalents(0.25);
        bike.setNetworkMode("bike");
        scenario.getVehicles().addVehicleType(bike);

        VehicleType rides = VehicleUtils.getFactory().createVehicleType(Id.create("ride", VehicleType.class));
        rides.setMaximumVelocity(15.0);
        rides.setPcuEquivalents(0.05);
        rides.setNetworkMode("ride");
        scenario.getVehicles().addVehicleType(rides);

        VehicleType walks = VehicleUtils.getFactory().createVehicleType(Id.create("walk", VehicleType.class));
        walks.setMaximumVelocity(1.5);
        walks.setPcuEquivalents(0.10);  			// assumed pcu for walks is 0.1
        walks.setNetworkMode("walk");
        scenario.getVehicles().addVehicleType(walks);

        VehicleType bicycles = VehicleUtils.getFactory().createVehicleType(Id.create("bicycles", VehicleType.class));
        bicycles.setMaximumVelocity(1.5);
        bicycles.setPcuEquivalents(0.10);  			// assumed pcu for walks is 0.1
        scenario.getVehicles().addVehicleType(bicycles);
        bicycles.setNetworkMode("bicycles");

        Controler controler = new Controler(scenario);
        controler.run();
    }
}