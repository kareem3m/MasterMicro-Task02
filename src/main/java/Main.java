import java.io.File;
import java.io.IOException;

public class Main {
    static final TopologyAPI topologyApi = new TopologyAPI();

    public static void main(String[] args) throws IOException {
        File[] directoryListing = (new File("src/main/resources")).listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                topologyApi.readJSON(file.getAbsolutePath());
            }
            System.out.println("Topologies: " + topologyApi.queryTopologies().toString());
        } else {
            System.out.println("Failed to open Directory");
            throw new IOException();
        }
    }
}
