import java.util.*;

public class Topology {
    private final String id;
    private final Map<String, ArrayList<Component>> nodes = new HashMap<>();
    private final ArrayList<Component> devices = new ArrayList<>();

    public Topology(String id) {
        this.id = id;
    }

    public void insertDevice(Component component){
        devices.add(component);
        for(String node : component.getNetlist()){
            if(nodes.containsKey(node)){
                nodes.get(node).add(component);
            }
            else{
                ArrayList<Component> arrayList = new ArrayList<>();
                arrayList.add(component);
                nodes.put(node, arrayList);
            }
        }
    }

    public List<Component> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public List<Component> getDevicesAtNode(String nodeID){
        return Collections.unmodifiableList(nodes.get(nodeID));
    }

    @Override
    public String toString() {
        return id;
    }
}
