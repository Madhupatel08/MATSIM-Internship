package org.matsim.madhu;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordUtils;

public class NetworkTest {
    public static void main(String[] args){
        Config config = ConfigUtils.createConfig();
        Scenario scenario = ScenarioUtils.loadScenario(config);
        Network network = NetworkUtils.createNetwork();

        NetworkFactory networkFactory = network.getFactory();

        Coord from = CoordUtils.createCoord(0, 0);
        Coord to = CoordUtils.createCoord(1000, 0);

        Node n1 = networkFactory.createNode(Id.createNodeId("1"), from);
        Node n2 = networkFactory.createNode(Id.createNodeId("2"), to);

        network.addNode(n1);
        network.addNode(n2);

        Link linkForward = networkFactory.createLink(Id.createLinkId("link1"), n1, n2);
        Link linkReverse = networkFactory.createLink(Id.createLinkId("link2"), n2, n1);

        network.addLink(linkForward);
        network.addLink(linkReverse);


        linkForward.setLength(1000);
        linkReverse.setLength(1000);
        linkForward.setCapacity(3600);
        linkReverse.setCapacity(3600);
        linkForward.setNumberOfLanes(1);
        linkReverse.setNumberOfLanes(1);

        NetworkWriter networkWriter = new NetworkWriter(network);
        networkWriter.write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/network.xml");

    }
}
