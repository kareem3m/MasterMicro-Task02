import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TopologyAPITest {
    @Test
    void readJSON() {
        TopologyAPI topologyAPI = new TopologyAPI();
        Assertions.assertTrue(topologyAPI.readJSON("src/test/resources/topology1.json"));
        Assertions.assertTrue(topologyAPI.readJSON("src/test/resources/topology2.json"));
    }

    @Test
    void writeJSON() {
        TopologyAPI topologyAPI = new TopologyAPI();
        readTestFiles(topologyAPI);
        Assertions.assertTrue(topologyAPI.writeJSON("top1"));
        Assertions.assertTrue(topologyAPI.writeJSON("top2"));
    }

    @Test
    void queryTopologies() {
        TopologyAPI topologyAPI = new TopologyAPI();
        readTestFiles(topologyAPI);
        Assertions.assertEquals(2, topologyAPI.queryTopologies().size());
        Assertions.assertTrue(topologyAPI.queryTopologies().toString().contains("top1"));
        Assertions.assertTrue(topologyAPI.queryTopologies().toString().contains("top2"));
    }

    @Test
    void deleteTopology() {
        TopologyAPI topologyAPI = new TopologyAPI();
        readTestFiles(topologyAPI);

        Assertions.assertTrue(topologyAPI.deleteTopology("top1"));
        Assertions.assertEquals(1, topologyAPI.queryTopologies().size());
        Assertions.assertFalse(topologyAPI.queryTopologies().toString().contains("top1"));

        Assertions.assertTrue(topologyAPI.deleteTopology("top2"));
        Assertions.assertEquals(0, topologyAPI.queryTopologies().size());
        Assertions.assertFalse(topologyAPI.queryTopologies().toString().contains("top2"));
    }

    @Test
    void queryDevices() {
        TopologyAPI topologyAPI = new TopologyAPI();
        readTestFiles(topologyAPI);
        Assertions.assertTrue(topologyAPI.queryDevices("top1").toString().contains("res1"));
        Assertions.assertTrue(topologyAPI.queryDevices("top1").toString().contains("m1"));
        Assertions.assertTrue(topologyAPI.queryDevices("top2").toString().contains("res1"));
        Assertions.assertTrue(topologyAPI.queryDevices("top2").toString().contains("res2"));
        Assertions.assertTrue(topologyAPI.queryDevices("top2").toString().contains("m1"));
        Assertions.assertTrue(topologyAPI.queryDevices("top2").toString().contains("m2"));
    }

    @Test
    void queryDevicesWithNetlistNode() {
        TopologyAPI topologyAPI = new TopologyAPI();
        readTestFiles(topologyAPI);
        Assertions.assertEquals(3, topologyAPI.queryDevicesWithNetlistNode("top2", "n1").size());
        Assertions.assertTrue(topologyAPI.queryDevicesWithNetlistNode("top2", "n1").toString().contains("res1"));
        Assertions.assertTrue(topologyAPI.queryDevicesWithNetlistNode("top2", "n1").toString().contains("m1"));
        Assertions.assertTrue(topologyAPI.queryDevicesWithNetlistNode("top2", "n1").toString().contains("m2"));
    }

    void readTestFiles(TopologyAPI topologyAPI) {
        topologyAPI.readJSON("src/test/resources/topology1.json");
        topologyAPI.readJSON("src/test/resources/topology2.json");
    }
}