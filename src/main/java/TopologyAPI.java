import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class TopologyAPI {
    private final Map<String, Topology> topologies = new HashMap<>();

    /**
     * Reads a topology from the given JSON file and stores it in a Map
     * @param fileName path to JSON file
     * @return True if read succeeded
     */
    public boolean readJSON(String fileName) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(fileName)) {
            JSONObject topologyObject = (JSONObject) parser.parse(reader);
            topologies.put((String) topologyObject.get("id"), readTopology(topologyObject));
            return true;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Writes a topology from memory to a JSON file
     * @param topologyID ID of topology to write
     * @return True if write succeeded
     */
    public boolean writeJSON(String topologyID){
        Topology topology = topologies.get(topologyID);
        Gson gson =  new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("./src/output-files/" + topologyID + ".json")) {
            JSONObject topologyObject = new JSONObject();
            topologyObject.put("id", topologyID);

            JSONArray componentsArray = new JSONArray();
            for(Component component : topology.getDevices()){
                componentsArray.add(gson.toJsonTree(component));
            }
            topologyObject.put("components", componentsArray);

            
            writer.write(gson.toJson(topologyObject));
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns all topologies currently stored in memory
     * @return List of Topology Objects
     */
    public List<Topology> queryTopologies() {
        return Collections.unmodifiableList(new ArrayList<>(topologies.values()));
    }

    /**
     * Deletes a topology from memory
     * @param TopologyID ID of topology to delete
     * @return true if topology found and deleted
     */
    public boolean deleteTopology(String TopologyID) {
        return topologies.remove(TopologyID) != null;
    }

    /**
     * Returns all devices in a specified topology
     * @param TopologyID ID of topology
     * @return List of Component Objects
     */
    public List<Component> queryDevices(String TopologyID) {
        return topologies.containsKey(TopologyID) ? topologies.get(TopologyID).getDevices() : null;
    }

    /**
     * Returns all devices connected to a specified node in a specified topology
     * @param TopologyID ID of topology
     * @param NetlistNodeID ID of node
     * @return List of Component Objects
     */
    public List<Component> queryDevicesWithNetlistNode(String TopologyID, String NetlistNodeID) {
        return topologies.containsKey(TopologyID) ? topologies.get(TopologyID).getDevicesAtNode(NetlistNodeID) : null;
    }

    private Topology readTopology(JSONObject topologyObject) {
        Topology topology = new Topology((String) topologyObject.get("id"));
        JSONArray componentsArray = (JSONArray) topologyObject.get("components");
        for (JSONObject componentObject : (Iterable<JSONObject>) componentsArray) {
            topology.insertDevice(readComponent(componentObject));
        }
        return topology;
    }

    private Component readComponent(JSONObject componentObject) {
        Gson gson = new Gson();
        return switch ((String) componentObject.get("type")) {
            case "resistor" -> gson.fromJson(String.valueOf(componentObject), Resistor.class);
            case "nmos" -> gson.fromJson(String.valueOf(componentObject), Nmos.class);
            default -> null;
        };
    }
}
