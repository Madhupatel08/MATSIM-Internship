package org.matsim.madhu;

import com.sun.jdi.connect.Transport;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.ConfigWriter;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.network.algorithms.NetworkCleaner;

import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.config.groups.ChangeModeConfigGroup;
import org.matsim.core.utils.io.OsmNetworkReader;

import java.util.*;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

public class CreateNetworkFromOsmSolution {
    public static void main(String[] args) {

        Config config = ConfigUtils.createConfig();

        //Create Scenarios
        Scenario scenario = ScenarioUtils.createScenario(config);
        // Get a reference to the network object
        Network network = scenario.getNetwork();
        NetworkFactory fac = network.getFactory();

        CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation("WGS84", "EPSG:7760");



        new OsmNetworkReader(network, ct).parse("/home/madhu/Desktop/IIT Roorkee/map.osm");

        //a map of all allowed modes and its utilite
        HashSet<String>allowedModes = new HashSet<>();

        allowedModes.add("walk");
        allowedModes.add("bike");
        allowedModes.add("car");
        allowedModes.add("ride");
        allowedModes.add("bicycles");

        //iterate over every link in the network
        for (Link link : network.getLinks().values()) {
            link.setAllowedModes(allowedModes);
        }

        NetworkCleaner networkCleaner = new NetworkCleaner();
        networkCleaner.run(network);
        NetworkWriter networkWriter = new NetworkWriter(network);
        networkWriter.write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/networkStudyArea.xml");


//        for(Node node1 : network.getNodes().values()) {
//            for (Node node2 : network.getNodes().values()) {
//                boolean linkExists = false;
//                if (node1 != node2) {
//                    for (Link link : network.getLinks().values()) {
//                        if (link.getFromNode().equals(node1) && link.getToNode().equals(node2)) {
//                            linkExists = true;
//                            break;
//                        }
//                    }
//                    if (!linkExists) {
//                        Link link = fac.createLink(Id.createLinkId("link_" + node1.getId() + "_" + node2.getId()), node1, node2);
//                        network.addLink(link);
//                    }
//                }
//            }
//        }

//            Set<String> checkedNodePairs = new HashSet<>();
//            for (Link link : network.getLinks().values()) {
//                checkedNodePairs.add(link.getFromNode().getId().toString() + "_" + link.getToNode().getId().toString());
//            }
//            for (Node node1 : network.getNodes().values()) {
//                for (Node node2 : network.getNodes().values()) {
//                    if (node1 != node2) {
//                        String nodePair1 = node1.getId().toString() + "_" + node2.getId().toString();
//                        String nodePair2 = node2.getId().toString() + "_" + node1.getId().toString();
//                        if (!checkedNodePairs.contains(nodePair1) && !checkedNodePairs.contains(nodePair2)) {
//                            Link link = fac.createLink(Id.createLinkId("link_" + node1.getId() + "_" + node2.getId()), node1, node2);
//                            network.addLink(link);
//                            checkedNodePairs.add(nodePair1);
//                        }
//                    }
//                }
//            }

//        // Get the ID of the node we want
//        Id<Node>nodeId1 = Id.createNodeId("4774765615");
//        Node n1 = network.getNodes().get(nodeId1);
//
//        Id<Node>nodeId2 = Id.createNodeId("6720350047");
//        Node n2 = network.getNodes().get(nodeId2);
//
//        Link l_12 = fac.createLink(Id.createLinkId("paharganj_ramnagar0"), n1, n2);
//        network.addLink(l_12);
//
//        l_12.setCapacity(500);
//        l_12.setFreespeed(13.33);
//
//        // Get the ID of the node we want
//        Id<Node>nodeId3 = Id.createNodeId("10274798170");
//        Node n3 = network.getNodes().get(nodeId3);
//
//        Id<Node>nodeId4 = Id.createNodeId("9798037577");
//        Node n4 = network.getNodes().get(nodeId4);
//
//        Link l_34 = fac.createLink(Id.createLinkId("paharganj_ramnagar1"), n3, n4);
//        network.addLink(l_34);
//
//        l_34.setCapacity(500);
//        l_34.setFreespeed(13.33);
//
//
//        // Get the ID of the node we want
//        Id<Node>nodeId5 = Id.createNodeId("9873677637");
//        Node n5 = network.getNodes().get(nodeId5);
//
//        Id<Node>nodeId6 = Id.createNodeId("9798037577");
//        Node n6 = network.getNodes().get(nodeId6);
//
//        Link l_56 = fac.createLink(Id.createLinkId("paharganj_ramnagar10"), n5, n6);
//        network.addLink(l_56);
//
//        l_56.setCapacity(500);
//        l_56.setFreespeed(13.33);
//
//
        NetworkWriter networkWriter2 = new NetworkWriter(network);

        networkWriter2.write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/networkStudyArea.xml");

    }
}
