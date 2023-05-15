package org.matsim.madhu;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.ConfigWriter;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.StrategyConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule;
import org.matsim.core.scenario.ScenarioUtils;

public class CreateRunMatsim {
    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();

        config.network().setInputFile("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/networkAssign.xml");
        config.plans().setInputFile("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/populationAssign.xml");
        config.controler().setOutputDirectory("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/output/Test");
        config.controler().setLastIteration(10);
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);

//        config.qsim().setStartTime(5*10*10);
//        config.qsim().setEndTime(11*60*60);

        PlanCalcScoreConfigGroup.ActivityParams home = new PlanCalcScoreConfigGroup.ActivityParams("home");
        home.setTypicalDuration(16*60*60);

        PlanCalcScoreConfigGroup.ActivityParams work = new PlanCalcScoreConfigGroup.ActivityParams("work");
        work.setTypicalDuration(8*60*60);
        config.planCalcScore().addActivityParams(home);
        config.planCalcScore().addActivityParams(work);

        StrategyConfigGroup.StrategySettings strat = new StrategyConfigGroup.StrategySettings();
        strat.setStrategyName(DefaultPlanStrategiesModule.DefaultStrategy.ReRoute.toString());
        strat.setWeight(0.15);
        config.strategy().addStrategySettings(strat);

        StrategyConfigGroup.StrategySettings stratBestScore = new StrategyConfigGroup.StrategySettings();
        stratBestScore.setStrategyName(DefaultPlanStrategiesModule.DefaultSelector.BestScore.toString());
        stratBestScore.setWeight(0.9);

        config.strategy().addStrategySettings(stratBestScore);

        config.vspExperimental().setWritingOutputEvents(true);

        config.strategy().addParam("maxAgentPlanMemorySize", "5");

        Scenario scenario = ScenarioUtils.loadScenario(config);
        //new ConfigWriter(config).write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/config.xml");
        Controler controler = new Controler(scenario);
        controler.run();
    }
}
