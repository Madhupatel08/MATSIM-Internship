package org.matsim.delhi;

import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.router.RoutingModule;
import org.matsim.core.router.RoutingRequest;

import javax.inject.Named;
import java.util.List;

public class BicycleRoutingModule implements RoutingModule{

    @Override
    @Named("bicycles")
    public List<? extends PlanElement> calcRoute(RoutingRequest routingRequest) {
        return null;
    }

}

