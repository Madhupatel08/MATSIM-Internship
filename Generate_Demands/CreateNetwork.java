package org.matsim.madhu;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;

public class CreateNetwork {

    // define capacity of the links in suburban and urban area
    private static final long CAP_Suburban = 500; // [veh/h]
    private static final long CAP_Urban = 250; // [veh/h]

    // define freespeed for links in suburban and urban area
    private static final double LINK_SPEED_Urban = 13.88;
    private static final double LINK_SPEED_Suburban = 22.22; // [m/s]


    public static void main(String[] args) {

        Config config = ConfigUtils.createConfig();
        Scenario scenario = ScenarioUtils.createScenario(config);
        Network net = scenario.getNetwork();
        NetworkFactory fac = net.getFactory();

        // create nodes
        Node n0 = fac.createNode(Id.createNodeId(0), new Coord(0, 0));
        net.addNode(n0);
        Node n1 = fac.createNode(Id.createNodeId(1), new Coord(1000, 0));
        net.addNode(n1);
        Node n2 = fac.createNode(Id.createNodeId(2), new Coord(2000, 0));
        net.addNode(n2);
        Node n3 = fac.createNode(Id.createNodeId(3), new Coord(3000, 1000));
        net.addNode(n3);
        Node n4 = fac.createNode(Id.createNodeId(4), new Coord(3000, -1000));
        net.addNode(n4);
        Node n5 = fac.createNode(Id.createNodeId(5), new Coord(4000, 0));
        net.addNode(n5);
        Node n6 = fac.createNode(Id.createNodeId(6), new Coord(5000, 0));
        net.addNode(n6);
        Node n7 = fac.createNode(Id.createNodeId(7), new Coord(6000, 0));
        net.addNode(n7);

        //
        //
        //add missing nodes here...
        //
        //

        // create links

        //link 0 <-> 1
        Link l_01 = fac.createLink(Id.createLinkId("0_1"), n0, n1);
        net.addLink(l_01);
        Link l_10 = fac.createLink(Id.createLinkId("1_0"), n1, n0);
        net.addLink(l_10);

        //link 1 <-> 2
        Link l_12 = fac.createLink(Id.createLinkId("1_2"), n1, n2);
        net.addLink(l_12);
        Link l_21 = fac.createLink(Id.createLinkId("2_1"), n2, n1);
        net.addLink(l_21);

        //link 2 <-> 3
        Link l_23 = fac.createLink(Id.createLinkId("2_3"), n2, n3);
        net.addLink(l_23);
        Link l_32 = fac.createLink(Id.createLinkId("3_2"), n3, n2);
        net.addLink(l_32);

        //link 2 <-> 4
        Link l_24 = fac.createLink(Id.createLinkId("2_4"), n2, n4);
        net.addLink(l_24);
        Link l_42 = fac.createLink(Id.createLinkId("4_2"), n4, n2);
        net.addLink(l_42);

        //link 3 <-> 5
        Link l_35 = fac.createLink(Id.createLinkId("3_5"), n3, n5);
        net.addLink(l_35);
        Link l_53 = fac.createLink(Id.createLinkId("5_3"), n5, n3);
        net.addLink(l_53);

        //link 4 <-> 5
        Link l_45 = fac.createLink(Id.createLinkId("4_5"), n4, n5);
        net.addLink(l_45);
        Link l_54 = fac.createLink(Id.createLinkId("5_4"), n5, n4);
        net.addLink(l_54);

        //link 5 <-> 6
        Link l_56 = fac.createLink(Id.createLinkId("5_6"), n5, n6);
        net.addLink(l_56);
        Link l_65 = fac.createLink(Id.createLinkId("6_5"), n6, n5);
        net.addLink(l_65);

        //link 6 <-> 7
        Link l_67 = fac.createLink(Id.createLinkId("6_7"), n6, n7);
        net.addLink(l_67);
        Link l_76 = fac.createLink(Id.createLinkId("7_6"), n7, n6);
        net.addLink(l_76);
        //add missing links here...
        //
        //

        //
        //
        // set link attributes
        // SubUrban Roads : capacity: 500[veh/hr], FreeSpeed: 22.22[m/s]
        setLinkAttributes(l_01, CAP_Suburban, LINK_SPEED_Suburban);
        setLinkAttributes(l_10, CAP_Suburban, LINK_SPEED_Suburban);

        setLinkAttributes(l_12, CAP_Suburban, LINK_SPEED_Suburban);
        setLinkAttributes(l_21, CAP_Suburban, LINK_SPEED_Suburban);

        setLinkAttributes(l_56, CAP_Suburban, LINK_SPEED_Suburban);
        setLinkAttributes(l_65, CAP_Suburban, LINK_SPEED_Suburban);

        setLinkAttributes(l_67, CAP_Suburban, LINK_SPEED_Suburban);
        setLinkAttributes(l_76, CAP_Suburban, LINK_SPEED_Suburban);

        //Urban Roads : capacity: 250[veh/hr], FreeSpeed: 13.88[m/s]
        setLinkAttributes(l_23, CAP_Urban, LINK_SPEED_Urban);
        setLinkAttributes(l_32, CAP_Urban, LINK_SPEED_Urban);

        setLinkAttributes(l_35,CAP_Urban, LINK_SPEED_Urban);
        setLinkAttributes(l_53, CAP_Urban, LINK_SPEED_Urban);

        setLinkAttributes(l_24, CAP_Urban, LINK_SPEED_Urban);
        setLinkAttributes(l_42, CAP_Urban, LINK_SPEED_Urban);

        setLinkAttributes(l_45, CAP_Urban, LINK_SPEED_Urban);
        setLinkAttributes(l_54, CAP_Urban, LINK_SPEED_Urban);

        //
        //


        // write network
        NetworkWriter networkWriter = new NetworkWriter(net);
        networkWriter.write("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/madhu/networkAssign.xml");
    }

    private static void setLinkAttributes(Link link, double capacity, double freespeed) {
        link.setCapacity(capacity);
        link.setFreespeed(freespeed);
    }
}
